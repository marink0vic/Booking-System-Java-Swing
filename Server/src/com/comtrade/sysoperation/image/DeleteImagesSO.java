package com.comtrade.sysoperation.image;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.PropertyImage;
import com.comtrade.generics.GenericList;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class DeleteImagesSO extends GeneralSystemOperation<GenericList<PropertyImage>> {

	@Override
	protected void executeSpecificOperation(GenericList<PropertyImage> object) throws SQLException {
		List<PropertyImage> imagesForDeletion = object.getList();
		deleteImages(imagesForDeletion);
	}

	private void deleteImages(List<PropertyImage> images_for_deletion) throws SQLException {
		IBroker iBroker = new Broker();
		for (PropertyImage propertyImage : images_for_deletion) {
			Path pathForDeletion = Paths.get(propertyImage.getImage());
			
			try {
				java.nio.file.Files.delete(pathForDeletion);
			} catch (IOException e) {
				e.printStackTrace();
			}
			iBroker.delete(propertyImage);
		}
	}

}
