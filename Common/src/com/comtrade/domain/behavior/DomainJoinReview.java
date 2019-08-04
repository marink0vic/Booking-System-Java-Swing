package com.comtrade.domain.behavior;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.comtrade.domain.PropertyReview;

public interface DomainJoinReview extends DomainJoin {
	List<PropertyReview> returnJoinTables(ResultSet rs) throws SQLException;
}
