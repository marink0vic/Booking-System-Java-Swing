package com.comtrade.sysoperation.review;

import java.sql.SQLException;

import com.comtrade.broker.Broker;
import com.comtrade.broker.IBroker;
import com.comtrade.domain.PropertyReview;
import com.comtrade.generics.GenericClass;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class SaveReviewOS extends GeneralSystemOperation<GenericClass<PropertyReview>> {

	@Override
	protected void executeSpecificOperation(GenericClass<PropertyReview> generic) throws SQLException {
		PropertyReview review = generic.getDomain();
		IBroker iBroker = new Broker();
		iBroker.save(review);
	}

}
