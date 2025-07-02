package utils;

import java.util.Map;
import java.util.HashMap;

public class TeacherUtil {

    public static final Map<Integer, String> titles = new HashMap<>();

    static {
        titles.put(1, "Postgraduate");
        titles.put(2, "Master's Degree");
        titles.put(3, "Doctorate");
        titles.put(4, "Postdoctoral");

    }

    public static String getTitleById(int id) {
        return titles.get(id);
    }
}