package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class Property implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idProperty;
	private int idUser;
	private int idAddress;
	private String type;
	private String name;
	private String phoneNumner;
	private int rating;
	private double latitude;
	private double longitude;
	private String description;
	private LocalDateTime created;
	
	public Property() {
		
	}

	public Property(int idUser, int idAddress, String type, String name, String phoneNumner, int rating,
			String description) {
		super();
		this.idUser = idUser;
		this.idAddress = idAddress;
		this.type = type;
		this.name = name;
		this.phoneNumner = phoneNumner;
		this.rating = rating;
		this.description = description;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumner() {
		return phoneNumner;
	}

	public void setPhoneNumner(String phoneNumner) {
		this.phoneNumner = phoneNumner;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	

	@Override
	public String returnTableName() {
		return "property";
	}

	@Override
	public String returnColumnNames() {
		return " (id_user, id_address, type, name, phone_number, rating, latitude, longitude, description, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	public void fillStatementWithValues(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idUser);
		preparedStatement.setInt(index.next(), idAddress);
		preparedStatement.setString(index.next(), type);
		preparedStatement.setString(index.next(), name);
		preparedStatement.setString(index.next(), phoneNumner);
		preparedStatement.setInt(index.next(), rating);
		preparedStatement.setDouble(index.next(), latitude);
		preparedStatement.setDouble(index.next(), longitude);
		preparedStatement.setString(index.next(), description);
		preparedStatement.setString(index.next(), sdf.format(date));
	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_property";
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		Property property = new Property();
		if (resultSet.next()) {
			property.idProperty = resultSet.getInt("id_property");
			property.idUser = resultSet.getInt("id_user");
			property.idAddress = resultSet.getInt("id_address");
			property.type = resultSet.getString("type");
			property.name = resultSet.getString("name");
			property.phoneNumner = resultSet.getString("phone_number");
			property.rating = resultSet.getInt("rating");
			property.latitude = resultSet.getDouble("latitude");
			property.longitude = resultSet.getDouble("longitude");
			property.description = resultSet.getString("description");
			return property;
		}
		return null;
	}
	

}
