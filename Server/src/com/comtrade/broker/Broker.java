package com.comtrade.broker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.connection.Connection;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.Location;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Country;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Position;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.domain.behavior.DomainJoin;
import com.comtrade.domain.behavior.DomainJoinBookings;
import com.comtrade.domain.behavior.DomainJoinReview;
import com.comtrade.domain.behavior.DomainUpdate;
import com.comtrade.domain.behavior.GeneralDomain;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.lock.DbLock;

public class Broker implements IBroker {
	
	private DbLock dbLock = DbLock.getInstance();

	@Override
	public GeneralDomain save(GeneralDomain domain) throws SQLException {
		String sql = "INSERT INTO " + domain.returnTableName() + "" + domain.returnColumnNames() + "" + domain.returnStatementPlaceholder();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		Position index = new Position();
		domain.preparedStatementInsert(preparedStatement, index);
		dbLock.lock();
		try {
			preparedStatement.executeUpdate();
			return returnLastInsertedData(domain);
		} finally {
			dbLock.unlock();
		}
	}
	
	@Override
	public void update(DomainUpdate domain) throws SQLException {
		String sql = "UPDATE " + domain.returnTableName() + " SET " + domain.returnColumnsForUpdate() + " WHERE " + domain.returnIdColumnName() + " = ?";
		Position index = new Position();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		domain.preparedStatementUpdate(preparedStatement, index);
		preparedStatement.executeUpdate();
	}
	
	@Override
	public void updateCollectionOfData(List<? extends DomainUpdate> list) throws SQLException {
		DomainUpdate domain = list.get(0);
		String sql = "UPDATE " + domain.returnTableName() + " SET " + domain.returnColumnsForUpdate() + " WHERE " + domain.returnIdColumnName() + " = ?";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		Position index = new Position();
		for (DomainUpdate d : list) {
			d.preparedStatementUpdate(preparedStatement, index);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}

	@Override
	public void delete(GeneralDomain domain) throws SQLException {
		String sql = "DELETE FROM " + domain.returnTableName() + " WHERE " + domain.returnIdColumnName() + " = ?";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		preparedStatement.setInt(1, domain.returnIdNumber());
		preparedStatement.executeUpdate();
	}
	
	@Override
	public void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException {
		if (list.size() == 0) return;
		
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
			list.get(i).preparedStatementInsert(preparedStatement, index);
		}
		preparedStatement.executeUpdate();
	}
	
	@Override
	public List<? extends GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException {
		String sql = "SELECT * FROM " + domain.returnTableName();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		return domain.returnList(resultSet);
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
				User u = user.createUser(resultSet);
				return u;
			}
		}
		return null;
	}


	@Override
	public Map<Booking, List<BookedRoom>> insertBookings(DomainJoinBookings domain_join, int id_domain) throws SQLException {
		ResultSet rs = prepareResultSet(domain_join, id_domain);
		return domain_join.returnJoinTables(rs);
	}

	@Override
	public List<PropertyReview> returnPropertyReviews(DomainJoinReview domain_join, int id_domain) throws SQLException {
		ResultSet rs = prepareResultSet(domain_join, id_domain);
		return domain_join.returnJoinTables(rs);
	}
	
	private ResultSet prepareResultSet(DomainJoin domain, int id_domain) throws SQLException {
		String sql = domain.prepareJoin();
		PreparedStatement ps = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ps.setInt(1, id_domain);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	@Override
	public void insertPropertyForOwner(PropertyWrapper wrapper) throws SQLException {
		setPropertyAndAddress(wrapper);
		if (wrapper.getProperty() == null) return;
		int idProperty = wrapper.getProperty().getIdProperty();
		wrapper.setRooms(returnRoomAndRoomInfo(idProperty));
		wrapper.setImages(returnPropertyImages(idProperty));
		wrapper.setPaymentList(returnPayments(idProperty));
		wrapper.setCountry(returnCountry(wrapper.getAddress().getIdCountry()));
		wrapper.setBookings(insertBookings(new Property(), wrapper.getProperty().getIdProperty()));
		wrapper.setReviews(returnPropertyReviews(new PropertyReview(), idProperty));
	}	


	private void setPropertyAndAddress(PropertyWrapper wrapper) throws SQLException {
		String sql = "SELECT * FROM property JOIN location "
				+ "ON property.id_location = location.id_location "
				+ "WHERE property.id_user = " + wrapper.getUser().getIdUser();
		
		PreparedStatement statement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		
		if (resultSet.next()) {
			int idProperty = resultSet.getInt("id_property");
			int idLocation = resultSet.getInt("id_location");
			String type = resultSet.getString("type");
			String name = resultSet.getString("name");
			String phone = resultSet.getString("phone_number");
			int rating = resultSet.getInt("rating");
			String description = resultSet.getString("description");
			String created = resultSet.getString("created");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(created, formatter);
	
			Property property = new Property(wrapper.getUser().getIdUser(), idLocation, type, name, phone, rating, description);
			property.setIdProperty(idProperty);
			property.setCreated(dateTime);
			
			int idCountry = resultSet.getInt("id_country");
			String street = resultSet.getString("street");
			String number = resultSet.getString("number");
			String city = resultSet.getString("city");
			int zip = resultSet.getInt("post_or_zipcode");
			double latitude = resultSet.getDouble("latitude");
			double longitude = resultSet.getDouble("longitude");
			Location location = new Location(idCountry, street, number, city, zip);
			location.setIdLocation(idLocation);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			
			wrapper.setAddress(location);
			wrapper.setProperty(property);
		}
		
	}

	private Map<RoomType, Room> returnRoomAndRoomInfo(int id_property) throws SQLException {
		String sql = "SELECT * FROM room_type JOIN room ON room_type.id_room_type = room.id_room_type"
					+ " WHERE room_type.id_property = " + id_property;
		
		PreparedStatement statement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();
		Map<RoomType, Room> room = new LinkedHashMap<>();
		while (resultSet.next()) {
			int idRoomType = resultSet.getInt("id_room_type");
			int idProperty = id_property;
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
			Room rInfo = new Room(numOfBads, kitchen, tv, airConditioning, wifi);
			rInfo.setIdRoom(idRoomInfo);
			rInfo.setIdRoomType(idRoomType);
			
			room.put(rType, rInfo);
		}
		return room;
	}
	
	@Override
	public List<PropertyImage> returnPropertyImages(int id_property) throws SQLException {
		
		String sql = "SELECT * FROM property_images WHERE id_property = " + id_property;
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<PropertyImage> propertyImages = new ArrayList<>();
		
		while (resultSet.next()) {
			int idImage = resultSet.getInt("id_image");
			String fullPath = ImageFolder.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
			PropertyImage propertyImage = new PropertyImage();
			propertyImage.setIdImage(idImage);
			propertyImage.setIdProperty(id_property);
			propertyImage.setImage(fullPath);
			propertyImages.add(propertyImage);
		}
		
		return propertyImages;
	}
	
	@Override
	public List<PaymentType> returnPayments(int id_property) throws SQLException {
		String sql = "SELECT * FROM payment_type" + " "
				+ "JOIN payment_property ON payment_property.id_payment = payment_type.id_card_type" + " "
				+ "JOIN property ON property.id_property = payment_property.id_property" + " "
				+ "WHERE property.id_property = " + id_property; 
		
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
		
		return payments;
	}
	
	private Country returnCountry(int id_country) throws SQLException {
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
			return country;
		}
		return null;
	}
	
	private GeneralDomain returnLastInsertedData(GeneralDomain domain) throws SQLException {
		String sql = "SELECT * FROM " + domain.returnTableName() + " ORDER BY " + domain.returnIdColumnName()+" DESC LIMIT 1";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		return domain.returnLastInsertedObject(resultSet);
	}

	@Override
	public List<User> returnUsers(User user, String status) throws SQLException {
		String sql = "SELECT * FROM user WHERE status = ?";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		preparedStatement.setString(1,status);
		ResultSet resultSet = preparedStatement.executeQuery();
		return user.returnList(resultSet);
	}

	
}
