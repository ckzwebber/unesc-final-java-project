package service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.time.LocalDate;
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
					case '0':
						String courseName = line.substring(1, 51).trim();
						LocalDate processingDate = LocalDate.parse(line.substring(51, 59).trim());
						String startPhase = line.substring(59, 66).trim();
						String endPhase = line.substring(66, 73).trim();
						int sequence = Integer.parseInt(line.substring(73, 80).trim());
						String layout = line.substring(80, 83).trim();
						course = new Course(courseName, processingDate, startPhase, endPhase, sequence, layout);
						break;

					case '1':
						String phaseName = line.substring(1, 8).trim();
						int subjectCount = Integer.parseInt(line.substring(8, 10).trim());
						int teachersCount = Integer.parseInt(line.substring(10, 12).trim());
						phases.add(new Phase(phaseName, subjectCount, teachersCount, course.getId()));
						break;

					case '2':
						String subjectCode = line.substring(1, 7).trim();
						int dayOfWeek = Integer.parseInt(line.substring(7, 9).trim());
						int teacherQuantity = (Integer.parseInt(line.substring(9, 11).trim()));
						int subjectPhaseIndex = subjects.size();
						int subjectPhase = phases.get(subjectPhaseIndex).getId();
						String subjectName = SubjectUtil.getSubjectNameByCode(subjectCode);
						subjects.add(new Subject(subjectCode, subjectName, dayOfWeek, teacherQuantity, subjectPhase));
						break;

					case '3':
						String teacherName = line.substring(1, 41).trim();
						int teacherTitle = Integer.parseInt(line.substring(41, 43).trim());
						int teacherSubjectIndex = subjects.size();
						int subjectId = subjects.get(teacherSubjectIndex).getId();
						teachers.add(new Teacher(teacherName, teacherTitle, subjectId));
						break;

					case '9':
						int importCount = Integer.parseInt(line.substring(1, 12).trim());
						break;
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