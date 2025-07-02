package utils;

import java.util.List;

import database.model.Phase;

public class CourseUtil {

	public static String groupPhaseByCourseId(List<Phase> phases) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < phases.size(); i++) {
		    sb.append(phases.get(i).getName());
		    if (i < phases.size() - 1) {
		        sb.append(", ");
		    }
		}
	    return sb.toString();
	}

}
