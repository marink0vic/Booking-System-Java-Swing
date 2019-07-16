package com.comtrade.sysoperation.property;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.Address;
import com.comtrade.domain.PaymentProperty;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SavePropertySO extends GeneralSystemOperation<PropertyWrapper> {
	
	private IBroker ib = new Broker();

	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		User user = wrapper.getUser();
		Address address = saveAddress(wrapper.getAddress());

		Property property = wrapper.getProperty();
		property.setIdAddress(address.getIdAddress());
		property = saveProperty(property);
		
		Map<RoomType, RoomInfo> room = wrapper.getRoom();
		saveAllRooms(room, property.getIdProperty());
		
		List<PropertyImage> imageFiles = wrapper.getImages();
		saveAllImages(imageFiles, property.getIdProperty(), user.getUsername());
		
		List<PaymentType> payments = wrapper.getPaymentList();
		savePropertyPayments(payments, property.getIdProperty());
	}

	private void savePropertyPayments(List<PaymentType> payments, int id_property) throws SQLException {
		List<PaymentProperty> paymentProperty = new ArrayList<>();
		for (PaymentType type : payments) {
			PaymentProperty pp = new PaymentProperty(type.getId_payment(), id_property);
			paymentProperty.add(pp);
		}
		ib.saveCollectionOfData(paymentProperty);
	}

	private void saveAllImages(List<PropertyImage> image_files, int id_property, String username) throws SQLException {
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
		
		saveImagesToDataBase(propertyImages, id_property);
	}

	private void saveImagesToDataBase(List<PropertyImage> property_images, int id_property) throws SQLException {
		ib.saveCollectionOfData(property_images);
	}

	private void saveAllRooms(Map<RoomType, RoomInfo> room, int id_property) throws SQLException {
		for (Map.Entry<RoomType, RoomInfo> mapRoom : room.entrySet()) {
			RoomType roomType = mapRoom.getKey();
			roomType.setIdProperty(id_property);
			roomType.setIdRoomType(saveRoomType(roomType));
			
			RoomInfo info = mapRoom.getValue();
			info.setIdRoomType(roomType.getIdRoomType());
			saveRoomInfo(info);
		}
	}

	private void saveRoomInfo(RoomInfo info) throws SQLException {
		ib.save(info);
	}

	private int saveRoomType(RoomType r) throws SQLException {
		RoomType roomType = (RoomType) ib.save(r);
		return roomType.getIdRoomType();
	}

	private Property saveProperty(Property property) throws SQLException {
		return (Property) ib.save(property);
	}

	private Address saveAddress(Address address) throws SQLException {
		return (Address) ib.save(address);
	}

}
