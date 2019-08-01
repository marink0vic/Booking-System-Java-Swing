package com.comtrade.sysoperation.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.constants.ImageFolder;
import com.comtrade.domain.User;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class UpdateUserSO extends GeneralSystemOperation<GenericClass<User>> {

	@Override
	protected void executeSpecificOperation(GenericClass<User> object) throws SQLException {
		User user = object.getDomain();
		String imageServerPath = saveImageToFolder(user);
		user.setProfilePicture(imageServerPath);
		IBroker ib = new Broker();
		ib.update(user);
	}

	private String saveImageToFolder(User user) {
		String folder = ImageFolder.IMAGE_REGULAR_USER_FOLDER.getPath();
		File f = new File(user.getProfilePicture());
		File f2 = new File(folder + "/" + f.getName());
		File pathForDatabase = new File(folder + "/" + user.getUsername() + ".jpg");
		f2.renameTo(pathForDatabase);
		String image = pathForDatabase.getPath();
		try {
			java.nio.file.Files.copy(f.toPath(), pathForDatabase.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image.substring(1);
	}

}
