package com.comtrade.controller;

import com.comtrade.transfer.TransferClass;

public interface IControllerBL {

	TransferClass executeOperation(TransferClass sender);
}
