package database.model.imports;

import java.util.ArrayList;
import java.util.List;

import database.model.Phase;

public class PhaseImport {
    private Phase phase;
    private List<SubjectImport> subjects = new ArrayList<>();

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public List<SubjectImport> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectImport> subjects) {
        this.subjects = subjects;
    }
}
