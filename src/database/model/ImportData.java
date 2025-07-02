package database.model;

import java.util.List;

public class ImportData {

	private Course course;
	private List<Phase> phases;
	private List<Subject> disciplines;
	private List<Teacher> teachers;
	private FileHash fileHash;

	public ImportData(Course course, List<Phase> phases, List<Subject> disciplines, List<Teacher> teachers,
			FileHash fileHash) {
		this.course = course;
		this.phases = phases;
		this.disciplines = disciplines;
		this.teachers = teachers;
		this.fileHash = fileHash;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

	public List<Subject> getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(List<Subject> disciplines) {
		this.disciplines = disciplines;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public FileHash getFileHash() {
		return fileHash;
	}

	public void setFileHash(FileHash fileHash) {
		this.fileHash = fileHash;
	}
}
