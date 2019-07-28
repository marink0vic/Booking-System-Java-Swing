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
	private double priceForStay;
	private LocalDateTime created;
	
	public Booking() {
		
	}

	public Booking(int idUser, int idProperty, LocalDate checkIn, LocalDate checkOut) {
		this.idUser = idUser;
		this.idProperty = idProperty;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
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
		return " (id_user, id_property, check_in, check_out, price_for_stay, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?)";
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
	
	public Booking createBooking (ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_booking");
		int idUser = resultSet.getInt("id_user");
		int idProperty = resultSet.getInt("id_property");
		LocalDate checkIn = resultSet.getDate("check_in").toLocalDate();
		LocalDate checkOut = resultSet.getDate("check_out").toLocalDate();
		double priceForStay = resultSet.getDouble("price_for_stay");
		Booking booking = new Booking(idUser, idProperty, checkIn, checkOut);
		booking.idBooking = id;
		booking.priceForStay = priceForStay;
		return booking;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idBooking;
		result = prime * result + idProperty;
		result = prime * result + idUser;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (idBooking != other.idBooking)
			return false;
		if (idProperty != other.idProperty)
			return false;
		if (idUser != other.idUser)
			return false;
		return true;
	}
	
	
}
