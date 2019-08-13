package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Room implements DomainUpdate, Serializable {

	
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
	
	public Map<String, Boolean> roomInfoData() {
		Map<String, Boolean> map = new HashMap<>();
		map.put("Kitchen", kitchen);
		map.put("TV", tv);
		map.put("Wifi", wifi);
		map.put("Air conditioning", airConditioning);
		return map;
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
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, idRoomType);
		preparedStatement.setInt(2, numOfBads);
		preparedStatement.setBoolean(3, kitchen);
		preparedStatement.setBoolean(4, tv);
		preparedStatement.setBoolean(5, airConditioning);
		preparedStatement.setBoolean(6, wifi);
		preparedStatement.setString(7, sdf.format(date));
	}

	@Override
	public String returnIdColumnName() {
		return "id_room";
	}

	@Override
	public int returnIdNumber() {
		return idRoom;
	}

	@Override
	public String returnColumnsForUpdate() {
		return "num_of_bads = ?, kitchen = ?, tv = ?, air_conditioning = ?, wifi = ?";
	}

	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(1, numOfBads);
		preparedStatement.setBoolean(2, kitchen);
		preparedStatement.setBoolean(3, tv);
		preparedStatement.setBoolean(4, airConditioning);
		preparedStatement.setBoolean(5, wifi);
		preparedStatement.setInt(6, idRoom);
	}

}
