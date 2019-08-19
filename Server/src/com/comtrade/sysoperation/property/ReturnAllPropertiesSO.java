package com.comtrade.sysoperation.property;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import com.comtrade.domain.PropertyReview;
import com.comtrade.dto.PropertyWrapper;
import com.comtrade.generics.GenericList;
import com.comtrade.serverdata.ServerData;
import com.comtrade.sysoperation.GeneralSystemOperation;

public class ReturnAllPropertiesSO extends GeneralSystemOperation<GenericList<PropertyWrapper>> {

	@Override
	protected void executeSpecificOperation(GenericList<PropertyWrapper> listWrapper) throws SQLException {
		List<PropertyWrapper> listOfProperties = ServerData.getInstance().returnAllProperties();
		sortProperties(listOfProperties);
		listWrapper.setList(listOfProperties);
	}

	private void sortProperties(List<PropertyWrapper> list_of_properties) {
		list_of_properties.sort(new Comparator<PropertyWrapper>() {
			@Override
			public int compare(PropertyWrapper wrapper1, PropertyWrapper wrapper2) {
				Double avg1 = calculateAverage(wrapper1.getReviews());
				Double avg2 = calculateAverage(wrapper2.getReviews());

				if (avg1 >= avg2) return compareNumberOfGuests(wrapper1, wrapper2) ? -1 : 1;
				if (avg1 <= avg2) return compareNumberOfGuests(wrapper2, wrapper1) ? 1 : -1;
				else return 0;
			}
		});
	}
	
	private boolean compareNumberOfGuests(PropertyWrapper wrapper1, PropertyWrapper wrapper2) {
		int bookings1 = wrapper1.getBookings().size();
		int bookings2 = wrapper2.getBookings().size();
		if (bookings1 >= bookings2) return true;
		int percent = (int)(bookings1 * 100 / bookings2);
		return percent > 70;
	}

	private double calculateAverage(List<PropertyReview> reviews) {
		int sum = reviews.stream().mapToInt(v -> v.getRating()).sum();
		if (sum == 0) return 0.0;
		return (double) sum / reviews.size();
	}

}
