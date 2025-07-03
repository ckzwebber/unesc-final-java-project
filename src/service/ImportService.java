package service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import controller.CourseController;
import controller.FileHashController;
import controller.PhaseController;
import controller.SubjectController;
import controller.TeacherController;
import database.model.ImportData;
import database.model.Course;
import database.model.Phase;
import database.model.Subject;
import database.model.Teacher;
import database.model.imports.PhaseImport;
import database.model.imports.SubjectImport;
import utils.SubjectUtil;

public class ImportService {

	public ImportData readImportFile(String path) {
		try {
			Course course = null;
			List<PhaseImport> phases = new ArrayList<>();

			InputStream inputStream = new FileInputStream(path);
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(digestInputStream));

			String line;
			PhaseImport currentPhaseImport = null;
			SubjectImport currentSubjectImport = null;

			while ((line = buffer.readLine()) != null) {
				char recordType = line.charAt(0);

				switch (recordType) {
					case '0': {
						String rawCourseName = line.substring(1, 51);
						String rawProcessingDate = line.substring(51, 59);
						String rawStartPhase = line.substring(59, 66);
						String rawEndPhase = line.substring(66, 73);
						String rawSequence = line.substring(73, 80);
						String rawLayout = line.substring(80, Math.min(line.length(), 83));

						if (rawCourseName.trim().isEmpty()) {
							throw new IllegalArgumentException("Course name is empty");
						}
						if (!rawProcessingDate.matches("\\d{8}")) {
							throw new Exception("Invalid processing date: " + rawProcessingDate);
						}
						LocalDate processingDate;
						try {
							processingDate = LocalDate.parse(rawProcessingDate.trim(),
									DateTimeFormatter.ofPattern("yyyyMMdd"));
						} catch (DateTimeParseException e) {
							throw new IllegalArgumentException("Incorrect date format: " + rawProcessingDate, e);
						}
						if (!rawStartPhase.trim().matches("[A-Za-z0-9]{7}")) {
							throw new IllegalArgumentException(
									"StartPhase must have 7 alphanumeric characters: " + rawStartPhase);
						}
						if (!rawEndPhase.trim().matches("[A-Za-z0-9]{7}")) {
							throw new IllegalArgumentException(
									"EndPhase must have 7 alphanumeric characters: " + rawEndPhase);
						}
						int sequence;
						try {
							sequence = Integer.parseInt(rawSequence.trim());
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException("Sequence is not an integer: " + rawSequence, e);
						}
						if (sequence < 0) {
							throw new IllegalArgumentException("Negative sequence: " + sequence);
						}
						if (rawLayout.trim().length() != 3) {
							throw new IllegalArgumentException("Layout must have 3 chars: " + rawLayout);
						}

						course = new Course(rawCourseName.trim(), processingDate, rawStartPhase.trim(),
								rawEndPhase.trim(),
								sequence, rawLayout.trim());
						break;
					}

					case '1': {
						String rawPhaseName = line.substring(1, 8);
						String rawSubjectCount = line.substring(8, 10);
						String rawTeachersCount = line.substring(10, 12);

						if (rawPhaseName.trim().isEmpty()) {
							throw new IllegalArgumentException("Phase name is empty");
						}
						int subjectCount, teachersCount;
						try {
							subjectCount = Integer.parseInt(rawSubjectCount.trim());
							teachersCount = Integer.parseInt(rawTeachersCount.trim());
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException(
									"Invalid phase counters: " + rawSubjectCount + ", " + rawTeachersCount, e);
						}
						if (subjectCount < 0 || teachersCount < 0) {
							throw new IllegalArgumentException("Negative counters in phase");
						}

						Phase phase = new Phase(
								rawPhaseName.trim(),
								subjectCount,
								teachersCount,
								0);

						currentPhaseImport = new PhaseImport();
						currentPhaseImport.setPhase(phase);
						phases.add(currentPhaseImport);

						currentSubjectImport = null;
						break;
					}

					case '2': {
						if (currentPhaseImport == null) {
							throw new IllegalStateException("Subject declared before any phase");
						}

						String rawSubjectCode = line.substring(1, 7);
						String rawDayOfWeek = line.substring(7, 9);
						String rawTeacherQty = line.substring(9, 11);

						if (!rawSubjectCode.trim().matches("\\d{3,6}")) {
							throw new IllegalArgumentException("Invalid SubjectCode: " + rawSubjectCode);
						}
						int dayOfWeek, teacherQty;
						try {
							dayOfWeek = Integer.parseInt(rawDayOfWeek.trim());
							teacherQty = Integer.parseInt(rawTeacherQty.trim());
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException(
									"Invalid day or teacher quantity: " + rawDayOfWeek + ", " + rawTeacherQty, e);
						}
						if (dayOfWeek < 1 || dayOfWeek > 7) {
							throw new IllegalArgumentException("DayOfWeek out of range 1–7: " + dayOfWeek);
						}
						if (teacherQty < 0) {
							throw new IllegalArgumentException("Negative TeacherQuantity: " + teacherQty);
						}

						String subjectName = SubjectUtil.getSubjectNameByCode(rawSubjectCode.trim());
						if (subjectName == null) {
							throw new IllegalArgumentException("Unknown subject code: " + rawSubjectCode);
						}

						Subject subject = new Subject(
								rawSubjectCode.trim(),
								subjectName,
								dayOfWeek,
								teacherQty,
								0);
						currentSubjectImport = new SubjectImport();
						currentSubjectImport.setSubject(subject);
						currentPhaseImport.getSubjects().add(currentSubjectImport);

						break;
					}

					case '3': {
						if (currentSubjectImport == null) {
							throw new IllegalStateException("Teacher declared before any subject");
						}

						String rawTeacherName = line.substring(1, 41);
						String rawTitle = line.substring(41, 43);

						if (rawTeacherName.trim().isEmpty()) {
							throw new IllegalArgumentException("Teacher name is empty");
						}
						int teacherTitle;
						try {
							teacherTitle = Integer.parseInt(rawTitle.trim());
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException("Invalid teacher title: " + rawTitle, e);
						}
						if (teacherTitle < 0 || teacherTitle > 99) {
							throw new IllegalArgumentException("Teacher title out of range 0–99: " + teacherTitle);
						}

						Teacher teacher = new Teacher(
								rawTeacherName.trim(),
								teacherTitle,
								0);
						currentSubjectImport.getTeachers().add(teacher);
						break;
					}

					case '9': {
						String rawImportCount = line.substring(1, 12);
						int importCount;
						try {
							importCount = Integer.parseInt(rawImportCount.trim());
						} catch (NumberFormatException e) {
							throw new IllegalArgumentException("Invalid ImportCount: " + rawImportCount, e);
						}
						break;
					}

					default:
						throw new IllegalArgumentException("Unknown record type: " + recordType);
				}
			}

			buffer.close();

			ImportData importData = new ImportData(course, phases, messageDigest);
			return importData;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ImportData importDataFile(ImportData importData) {
		if (importData == null) {
			throw new IllegalArgumentException("Import data cannot be null");
		}

		if (importData.getCourse() == null) {
			throw new IllegalArgumentException("Course cannot be null");
		}

		if (importData.getPhases() == null || importData.getPhases().isEmpty()) {
			throw new IllegalArgumentException("Phases cannot be null or empty");
		}

		if (importData.getMessageDigest() == null) {
			throw new IllegalArgumentException("Message digest cannot be null");
		}

		try {
			FileHashController.insert(importData.getMessageDigest(), 9);

			Course createdCourse = CourseController.insert(
					importData.getCourse().getName(),
					importData.getCourse().getProcessingDate(),
					importData.getCourse().getStartPhase(),
					importData.getCourse().getEndPhase(),
					importData.getCourse().getSequence(),
					importData.getCourse().getLayout());

			for (PhaseImport pi : importData.getPhases()) {
				Phase createdPhase = PhaseController.insert(
						pi.getPhase().getName(),
						pi.getPhase().getSubjectCount(),
						pi.getPhase().getTeacherCount(),
						createdCourse.getId());

				pi.getPhase().setId(createdPhase.getId());

				for (SubjectImport si : pi.getSubjects()) {
					Subject createdSubject = SubjectController.insert(
							si.getSubject().getCode(),
							si.getSubject().getName(),
							si.getSubject().getWeekDay(),
							si.getSubject().getTeacherQuantity(),
							createdPhase.getId());
					si.getSubject().setId(createdSubject.getId());

					for (Teacher t : si.getTeachers()) {
						Teacher createdTeacher = TeacherController.insert(
								t.getName(),
								t.getTitle(),
								createdSubject.getId());
						t.setId(createdTeacher.getId());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return importData;
	}
}