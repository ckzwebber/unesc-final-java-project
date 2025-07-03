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
import utils.SubjectUtil;

public class ImportService {

	public ImportData readImportFile(String path) {
		try {
			Course course = new Course();
			List<Phase> phases = new ArrayList<>();
			List<Subject> subjects = new ArrayList<>();
			List<Teacher> teachers = new ArrayList<>();

			InputStream inputStream = new FileInputStream(path);

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);

			InputStreamReader inputStreamReader = new InputStreamReader(digestInputStream);
			BufferedReader buffer = new BufferedReader(inputStreamReader);

			String line;

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
						throw new IllegalArgumentException("Nome do curso vazio");
					}
					if (!rawProcessingDate.matches("\\d{8}")) {
						throw new Exception("Data de processamento inválida: " + rawProcessingDate);
					}
					LocalDate processingDate;
					try {
						processingDate = LocalDate.parse(rawProcessingDate.trim(),
								DateTimeFormatter.ofPattern("yyyyMMdd"));
					} catch (DateTimeParseException e) {
						throw new IllegalArgumentException("Formato de data incorreto: " + rawProcessingDate, e);
					}
					if (!rawStartPhase.trim().matches("[A-Za-z0-9]{7}")) {
						throw new IllegalArgumentException(
								"StartPhase deve ter 7 caracteres alfanuméricos: " + rawStartPhase);
					}
					if (!rawEndPhase.trim().matches("[A-Za-z0-9]{7}")) {
						throw new IllegalArgumentException(
								"EndPhase deve ter 7 caracteres alfanuméricos: " + rawEndPhase);
					}
					int sequence;
					try {
						sequence = Integer.parseInt(rawSequence.trim());
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Sequence não é inteiro: " + rawSequence, e);
					}
					if (sequence < 0) {
						throw new IllegalArgumentException("Sequence negativo: " + sequence);
					}
					if (rawLayout.trim().length() != 3) {
						throw new IllegalArgumentException("Layout deve ter 3 chars: " + rawLayout);
					}

					course = new Course(rawCourseName.trim(), processingDate, rawStartPhase.trim(), rawEndPhase.trim(),
							sequence, rawLayout.trim());
					break;
				}

				case '1': {
					String rawPhaseName = line.substring(1, 8);
					String rawSubjectCount = line.substring(8, 10);
					String rawTeachersCount = line.substring(10, 12);

					if (rawPhaseName.trim().isEmpty()) {
						throw new IllegalArgumentException("Nome da fase vazio");
					}
					int subjectCount, teachersCount;
					try {
						subjectCount = Integer.parseInt(rawSubjectCount.trim());
						teachersCount = Integer.parseInt(rawTeachersCount.trim());
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException(
								"Contadores da fase inválidos: " + rawSubjectCount + ", " + rawTeachersCount, e);
					}
					if (subjectCount < 0 || teachersCount < 0) {
						throw new IllegalArgumentException("Contadores negativos na fase");
					}

					phases.add(new Phase(rawPhaseName.trim(), subjectCount, teachersCount, course.getId()));
					break;
				}

				case '2': {
					String rawSubjectCode = line.substring(1, 7);
					String rawDayOfWeek = line.substring(7, 9);
					String rawTeacherQty = line.substring(9, 11);

					if (!rawSubjectCode.trim().matches("\\d{3,6}")) {
						throw new IllegalArgumentException("SubjectCode inválido: " + rawSubjectCode);
					}
					int dayOfWeek, teacherQty;
					try {
						dayOfWeek = Integer.parseInt(rawDayOfWeek.trim());
						teacherQty = Integer.parseInt(rawTeacherQty.trim());
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException(
								"Dia ou qtd de professores inválidos: " + rawDayOfWeek + ", " + rawTeacherQty, e);
					}
					if (dayOfWeek < 1 || dayOfWeek > 7) {
						throw new IllegalArgumentException("DayOfWeek fora do intervalo 1–7: " + dayOfWeek);
					}
					if (teacherQty < 0) {
						throw new IllegalArgumentException("TeacherQuantity negativo: " + teacherQty);
					}

					String subjectName = SubjectUtil.getSubjectNameByCode(rawSubjectCode.trim());
					if (subjectName == null) {
						throw new IllegalArgumentException("Código de disciplina desconhecido: " + rawSubjectCode);
					}
					int subjectPhase = phases.getFirst().getId();

					subjects.add(new Subject(rawSubjectCode.trim(), subjectName, dayOfWeek, teacherQty, subjectPhase));
					break;
				}

				case '3': {
					String rawTeacherName = line.substring(1, 41);
					String rawTitle = line.substring(41, 43);

					if (rawTeacherName.trim().isEmpty()) {
						throw new IllegalArgumentException("Nome do professor vazio");
					}
					int teacherTitle;
					try {
						teacherTitle = Integer.parseInt(rawTitle.trim());
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("Título do professor inválido: " + rawTitle, e);
					}
					if (teacherTitle < 0 || teacherTitle > 99) {
						throw new IllegalArgumentException("Título do prof fora de 0–99: " + teacherTitle);
					}
					if (subjects.isEmpty()) {
						throw new IllegalStateException("Professor declarado antes de qualquer disciplina");
					}
					int subjectId = subjects.getFirst().getId();

					teachers.add(new Teacher(rawTeacherName.trim(), teacherTitle, subjectId));
					break;
				}

				case '9': {
					String rawImportCount = line.substring(1, 12);
					int importCount;
					try {
						importCount = Integer.parseInt(rawImportCount.trim());
					} catch (NumberFormatException e) {
						throw new IllegalArgumentException("ImportCount inválido: " + rawImportCount, e);
					}
					break;
				}

				default:
					throw new IllegalArgumentException("Tipo de registro desconhecido: " + recordType);
				}
			}

			buffer.close();

			ImportData importData = new ImportData(course, phases, subjects, teachers, messageDigest);

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

		if (importData.getSubjects() == null || importData.getSubjects().isEmpty()) {
			throw new IllegalArgumentException("Subjects cannot be null or empty");
		}

		if (importData.getTeachers() == null || importData.getTeachers().isEmpty()) {
			throw new IllegalArgumentException("Teachers cannot be null or empty");
		}

		if (importData.getMessageDigest() == null) {
			throw new IllegalArgumentException("Message digest cannot be null");
		}

		try {
			Course course = importData.getCourse();
			CourseController.insert(course.getName(), course.getProcessingDate(), course.getStartPhase(),
					course.getEndPhase(), course.getSequence(), course.getLayout());

			for (Phase phase : importData.getPhases()) {
				PhaseController.insert(phase.getName(), phase.getSubjectCount(), phase.getTeacherCount(),
						phase.getCourseId());
			}

			for (Subject subject : importData.getSubjects()) {
				SubjectController.insert(subject.getCode(), subject.getName(), subject.getWeekDay(),
						subject.getTeacherQuantity(), subject.getPhaseId());
			}

			for (Teacher teacher : importData.getTeachers()) {
				TeacherController.insert(teacher.getName(), teacher.getTitle(), teacher.getSubjectId());
			}

			int importType = 9;
			MessageDigest messageDigest = importData.getMessageDigest();
			FileHashController.insert(messageDigest, importType);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return importData;
	}
}