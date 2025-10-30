package com.queen.infrastructure.auth.repository;

import com.queen.infrastructure.persistence.DailyTracking;
import com.queen.infrastructure.persistence.Migraine;
import com.queen.infrastructure.persistence.Period;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PeriodsWithDailyMigraineExtractor implements ResultSetExtractor<List<Period>> {
	@Override
	public List<Period> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<UUID, Period> periods = new LinkedHashMap<>();
		Map<UUID, List<Migraine>> migraines = new LinkedHashMap<>();
		Map<UUID, List<DailyTracking>> trackings = new LinkedHashMap<>();
		while (rs.next()) {
			UUID pid = rs.getObject("periodId", UUID.class);
			var userId = rs.getObject("userId", UUID.class);

			var dailyTrackingId = rs.getObject("trackingId", UUID.class);
			var dailyTrackingDate = rs.getObject("trackingDate", LocalDate.class);
			var flowLevel = rs.getInt("flowLevel");
			var painLevel = rs.getInt("painLevel");
			var dailyTracking = new DailyTracking(userId, dailyTrackingDate, false);
			dailyTracking.setId(dailyTrackingId);
			dailyTracking.setPainLevel(painLevel);
			dailyTracking.setFlowLevel(flowLevel);
			dailyTracking.setPeriodId(pid);

			trackings.computeIfPresent(pid, (id, list) -> {
				var dts = new ArrayList<>(list);
				dts.add(dailyTracking);
				return dts;
			});

			trackings.computeIfAbsent(pid, id -> List.of(dailyTracking));

			var migraineId = rs.getObject("migraineId", UUID.class);
			if (migraineId != null) {
				var migraineSeverity = rs.getInt("migraineSeverity");
				var migraineDate = rs.getObject("migraineDate", LocalDate.class);
				var migraine = new Migraine(userId, migraineDate, false);
				migraine.setId(migraineId);
				migraine.setSeverityLevel(migraineSeverity);

				migraines.computeIfPresent(pid, (id, list) -> {
						var m = new ArrayList<>(list);
						m.add(migraine);
						return m;
				});
				migraines.computeIfAbsent(pid, id -> List.of(migraine));
			}
			periods.computeIfAbsent(pid, id -> {
				try {
					var date = rs.getObject("date", LocalDate.class);
					Period np = new Period(id, userId, date);
					np.setId(id);
					np.setCycleLength(rs.getInt("cycleLength"));
					return np;
				} catch (Exception e) {
					//@TODO proper exception
					throw new RuntimeException(e);
				}
			});
			periods.forEach((p, d) -> {
				var dts = trackings.get(p);
				d.setDailyTracking(dts);
				var m = migraines.get(p);
				d.setMigraine(m);
			});
		}
		return periods.values().stream().toList();
	}
}
