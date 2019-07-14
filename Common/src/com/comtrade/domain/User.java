package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import crypt.BCrypt;

public class User implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idUser;
	private int idCountry;
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

	public User(int idCountry, String firstName, String lastName, String email, String username, String password,
			LocalDate dateOfBirth, String status) {
		super();
		this.idCountry = idCountry;
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

	public int getIdCountry() {
		return idCountry;
	}

	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
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
	public void fillStatementWithValues(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idCountry);
		preparedStatement.setString(index.next(), firstName);
		preparedStatement.setString(index.next(), lastName);
		preparedStatement.setString(index.next(), email);
		preparedStatement.setString(index.next(), username);
		preparedStatement.setString(index.next(), hashPassword(password));
		preparedStatement.setDate(index.next(), java.sql.Date.valueOf(dateOfBirth));
		preparedStatement.setString(index.next(), profilePicture);
		preparedStatement.setString(index.next(), status);
		preparedStatement.setString(index.next(), sdf.format(date));
	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		return null;
	}

	@Override
	public String returnIdColumnName() {
		return "id_user";
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		User user = new User();
		if (resultSet.next()) {
			user.setIdUser(resultSet.getInt(1));
			user.setIdCountry(resultSet.getInt(2));
			user.setFirstName(resultSet.getString(3));
			user.setLastName(resultSet.getString(4));
			user.setEmail(resultSet.getString(5));
			user.setUsername(resultSet.getString(6));
			user.setPassword(resultSet.getString(7));
			java.sql.Date date = resultSet.getDate(8);
		    user.setDateOfBirth(date.toLocalDate());
		    user.setProfilePicture(resultSet.getString(9));
		    user.setStatus(resultSet.getString(10));
		    return user;
		}
		return null;
	}
	private String hashPassword(String password) {
		String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(12));
		return generatedSecuredPasswordHash;
	}

	public boolean checkPassword(String password, String hash_password) {
		return BCrypt.checkpw(password, hash_password);
	}

}
