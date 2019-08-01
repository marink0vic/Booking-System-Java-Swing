package com.comtrade.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DomainJoin extends GeneralDomain {

	String returnBookingJoin() throws SQLException;
	Map<Booking, List<BookedRoom>> returnJoinTables(ResultSet rs) throws SQLException;
}
