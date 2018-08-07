package com.telstra.health.shot.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.telstra.health.shot.util.ShotUtils;

public class ShotUtilsTest {

	@Test
	public void test_isEmpty() {		
		assertThat(ShotUtils.isEmpty(null),is(true));
		assertThat(ShotUtils.isEmpty(""),is(true));
		assertThat(ShotUtils.isEmpty("null"),is(true));
		
		assertThat(ShotUtils.isEmpty(" "),is(false));
		assertThat(ShotUtils.isEmpty("test"),is(false));
	}

}
