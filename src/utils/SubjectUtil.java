package utils;

import java.util.Map;
import java.util.HashMap;

public class SubjectUtil {

	public static final Map<Integer, String> days = new HashMap<>();
	public static final Map<String, String> subjectsCodes = new HashMap<>();

	static {
		days.put(01, "Saturday");
		days.put(02, "Monday");
		days.put(03, "Tuesday");
		days.put(04, "Wednesday");
		days.put(05, "Thursday");
		days.put(06, "Friday");
		days.put(07, "Sunday");
	}

	static {
		subjectsCodes.put("010850", "Algoritmos e Programação");
		subjectsCodes.put("010854", "Fundamentos Matemáticos");
		subjectsCodes.put("010851", "Introd. Ciência da Computação");
	}

	public static String getDayByCode(int code) {
		return days.get(code);
	}

	public static int getCodeByDay(String day) {
		for (Map.Entry<Integer, String> entry : days.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(day)) {
				return entry.getKey();
			}
		}
		throw new IllegalArgumentException("Invalid day: " + day);
	}

	public static String getSubjectNameByCode(String code) {
		return subjectsCodes.get(code);
	}

	public static String getSubjectCodeByName(String name) {
		for (Map.Entry<String, String> entry : subjectsCodes.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(name)) {
				return entry.getKey();
			}
		}
		throw new IllegalArgumentException("Invalid discipline name: " + name);
	}
}
