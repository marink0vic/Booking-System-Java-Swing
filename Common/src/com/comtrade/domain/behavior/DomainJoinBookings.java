package com.comtrade.domain.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;

public interface DomainJoinBookings extends DomainJoin {
	Map<Booking, List<BookedRoom>> returnJoinTables(ResultSet rs) throws SQLException;
}
