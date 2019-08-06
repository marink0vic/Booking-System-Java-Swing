package com.comtrade.serverdata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comtrade.constants.UserType;
import com.comtrade.domain.BookedRoom;
import com.comtrade.domain.Booking;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.threads.ClientThread;
import com.comtrade.transfer.TransferClass;

public class UserActiveThreads implements ActiveThreads {

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
	
	@Override
	public void register(User user, ClientThread thread) {
		if (user.getStatus().equals(UserType.USER.getAccess())) {
			userThreads.put(user, thread);
		} else if (user.getStatus().equals(UserType.SUPER_USER.getAccess())) {
			hostThreads.put(user, thread);
		}
	}
	
	@Override
	public void removeThread(ClientThread clientThread) {
		for (Map.Entry<User, ClientThread> entry : userThreads.entrySet()) {
			ClientThread temp = entry.getValue();
			if (temp == clientThread) {
				unregister(entry.getKey());
				return;
			}
		}
		
		for (Map.Entry<User, ClientThread> entry : hostThreads.entrySet()) {
			ClientThread temp = entry.getValue();
			if (temp == clientThread) {
				unregister(entry.getKey());
				return;
			}
		}
	}
	
	@Override
	public void notify(PropertyWrapper wrapper) {
		notifyHost(wrapper);
		notifyUsers(wrapper.getBookings());
	}

	private void notifyUsers(Map<Booking, List<BookedRoom>> bookings) {
		for (Map.Entry<User, ClientThread> entry : userThreads.entrySet()) {
			entry.getValue().sendToUsers(bookings);
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
