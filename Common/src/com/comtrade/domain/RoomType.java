package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import com.comtrade.domain.behavior.DomainUpdate;
import com.comtrade.domain.behavior.GeneralDomain;

public class RoomType implements DomainUpdate, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idRoomType;
	private int idProperty;
	private String roomType;
	private int numberOfRooms;
	private double pricePerNight;
	private LocalDateTime created;
	
	public RoomType() {
		
	}

	public RoomType(String roomType, int numberOfRooms, double pricePerNight) {
		super();
		this.roomType = roomType;
		this.numberOfRooms = numberOfRooms;
		this.pricePerNight = pricePerNight;
	}

	public int getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(int idRoomType) {
		this.idRoomType = idRoomType;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public String returnTableName() {
		return "room_type";
	}

	@Override
	public String returnColumnNames() {
		return " (id_property, type, num_of_rooms, price_per_night, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?)";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idProperty);
		preparedStatement.setString(index.next(), roomType);
		preparedStatement.setInt(index.next(), numberOfRooms);
		preparedStatement.setDouble(index.next(), pricePerNight);
		preparedStatement.setString(index.next(), sdf.format(date));

	}

	@Override
	public List<RoomType> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_room_type";
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		RoomType roomType = new RoomType();
		if (resultSet.next()) {
			roomType.setIdRoomType(resultSet.getInt("id_room_type"));
			roomType.setIdProperty(resultSet.getInt("id_property"));
			roomType.setRoomType(resultSet.getString("type"));
			roomType.setNumberOfRooms(resultSet.getInt("num_of_rooms"));
			roomType.setPricePerNight(resultSet.getDouble("price_per_night"));
			return roomType;
		}
		return null;
	}

	@Override
	public int returnIdNumber() {
		return idRoomType;
	}

	@Override
	public String returnColumnsForUpdate() {
		return "type = ?, num_of_rooms = ?, price_per_night = ?";
	}

	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException {
		preparedStatement.setString(index.next(), roomType);
		preparedStatement.setInt(index.next(), numberOfRooms);
		preparedStatement.setDouble(index.next(), pricePerNight);
		preparedStatement.setInt(index.next(), idRoomType);
	}
	
}
