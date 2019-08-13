package com.comtrade.broker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.comtrade.connection.Connection;
import com.comtrade.constants.ServerResourcePath;
import com.comtrade.domain.Location;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Country;
import com.comtrade.domain.DomainJoin;
import com.comtrade.domain.DomainList;
import com.comtrade.domain.DomainUpdate;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.Property;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;

public class Broker implements IBroker {

	@Override
	public int save(GeneralDomain domain) throws SQLException {
		String sql = "INSERT INTO " + domain.returnTableName() + "" + domain.returnColumnNames() + "" + domain.returnStatementPlaceholder();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql ,PreparedStatement.RETURN_GENERATED_KEYS);
		
		domain.preparedStatementInsert(preparedStatement);
		preparedStatement.executeUpdate();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		int id = -1;
		if (rs.next()) {
			try {
				id = rs.getInt(1);
			} catch (Exception e) {
				System.out.println("primary key is not integer");
			}
		}
		return id;
	}
	
	@Override
	public void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException {
		if (list.size() == 0) return;
		GeneralDomain domain = list.get(0);
		String sql = "INSERT INTO " + domain.returnTableName() + "" + domain.returnColumnNames() + "" + domain.returnStatementPlaceholder();
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		for (GeneralDomain d : list) {
			d.preparedStatementInsert(preparedStatement);
			preparedStatement.addBatch();
		}
		preparedStatement.executeBatch();
	}
	
	@Override
	public void update(DomainUpdate domain) throws SQLException {
		String sql = "UPDATE " + domain.returnTableName() + " SET " + domain.returnColumnsForUpdate() + " WHERE " + domain.returnIdColumnName() + " = ?";	
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		domain.preparedStatementUpdate(preparedStatement);
		preparedStatement.executeUpdate();
	}
	
	@Override
	public void updateCollectionOfData(List<? extends DomainUpdate> list) throws SQLException {
		DomainUpdate domain = list.get(0);
		String sql = "UPDATE " + domain.returnTableName() + " SET " + domain.returnColumnsForUpdate() + " WHERE " + domain.returnIdColumnName() + " = ?";
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		
		for (DomainUpdate d : list) {
			d.preparedStatementUpdate(preparedStatement);
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
	public List<? extends GeneralDomain> returnAllData(DomainList domain) throws SQLException {
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
		return new User();
	}
	
	@Override
	public void insertPropertyForOwner(PropertyWrapper wrapper) throws SQLException {
		setAddressAndProperty(new Location(), wrapper);
		if (wrapper.getProperty() == null) return;
		int idProperty = wrapper.getProperty().getIdProperty();
		wrapper.setRooms(returnRoomAndRoomInfo(new RoomType(),idProperty));
		wrapper.setImages(returnPropertyImages(idProperty));
		wrapper.setPaymentList(returnPayments(new PaymentType(), idProperty));
		wrapper.setCountry(returnCountry(wrapper.getAddress().getIdCountry()));
		wrapper.setBookings(returnBookings(new Property(), wrapper.getProperty().getIdProperty()));
		wrapper.setReviews(returnPropertyReviews(new PropertyReview(), idProperty));
	}	


	private void setAddressAndProperty(DomainJoin domain_join, PropertyWrapper wrapper) throws SQLException {
		ResultSet rs = prepareResultSet(domain_join, wrapper.getUser().getIdUser());
		PropertyWrapper wp = domain_join.returnJoinTables(rs);
		wrapper.setAddress(wp.getAddress());
		wrapper.setProperty(wp.getProperty());
	}
	
	@Override
	public Map<Booking, List<BookedRoom>> returnBookings(DomainJoin domain_join, int id_domain) throws SQLException {
		ResultSet rs = prepareResultSet(domain_join, id_domain);
		PropertyWrapper wrapper = domain_join.returnJoinTables(rs);
		return wrapper.getBookings();
	}

	@Override
	public List<PropertyReview> returnPropertyReviews(DomainJoin domain_join, int id_domain) throws SQLException {
		ResultSet rs = prepareResultSet(domain_join, id_domain);
		PropertyWrapper wrapper = domain_join.returnJoinTables(rs);
		return wrapper.getReviews();
	}
	
	private Map<RoomType, Room> returnRoomAndRoomInfo(DomainJoin domain_join, int id_domain) throws SQLException {
		ResultSet rs = prepareResultSet(domain_join, id_domain);
		PropertyWrapper wrapper = domain_join.returnJoinTables(rs);
		return wrapper.getRooms();
	}
	
	private List<PaymentType> returnPayments(DomainJoin domain_join, int id_domain) throws SQLException {
		ResultSet rs = prepareResultSet(domain_join, id_domain);
		PropertyWrapper wrapper = domain_join.returnJoinTables(rs);
		return wrapper.getPaymentList();
	}
	
	private ResultSet prepareResultSet(DomainJoin domain_join, int id_domain) throws SQLException {
		String sql = domain_join.prepareJoin();
		PreparedStatement ps = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ps.setInt(1, id_domain);
		ResultSet rs = ps.executeQuery();
		return rs;
	}
	
	@Override
	public List<PropertyImage> returnPropertyImages(int id_property) throws SQLException {
		String sql = "SELECT * FROM property_images WHERE id_property = " + id_property;
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		List<PropertyImage> propertyImages = new ArrayList<>();
		
		while (resultSet.next()) {
			int idImage = resultSet.getInt("id_image");
			String fullPath = ServerResourcePath.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
			PropertyImage propertyImage = new PropertyImage();
			propertyImage.setIdImage(idImage);
			propertyImage.setIdProperty(id_property);
			propertyImage.setImage(fullPath);
			propertyImages.add(propertyImage);
		}
		
		return propertyImages;
	}

	private Country returnCountry(int id_country) throws SQLException {
		String sql = "SELECT * FROM country WHERE id_country = " + id_country;
		PreparedStatement preparedStatement = Connection.getConnection().getSqlConnection().prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int idCountry = resultSet.getInt("id_country");
			String fullPath = ServerResourcePath.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
			String name = resultSet.getString("name");
			Country country = new Country();
			country.setIdCountry(idCountry);
			country.setImage(fullPath);
			country.setName(name);
			return country;
		}
		return null;
	}
}
