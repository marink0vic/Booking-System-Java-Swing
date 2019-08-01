package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import crypt.BCrypt;

public class User implements DomainUpdate, DomainJoin, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idUser;
	private Country country;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private LocalDate dateOfBirth;
	private String profilePicture;
	private String status;
	private LocalDateTime created;
	
	public User() {
		
	}

	public User(Country country, String firstName, String lastName, String email, String username, String password,
			LocalDate dateOfBirth, String status) {
		super();
		this.country = country;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.status = status;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
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
	public String returnTableName() {
		return "user";
	}

	@Override
	public String returnColumnNames() {
		return " (country_id, first_name, last_name, email, username, password, date_of_birth, profile_picture, status, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?,?,?,?,?)";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), country.getIdCountry());
		preparedStatement.setString(index.next(), firstName);
		preparedStatement.setString(index.next(), lastName);
		preparedStatement.setString(index.next(), email);
		preparedStatement.setString(index.next(), username);
		preparedStatement.setString(index.next(), hashPassword(password));
		preparedStatement.setDate(index.next(), java.sql.Date.valueOf(dateOfBirth));
		preparedStatement.setString(index.next(), "/resources/images/users/user-icon.jpg");
		preparedStatement.setString(index.next(), status);
		preparedStatement.setString(index.next(), sdf.format(date));
	}

	@Override
	public List<User> returnList(ResultSet resultSet) throws SQLException {
		List<User> users = new ArrayList<>();
		while (resultSet.next()) {
			int id = resultSet.getInt("id_user");
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			String username = resultSet.getString("username");
			LocalDate date = resultSet.getDate("date_of_birth").toLocalDate();
			String profilePicture = resultSet.getString("profile_picture");
			String status = resultSet.getString("status");
			User user = new User(null, firstName, lastName, email, username, null, date, status);
			user.setProfilePicture(profilePicture);
			user.setIdUser(id);
			users.add(user);
		}
		return users;
	}

	@Override
	public String returnIdColumnName() {
		return "id_user";
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		User user = new User();
		if (resultSet.next()) {
			user.setIdUser(resultSet.getInt("id_user"));
			user.setFirstName(resultSet.getString("first_name"));
			user.setLastName(resultSet.getString("last_name"));
			user.setEmail(resultSet.getString("email"));
			user.setUsername(resultSet.getString("username"));
			java.sql.Date date = resultSet.getDate("date_of_birth");
		    user.setDateOfBirth(date.toLocalDate());
		    user.setProfilePicture(resultSet.getString("profile_picture"));
		    user.setStatus(resultSet.getString("status"));
		    return user;
		}
		return null;
	}
	
	@Override
	public int returnIdNumber() {
		return idUser;
	}
	
	@Override
	public String returnColumnsForUpdate() {
		return "profile_picture = ?";
	}
	
	@Override
	public void preparedStatementUpdate(PreparedStatement preparedStatement, Position index) throws SQLException {
		preparedStatement.setString(index.next(), profilePicture);
		preparedStatement.setInt(index.next(), idUser);
	}
	
	private String hashPassword(String password) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		return generatedSecuredPasswordHash;
	}

	public boolean checkPassword(String password, String hash_password) {
		return BCrypt.checkpw(password, hash_password);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + idUser;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idUser != other.idUser)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String returnBookingJoin() throws SQLException {
		String join =  "SELECT * FROM bookings"
					+ " JOIN property ON property.id_property = bookings.id_property"
					+ " JOIN booked_room ON booked_room.id_booking = bookings.id_booking"
					+ " WHERE bookings.id_user = ?";
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
			
			Property property = new Property();
			property.setIdProperty(rs.getInt("id_property"));
			property.setType(rs.getString("type"));
			property.setName(rs.getString("name"));
			booking.setProperty(property);
			
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
}
