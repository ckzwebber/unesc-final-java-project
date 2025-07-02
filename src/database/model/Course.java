package database.model;

import java.time.LocalDate;

public class Course {

    private int id;
    private String name;
    private LocalDate processingDate;
    private String startPhase; 
	private String endPhase;
    private int sequence;
    private String layout;

    public Course() {
    }

    public Course(String name, LocalDate processingDate, String startPhase, String endPhase, int sequence, String layout) {
        this.name = name;
        this.processingDate = processingDate;
        this.startPhase = startPhase;
        this.endPhase = endPhase;
        this.sequence = sequence;
        this.layout = layout;
    }

    public Course(int id, String name, LocalDate processingDate, String startPhase, String endPhase, int sequence, String layou) {
        this.id = id;
        this.name = name;
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

	public LocalDate getProcessingDate() {
		return processingDate;
	}

	public void setProcessingDate(LocalDate processingdDate) {
		this.processingDate = processingdDate;
	}

	public String getStartPhase() {
		return startPhase;
	}

	public void setStartPhase(String startPhase) {
		this.startPhase = startPhase;
	}

	public String getEndPhase() {
		return endPhase;
	}

	public void setEndPhase(String endPhase) {
		this.endPhase = endPhase;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getIdAsString(int id) {
		return String.valueOf(id);
	}

    
}