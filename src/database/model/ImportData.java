package database.model;

import java.util.List;

public class ImportData {

	private Course course;
	private String processDate;
	private String phaseInitialPeriod;
	private String phaseLastPeriod;
	private int fileSequence;
	private String fileLayout;
	private List<Phase> phases;
	private int quantityOfDisciplines;
	private int quantityOfTeachers;
	private List<Discipline> disciplines;
	private List<Integer> quantityOfTeachersInDiscipline;
	private List<Teacher> teachers;
	private int typeOfImport;
	private int totalOfImports;

	public ImportData(Course course, String processDate, String phaseInitialPeriod, String phaseLastPeriod,
			int fileSequence, String fileLayout, List<Phase> phases, int quantityOfDisciplines,
			int quantityOfTeachers, List<Discipline> disciplines, List<Integer> quantityOfTeachersInDiscipline,
			List<Teacher> teachers, int typeOfImport, int totalOfImports) {
		this.course = course;
		this.processDate = processDate;
		this.phaseInitialPeriod = phaseInitialPeriod;
		this.phaseLastPeriod = phaseLastPeriod;
		this.fileSequence = fileSequence;
		this.fileLayout = fileLayout;
		this.phases = phases;
		this.quantityOfDisciplines = quantityOfDisciplines;
		this.quantityOfTeachers = quantityOfTeachers;
		this.disciplines = disciplines;
		this.quantityOfTeachersInDiscipline = quantityOfTeachersInDiscipline;
		this.teachers = teachers;
		this.typeOfImport = typeOfImport;
		this.totalOfImports = totalOfImports;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getProcessDate() {
		return processDate;
	}

	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}

	public String getPhaseInitialPeriod() {
		return phaseInitialPeriod;
	}

	public void setPhaseInitialPeriod(String phaseInitialPeriod) {
		this.phaseInitialPeriod = phaseInitialPeriod;
	}

	public String getPhaseLastPeriod() {
		return phaseLastPeriod;
	}

	public void setPhaseLastPeriod(String phaseLastPeriod) {
		this.phaseLastPeriod = phaseLastPeriod;
	}

	public int getFileSequence() {
		return fileSequence;
	}

	public void setFileSequence(int fileSequence) {
		this.fileSequence = fileSequence;
	}

	public String getFileLayout() {
		return fileLayout;
	}

	public void setFileLayout(String fileLayout) {
		this.fileLayout = fileLayout;
	}

	public List<Phase> getPhases() {
		return phases;
	}

	public void setPhases(List<Phase> phases) {
		this.phases = phases;
	}

	public int getQuantityOfDisciplines() {
		return quantityOfDisciplines;
	}

	public void setQuantityOfDisciplines(int quantityOfDisciplines) {
		this.quantityOfDisciplines = quantityOfDisciplines;
	}

	public int getQuantityOfTeachers() {
		return quantityOfTeachers;
	}

	public void setQuantityOfTeachers(int quantityOfTeachers) {
		this.quantityOfTeachers = quantityOfTeachers;
	}

	public List<Discipline> getDisciplines() {
		return disciplines;
	}

	public void setDisciplines(List<Discipline> disciplines) {
		this.disciplines = disciplines;
	}

	public List<Integer> getQuantityOfTeachersInDiscipline() {
		return quantityOfTeachersInDiscipline;
	}

	public void setQuantityOfTeachersInDiscipline(List<Integer> quantityOfTeachersInDiscipline) {
		this.quantityOfTeachersInDiscipline = quantityOfTeachersInDiscipline;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public int getTypeOfImport() {
		return typeOfImport;
	}

	public void setTypeOfImport(int typeOfImport) {
		this.typeOfImport = typeOfImport;
	}

	public int getTotalOfImports() {
		return totalOfImports;
	}

	public void setTotalOfImports(int totalOfImports) {
		this.totalOfImports = totalOfImports;
	}

	public String getFileSequenceAsString() {
		return String.valueOf(fileSequence);
	}

	public String getQuantityOfDisciplinesAsString() {
		return String.valueOf(quantityOfDisciplines);
	}

	public String getQuantityOfTeachersAsString() {
		return String.valueOf(quantityOfTeachers);
	}

	public String getTypeOfImportAsString() {
		return String.valueOf(typeOfImport);
	}

	public String getTotalOfImportsAsString() {
		return String.valueOf(totalOfImports);
	}
}
