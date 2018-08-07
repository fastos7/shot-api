package com.telstra.health.shot.entity.generator;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Id Generator for Users. It takes into account the IDs of USER_MASTER anD
 * SHOT_USERS tables and generators the maximum ID+1.
 *
 */
public class UserIdGenerator implements IdentifierGenerator {

	private static Logger logger = LoggerFactory.getLogger(UserIdGenerator.class);

	/**
	 * Generate User Id when saving a New User.
	 */
	@Override
	public Serializable generate(SessionImplementor sessionImpl, Object object) throws HibernateException {
		Serializable result = null;
		try {
			Connection connection = sessionImpl.connection();
			PreparedStatement updateIdStatement = connection.prepareStatement("update idn_identity set idn_value = (idn_value + 1), idn_lastupdwhen = getdate() where idn_type = 'USR'");
			PreparedStatement idStatement = connection.prepareStatement("select convert(int, idn_value) from idn_identity where idn_type = 'USR'");
			updateIdStatement.executeUpdate();
			ResultSet idResult = idStatement.executeQuery();
			if (idResult.next()) {
				result = idResult.getLong(1);
			} else {
				throw new RuntimeException("No User Id configuration found.");
			}
			logger.info("UserId: {} generated for the new User", result);
		} catch (Exception ex) {
			logger.error("Error occurred in generating User Id", ex);
			throw new RuntimeException("Error occurred in generating User Id", ex);
		}
		return result;
	}

}
