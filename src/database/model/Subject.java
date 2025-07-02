package database.model;

public class Subject {

	private int id;
	private String code;
	private String name;
	private int weekDay;
	private int teacherQuantity;
	private int phaseId;

	public Subject() {
	}

	public Subject(String code, String name, int weekDay, int teacherQuantity, int phaseId) {
		this.code = code;
		this.name = name;
		this.weekDay = weekDay;
		this.teacherQuantity = teacherQuantity;
		this.phaseId = phaseId;
	}

	public Subject(int id, String code, String name, int weekDay, int teacherQuantity, int phaseId) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.weekDay = weekDay;
		this.teacherQuantity = teacherQuantity;
		this.phaseId = phaseId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}

	public int getTeacherQuantity() {
		return teacherQuantity;
	}

	public void setTeacherQuantity(int teacherQuantity) {
		this.teacherQuantity = teacherQuantity;
	}

	public int getPhaseId() {
		return phaseId;
	}

	public void setPhaseId(int phaseId) {
		this.phaseId = phaseId;
	}

	public String getIdAsString() {
		return String.valueOf(id);
	}

	public String getPhaseIdAsString() {
		return String.valueOf(phaseId);
	}

}
