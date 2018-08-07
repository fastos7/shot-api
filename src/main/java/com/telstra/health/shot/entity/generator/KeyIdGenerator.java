package com.telstra.health.shot.entity.generator;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.telstra.health.shot.api.exception.ApiException;

public class KeyIdGenerator implements IdentifierGenerator {

	@SuppressWarnings("resource")
	@Override
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		// TODO Auto-generated method stub

		Serializable result = null;
		Connection connection = session.connection();
		try {

			String returnValue = null;
			String prefix = object.getClass().getSimpleName().substring(4, 7);

			if (prefix.equals("Bat")) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {

					String updateQuery = "update idn_identity set idn_value=idn_value + 1  , idn_lastupdby= ?, idn_lastupdwhen=getdate(), idn_lastupdaction='U'   where idn_type = ? and  idn_subtype = ?  and IDN_EntKey = ? ";
					ps = connection.prepareStatement(updateQuery);
					ps.setString(1, "679");
					ps.setString(2, "BAT");
					ps.setString(3, "ESSQL003");
					ps.setString(4, "3ES100");
					int returnUpdate = ps.executeUpdate();
					if (returnUpdate == 1) {

						String query = "select 'B'+ IDN_SubTypeAbbrev + right(cast(datepart(Year,getdate()) as varchar(4)),2) + right('0'+ cast(datepart(MONTH,getdate()) as varchar(2)),2)  + cast(IDN_VALUE as varchar(10)) as NextBatchNumber from ENT_Entity join IDN_Identity on IDN_ENTKey = ENT_KEY and IDN_TYPE = ?  where IDN_ENTKey = ?";

						ps = connection.prepareStatement(query);
						ps.setString(1, "BAT");
						ps.setString(2, "3ES100");
						rs = ps.executeQuery();
						if (rs.next()) {
							System.out.println(rs.getString(1));
							returnValue = rs.getString(1);
						}
					}

				} finally {
					if (rs != null)
						rs.close();
					ps.close();

				}
			} else {
				CallableStatement call = connection.prepareCall("{call SP_GetNextKey(?,?)}");
				call.registerOutParameter(2, java.sql.Types.VARCHAR);
				call.setString(1, prefix);

				boolean hasResults = call.execute();

				returnValue = call.getString(2);

			}
			return returnValue;

		} catch (SQLException e) {
			throw new ApiException(e.getMessage());

		}

	}

}
