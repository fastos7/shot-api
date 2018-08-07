package com.telstra.health.shot.resources;

import com.telstra.health.shot.entity.ShotBatch;

public class DummyShotBatch {

	public static ShotBatch getTestShotBatch() {

		ShotBatch shotBatch = null;
		shotBatch = new ShotBatch();
		return shotBatch;
	}

}
