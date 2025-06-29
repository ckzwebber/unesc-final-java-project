package utils;

import java.util.Map;
import java.util.HashMap;

public class TeacherUtil {

    private static final Map<Integer, String> titles = new HashMap<>();

    static {
        titles.put(1, "Pós-Graduação");
        titles.put(2, "Mestrado");
        titles.put(3, "Doutorado");
        titles.put(4, "Pós-Doutorado");
    }

    public static String getTitleById(int id) {
        return titles.get(id);
    }
}