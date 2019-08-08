package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.comtrade.domain.behavior.DomainJoinReview;
import com.comtrade.domain.behavior.GeneralDomain;

public class PropertyReview implements GeneralDomain, DomainJoinReview, Serializable {

	
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
	public void preparedStatementInsert(PreparedStatement preparedStatement) throws SQLException {
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
	public String prepareJoin() throws SQLException {
		String join =  "SELECT * FROM property_review"
					+ " JOIN user ON user.id_user = property_review.id_user"
					+ " JOIN country on user.country_id = country.id_country"
					+ " WHERE property_review.id_property = ?";
		return join;
	}

	@Override
	public List<PropertyReview> returnJoinTables(ResultSet rs) throws SQLException {
		List<PropertyReview> reviews = new ArrayList<>();
		while (rs.next()) {
			User user = new User().createUser(rs);
			int idReview = rs.getInt("id_review");
			int idBooking = rs.getInt("id_booking");
			int idProperty = rs.getInt("id_property");
			int rating = rs.getInt("rating");
			String comment = rs.getString("comment");
			
			PropertyReview pr = new PropertyReview(idBooking, idProperty, user, rating, comment);
			pr.setIdPropertyReview(idReview);
			
			reviews.add(pr);
		}
		return reviews;
	}


}
