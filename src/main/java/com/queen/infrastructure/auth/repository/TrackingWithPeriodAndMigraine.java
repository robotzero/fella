package com.queen.infrastructure.auth.repository;

import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Period;
import com.queen.infrastructure.persistence.Tracking;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TrackingWithPeriodAndMigraine implements ResultSetExtractor<List<Tracking>> {
	@Override
	public List<Tracking> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<UUID, Period> periods = new LinkedHashMap<>();
		Map<UUID, Migraine> migraines = new LinkedHashMap<>();
		Map<UUID, Tracking> trackings = new LinkedHashMap<>();
		while (rs.next()) {
			UUID pid = rs.getObject("trackingId", UUID.class);
			var userId = rs.getObject("userId", UUID.class);

			var periodId = rs.getObject("periodId", UUID.class);
			var dailyTrackingDate = rs.getObject("trackingDate", LocalDate.class);
			var flowLevel = rs.getInt("flowLevel");
			var painLevel = rs.getInt("painLevel");
			var tracking = new Tracking(userId, dailyTrackingDate, false);
			tracking.setId(pid);
			tracking.setPainLevel(painLevel);
			tracking.setFlowLevel(flowLevel);

			trackings.computeIfAbsent(pid, id -> tracking);

			var migraineId = rs.getObject("migraineId", UUID.class);
			if (migraineId != null) {
				var migraineSeverity = rs.getInt("migraineSeverity");
				var migraineDate = rs.getObject("migraineDate", LocalDate.class);
				var migraine = new Migraine(userId, migraineDate, false);
				migraine.setId(migraineId);
				migraine.setSeverityLevel(migraineSeverity);

				migraines.computeIfAbsent(pid, id -> migraine);
			}
			periods.computeIfAbsent(pid, id -> {
				try {
					var date = rs.getObject("date", LocalDate.class);
					Period p = new Period(periodId, userId, date);
					p.setId(periodId);
					return p;
				} catch (Exception e) {
					//@TODO proper exception
					throw new RuntimeException(e);
				}
			});
			trackings.forEach((i, d) -> {
				var p = periods.get(i);
				var m = migraines.get(i);
				d.setMigraine(m);
				d.setPeriod(p);
			});
		}
		return trackings.values().stream().toList();
	}
}
