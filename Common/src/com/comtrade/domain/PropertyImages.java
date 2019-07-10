package com.comtrade.domain;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class PropertyImages implements GeneralDomain, Serializable {

	
	private static final long serialVersionUID = 1L;
	private int IdImage;
	private int idProperty;
	private String image;
	private LocalDateTime created;
	
	public PropertyImages() {
		
	}

	public PropertyImages(int idProperty, String image) {
		super();
		this.idProperty = idProperty;
		this.image = image;
	}

	public int getIdImage() {
		return IdImage;
	}

	public void setIdImage(int idImage) {
		IdImage = idImage;
	}

	public int getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(int idProperty) {
		this.idProperty = idProperty;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public String returnTableName() {
		return "property_images";
	}

	@Override
	public String returnColumnNames() {
		return " (id_property, image, created) VALUES ";
	}

	@Override
	public String returnStatementPlaceholder() {
		return "(?,?,?)";
	}

	@Override
	public void fillStatementWithValues(PreparedStatement preparedStatement, Position index) throws SQLException {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		preparedStatement.setInt(index.next(), idProperty);
		preparedStatement.setString(index.next(), image);
		preparedStatement.setString(index.next(), sdf.format(date));

	}

	@Override
	public List<GeneralDomain> returnList(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String returnIdColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GeneralDomain returnLastInsertedObject(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
