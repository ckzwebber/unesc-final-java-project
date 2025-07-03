package database.model;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import database.model.imports.PhaseImport;

public class ImportData {

	private Course course;
	private List<PhaseImport> phases = new ArrayList<>();
	private MessageDigest messageDigest;

	public ImportData(Course course, List<PhaseImport> phases, MessageDigest messageDigest) {
		this.course = course;
		this.phases = phases;
		this.messageDigest = messageDigest;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<PhaseImport> getPhases() {
		return phases;
	}

	public void setPhases(List<PhaseImport> phases) {
		this.phases = phases;
	}

	public MessageDigest getMessageDigest() {
		return messageDigest;
	}

	public void setMessageDigest(MessageDigest messageDigest) {
		this.messageDigest = messageDigest;
	}
}
