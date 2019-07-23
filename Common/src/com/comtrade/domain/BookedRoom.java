package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookedRoom implements GeneralDomain, Serializable {

	private static final long serialVersionUID = 1L;
	private int idBookedRoom;
	private int idBooking;
	private int idRoomType;
	private int numberOfRooms;
	private String status;
	private LocalDateTime created;
	
	public BookedRoom() {
		
	}
	
	public BookedRoom(int idBooking, int idRoomType, int numberOfRooms, String status) {
		this.idBooking = idBooking;
		this.idRoomType = idRoomType;
		this.numberOfRooms = numberOfRooms;
		this.status = status;
	}

	public int getIdBookedRoom() {
		return idBookedRoom;
	}

	public void setIdBookedRoom(int idBookedRoom) {
		this.idBookedRoom = idBookedRoom;
	}

	public int getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}

	public int getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(int idRoomType) {
		this.idRoomType = idRoomType;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
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
		return idBookedRoom;
	}

	@Override
	public String returnTableName() {
		return "booked_room";
	}
	
	@Override
	public String returnColumnNames() {
		return " (id_booking, id_room_type, number_of_rooms, status, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?)";
	}

	@Override
	public String returnColumnsForUpdate() {
		return "status = ?";
	}

	@Override
	public String returnIdColumnName() {
		return "id_booked_room";
	}

	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException {
		preparedStatement.setString(index.next(), status);
	}
	
	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idBooking);
		preparedStatement.setInt(index.next(), idRoomType);
		preparedStatement.setInt(index.next(), numberOfRooms);
		preparedStatement.setString(index.next(), status);
		preparedStatement.setString(index.next(), sdf.format(date));
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

}
