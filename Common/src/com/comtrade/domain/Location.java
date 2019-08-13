package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.comtrade.domain.behavior.DomainJoin;
import com.comtrade.domain.behavior.GeneralDomain;
import com.comtrade.dto.PropertyWrapper;

public class Location implements GeneralDomain, DomainJoin, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idLocation;
	private int idCountry;
	private String street;
	private String number;
	private String city;
	private int zipCode;
	private double latitude;
	private double longitude;
	private LocalDateTime created;

	public Location(int idCountry, String street, String number, String city, int zipCode) {
		super();
		this.idCountry = idCountry;
		this.street = street;
		this.number = number;
		this.city = city;
		this.zipCode = zipCode;
	}
	public Location() {
		
	}
	
	public int getIdLocation() {
		return idLocation;
	}
	public void setIdLocation(int idLocation) {
		this.idLocation = idLocation;
	}
	public int getIdCountry() {
		return idCountry;
	}
	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
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
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	@Override
	public String returnTableName() {
		return "location";
	}

	@Override
	public String returnColumnNames() {
		return " (id_country, street, number, city, post_or_zipcode, latitude, longitude, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?,?)";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, idCountry);
		preparedStatement.setString(2, street);
		preparedStatement.setString(3, number);
		preparedStatement.setString(4, city);
		preparedStatement.setInt(5, zipCode);
		preparedStatement.setDouble(6, latitude);
		preparedStatement.setDouble(7, longitude);
		preparedStatement.setString(8, sdf.format(date));

	}

	@Override
	public String returnIdColumnName() {
		return "id_location";
	}

	@Override
	public int returnIdNumber() {
		return idLocation;
	}
	@Override
	public String prepareJoin() throws SQLException {
		String join = "SELECT * FROM location JOIN property"
				+ " ON location.id_location = property.id_location"
				+ " WHERE property.id_user = ?";
		return join;
	}
	@Override
	public PropertyWrapper returnJoinTables(ResultSet rs) throws SQLException {
		PropertyWrapper wrapper = new PropertyWrapper();
		if (rs.next()) {
			int idProperty = rs.getInt("id_property");
			int idLocation = rs.getInt("id_location");
			int idUser = rs.getInt("id_user");
			String type = rs.getString("type");
			String name = rs.getString("name");
			String phone = rs.getString("phone_number");
			int rating = rs.getInt("rating");
			String description = rs.getString("description");
			String created = rs.getString("created");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(created, formatter);
	
			Property property = new Property(idUser, idLocation, type, name, phone, rating, description);
			property.setIdProperty(idProperty);
			property.setCreated(dateTime);
			
			int idCountry = rs.getInt("id_country");
			String street = rs.getString("street");
			String number = rs.getString("number");
			String city = rs.getString("city");
			int zip = rs.getInt("post_or_zipcode");
			double latitude = rs.getDouble("latitude");
			double longitude = rs.getDouble("longitude");
			Location location = new Location(idCountry, street, number, city, zip);
			location.setIdLocation(idLocation);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			
			wrapper.setAddress(location);
			wrapper.setProperty(property);
		}
		return wrapper;
	}

}
