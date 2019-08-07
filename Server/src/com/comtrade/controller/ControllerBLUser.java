package com.comtrade.controller;

import java.sql.SQLException;
import java.util.Map;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.constants.UserType;
import com.comtrade.domain.User;
import com.comtrade.domain.behavior.GeneralDomain;
import com.comtrade.dto.Message;
import com.comtrade.dto.UserWrapper;
import com.comtrade.generics.GenericClass;
import com.comtrade.serverdata.UserActiveThreads;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.user.LoginUserSO;
import com.comtrade.sysoperation.user.SaveUserSO;
import com.comtrade.sysoperation.user.UpdateUserSO;
import com.comtrade.sysoperation.user.UserBookingSO;
import com.comtrade.threads.ClientThread;
import com.comtrade.transfer.TransferClass;

public class ControllerBLUser implements IControllerBL {
	
	private ClientThread clientThread;

	public ControllerBLUser(ClientThread clientThread) {
		this.clientThread = clientThread;
	}

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case SAVE:
		{
			User user = (User) sender.getClientRequest();
			User returnedUser = null;
			try {
				returnedUser = saveUser(user);
				UserActiveThreads.getActiveThreads().register(returnedUser, clientThread);
				
				receiver.setServerResponse(returnedUser);
				receiver.setDomainType(DomainType.USER);
				receiver.setOperation(Operations.SAVE);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while saving user to database");
				e.printStackTrace();
			}
			
			return receiver;
		}
		case LOGIN_USER:
		{
			User user = (User) sender.getClientRequest();
			User returnedUser = null;
			try {
				returnedUser = loginUser(user);
				receiver.setDomainType(DomainType.USER);
				receiver.setOperation(Operations.LOGIN_USER);
				if (returnedUser == null) {
					receiver.setMessageResponse("Entered information does not exist in the database");
				} else {
					UserActiveThreads.getActiveThreads().register(returnedUser, clientThread);
				}
				receiver.setServerResponse(returnedUser);
			} catch (SQLException e) {
				receiver.setMessageResponse("Problem occurred while login user to database");
				e.printStackTrace();
			}
			return receiver;
		}
		case RETURN_BOOKING_FOR_USER:
		{
			UserWrapper userWrapper = (UserWrapper) sender.getClientRequest();
			UserWrapper returnedWrapper = null;
			try {
				returnedWrapper = returnBookingsForUser(userWrapper);
				receiver.setServerResponse(returnedWrapper);
				receiver.setDomainType(DomainType.USER);
				receiver.setOperation(Operations.RETURN_BOOKING_FOR_USER);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return receiver;
		}
		case UPDATE:
		{
			User user = (User) sender.getClientRequest();
			try {
				updateUser(user);
				receiver.setDomainType(DomainType.USER);
				receiver.setOperation(Operations.UPDATE);
				receiver.setMessageResponse("User updated successfully");
			} catch (SQLException e) {
				receiver.setMessageResponse("Update failed");
				e.printStackTrace();
			}
			return receiver;
		}
		case MESSAGE:
		{
			Message message = (Message) sender.getClientRequest();
			UserActiveThreads.getActiveThreads().sendMessage(message.getSender(), message.getReceiver(), message.getMessage());
			receiver.setMessageResponse("Message sent");
			receiver.setDomainType(DomainType.NO_DOMAIN);
			return receiver;
		}
		default:
			return null;
		}
		
	}


	private User loginUser(User user) throws SQLException {
		GenericClass<User> domainUser = new GenericClass<>(user);
		GeneralSystemOperation<GenericClass<User>> sysOperation = new LoginUserSO();
		sysOperation.executeSystemOperation(domainUser);
		return domainUser.getDomain();
	}

	private User saveUser(User user) throws SQLException {
		GenericClass<User> genericClass = new GenericClass<>(user);
		GeneralSystemOperation<GenericClass<User>> sysOperation = new SaveUserSO();
		sysOperation.executeSystemOperation(genericClass);
		return  genericClass.getDomain();
	}
	
	private UserWrapper returnBookingsForUser(UserWrapper user_wrapper) throws SQLException {
		GenericClass<UserWrapper> genericClass = new GenericClass<UserWrapper>(user_wrapper);
		GeneralSystemOperation<GenericClass<UserWrapper>> sysOperation = new UserBookingSO();
		sysOperation.executeSystemOperation(genericClass);
		return genericClass.getDomain();
	}

	private void updateUser(User user) throws SQLException {
		GenericClass<User> domainUser = new GenericClass<>(user);
		GeneralSystemOperation<GenericClass<User>> sysOperation = new UpdateUserSO();
		sysOperation.executeSystemOperation(domainUser);
	}
}
