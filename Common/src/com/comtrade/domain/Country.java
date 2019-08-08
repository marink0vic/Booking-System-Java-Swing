package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.comtrade.domain.behavior.GeneralDomain;

public class Country implements GeneralDomain, Serializable {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnStatementPlaceholder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return 0;
	}

}
