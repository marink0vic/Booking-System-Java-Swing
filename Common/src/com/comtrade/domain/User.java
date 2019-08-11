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

import com.comtrade.constants.ServerResourcePath;
import com.comtrade.domain.behavior.DomainJoinBookings;
import com.comtrade.domain.behavior.DomainList;
import com.comtrade.domain.behavior.DomainUpdate;
import com.comtrade.domain.behavior.GeneralDomain;

import crypt.BCrypt;

public class User implements DomainUpdate, DomainJoinBookings, DomainList, Serializable {

	
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
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, country.getIdCountry());
		preparedStatement.setString(2, firstName);
		preparedStatement.setString(3, lastName);
		preparedStatement.setString(4, email);
		preparedStatement.setString(5, username);
		preparedStatement.setString(6, hashPassword(password));
		preparedStatement.setDate(7, java.sql.Date.valueOf(dateOfBirth));
		preparedStatement.setString(8, profilePicture);
		preparedStatement.setString(9, status);
		preparedStatement.setString(10, sdf.format(date));
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
	
	public User createUser(ResultSet resultSet) throws SQLException {
		int userId = resultSet.getInt("id_user");
		int countryId = resultSet.getInt("country_id");
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		String email = resultSet.getString("email");
		String username = resultSet.getString("username");
		String image = resultSet.getString("profile_picture");
		String status = resultSet.getString("status");
		LocalDate date = resultSet.getDate("date_of_birth").toLocalDate();
		
		String countryName = resultSet.getString("name");
		String countryImage = ServerResourcePath.SERVER_RESOURCES_PATH.getPath() + resultSet.getString("image");
		Country c = new Country(countryId, countryName, countryImage);
		User user = new User(c, firstName, lastName, email, username, null, date, status);
		
		user.setProfilePicture(ServerResourcePath.SERVER_RESOURCES_PATH.getPath() + image);
		user.idUser = userId;
		
		return user;
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
	public void preparedStatementUpdate(PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(1, profilePicture);
		preparedStatement.setInt(2, idUser);
	}
	
	private String hashPassword(String password) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		return generatedSecuredPasswordHash;
	}

	public boolean checkPassword(String password, String hash_password) {
		return BCrypt.checkpw(password, hash_password);
	}
	
	@Override
	public String prepareJoin() throws SQLException {
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
}
