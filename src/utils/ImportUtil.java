package utils;

import database.model.Course;
import database.model.ImportData;
import database.model.Phase;
import database.model.Subject;
import database.model.Teacher;
import database.model.imports.PhaseImport;
import database.model.imports.SubjectImport;

public class ImportUtil {

    public static String generatePreviewImportString(ImportData importData) {
        StringBuilder sb = new StringBuilder();
        Course c = importData.getCourse();
        sb.append("Course name: ").append(c.getName()).append("\n")
                .append("Process date: ").append(c.getProcessingDate()).append("\n")
                .append(c.getStartPhase()).append(" until ").append(c.getEndPhase()).append("\n")
                .append("File sequence: ").append(c.getSequence()).append("\n")
                .append("File layout: ").append(c.getLayout()).append("\n\n");

        for (PhaseImport pi : importData.getPhases()) {
            Phase p = pi.getPhase();
            sb.append("Phase: ").append(p.getName()).append("\n")
                    .append("  → Quantity of Disciplines: ").append(p.getSubjectCount()).append("\n")
                    .append("  → Quantity of Teachers: ").append(p.getTeacherCount()).append("\n");

            for (SubjectImport si : pi.getSubjects()) {
                Subject s = si.getSubject();
                sb.append("    * Discipline: ")
                        .append(s.getCode()).append(" - ").append(s.getName()).append("\n")
                        .append("      • Week day: ")
                        .append(SubjectUtil.getDayByCode(s.getWeekDay())).append("\n")
                        .append("      • Teachers needed: ")
                        .append(s.getTeacherQuantity()).append("\n");

                for (Teacher t : si.getTeachers()) {
                    sb.append("        - Teacher name: ").append(t.getName()).append("\n")
                            .append("          Title: ")
                            .append(TeacherUtil.getTitleById(t.getTitle())).append("\n");
                }
                sb.append("\n");
            }
        }

        sb.append("Type of import: 9\n");

        return sb.toString();
    }

}
