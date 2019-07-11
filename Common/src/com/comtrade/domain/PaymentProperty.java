package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class PaymentProperty implements GeneralDomain, Serializable {

	private static final long serialVersionUID = 1L;
	private int idPayment;
	private int idProperty;
	private LocalDateTime created;
	

	public int getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public String returnTableName() {
		return "payment_property";
	}

	@Override
	public String returnColumnNames() {
		return " (id_payment, id_property, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?)";
	}

	@Override
	public void fillStatementWithValues(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idPayment);
		preparedStatement.setInt(index.next(), idProperty);
		preparedStatement.setString(index.next(), sdf.format(date));
	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
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

}
