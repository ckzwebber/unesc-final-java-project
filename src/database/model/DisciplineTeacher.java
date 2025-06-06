package database.model;

public class DisciplineTeacher {

	private Discipline discipline;
	private Teacher teacher;

	public DisciplineTeacher() {
	}

	public DisciplineTeacher(Discipline discipline, Teacher teacher) {
		this.discipline = discipline;
		this.teacher = teacher;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
}
