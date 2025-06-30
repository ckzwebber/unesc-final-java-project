package utils;

import java.util.Map;
import java.util.HashMap;

public class DisciplineUtil {

	private static final Map<Integer, String> days = new HashMap<>();

	static {
		days.put(1, "Domingo");
		days.put(2, "Monday");
		days.put(3, "Tuesday");
		days.put(4, "Wednesday");
		days.put(5, "Thursday");
		days.put(6, "Friday");
		days.put(7, "Sunday");
	}

	public static String getDayByCode(int code) {
		return days.get(code);
	}
}
