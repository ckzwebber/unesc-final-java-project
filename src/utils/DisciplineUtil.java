package utils;

import java.util.Map;
import java.util.HashMap;

public class DisciplineUtil {

    private static final Map<Integer, String> days = new HashMap<>();

    static {
        days.put(1, "Domingo");
        days.put(2, "Segunda-Feira");
        days.put(3, "Terça-Feira");
        days.put(4, "Quarta-Feira");
        days.put(5, "Quinta-Feira");
        days.put(6, "Sexta-Feira");
        days.put(7, "Sábado");
    }

    public static String getDayByCode(int code) {
        return days.get(code);
    }
}
