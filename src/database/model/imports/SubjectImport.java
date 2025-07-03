package database.model.imports;

import java.util.ArrayList;
import java.util.List;

import database.model.Subject;
import database.model.Teacher;

public class SubjectImport {
    private Subject subject;
    private List<Teacher> teachers = new ArrayList<>();

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

}
