package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.comtrade.dto.PropertyWrapper;

public class RoomType implements DomainUpdate, DomainJoin, Serializable {

	
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
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, idProperty);
		preparedStatement.setString(2, roomType);
		preparedStatement.setInt(3, numberOfRooms);
		preparedStatement.setDouble(4, pricePerNight);
		preparedStatement.setString(5, sdf.format(date));

	}

	@Override
	public String returnIdColumnName() {
		return "id_room_type";
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
	public void preparedStatementUpdate(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, roomType);
		preparedStatement.setInt(2, numberOfRooms);
		preparedStatement.setDouble(3, pricePerNight);
		preparedStatement.setInt(4, idRoomType);
	}

	@Override
	public String prepareJoin() throws SQLException {
		String join = "SELECT * FROM room_type"
					+ " JOIN room ON room_type.id_room_type = room.id_room_type"
					+ " WHERE room_type.id_property = ?";
		return join;
	}

	@Override
	public PropertyWrapper returnJoinTables(ResultSet resultSet) throws SQLException {
		Map<RoomType, Room> room = new LinkedHashMap<>();
		while (resultSet.next()) {
			int idRoomType = resultSet.getInt("id_room_type");
			int idProperty = resultSet.getInt("id_property");;
			String type = resultSet.getString("type");
			int numOfRooms = resultSet.getInt("num_of_rooms");	
			double pricePerNight = resultSet.getDouble("price_per_night");
			RoomType rType = new RoomType(type, numOfRooms, pricePerNight);
			rType.setIdRoomType(idRoomType);
			rType.setIdProperty(idProperty);
			
			int idRoomInfo = resultSet.getInt("id_room");
			int numOfBads = resultSet.getInt("num_of_bads");
			boolean kitchen = resultSet.getBoolean("kitchen");
			boolean tv = resultSet.getBoolean("tv");
			boolean airConditioning = resultSet.getBoolean("air_conditioning");
			boolean wifi = resultSet.getBoolean("wifi");
			Room rInfo = new Room(numOfBads, kitchen, tv, airConditioning, wifi);
			rInfo.setIdRoom(idRoomInfo);
			rInfo.setIdRoomType(idRoomType);
			
			room.put(rType, rInfo);
		}
		PropertyWrapper pw = new PropertyWrapper();
		pw.setRooms(room);
		return pw;
	}
	
}
