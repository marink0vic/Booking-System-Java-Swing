package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class Room implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idRoom;
	private int idRoomType;
	private int numOfBads;
	private boolean kitchen;
	private boolean tv;
	private boolean airConditioning;
	private boolean wifi;
	private LocalDateTime created;
	
	public Room() {
		
	}

	public Room(int numOfBads, boolean kitchen, boolean tv, boolean airConditioning, boolean wifi) {
		super();
		this.numOfBads = numOfBads;
		this.kitchen = kitchen;
		this.tv = tv;
		this.airConditioning = airConditioning;
		this.wifi = wifi;
	}

	public int getIdRoom() {
		return idRoom;
	}

	public void setIdRoom(int idRoom) {
		this.idRoom = idRoom;
	}

	public int getIdRoomType() {
		return idRoomType;
	}

	public void setIdRoomType(int idRoomType) {
		this.idRoomType = idRoomType;
	}

	public int getNumOfBads() {
		return numOfBads;
	}

	public void setNumOfBads(int numOfBads) {
		this.numOfBads = numOfBads;
	}

	public boolean isKitchen() {
		return kitchen;
	}

	public void setKitchen(boolean kitchen) {
		this.kitchen = kitchen;
	}

	public boolean isTv() {
		return tv;
	}

	public void setTv(boolean tv) {
		this.tv = tv;
	}

	public boolean isAirConditioning() {
		return airConditioning;
	}

	public void setAirConditioning(boolean airConditioning) {
		this.airConditioning = airConditioning;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public String returnTableName() {
		return "room";
	}

	@Override
	public String returnColumnNames() {
		return " (id_room_type, num_of_bads, kitchen, tv, air_conditioning, wifi, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?)";
	}

	@Override
	public void fillStatementWithValues(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idRoomType);
		preparedStatement.setInt(index.next(), numOfBads);
		preparedStatement.setBoolean(index.next(), kitchen);
		preparedStatement.setBoolean(index.next(), tv);
		preparedStatement.setBoolean(index.next(), airConditioning);
		preparedStatement.setBoolean(index.next(), wifi);
		preparedStatement.setString(index.next(), sdf.format(date));
	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_room";
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		Room room = new Room();
		if (resultSet.next()) {
			room.idRoom = resultSet.getInt("id_room");
			room.idRoomType = resultSet.getInt("id_room_type");
			room.numOfBads = resultSet.getInt("num_of_bads");
			room.kitchen = resultSet.getBoolean("kitchen");
			room.tv = resultSet.getBoolean("tv");
			room.airConditioning = resultSet.getBoolean("air_conditioning");
			room.wifi = resultSet.getBoolean("wifi");
			return room;
		}
		return null;
	}

}