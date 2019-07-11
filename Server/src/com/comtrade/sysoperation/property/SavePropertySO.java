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
		
		Map<RoomType, List<RoomInfo>> rooms = wrapper.getRooms();
		wrapper.setRooms(saveAllRooms(rooms, property.getIdProperty()));
	}

	private Map<RoomType, List<RoomInfo>> saveAllRooms(Map<RoomType, List<RoomInfo>> rooms, int idProperty) throws SQLException {
		Map<RoomType, List<RoomInfo>> savedRooms = new LinkedHashMap<>();
		for (Map.Entry<RoomType, List<RoomInfo>> mapRooms : rooms.entrySet()) {
			RoomType roomType = mapRooms.getKey();
			roomType.setIdProperty(idProperty);
			roomType.setIdRoomType(saveRoomType(roomType));
			
			List<RoomInfo> roomList = mapRooms.getValue();
			roomList = saveRooms(roomList, roomType.getIdRoomType());
			savedRooms.put(roomType, roomList);
		}
		return savedRooms;
	}

	private List<RoomInfo> saveRooms(List<RoomInfo> roomList, int idRoomType) throws SQLException {
//		for (RoomInfo room : roomList) {
//			room.setIdRoomType(idRoomType);
//		}
//		ib.saveCollectionOfData(roomList);
		return null;
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
