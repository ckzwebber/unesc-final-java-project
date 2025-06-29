package database.model;

public class Teacher {

	private int id;
	private String name;
	private int title;

	public Teacher() {
	}

	public Teacher(String name, int title) {
		this.name = name;
		this.title = title;
	}

	public Teacher(int id, String name, int title) {
		this.id = id;
		this.name = name;
		this.title = title;
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

	public String getIdAsString() {
		return String.valueOf(id);
	}
	
	public String getTitleAsString() {
		return String.valueOf(title);
	}
}
