package database.model;

import java.util.List;

public class CourseInfo {

	private String course;
	private String date;
	private String initialPeriod;
	private String lastPeriod;
	private String layout;
	private List<String> teachers;

	public CourseInfo(String course, String date, String initialPeriod, String lastPeriod, String layout) {
		this.course = course;
		this.date = date;
		this.initialPeriod = initialPeriod;
		this.lastPeriod = lastPeriod;
		this.layout = layout;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInitialPeriod() {
		return initialPeriod;
	}

	public void setInitialPeriod(String initialPeriod) {
		this.initialPeriod = initialPeriod;
	}

	public String getLastPeriod() {
		return lastPeriod;
	}

	public void setLastPeriod(String lastPeriod) {
		this.lastPeriod = lastPeriod;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public List<String> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<String> teachers) {
		this.teachers = teachers;
	}

	public String courseInfoToString() {
		StringBuilder courseInfoStringBuilder = new StringBuilder();
		courseInfoStringBuilder.append("Course Information:\n");
		courseInfoStringBuilder.append("  Course: ").append(course).append("\n");
		courseInfoStringBuilder.append("  Date: ").append(date).append("\n");
		courseInfoStringBuilder.append("  Initial Period: ").append(initialPeriod).append("\n");
		courseInfoStringBuilder.append("  Final Period: ").append(lastPeriod).append("\n");
		courseInfoStringBuilder.append("  Layout: ").append(layout).append("\n");

		return courseInfoStringBuilder.toString();
	}

}
