package com.comtrade.sysoperation.property;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.Address;
import com.comtrade.domain.Property;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SavePropertySO extends GeneralSystemOperation<PropertyWrapper> {
	
	private IBroker ib = new Broker();

	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		Address address = saveAddress(wrapper.getAddress());
		wrapper.setAddress(address);

		Property property = wrapper.getProperty();
		property.setIdAddress(address.getIdAddress());
		property = saveProperty(property);
		wrapper.setProperty(property);
		
		Map<RoomType, RoomInfo> room = wrapper.getRoom();
		wrapper.setRoom(saveAllRooms(room, property.getIdProperty()));
	}

	private Map<RoomType, RoomInfo> saveAllRooms(Map<RoomType, RoomInfo> room, int idProperty) throws SQLException {
		Map<RoomType, RoomInfo> savedRoom = new LinkedHashMap<>();
		for (Map.Entry<RoomType, RoomInfo> mapRoom : room.entrySet()) {
			RoomInfo info = mapRoom.getValue();
			info.setIdRoom(saveRoomInfo(info));
			
			RoomType roomType = mapRoom.getKey();
			roomType.setIdProperty(idProperty);
			roomType.setIdRoomInfo(info.getIdRoom());
			roomType.setIdRoomType(saveRoomType(roomType));
			
			savedRoom.put(roomType, info);
		}
		return savedRoom;
	}

	private int saveRoomInfo(RoomInfo info) throws SQLException {
		ib.save(info);
		RoomInfo temp = (RoomInfo) ib.returnLastInsertedData(info);
		return temp.getIdRoom();
	}

	private int saveRoomType(RoomType r) throws SQLException {
		ib.save(r);
		RoomType temp = (RoomType) ib.returnLastInsertedData(r);
		return temp.getIdRoomType();
	}

	private Property saveProperty(Property property) throws SQLException {
		ib.save(property);
		return (Property) ib.returnLastInsertedData(property);
	}

	private Address saveAddress(Address address) throws SQLException {
		ib.save(address);
		return (Address) ib.returnLastInsertedData(address);
	}

}
