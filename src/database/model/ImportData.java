package database.model;

import java.security.MessageDigest;
import java.util.List;

public class ImportData {

	private Course course;
	private List<Phase> phases;
	private List<Subject> subjects;
	private List<Teacher> teachers;
	private MessageDigest messageDigest;

	public ImportData(Course course, List<Phase> phases, List<Subject> subjects, List<Teacher> teachers,
			MessageDigest messageDigest) {
		this.course = course;
		this.phases = phases;
		this.subjects = subjects;
		this.teachers = teachers;
		this.messageDigest = messageDigest;
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

	public MessageDigest getMessageDigest() {
		return messageDigest;
	}

	public void setMessageDigest(MessageDigest messageDigest) {
		this.messageDigest = messageDigest;
	}
}
