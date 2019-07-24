package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class Address implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idAddress;
	private int idCountry;
	private String street;
	private int number;
	private String city;
	private int zipCode;
	private LocalDateTime created;

	public Address(int idCountry, String street, int number, String city, int zipCode) {
		super();
		this.idCountry = idCountry;
		this.street = street;
		this.number = number;
		this.city = city;
		this.zipCode = zipCode;
	}
	public Address() {
		
	}

	public int getIdAddress() {
		return idAddress;
	}
	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
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
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
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
	public LocalDateTime getCreated() {
		return created;
	}
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	@Override
	public String returnTableName() {
		return "address";
	}

	@Override
	public String returnColumnNames() {
		return " (id_country, street, number, city, post_or_zipcode, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?)";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idCountry);
		preparedStatement.setString(index.next(), street);
		preparedStatement.setInt(index.next(), number);
		preparedStatement.setString(index.next(), city);
		preparedStatement.setInt(index.next(), zipCode);
		preparedStatement.setString(index.next(), sdf.format(date));

	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_address";
	}

	@Override
	public int returnIdNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String returnColumnsForUpdate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		Address address = new Address();
		if (resultSet.next()) {
			address.idAddress = resultSet.getInt("id_address");
			address.idCountry = resultSet.getInt("id_country");
			address.street = resultSet.getString("street");
			address.number = resultSet.getInt("number");
			address.city = resultSet.getString("city");
			address.zipCode = resultSet.getInt("post_or_zipcode");
			return address;
		}
		return null;
	}

}
