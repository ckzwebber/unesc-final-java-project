package utils;

import java.sql.SQLException;
import java.util.List;

import controller.SubjectController;
import database.model.Subject;

public class PhaseUtil {
	
	public static String groupSubjectsByPhaseId(int phaseId) {
		StringBuilder sb = new StringBuilder();
	    List<Subject> subject;
		try {
			subject = SubjectController.getByPhaseId(phaseId);

			    for (int i = 0; i < subject.size(); i++) {
			        sb.append(subject.get(i).getName());
			        if (i < subject.size() - 1) {
			            sb.append(", ");
			        }
			    }
		} catch (SQLException e) {
			e.printStackTrace();
		}

	    return sb.toString();
	}

}
