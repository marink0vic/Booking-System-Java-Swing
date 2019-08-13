package com.comtrade.broker;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.PropertyReview;
import com.comtrade.domain.User;
import com.comtrade.domain.behavior.DomainJoin;
import com.comtrade.domain.behavior.DomainList;
import com.comtrade.domain.behavior.DomainUpdate;
import com.comtrade.domain.behavior.GeneralDomain;
import com.comtrade.dto.PropertyWrapper;

public interface IBroker {
	int save(GeneralDomain domain) throws SQLException;
	void delete(GeneralDomain domain) throws SQLException;
	void update(DomainUpdate domain) throws SQLException;
	void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException;
	void updateCollectionOfData(List<? extends DomainUpdate> list) throws SQLException;
	List<? extends GeneralDomain> returnAllData(DomainList domain) throws SQLException;
	
	void insertPropertyForOwner(PropertyWrapper wrapper) throws SQLException;
	List<PropertyImage> returnPropertyImages(int id_property) throws SQLException;
	User login(User user) throws SQLException;
	
	Map<Booking, List<BookedRoom>> returnBookings(DomainJoin domain_join, int id_domain) throws SQLException;
	List<PropertyReview> returnPropertyReviews(DomainJoin domain_join, int id_domain) throws SQLException;
}
