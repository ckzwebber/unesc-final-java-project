package database.model;

public class Phase {

	private int id;
	private String name;
	private Course course;

	public Phase() {
	}

	public Phase(String name, Course course) {
		this.name = name;
		this.course = course;
	}

	public Phase(int id, String name, Course course) {
		this.id = id;
		this.name = name;
		this.course = course;
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getIdAsString() {
		return String.valueOf(id);
	}
}
