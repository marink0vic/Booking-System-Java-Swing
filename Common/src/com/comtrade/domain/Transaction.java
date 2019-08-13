package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.comtrade.domain.behavior.DomainList;
import com.comtrade.domain.behavior.GeneralDomain;

public class Transaction implements GeneralDomain, DomainList, Serializable {


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
		double d = (amount / 100) * percentProperty;
		DecimalFormat df = new DecimalFormat("#.####");
		this.amount = Double.parseDouble(df.format(d));
	}

	public double getSiteFees() {
		return siteFees;
	}

	public void setSiteFees(double amount) {
		double d = (amount / 100) * percentSite;
		DecimalFormat df = new DecimalFormat("#.####");
		this.siteFees = Double.parseDouble(df.format(d));
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
	public String returnIdColumnName() {
		return "id_transaction";
	}
	
	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, idSender);
		preparedStatement.setInt(2, idReceiver);
		preparedStatement.setInt(3, idBooking);
		preparedStatement.setDate(4, java.sql.Date.valueOf(transferDate));
		preparedStatement.setTime(5, java.sql.Time.valueOf(transferTime));
		preparedStatement.setDouble(6, amount);
		preparedStatement.setDouble(7, siteFees);
	}
	@Override
	public List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		List<Transaction> transactions = new ArrayList<>();
		while (resultSet.next()) {
			Transaction t = createTransaction(resultSet);
			transactions.add(t);
		}
		return transactions;
	}

	private Transaction createTransaction(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_transaction");
		int idSender = resultSet.getInt("id_sender");
		int idReceiver = resultSet.getInt("id_receiver");
		int bookingId = resultSet.getInt("id_booking");
		LocalDate ld = resultSet.getDate("transfer_date").toLocalDate();
		LocalTime lt = resultSet.getTime("transfer_time").toLocalTime();
		double amount = resultSet.getDouble("amount");
		double siteFees = resultSet.getDouble("site_fees");
		
		Transaction t = new Transaction(idSender, idReceiver, ld, lt);
		t.amount = amount;
		t.siteFees = siteFees;
		t.setIdBooking(bookingId);
		t.setIdTransaction(id);
		
		return t;
	}

}
