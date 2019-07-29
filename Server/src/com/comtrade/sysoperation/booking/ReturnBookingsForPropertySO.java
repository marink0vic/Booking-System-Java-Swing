package com.comtrade.sysoperation.booking;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnBookingsForPropertySO extends GeneralSystemOperation<GenericClass<PropertyWrapper>> {

	@Override
	protected void executeSpecificOperation(GenericClass<PropertyWrapper> generic) throws SQLException {
		PropertyWrapper pw = generic.getDomain();
		Map<Booking, List<BookedRoom>> newBookings = ServerData.getInstance().returnBookingsForProperty(pw.getProperty());
		pw.setBookings(newBookings);
	}

}
