package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import com.comtrade.domain.behavior.GeneralDomain;

public class PaymentProperty implements GeneralDomain, Serializable {

	private static final long serialVersionUID = 1L;
	private int idPayment;
	private int idProperty;
	private LocalDateTime created;
	
	public PaymentProperty() {
		
	}

	public PaymentProperty(int idPayment, int idProperty) {
		this.idPayment = idPayment;
		this.idProperty = idProperty;
	}

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
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, idPayment);
		preparedStatement.setInt(2, idProperty);
		preparedStatement.setString(3, sdf.format(date));
	}

	@Override
	public String returnIdColumnName() {
		return "id_payment";
	}

	@Override
	public int returnIdNumber() {
		return idPayment;
	}

}
