package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import com.comtrade.domain.behavior.GeneralDomain;

public class Location implements GeneralDomain, Serializable {

	
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
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idCountry);
		preparedStatement.setString(index.next(), street);
		preparedStatement.setString(index.next(), number);
		preparedStatement.setString(index.next(), city);
		preparedStatement.setInt(index.next(), zipCode);
		preparedStatement.setDouble(index.next(), latitude);
		preparedStatement.setDouble(index.next(), longitude);
		preparedStatement.setString(index.next(), sdf.format(date));

	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_location";
	}

	@Override
	public int returnIdNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		Location location = new Location();
		if (resultSet.next()) {
			location.idLocation = resultSet.getInt("id_location");
			location.idCountry = resultSet.getInt("id_country");
			location.street = resultSet.getString("street");
			location.number = resultSet.getString("number");
			location.city = resultSet.getString("city");
			location.zipCode = resultSet.getInt("post_or_zipcode");
			location.latitude = resultSet.getDouble("latitude");
			location.longitude = resultSet.getDouble("longitude");
			return location;
		}
		return null;
	}

}
