package database.model;

import java.util.List;

public class ImportData {

	private Course course;
	private List<Phase> phases;
	private List<Subject> subjects;
	private List<Teacher> teachers;
	private FileHash fileHash;

	public ImportData(Course course, List<Phase> phases, List<Subject> subjects, List<Teacher> teachers,
			FileHash fileHash) {
		this.course = course;
		this.phases = phases;
		this.subjects = subjects;
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

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
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
