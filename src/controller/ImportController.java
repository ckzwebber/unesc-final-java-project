package controller;

import database.model.ImportData;
import service.ImportService;

public class ImportController {

    private static ImportService importService;

    static {
        importService = new ImportService();
    }

    public static ImportData readImportFile(String path) {
        ImportData importData = importService.readImportFile(path);
        return importData;
    }

}
