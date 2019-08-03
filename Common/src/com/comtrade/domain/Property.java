package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Property implements GeneralDomain, DomainJoin, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idProperty;
	private int idUser;
	private int idAddress;
	private String type;
	private String name;
	private String phoneNumner;
	private int rating;
	private double latitude;
	private double longitude;
	private String description;
	private LocalDateTime created;
	
	public Property() {
		
	}

	public Property(int idUser, int idAddress, String type, String name, String phoneNumner, int rating,
			String description) {
		super();
		this.idUser = idUser;
		this.idAddress = idAddress;
		this.type = type;
		this.name = name;
		this.phoneNumner = phoneNumner;
		this.rating = rating;
		this.description = description;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public int getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(int idAddress) {
		this.idAddress = idAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumner() {
		return phoneNumner;
	}

	public void setPhoneNumner(String phoneNumner) {
		this.phoneNumner = phoneNumner;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	

	@Override
	public String returnTableName() {
		return "property";
	}

	@Override
	public String returnColumnNames() {
		return " (id_user, id_address, type, name, phone_number, rating, latitude, longitude, description, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idUser);
		preparedStatement.setInt(index.next(), idAddress);
		preparedStatement.setString(index.next(), type);
		preparedStatement.setString(index.next(), name);
		preparedStatement.setString(index.next(), phoneNumner);
		preparedStatement.setInt(index.next(), rating);
		preparedStatement.setDouble(index.next(), latitude);
		preparedStatement.setDouble(index.next(), longitude);
		preparedStatement.setString(index.next(), description);
		preparedStatement.setString(index.next(), sdf.format(date));
	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_property";
	}

	@Override
	public int returnIdNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idAddress;
		result = prime * result + idProperty;
		result = prime * result + idUser;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Property other = (Property) obj;
		if (idAddress != other.idAddress)
			return false;
		if (idProperty != other.idProperty)
			return false;
		if (idUser != other.idUser)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		Property property = new Property();
		if (resultSet.next()) {
			property.idProperty = resultSet.getInt("id_property");
			property.idUser = resultSet.getInt("id_user");
			property.idAddress = resultSet.getInt("id_address");
			property.type = resultSet.getString("type");
			property.name = resultSet.getString("name");
			property.phoneNumner = resultSet.getString("phone_number");
			property.rating = resultSet.getInt("rating");
			property.latitude = resultSet.getDouble("latitude");
			property.longitude = resultSet.getDouble("longitude");
			property.description = resultSet.getString("description");
			String created = resultSet.getString("created");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.parse(created, formatter);
			property.created = dateTime;
			return property;
		}
		return null;
	}

	@Override
	public String returnBookingJoin() throws SQLException {
		String join =  "SELECT * FROM bookings"
				    + " JOIN user ON user.id_user = bookings.id_user"
				    + " JOIN booked_room ON booked_room.id_booking = bookings.id_booking"
				    + " WHERE bookings.id_property = ?";
		return join;
	}

	@Override
	public Map<Booking, List<BookedRoom>> returnJoinTables(ResultSet rs) throws SQLException {
		Map<Booking, List<BookedRoom>> bookings = new HashMap<>();
		while (rs.next()) {
			Booking booking = new Booking();
			booking = booking.createBooking(rs);
			
			BookedRoom br = new BookedRoom();
			br = br.createBookedRoom(rs);
			
			User user = createUser(rs);
			booking.setUser(user);
			
			if (bookings.containsKey(booking)) {
				bookings.get(booking).add(br);
			} else {
				List<BookedRoom> rooms = new ArrayList<>();
				rooms.add(br);
				bookings.put(booking, rooms);
			}
		}
		return bookings;
	}
	
	private User createUser(ResultSet result_set) throws SQLException {
		User user = new User();
		user.setIdUser(result_set.getInt("id_user"));
		user.setFirstName(result_set.getString("first_name"));
		user.setLastName(result_set.getString("last_name"));
		user.setUsername(result_set.getString("username"));
		user.setProfilePicture(result_set.getString("profile_picture"));
		return user;
	}

}
