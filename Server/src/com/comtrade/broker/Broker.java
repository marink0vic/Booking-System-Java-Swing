package com.comtrade.broker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.connection.Connection;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.Address;
import com.comtrade.domain.Country;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Position;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.RoomInfo;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.lock.DbLock;

public class Broker implements IBroker {
	
	private DbLock dbLock = DbLock.getInstance();

	@Override
	public GeneralDomain save(GeneralDomain domain) throws SQLException {
		String sql = "INSERT INTO " + domain.returnTableName() + "" + domain.returnColumnNames() + "" + domain.returnStatementPlaceholder();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		Position index = new Position();
		domain.fillStatementWithValues(preparedStatement, index);
		dbLock.lock();
		try {
			preparedStatement.executeUpdate();
			return returnLastInsertedData(domain);
		} finally {
			dbLock.unlock();
		}
	}
	
	@Override
	public void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException {
		if (list.size() > 0) {
			GeneralDomain domain = list.get(0);
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO ").append(domain.returnTableName()).append(domain.returnColumnNames());
			for (int i = 0; i < list.size(); i++) {
				sb.append(domain.returnStatementPlaceholder()).append(",");
			}
			
			String sql = sb.substring(0, sb.length() - 1);
			Position index = new Position();
			PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
			
			for (int i = 0; i < list.size(); i++) {
				list.get(i).fillStatementWithValues(preparedStatement, index);
			}
			preparedStatement.executeUpdate();
		}
	}
	
	@Override
	public List<GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException {
		String sql = "SELECT * FROM " + domain.returnTableName();
		List<GeneralDomain> list = new ArrayList<>();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		list = domain.returnList(resultSet);
		return list;
	}
	
	@Override
	public User login(User user) throws SQLException {
		String sql = "SELECT * FROM user JOIN country ON country.id_country = user.country_id WHERE username = ?";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		preparedStatement.setString(1, user.getUsername());
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			String hash = resultSet.getString("password");
			if (user.checkPassword(user.getPassword(), hash)) {
				
				int userId = resultSet.getInt("id_user");
				int countryId = resultSet.getInt("country_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				String username = resultSet.getString("username");
				String profilePicture = resultSet.getString("profile_picture");
				String status = resultSet.getString("status");
				LocalDate date = resultSet.getDate("date_of_birth").toLocalDate();
				
				String countryName = resultSet.getString("name");
				String fullPath = ImageFolder.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
				
				User u = new User(new Country(countryId, countryName, fullPath), firstName, lastName, email, username, null, date, status);
				u.setProfilePicture(profilePicture);
				u.setIdUser(userId);
				return u;
			}
		}
		return null;
	}


	@Override
	public PropertyWrapper returnPropertyForOwner(PropertyWrapper owner) throws SQLException {
		setPropertyAddress(owner);
		setRoomAndRoomInfo(owner);
		setPropertyImages(owner);
		setPayments(owner, owner.getProperty().getIdProperty());
		setCountry(owner, owner.getAddress().getIdCountry());
		return owner;
	}	

	private void setPropertyAddress(PropertyWrapper owner) throws SQLException {
		String sql = "SELECT * FROM property JOIN address "
				+ "ON property.id_address = address.id_address "
				+ "WHERE property.id_user = " + owner.getUser().getIdUser();
		
		PreparedStatement statement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			int idProperty = resultSet.getInt("id_property");
			int idAddress = resultSet.getInt("id_address");
			String type = resultSet.getString("type");
			String name = resultSet.getString("name");
			String phone = resultSet.getString("phone_number");
			int rating = resultSet.getInt("rating");
			double latitude = resultSet.getDouble("latitude");
			double longitude = resultSet.getDouble("longitude");
			String description = resultSet.getString("description");
	
			Property property = new Property(owner.getUser().getIdUser(), idAddress, type, name, phone, rating, description);
			property.setIdProperty(idProperty);
			property.setLatitude(latitude);
			property.setLongitude(longitude);
			
			int idCountry = resultSet.getInt("id_country");
			String street = resultSet.getString("street");
			int number = resultSet.getInt("number");
			String city = resultSet.getString("city");
			int zip = resultSet.getInt("post_or_zipcode");
			Address address = new Address(idCountry, street, number, city, zip);
			address.setIdAddress(idAddress);
			
			owner.setAddress(address);
			owner.setProperty(property);
		}
		
	}

	private void setRoomAndRoomInfo(PropertyWrapper owner) throws SQLException {
		String sql = "SELECT * FROM room_type JOIN room_info ON room_type.id_room_type = room_info.id_room_type"
					+ " WHERE room_type.id_property = " + owner.getProperty().getIdProperty();
		
		PreparedStatement statement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		Map<RoomType, RoomInfo> room = new LinkedHashMap<>();
		while (resultSet.next()) {
			int idRoomType = resultSet.getInt("id_room_type");
			int idProperty = owner.getProperty().getIdProperty();
			String type = resultSet.getString("type");
			int numOfRooms = resultSet.getInt("num_of_rooms");	
			double pricePerNight = resultSet.getDouble("price_per_night");
			RoomType rType = new RoomType(type, numOfRooms, pricePerNight);
			rType.setIdRoomType(idRoomType);
			rType.setIdProperty(idProperty);
			
			int idRoomInfo = resultSet.getInt("id_room");
			int numOfBads = resultSet.getInt("num_of_bads");
			boolean kitchen = resultSet.getBoolean("kitchen");
			boolean tv = resultSet.getBoolean("tv");
			boolean airConditioning = resultSet.getBoolean("air_conditioning");
			boolean wifi = resultSet.getBoolean("wifi");
			RoomInfo rInfo = new RoomInfo(numOfBads, kitchen, tv, airConditioning, wifi);
			rInfo.setIdRoom(idRoomInfo);
			rInfo.setIdRoomType(idRoomType);
			
			room.put(rType, rInfo);
		}
		owner.setRoom(room);
	}
	
	private void setPropertyImages(PropertyWrapper owner) throws SQLException {
		
		String sql = "SELECT * FROM property_images WHERE id_property = " + owner.getProperty().getIdProperty();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<PropertyImage> propertyImages = new ArrayList<>();
		
		while (resultSet.next()) {
			int idImage = resultSet.getInt("id_image");
			String fullPath = ImageFolder.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
			PropertyImage propertyImage = new PropertyImage();
			propertyImage.setIdImage(idImage);
			propertyImage.setIdProperty(owner.getProperty().getIdProperty());
			propertyImage.setImage(fullPath);
			propertyImages.add(propertyImage);
		}
		
		owner.setImages(propertyImages);
	}
	
	private void setPayments(PropertyWrapper owner, int idProperty) throws SQLException {
		String sql = "SELECT * FROM payment_type" + " "
				+ "JOIN payment_property ON payment_property.id_payment = payment_type.id_card_type" + " "
				+ "JOIN property ON property.id_property = payment_property.id_property" + " "
				+ "WHERE property.id_property = " + idProperty; 
		
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<PaymentType> payments = new ArrayList<>();
		
		while (resultSet.next()) {
			int idCardType = resultSet.getInt("id_card_type");
			String name = resultSet.getString("name");
			String fullPath = ImageFolder.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
			PaymentType paymentType = new PaymentType();
			paymentType.setId_payment(idCardType);
			paymentType.setName(name);
			paymentType.setImage(fullPath);
			
			payments.add(paymentType);
		}
		
		owner.setPaymentList(payments);
	}
	
	private void setCountry(PropertyWrapper owner, int id_country) throws SQLException {
		String sql = "SELECT * FROM country WHERE id_country = " + id_country;
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int idCountry = resultSet.getInt("id_country");
			String fullPath = ImageFolder.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
			String name = resultSet.getString("name");
			Country country = new Country();
			country.setIdCountry(idCountry);
			country.setImage(fullPath);
			country.setName(name);
			owner.setCountry(country);
		}
	}
	
	public GeneralDomain returnLastInsertedData(GeneralDomain domain) throws SQLException {
		String sql = "SELECT * FROM " + domain.returnTableName() + " ORDER BY " + domain.returnIdColumnName()+" DESC LIMIT 1";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		GeneralDomain newDomain = domain.returnLastInsertedObject(resultSet);
		return newDomain;
	}
}
