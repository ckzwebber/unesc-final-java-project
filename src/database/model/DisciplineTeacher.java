package database.model;

public class DisciplineTeacher {

	private int disciplineId;
	private int teacherId;

	public DisciplineTeacher() {
	}

	public DisciplineTeacher(int disciplineId, int teacherId) {
		this.disciplineId = disciplineId;
		this.teacherId = teacherId;
	}

	public int getDisciplineId() {
		return disciplineId;
	}

	public void setDisciplineId(int disciplineId) {
		this.disciplineId = disciplineId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
}
