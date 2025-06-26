package database.model;

public class Discipline {

	private int id;
	private String code;
	private String name;
	private int weekDay;
	private Phase phase;

	public Discipline() {
	}

	public Discipline(String code, String name, int weekDay, Phase phase) {
		this.code = code;
		this.name = name;
		this.weekDay = weekDay;
		this.phase = phase;
	}

	public Discipline(int id, String code, String name, int weekDay, Phase phase) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.weekDay = weekDay;
		this.phase = phase;
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

	public Phase getPhase() {
		return phase;
	}

	public void setPhase(Phase phase) {
		this.phase = phase;
	}
}
