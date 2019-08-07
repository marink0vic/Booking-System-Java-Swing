package com.comtrade.sysoperation.property;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.Location;
import com.comtrade.domain.PaymentProperty;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericMap;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

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
		imageFiles = saveAllImages(imageFiles, property.getIdProperty(), user.getUsername());
		wrapper.setImages(imageFiles);
		
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

	private List<PropertyImage> saveAllImages(List<PropertyImage> image_files, int id_property, String username) throws SQLException {
		String pathToSave = ImageFolder.IMAGE_HOST_USER_FOLDER.getPath() + username + "_" + id_property;
		File folderToSave = new File(pathToSave);
		
		if (!folderToSave.exists()) {
			folderToSave.mkdir();
		}
		List<PropertyImage> propertyImages = new ArrayList<>();
		for (PropertyImage propertyImage : image_files) {
			File file = new File(propertyImage.getImage());
			File pathForDatabase = new File(pathToSave + "/" + file.getName());
			String image = pathForDatabase.getPath();
			propertyImages.add(new PropertyImage(id_property, image.substring(1)));
			try {
				java.nio.file.Files.copy(file.toPath(), pathForDatabase.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		saveImagesToDataBase(propertyImages);
		return ib.returnPropertyImages(id_property);
	}

	private void saveImagesToDataBase(List<PropertyImage> property_images) throws SQLException {
		ib.saveCollectionOfData(property_images);
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
