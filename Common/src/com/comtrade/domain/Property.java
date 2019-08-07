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

import com.comtrade.domain.behavior.DomainJoinBookings;
import com.comtrade.domain.behavior.GeneralDomain;

public class Property implements GeneralDomain, DomainJoinBookings, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idProperty;
	private int idUser;
	private int idLocation;
	private String type;
	private String name;
	private String phoneNumner;
	private int rating;
	private String description;
	private LocalDateTime created;
	
	public Property() {
		
	}

	public Property(int idUser, int idLocation, String type, String name, String phoneNumner, int rating,
			String description) {
		super();
		this.idUser = idUser;
		this.idLocation = idLocation;
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
	
	public int getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(int idLocation) {
		this.idLocation = idLocation;
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
		return " (id_user, id_location, type, name, phone_number, rating, description, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?,?)";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, idUser);
		preparedStatement.setInt(2, idLocation);
		preparedStatement.setString(3, type);
		preparedStatement.setString(4, name);
		preparedStatement.setString(5, phoneNumner);
		preparedStatement.setInt(6, rating);
		preparedStatement.setString(7, description);
		preparedStatement.setString(8, sdf.format(date));
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
		return idProperty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idLocation;
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
		if (idLocation != other.idLocation)
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
			property.idLocation = resultSet.getInt("id_location");
			property.type = resultSet.getString("type");
			property.name = resultSet.getString("name");
			property.phoneNumner = resultSet.getString("phone_number");
			property.rating = resultSet.getInt("rating");
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
	public String prepareJoin() throws SQLException {
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
