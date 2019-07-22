package com.comtrade.sysoperation.room;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveRoomSO extends GeneralSystemOperation<PropertyWrapper> {

	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		Map.Entry<RoomType,RoomInfo> temp = wrapper.getRooms().entrySet().iterator().next();
		RoomType type = temp.getKey();
		RoomInfo info = temp.getValue();
		
		IBroker iBroker = new Broker();
		type = (RoomType) iBroker.save(type);
		info.setIdRoomType(type.getIdRoomType());
		info = (RoomInfo) iBroker.save(info);
		
		Map<RoomType, RoomInfo> room = new HashMap<>();
		room.put(type, info);
		wrapper.setRooms(room);
	}

}
