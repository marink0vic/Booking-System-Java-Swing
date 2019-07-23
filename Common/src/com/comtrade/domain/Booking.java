package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idBooking;
	private int idUser;
	private int idProperty;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private int numberOfAdults;
	private int numberOfChildren;
	private double priceForStay;
	private LocalDateTime created;
	
	public Booking() {
		
	}

	public Booking(int idUser, int idProperty, LocalDate checkIn, LocalDate checkOut, int numberOfAdults, int numberOfChildren, double priceForStay) {
		this.idUser = idUser;
		this.idProperty = idProperty;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.priceForStay = priceForStay;
	}

	public int getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public int getNumberOfAdults() {
		return numberOfAdults;
	}

	public void setNumberOfAdults(int numberOfAdults) {
		this.numberOfAdults = numberOfAdults;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public double getPriceForStay() {
		return priceForStay;
	}

	public void setPriceForStay(double priceForStay) {
		this.priceForStay = priceForStay;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public int returnIdNumber() {
		return idBooking;
	}
	
	@Override
	public String returnTableName() {
		return "bookings";
	}

	@Override
	public String returnColumnNames() {
		return " (id_user, id_property, check_in, check_out, number_of_adults, number_of_children, price_for_stay, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?,?)";
	}

	@Override
	public String returnColumnsForUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_booking";
	}

	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idUser);
		preparedStatement.setInt(index.next(), idProperty);
		preparedStatement.setDate(index.next(), java.sql.Date.valueOf(checkIn));
		preparedStatement.setDate(index.next(), java.sql.Date.valueOf(checkOut));
		preparedStatement.setInt(index.next(), numberOfAdults);
		preparedStatement.setInt(index.next(), numberOfChildren);
		preparedStatement.setDouble(index.next(), priceForStay);
		preparedStatement.setString(index.next(), sdf.format(date));
	}

	@Override
	public List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		List<Booking> list = new ArrayList<>();
		while (resultSet.next()) {
			Booking booking = createBooking(resultSet);
			list.add(booking);
		}
		return list;
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		if (resultSet.next()) {
			Booking booking = createBooking(resultSet);
			return booking;
		}
		return null;
	}
	
	private Booking createBooking (ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_booking");
		int idUser = resultSet.getInt("id_user");
		int idProperty = resultSet.getInt("id_property");
		LocalDate checkIn = resultSet.getDate("check_in").toLocalDate();
		LocalDate checkOut = resultSet.getDate("check_out").toLocalDate();
		int numOfAdults = resultSet.getInt("number_of_adults");
		int numOfChildren = resultSet.getInt("number_of_children");
		double priceForStay = resultSet.getDouble("price_for_stay");
		Booking booking = new Booking(idUser, idProperty, checkIn, checkOut, numOfAdults, numOfChildren, priceForStay);
		booking.idBooking = id;
		return booking;
	}

}
