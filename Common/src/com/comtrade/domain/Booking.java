package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.comtrade.domain.behavior.DomainUpdate;
import com.comtrade.domain.behavior.GeneralDomain;

public class Booking implements GeneralDomain, DomainUpdate, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idBooking;
	private User user;
	private Property property;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private double priceForStay;
	private String status;
	private LocalDateTime created;
	
	public Booking() {
		
	}

	public Booking(User user, Property property, LocalDate checkIn, LocalDate checkOut) {
		this.user = user;
		this.property = property;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}

	public int getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return " (id_user, id_property, check_in, check_out, price_for_stay, status, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?)";
	}
	@Override
	public String returnIdColumnName() {
		return "id_booking";
	}


	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, user.getIdUser());
		preparedStatement.setInt(2, property.getIdProperty());
		preparedStatement.setDate(3, java.sql.Date.valueOf(checkIn));
		preparedStatement.setDate(4, java.sql.Date.valueOf(checkOut));
		preparedStatement.setDouble(5, priceForStay);
		preparedStatement.setString(6, "PENDING");
		preparedStatement.setString(7, sdf.format(date));
	}
	
	public Booking createBooking (ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_booking");
		int idUser = resultSet.getInt("id_user");
		int idProperty = resultSet.getInt("id_property");
		LocalDate checkIn = resultSet.getDate("check_in").toLocalDate();
		LocalDate checkOut = resultSet.getDate("check_out").toLocalDate();
		String status = resultSet.getString("status");
		double priceForStay = resultSet.getDouble("price_for_stay");
		String created = resultSet.getString("created");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(created, formatter);
		
		Property pr = new Property();
		pr.setIdProperty(idProperty);
		
		User user = new User();
		user.setIdUser(idUser);
		
		Booking booking = new Booking(user, pr, checkIn, checkOut);
		booking.idBooking = id;
		booking.priceForStay = priceForStay;
		booking.status = status;
		booking.setCreated(dateTime);
		return booking;
	}
	

	@Override
	public String returnColumnsForUpdate() {
		return "status = ?";
	}

	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, "ACCEPTED");
		preparedStatement.setInt(2, idBooking);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((checkIn == null) ? 0 : checkIn.hashCode());
		result = prime * result + ((checkOut == null) ? 0 : checkOut.hashCode());
		result = prime * result + idBooking;
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		if (checkIn == null) {
			if (other.checkIn != null)
				return false;
		} else if (!checkIn.equals(other.checkIn))
			return false;
		if (checkOut == null) {
			if (other.checkOut != null)
				return false;
		} else if (!checkOut.equals(other.checkOut))
			return false;
		if (idBooking != other.idBooking)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
