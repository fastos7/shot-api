package com.telstra.health.shot.dao;

import java.util.List;

import com.telstra.health.shot.dao.exception.DataAccessException;
import com.telstra.health.shot.entity.HomePageActions;

public interface CommonDAO {

	List getAppWebPageAceeses() throws DataAccessException;

	List<HomePageActions> getHomePageActions() throws DataAccessException;

	List findURLRoleAccesses();
}
