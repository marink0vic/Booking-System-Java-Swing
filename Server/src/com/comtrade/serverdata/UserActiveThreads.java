package com.comtrade.serverdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.constants.UserType;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.threads.ClientThread;
import com.comtrade.view.ServerForm;

public class UserActiveThreads {

	private static volatile UserActiveThreads activeThreads;
	private Map<User, ClientThread> userThreads;
	private Map<User, ClientThread> hostThreads;
	
	private UserActiveThreads() {
		userThreads = new HashMap<>();
		hostThreads = new HashMap<>();
	}
	
	public static UserActiveThreads getActiveThreads() {
		if (activeThreads == null) {
			synchronized (UserActiveThreads.class) {
				if (activeThreads == null) {
					activeThreads = new UserActiveThreads();
				}
			}
		}
		return activeThreads;
	}
	
	public Map<User, ClientThread> getUserThreads() {
		return userThreads;
	}

	public Map<User, ClientThread> getHostThreads() {
		return hostThreads;
	}

	public void register(User user, ClientThread thread) {
		if (user.getStatus().equals(UserType.USER.getAccess())) {
			userThreads.put(user, thread);
		} else if (user.getStatus().equals(UserType.SUPER_USER.getAccess())) {
			hostThreads.put(user, thread);
		}
		String txt = "User " + user.getUsername() + " has logged in\n";
		ServerForm.txtAreaServer.append(txt);
	}
	
	
	public void removeThread(ClientThread clientThread) {
		for (Map.Entry<User, ClientThread> entry : userThreads.entrySet()) {
			ClientThread temp = entry.getValue();
			if (temp == clientThread) {
				User user = entry.getKey();
				unregister(user);
				String txt = "User " + user.getUsername() + " has logged out\n";
				ServerForm.txtAreaServer.append(txt);
				return;
			}
		}
		
		for (Map.Entry<User, ClientThread> entry : hostThreads.entrySet()) {
			ClientThread temp = entry.getValue();
			if (temp == clientThread) {
				unregister(entry.getKey());
				String txt = "User " + entry.getKey().getUsername() + " has logged out\n";
				ServerForm.txtAreaServer.append(txt);
				return;
			}
		}
	}
	
	
	public void notify(PropertyWrapper wrapper) {
		notifyHost(wrapper);
		notifyUsers(wrapper.getBookings());
	}

	private void notifyUsers(Map<Booking, List<BookedRoom>> bookings) {
		for (Map.Entry<User, ClientThread> entry : userThreads.entrySet()) {
			entry.getValue().notifyLogedUsersWithNewBookings(bookings);
		}
	}

	public void notifyHost(PropertyWrapper wrapper) {
		for (Map.Entry<User, ClientThread> entry : hostThreads.entrySet()) {
			if (wrapper.getUser().equals(entry.getKey())) {
				entry.getValue().sendToHost(wrapper);
				break;
			}
		}
	}
	
	public void notifyUsersForAcceptedBookings(List<Booking> accepted_bookings) {
		for (Map.Entry<User, ClientThread> entry : userThreads.entrySet()) {
			List<Booking> myList = new ArrayList<>();
			User u = entry.getKey();
			for (Booking b : accepted_bookings) {
				if (u.getIdUser() == b.getUser().getIdUser()) myList.add(b);
			}
			entry.getValue().sendUpdatedBookingsToClient(myList);
		}
	}
	
	private void unregister(User user) {
		if (user.getStatus().equals(UserType.USER.getAccess())) {
			userThreads.remove(user);
		} else if (user.getStatus().equals(UserType.SUPER_USER.getAccess())) {
			hostThreads.remove(user);
		}
	}

	public void sendMessage(User sender, User receiver, String message) {
		if (sender.getStatus().equals(UserType.USER.getAccess())) {
			sendMessage(sender, receiver, message, hostThreads);
		} else if (sender.getStatus().equals(UserType.SUPER_USER.getAccess())) {
			sendMessage(sender, receiver, message, userThreads);
		}
		
	}

	private void sendMessage(User sender, User receiver, String message, Map<User, ClientThread> active_threads) {
		for (User user : active_threads.keySet()) {
			if (user.getIdUser() == receiver.getIdUser()) {
				ClientThread clientThread = active_threads.get(user);
				clientThread.forwardMessage(sender, message);
				break;
			}
		}
	}

}
