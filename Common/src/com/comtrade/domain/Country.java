package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Country implements GeneralDomain, DomainList, Serializable {

	private static final long serialVersionUID = 1L;
	private int idCountry;
	private String name;
	private String image;
	private LocalDateTime created;
	
	public Country() {
		
	}
	
	public Country(int idCountry, String name, String image) {
		this.idCountry = idCountry;
		this.name = name;
		this.image = image;
	}

	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public String returnTableName() {
		return "country";
	}
	
	@Override
	public String returnColumnNames() {
		return " (name, image, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?)";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, image);
		preparedStatement.setString(3, sdf.format(date));
	}

	@Override
	public List<Country> returnList(ResultSet resultSet) throws SQLException {
		List<Country> list = new ArrayList<>();
		while (resultSet.next()) {
			int id = resultSet.getInt("id_country");
			String name = resultSet.getString("name");
			String image = resultSet.getString("image");
			String dateTime = resultSet.getString("created");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime created = LocalDateTime.parse(dateTime, formatter);
			
			Country country = new Country();
			country.idCountry = id;
			country.name = name;
			country.image = image;
			country.created = created;
			
			list.add(country);
		}
		return list;
	}

	@Override
	public String returnIdColumnName() {
		return "id_country";
	}

	@Override
	public int returnIdNumber() {
		return idCountry;
	}

}
