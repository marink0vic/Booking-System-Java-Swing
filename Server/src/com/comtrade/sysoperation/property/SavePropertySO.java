package com.comtrade.sysoperation.property;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.Location;
import com.comtrade.domain.PaymentProperty;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.util.ImageProcessing;

public class SavePropertySO extends GeneralSystemOperation<PropertyWrapper> {
	
	private IBroker ib = new Broker();

	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		User user = wrapper.getUser();
		
		int idLocation = saveAddress(wrapper.getAddress());
		wrapper.getAddress().setIdLocation(idLocation);

		Property property = wrapper.getProperty();
		property.setIdLocation(idLocation);
		int idProperty = saveProperty(property);
		property.setIdProperty(idProperty);
		property.setCreated(LocalDateTime.now());
		
		Map<RoomType, Room> room = wrapper.getRooms();
		room = saveAllRooms(room, property.getIdProperty());
		wrapper.setRooms(room);
		
		List<PropertyImage> imageFiles = wrapper.getImages();
		imageFiles = ImageProcessing.formatServerPath(imageFiles, property.getIdProperty(), user.getUsername());
		ib.saveCollectionOfData(imageFiles);
		wrapper.setImages(ib.returnPropertyImages(idProperty));
		
		List<PaymentType> payments = wrapper.getPaymentList();
		savePropertyPayments(payments, property.getIdProperty());
		
		ServerData.getInstance().addNewProperty(wrapper);
	}

	private void savePropertyPayments(List<PaymentType> payments, int id_property) throws SQLException {
		List<PaymentProperty> paymentProperty = new ArrayList<>();
		for (PaymentType type : payments) {
			PaymentProperty pp = new PaymentProperty(type.getId_payment(), id_property);
			paymentProperty.add(pp);
		}
		ib.saveCollectionOfData(paymentProperty);
	}

	private Map<RoomType, Room> saveAllRooms(Map<RoomType, Room> room, int id_property) throws SQLException {
		Map<RoomType, Room> map = new LinkedHashMap<>();
		for (Map.Entry<RoomType, Room> mapRoom : room.entrySet()) {
			RoomType roomType = mapRoom.getKey();
			roomType.setIdProperty(id_property);
			int idRoomType = saveRoomType(roomType);
			roomType.setIdRoomType(idRoomType);
			
			Room info = mapRoom.getValue();
			info.setIdRoomType(idRoomType);
			int idRoom = saveRoomInfo(info);
			info.setIdRoom(idRoom);
			map.put(roomType, info);
		}
		return map;
	}

	private int saveRoomInfo(Room info) throws SQLException {
		return ib.save(info);
	}

	private int saveRoomType(RoomType r) throws SQLException {
		return ib.save(r);
	}

	private int saveProperty(Property property) throws SQLException {
		return ib.save(property);
	}

	private int saveAddress(Location address) throws SQLException {
		return ib.save(address);
	}

}
