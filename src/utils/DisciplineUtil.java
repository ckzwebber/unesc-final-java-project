package utils;

import java.util.Map;
import java.util.HashMap;

public class DisciplineUtil {

	private static final Map<Integer, String> days = new HashMap<>();
	private static final Map<String, String> disciplinesCodes = new HashMap<>();

	static {
		days.put(1, "Domingo");
		days.put(2, "Monday");
		days.put(3, "Tuesday");
		days.put(4, "Wednesday");
		days.put(5, "Thursday");
		days.put(6, "Friday");
		days.put(7, "Sunday");
	}

	static {
		disciplinesCodes.put("10850", "Algoritmos e Programação");
		disciplinesCodes.put("10854", "Fundamentos Matemáticos");
		disciplinesCodes.put("10851", "Introd. Ciência da Computação");
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

	public static String getDisciplineNameByCode(String code) {
		return disciplinesCodes.get(code);
	}

	public static String getDisciplineCodeByName(String name) {
		for (Map.Entry<String, String> entry : disciplinesCodes.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(name)) {
				return entry.getKey();
			}
		}
		throw new IllegalArgumentException("Invalid discipline name: " + name);
	}
}
