package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentType implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int id_payment;
	private String name;
	private String image;
	private LocalDateTime created;
	
	public int getId_payment() {
		return id_payment;
	}

	public void setId_payment(int id_payment) {
		this.id_payment = id_payment;
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
		return "payment_type";
	}

	@Override
	public String returnColumnNames() {
		return null;
	}

	@Override
	public String returnStatementPlaceholder() {
		return null;
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {

	}

	@Override
	public List<PaymentType> returnList(ResultSet resultSet) throws SQLException {
		List<PaymentType> list = new ArrayList<>();
		while (resultSet.next()) {
			int id = resultSet.getInt("id_card_type");
			String name = resultSet.getString("name");
			String image = resultSet.getString("image");
			String dateTime = resultSet.getString("created");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime created = LocalDateTime.parse(dateTime, formatter);
			
			PaymentType pt = new PaymentType();
			pt.id_payment = id;
			pt.name = name;
			pt.image = image;
			pt.created = created;
			
			list.add(pt);
		}
		return list;
	}

	@Override
	public String returnIdColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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

}
