package com.comtrade.view.login;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.User;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.user.host.PropertyOwnerFrame;

public class ProxyLogin implements IProxy {

	@Override
	public void login(User user) {
		IProxy proxy;
		TransferClass transferClass = null;
		try {
			transferClass = ControllerUI.getController().returnUser(user);
			user = (User) transferClass.getServerResponse();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		if (user != null) {
			if (user.getStatus().equals("ADMIN")) {
				JOptionPane.showMessageDialog(null, "You are admin");
			} else if (user.getStatus().equals("SUPER_USER")) {
				proxy = new PropertyOwnerFrame(user);
				proxy.login(user);
			} else if (user.getStatus().equals("USER")) {
				JOptionPane.showMessageDialog(null, "You are regular user");
			} else {
				JOptionPane.showMessageDialog(null, "Something went wrong");
			}
		} else {
			JOptionPane.showMessageDialog(null, transferClass.getMessageResponse());
		}

	}

}
