package utils;

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

	public String getDate() {
		return date;
	}

	public String getInitialPeriod() {
		return initialPeriod;
	}

	public String getLastPeriod() {
		return lastPeriod;
	}

	public String getLayout() {
		return layout;
	}

	public String courseInfoToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Course Information:\n");
		sb.append("  Course: ").append(course).append("\n");
		sb.append("  Date: ").append(date).append("\n");
		sb.append("  Initial Period: ").append(initialPeriod).append("\n");
		sb.append("  Final Period: ").append(lastPeriod).append("\n");
        sb.append("  Layout: ").append(layout).append("\n");

		return sb.toString();
	}

}
