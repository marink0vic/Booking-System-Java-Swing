package com.comtrade.controller.user;

import java.sql.SQLException;

import com.comtrade.constants.Operations;
import com.comtrade.controller.IControllerBL;
import com.comtrade.domain.GeneralDomain;
import com.comtrade.domain.User;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.sysoperation.user.LoginUserSO;
import com.comtrade.sysoperation.user.SaveUserSO;
import com.comtrade.transfer.TransferClass;

public class ControllerBLUser implements IControllerBL {

	@Override
	public TransferClass executeOperation(TransferClass sender) {
		Operations operation = sender.getOperation();
		TransferClass receiver = new TransferClass();
		
		switch (operation) {
		case SAVE_USER:
		{
			GeneralDomain user = (GeneralDomain) sender.getClientRequest();
			GeneralDomain returnedUser = null;
			try {
				returnedUser = saveUser(user);
				receiver.setServerResponse(returnedUser);
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
				if (returnedUser == null) {
					receiver.setMessageResponse("Entered information does not exist in the database");
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

	private GeneralDomain saveUser(GeneralDomain user) throws SQLException {
		GenericClass<GeneralDomain> genericClass = new GenericClass<>(user);
		GeneralSystemOperation<GenericClass<GeneralDomain>> sysOperation = new SaveUserSO();
		sysOperation.executeSystemOperation(genericClass);
		return  genericClass.getDomain();
	}

}
