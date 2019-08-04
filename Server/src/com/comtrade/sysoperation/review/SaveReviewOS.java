package com.comtrade.sysoperation.review;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.PropertyReview;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.serverdata.ServerData;
import com.comtrade.serverdata.UserActiveThreads;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveReviewOS extends GeneralSystemOperation<PropertyWrapper> {

	@Override
	protected void executeSpecificOperation(PropertyWrapper wrapper) throws SQLException {
		PropertyReview review = wrapper.getReviews().get(0);
		IBroker iBroker = new Broker();
		iBroker.save(review);
		
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				for (PropertyWrapper pw : ServerData.getInstance().returnAllProperties()) {
					if (review.getIdProperty() == pw.getProperty().getIdProperty()) {
						pw.addNewReview(review);
						break;
					}
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				UserActiveThreads.getActiveThreads().notifyHost(wrapper);
			}
		});
		
		t2.start();
		t.start();
	}

}
