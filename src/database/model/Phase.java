package database.model;

public class Phase {

	private int id;
	private String name;
	private int subjectCount;
	private int teacherCount;
	private int courseId;

	public Phase() {
	}

	public Phase(String name, int subjectCount, int teacherCount, int courseId) {
		this.name = name;
		this.subjectCount = subjectCount;
		this.teacherCount = teacherCount;
		this.courseId = courseId;
	}

	public Phase(int id, String name, int subjectCount, int teacherCount, int courseId) {
		this.id = id;
		this.name = name;
		this.subjectCount = subjectCount;
		this.teacherCount = teacherCount;
		this.courseId = courseId;
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

	public int getSubjectCount() {
		return subjectCount;
	}

	public void setSubjectCount(int subjectCount) {
		this.subjectCount = subjectCount;
	}

	public int getTeacherCount() {
		return teacherCount;
	}

	public void setTeacherCount(int teacherCount) {
		this.teacherCount = teacherCount;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getIdAsString() {
		return String.valueOf(id);
	}

	public String getSubjectCountAsString() {
		return String.valueOf(subjectCount);
	}

	public String getTeacherCountAsString() {
		return String.valueOf(teacherCount);
	}

	public String getCourseIdAsString() {
		return String.valueOf(courseId);
	}

}
