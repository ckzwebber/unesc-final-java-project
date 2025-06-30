package controller;

import java.sql.SQLException;
import java.util.List;

import database.model.Phase;
import service.PhaseService;

public class PhaseController {

	private static PhaseService phaseService;

	static {
		phaseService = new PhaseService();
	}

	public static List<Phase> list() throws SQLException {
		List<Phase> phases = phaseService.list();
		return phases;
	}

	public static Phase getById(int id) throws SQLException {
		Phase phase = phaseService.getById(id);
		return phase;
	}

	public static Phase insert(String name, int courseId) throws SQLException {
		Phase phase = phaseService.create(name, courseId);
		return phase;
	}

	public static Phase update(int id, String name, int courseId) throws SQLException {
		Phase phase = phaseService.update(id, name, courseId);
		return phase;
	}

	public static void delete(int id) throws SQLException {
		phaseService.delete(id);
	}
}
