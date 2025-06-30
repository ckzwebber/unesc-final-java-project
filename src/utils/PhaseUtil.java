package utils;

import java.sql.SQLException;
import java.util.List;

import controller.DisciplineController;
import database.model.Discipline;

public class PhaseUtil {
	
	public static String groupDiscpilinesByPhaseId(int phaseId) {
		StringBuilder sb = new StringBuilder();
	    List<Discipline> disciplines;
		try {
			disciplines = DisciplineController.getByPhaseId(phaseId);

			    for (int i = 0; i < disciplines.size(); i++) {
			        sb.append(disciplines.get(i).getName());
			        if (i < disciplines.size() - 1) {
			            sb.append(", ");
			        }
			    }
		} catch (SQLException e) {
			e.printStackTrace();
		}

	    return sb.toString();
	}

}
