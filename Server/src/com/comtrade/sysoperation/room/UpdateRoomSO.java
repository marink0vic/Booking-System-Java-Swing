package com.comtrade.sysoperation.room;

import java.sql.SQLException;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class UpdateRoomSO extends GeneralSystemOperation<PropertyWrapper> {

	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		Map.Entry<RoomType,RoomInfo> temp = wrapper.getRoom().entrySet().iterator().next();
		IBroker iBroker = new Broker();
		iBroker.update(temp.getKey());
		iBroker.update(temp.getValue());
	}

}
