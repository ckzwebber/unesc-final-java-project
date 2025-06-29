package utils;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.function.Function;

public class TablesUtil {
    public static <T> void refreshTable(DefaultTableModel model, List<T> list, Function<T, String[]> rowMapper) {
        model.setRowCount(0); 
        for (T item : list) {
            model.addRow(rowMapper.apply(item));
        }
    }
}

