package com.comtrade.view.login;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.comtrade.constants.DomainType;
import com.comtrade.constants.Operations;
import com.comtrade.constants.UserType;
import com.comtrade.controller.ControllerUI;
import com.comtrade.domain.User;
import com.comtrade.transfer.TransferClass;
import com.comtrade.view.MainForm;
import com.comtrade.view.user.host.PropertyOwnerFrame;
import com.comtrade.view.user.regular.UserHomeFrame;

public class ProxyLogin implements IProxy {
	
	private MainForm mainForm;
	public ProxyLogin() {
		
	}
	
	public ProxyLogin(MainForm main) {
		this.mainForm = main;
	}

	@Override
	public void login(User user) {
		IProxy proxy;
		TransferClass transferClass = new TransferClass();
		transferClass.setClientRequest(user);
		transferClass.setDomainType(DomainType.USER);
		transferClass.setOperation(Operations.LOGIN_USER);
		
		ControllerUI.getController().sendToServer(transferClass);
		user = ControllerUI.getController().getUser();
		
		if (user != null) {
			if (user.getStatus().equals(UserType.ADMIN.getAccess())) {
				JOptionPane.showMessageDialog(null, "You are admin");
			} else if (user.getStatus().equals(UserType.SUPER_USER.getAccess())) {
				proxy = new PropertyOwnerFrame(user);
				proxy.login(user);
			} else if (user.getStatus().equals(UserType.USER.getAccess())) {
				proxy = new UserHomeFrame(user);
				proxy.login(user);
			} else {
				JOptionPane.showMessageDialog(null, "Something went wrong");
			}
			mainForm.dispose();
		} else {
			JOptionPane.showMessageDialog(null, ControllerUI.getController().getMessageResponse());
		}

	}

}
