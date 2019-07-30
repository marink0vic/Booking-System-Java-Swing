package com.comtrade.broker;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.domain.DomainUpdate;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.PaymentType;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.dto.UserWrapper;

public interface IBroker {

	GeneralDomain save(GeneralDomain domain) throws SQLException;
	void delete(GeneralDomain domain) throws SQLException;
	void update(DomainUpdate domain) throws SQLException;
	void saveCollectionOfData(List<? extends GeneralDomain> list) throws SQLException;
	List<? extends GeneralDomain> returnAllData(GeneralDomain domain) throws SQLException;
	void insertPropertyForOwner(PropertyWrapper wrapper) throws SQLException;
	List<PropertyImage> returnPropertyImages(int id_property) throws SQLException;
	List<PaymentType> returnPayments(int id_property) throws SQLException;
	User login(User user) throws SQLException;
	List<User> returnUsers(User user, String status) throws SQLException;
	void insertBookingsForUser(UserWrapper wrapper) throws SQLException;
}
