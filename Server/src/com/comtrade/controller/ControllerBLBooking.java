package com.comtrade.controller;

import java.sql.SQLException;
import java.util.Set;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.domain.Booking;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.booking.ReturnBookingsForPropertySO;
import com.comtrade.sysoperation.booking.SaveBookingSO;
import com.comtrade.sysoperation.booking.UpdateBookingSO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLBooking implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		TransferClass receiver = new TransferClass();
		Operations operation = sender.getOperation();
		
		switch (operation) {
		case SAVE:
		{
			PropertyWrapper propertyWrapper = (PropertyWrapper) sender.getClientRequest();
			PropertyWrapper savedBooking;
			try {
				savedBooking = saveBooking(propertyWrapper);
				receiver.setServerResponse(savedBooking);
				receiver.setMessageResponse("Booking saved");
			} catch (SQLException e) {
				receiver.setServerResponse(null);
				receiver.setMessageResponse(e.getMessage());
				e.printStackTrace();
			}
			receiver.setDomainType(DomainType.BOOKING);
			receiver.setOperation(Operations.SAVE);
			return receiver;
		}
		case UPDATE:
		{
			PropertyWrapper pw = (PropertyWrapper) sender.getClientRequest();
			PropertyWrapper updated;
			try {
				updated = updateBooking(pw);
				receiver.setServerResponse(updated);
				receiver.setDomainType(DomainType.BOOKING);
				receiver.setOperation(Operations.UPDATE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return receiver;
		}
		case RETURN_BOOKING_FOR_PROPERTY:
		{
			PropertyWrapper propertyWrapper = (PropertyWrapper) sender.getClientRequest();
			PropertyWrapper newBookings;
			
			try {
				newBookings = returnBookingsForPropety(propertyWrapper);
				receiver.setServerResponse(newBookings);
				receiver.setMessageResponse("bookings list updated");
			} catch (SQLException e) {
				receiver.setMessageResponse("Could not retrive bookings");
				e.printStackTrace();
			}
			return receiver;
		}

		default:
			return null;
		}
		
	}

	private PropertyWrapper returnBookingsForPropety(PropertyWrapper propertyWrapper) throws SQLException {
		GenericClass<PropertyWrapper> generic  = new GenericClass<>(propertyWrapper);
		GeneralSystemOperation<GenericClass<PropertyWrapper>> sysOperation = new ReturnBookingsForPropertySO();
		sysOperation.executeSystemOperation(generic);
		return generic.getDomain();
	}

	private PropertyWrapper saveBooking(PropertyWrapper propertyWrapper) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new SaveBookingSO();
		try {
			sysOperation.executeSystemOperation(propertyWrapper);
		} catch (SQLException e) {
			throw new SQLException("Something went wrong, please check room availability");
		}
		return propertyWrapper;
	}
	
	private PropertyWrapper updateBooking(PropertyWrapper pw) throws SQLException {
		GeneralSystemOperation<PropertyWrapper> sysOperation = new UpdateBookingSO();
		sysOperation.executeSystemOperation(pw);
		return pw;
	}

}
