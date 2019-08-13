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

import com.comtrade.constants.ServerResourcePath;
import com.comtrade.dto.PropertyWrapper;

public class PaymentType implements GeneralDomain, DomainList, DomainJoin, Serializable {

	
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
		return "id_card_type";
	}

	@Override
	public int returnIdNumber() {
		return id_payment;
	}

	@Override
	public String prepareJoin() throws SQLException {
		String join = "SELECT * FROM payment_type"
					+ " JOIN payment_property ON payment_property.id_payment = payment_type.id_card_type"
					+ " JOIN property ON property.id_property = payment_property.id_property"
					+ " WHERE property.id_property = ?"; 
		return join;
	}

	@Override
	public PropertyWrapper returnJoinTables(ResultSet rs) throws SQLException {
		List<PaymentType> payments = new ArrayList<>();
		while (rs.next()) {
			int idCardType = rs.getInt("id_card_type");
			String name = rs.getString("name");
			String fullPath = ServerResourcePath.SERVER_RESOURCES_PATH.getPath() + rs.getString("image");
			PaymentType paymentType = new PaymentType();
			paymentType.setId_payment(idCardType);
			paymentType.setName(name);
			paymentType.setImage(fullPath);
			
			payments.add(paymentType);
		}
		PropertyWrapper pw = new PropertyWrapper();
		pw.setPaymentList(payments);
		return pw;
	}

}
