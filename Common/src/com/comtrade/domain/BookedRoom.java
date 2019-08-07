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

import com.comtrade.domain.behavior.GeneralDomain;

public class BookedRoom implements GeneralDomain, Serializable {

	private static final long serialVersionUID = 1L;
	private int idBookedRoom;
	private int idBooking;
	private int idRoomType;
	private int numberOfRooms;
	private int numberOfAdults;
	private int numberOfChildren;
	private double priceForRoom;
	private LocalDateTime created;
	
	public BookedRoom() {
		
	}

	public BookedRoom(int idRoomType, int numberOfRooms, int numberOfAdults, int numberOfChildren, double priceForRoom) {
		super();
		this.idRoomType = idRoomType;
		this.numberOfRooms = numberOfRooms;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
		this.priceForRoom  = priceForRoom;
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

	public double getPriceForRoom() {
		return priceForRoom;
	}

	public void setPriceForRoom(double priceForRoom) {
		this.priceForRoom = priceForRoom;
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
		return " (id_booking, id_room_type, number_of_rooms, number_of_adults, number_of_children, price_for_room, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?)";
	}
	
	@Override
	public String returnIdColumnName() {
		return "id_booked_room";
	}

	
	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, idBooking);
		preparedStatement.setInt(2, idRoomType);
		preparedStatement.setInt(3, numberOfRooms);
		preparedStatement.setInt(4, numberOfAdults);
		preparedStatement.setInt(5, numberOfChildren);
		preparedStatement.setDouble(6, priceForRoom);
		preparedStatement.setString(7, sdf.format(date));
	}

	@Override
	public List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		List<BookedRoom> bookedRooms = new ArrayList<>();
		while (resultSet.next()) {
			BookedRoom br = createBookedRoom(resultSet);
			bookedRooms.add(br);
		}
		return bookedRooms;
	}
	
	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		if (resultSet.next()) {
			return createBookedRoom(resultSet);
		}
		return null;
	}
	
	public BookedRoom createBookedRoom(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt("id_booked_room");
		int idBooking = resultSet.getInt("id_booking");
		int idRoomType = resultSet.getInt("id_room_type");
		int numOfRooms = resultSet.getInt("number_of_rooms");
		int numOfAdults = resultSet.getInt("number_of_adults");
		int numOfChildren = resultSet.getInt("number_of_children");
		double price = resultSet.getDouble("price_for_room");
		String created = resultSet.getString("created");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(created, formatter);
		
		BookedRoom bookedRoom = new BookedRoom(idRoomType, numOfRooms, numOfAdults, numOfChildren, price);
		bookedRoom.idBooking = idBooking;
		bookedRoom.idBookedRoom = id;
		bookedRoom.setCreated(dateTime);
		return bookedRoom;
	}

	@Override
	public String toString() {
		return "BookedRoom [idRoomType=" + idRoomType + ", numberOfRooms=" + numberOfRooms + ", numberOfAdults="
				+ numberOfAdults + ", numberOfChildren=" + numberOfChildren + "]";
	}

}
