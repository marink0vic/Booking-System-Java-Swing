package com.comtrade.sysoperation.user;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.constants.ImageFolder;
import com.comtrade.constants.ServerResourcePath;
import com.comtrade.domain.User;
import com.comtrade.domain.behavior.GeneralDomain;
import com.comtrade.generics.GenericClass;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveUserSO extends GeneralSystemOperation<GenericClass<User>> {

	@Override
	protected void executeSpecificOperation(GenericClass<User> generic) throws SQLException {
		IBroker iBroker = new Broker();
		User user = generic.getDomain();
		
		if (ServerData.getInstance().existsInDatabase(user)) {
			generic.setDomain(new User());
		} else {
			user.setProfilePicture(ImageFolder.IMAGE_DEFAULT_USER_PROFILE_PICTURE.getPath());
			int id = iBroker.save(user);
			user.setIdUser(id);
			user.setProfilePicture(ServerResourcePath.SERVER_RESOURCES_PATH.getPath() + ImageFolder.IMAGE_DEFAULT_USER_PROFILE_PICTURE.getPath());
			
			ServerData.getInstance().addNewUser(user);
		}
	}

}
