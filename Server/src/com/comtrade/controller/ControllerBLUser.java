package com.comtrade.controller;

import java.sql.SQLException;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.User;
import com.comtrade.generics.GenericClass;
import com.comtrade.serverdata.UserActiveThreads;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.user.LoginUserSO;
import com.comtrade.sysoperation.user.SaveUserSO;
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
			GeneralDomain user = (GeneralDomain) sender.getClientRequest();
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

	@SuppressWarnings("unchecked")
	private <T extends GeneralDomain> T saveUser(GeneralDomain user) throws SQLException {
		GenericClass<GeneralDomain> genericClass = new GenericClass<>(user);
		GeneralSystemOperation<GenericClass<GeneralDomain>> sysOperation = new SaveUserSO();
		sysOperation.executeSystemOperation(genericClass);
		return  (T) genericClass.getDomain();
	}

}
