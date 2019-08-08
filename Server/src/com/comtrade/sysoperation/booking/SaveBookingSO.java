package com.comtrade.sysoperation.booking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.Room;
import com.comtrade.domain.RoomType;
import com.comtrade.domain.Transaction;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.dto.UserWrapper;
import com.comtrade.lock.DbLock;
import com.comtrade.serverdata.ActiveThreads;
import com.comtrade.serverdata.ServerData;
import com.comtrade.serverdata.UserActiveThreads;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveBookingSO extends GeneralSystemOperation<PropertyWrapper> {

	private IBroker iBroker = new Broker();
	private DbLock dbLock = DbLock.getInstance();
	private ServerData server = ServerData.getInstance();
	
	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		saveReservation(wrapper);
	}
	private void saveReservation(PropertyWrapper wrapper) throws SQLException {
		Map.Entry<Booking, List<BookedRoom>> bookings = wrapper.getBookings().entrySet().iterator().next();
		Transaction transaction = wrapper.getTransactions().get(0);
		Set<RoomType> types = wrapper.getRooms().keySet();
		dbLock.lock();
		try {
			if (!checkRoomAvailability(bookings, types)) {
				throw new SQLException();
			}
			//---saving to database
			Booking booking = bookings.getKey();
			booking.setIdBooking(saveBooking(booking));
			booking.setStatus("PENDING");
			
			saveRooms(booking.getIdBooking(), bookings.getValue());
			transaction.setIdBooking(booking.getIdBooking());
			transaction.setIdTransaction(saveTransaction(transaction));
			wrapper.setTransactions(null);
			
			//---saving to server and notify logged users
			notifyUsers(wrapper.getUser(), booking, bookings.getValue(), transaction);
			addOwnerBookingOnServer(booking, bookings.getValue(), transaction);
		} finally {
			dbLock.unlock();
		}
	}
	
	private void notifyUsers(User user, Booking booking, List<BookedRoom> bookedRooms, Transaction tr) {
		PropertyWrapper pw = new PropertyWrapper();
		pw.addNewBooking(booking, bookedRooms);
		pw.addNewTransaction(tr);
		pw.setUser(user);
		UserActiveThreads.getActiveThreads().notify(pw);
	}
	private void addOwnerBookingOnServer(Booking booking, List<BookedRoom> bookedRooms, Transaction tr) {
		server.addNewTransaction(tr);
		for (PropertyWrapper pw : server.returnAllProperties()) {
			if (booking.getProperty().getIdProperty() == pw.getProperty().getIdProperty()) {
				pw.addNewBooking(booking, bookedRooms);
				pw.addNewTransaction(tr);
				break;
			}
		}
	}
	
	private boolean checkRoomAvailability(Entry<Booking, List<BookedRoom>> bookings, Set<RoomType> types) throws SQLException {
		Booking client = bookings.getKey();
		List<BookedRoom> rooms = bookings.getValue();
		
		Map<Integer, Integer> reservedRooms = returnReservedRoomsCount(client);
		
		outer:for (BookedRoom bookedRoom : rooms) {
			if (reservedRooms.containsKey(bookedRoom.getIdRoomType())) {
				for (RoomType type : types) {
					if (type.getIdRoomType() == bookedRoom.getIdRoomType()) {
						int numOfRooms = type.getNumberOfRooms();
						int avaiableRooms = numOfRooms - reservedRooms.get(bookedRoom.getIdRoomType());
						if (avaiableRooms - bookedRoom.getNumberOfRooms() < 0) return false;
						continue outer;
					}
				}
			}
		}
		
		return true;
	}
	private Map<Integer, Integer> returnReservedRoomsCount(Booking client) {
		Map<Integer, Integer> reservedRooms = new HashMap<>();
		for (PropertyWrapper pw : server.returnAllProperties()) {
			if (client.getProperty().getIdProperty() == pw.getProperty().getIdProperty()) {
				for (Map.Entry<Booking, List<BookedRoom>> savedBooking : pw.getBookings().entrySet()) {
					Booking server = savedBooking.getKey();
					if (checkDates(client, server)) continue;
					addToReservedCount(reservedRooms, savedBooking.getValue());
				}
				break;
			}
		}
		return reservedRooms;
	}
	private void addToReservedCount(Map<Integer, Integer> reserved_rooms, List<BookedRoom> booked_rooms) {
		for (BookedRoom room : booked_rooms) {
			int id = room.getIdRoomType();
			int occupiedRooms = room.getNumberOfRooms();
			if (reserved_rooms.containsKey(id)) {
				occupiedRooms += reserved_rooms.get(id);
			}
			reserved_rooms.put(id,occupiedRooms);
		}
												
	}
	private boolean checkDates(Booking client, Booking server) {
		if (client.getCheckIn().isAfter(server.getCheckOut()) || client.getCheckOut().isBefore(server.getCheckIn())) {
			return true;
		}
		return false;
	}
	
	private int saveTransaction(Transaction transaction) throws SQLException {
		return iBroker.save(transaction);
	}
	
	private void saveRooms(int idBooking, List<BookedRoom> list) throws SQLException {
		for (int i = 0; i < list.size(); i++) {
			BookedRoom br = list.get(i);
			br.setIdBooking(idBooking);
			br.setIdBookedRoom(iBroker.save(br));
		}
	}
	
	private int saveBooking(Booking booking) throws SQLException {
		return iBroker.save(booking);
	}

}
