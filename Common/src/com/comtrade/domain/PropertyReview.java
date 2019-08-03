package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class PropertyReview implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int idPropertyReview;
	private int idBooking;
	private int idProperty;
	private User user;
	private int rating;
	private String comment;
	private LocalDateTime created;
	
	public PropertyReview() {
		
	}

	public PropertyReview(int idBooking, int idProperty, User user, int rating, String comment) {
		this.idBooking = idBooking;
		this.idProperty = idProperty;
		this.user = user;
		this.rating = rating;
		this.comment = comment;
	}

	public int getIdPropertyReview() {
		return idPropertyReview;
	}

	public void setIdPropertyReview(int idPropertyReview) {
		this.idPropertyReview = idPropertyReview;
	}

	public int getIdBooking() {
		return idBooking;
	}

	public void setIdBooking(int idBooking) {
		this.idBooking = idBooking;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public int returnIdNumber() {
		return idPropertyReview;
	}

	@Override
	public String returnTableName() {
		return "property_review";
	}

	@Override
	public String returnColumnNames() {
		return " (id_user, id_property, id_booking, rating, comment, created ) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?,?,?,?)";
	}

	@Override
	public String returnIdColumnName() {
		return "id_review";
	}

	@Override
	public void preparedStatementInsert(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(1, user.getIdUser());
		preparedStatement.setInt(2, idProperty);
		preparedStatement.setInt(3, idBooking);
		preparedStatement.setInt(4, rating);
		preparedStatement.setString(5, comment);
		preparedStatement.setString(6, sdf.format(date));
	}

	@Override
	public List<? extends GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
