package com.comtrade.sysoperation.image;

import java.sql.SQLException;
import java.util.List;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.PropertyImage;
import com.comtrade.domain.User;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.sysoperation.GeneralSystemOperation;
import com.comtrade.util.ImageProcessing;

public class SaveImagesSO extends GeneralSystemOperation<PropertyWrapper> {
	
	
	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		IBroker ib = new Broker();
		User user = wrapper.getUser();
		int propertyId = wrapper.getImages().get(0).getIdProperty();
		
		List<PropertyImage> newImages = ImageProcessing.formatServerPath(wrapper.getImages(), propertyId, user.getUsername());
		ib.saveCollectionOfData(newImages);
		wrapper.setImages(ib.returnPropertyImages(propertyId));
	}
}
