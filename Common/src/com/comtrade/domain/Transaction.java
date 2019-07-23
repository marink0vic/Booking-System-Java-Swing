package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Transaction implements GeneralDomain, Serializable {


	private static final long serialVersionUID = 1L;
	private int idTransaction;
	private int idSender;
	private int idReceiver;
	private int idBooking;
	private LocalDate transferDate;
	private LocalTime transferTime;
	private double amount;
	private double siteFees;
	private transient final int percentSite = 10;
	private transient final int percentProperty = 90;
	
	public Transaction() {
		
	}
	
	public Transaction(int idSender, int idReceiver, LocalDate transferDate, LocalTime transferTime) {
		this.idSender = idSender;
		this.idReceiver = idReceiver;
		this.transferDate = transferDate;
		this.transferTime = transferTime;
	}

	public int getIdTransaction() {
		return idTransaction;
	}

	public void setIdTransaction(int idTransaction) {
		this.idTransaction = idTransaction;
	}

	public int getIdSender() {
		return idSender;
	}

	public void setIdSender(int idSender) {
		this.idSender = idSender;
	}

	public int getIdReceiver() {
		return idReceiver;
	}

	public void setIdReceiver(int idReceiver) {
		this.idReceiver = idReceiver;
	}

	public int getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}

	public LocalTime getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(LocalTime transferTime) {
		this.transferTime = transferTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = (amount / 100) * percentProperty;
	}

	public double getSiteFees() {
		return siteFees;
	}

	public void setSiteFees(double amount) {
		this.siteFees = (amount / 100) * percentSite;
	}

	@Override
	public int returnIdNumber() {
		return idTransaction;
	}

	@Override
	public String returnTableName() {
		return "transactions";
	}
	
	@Override
	public String returnColumnNames() {
		return " (id_sender, id_receiver, id_booking, transfer_date, transfer_time, amount, site_fees) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?)";
	}

	@Override
	public String returnColumnsForUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_transaction";
	}

	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		preparedStatement.setInt(index.next(), idSender);
		preparedStatement.setInt(index.next(), idReceiver);
		preparedStatement.setInt(index.next(), idBooking);
		preparedStatement.setDate(index.next(), java.sql.Date.valueOf(transferDate));
		preparedStatement.setTime(index.next(), java.sql.Time.valueOf(transferTime));
		preparedStatement.setDouble(index.next(), amount);
		preparedStatement.setDouble(index.next(), siteFees);
	}

	@Override
	public List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Transaction [idSender=" + idSender + ", idReceiver=" + idReceiver + ", transferDate=" + transferDate
				+ ", transferTime=" + transferTime + ", amount=" + amount + ", siteFees=" + siteFees + "]";
	}

}
