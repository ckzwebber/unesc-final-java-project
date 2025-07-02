package database.model;

import utils.TeacherUtil;

public class Teacher {

	private int id;
	private String name;
	private int title;
	private int subjectId;

	public Teacher() {
	}

	public Teacher(String name, int title, int subjectId) {
		this.name = name;
		this.title = title;
		this.subjectId = subjectId;
	}

	public Teacher(int id, String name, int title, int subjectId) {
		this.id = id;
		this.name = name;
		this.title = title;
		this.subjectId = subjectId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getIdAsString(int id) {
		return String.valueOf(id);
	}

	public String getTitleAsString(int title) {
		return TeacherUtil.getTitleById(title);
	}

	public String getSubjectIdAsString(int subjectId) {
		return String.valueOf(subjectId);
	}
}
