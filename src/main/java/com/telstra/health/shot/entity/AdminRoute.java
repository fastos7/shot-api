package com.telstra.health.shot.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AdminRoute {

	@Column(name = "RouteId")
	private String routeId;
	
	@Column(name = "RouteName")
	private String routeName;

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
}
