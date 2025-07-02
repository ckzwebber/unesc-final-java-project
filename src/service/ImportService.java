package service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import controller.FileHashController;
import database.model.ImportData;
import database.model.Course;
import database.model.FileHash;
import database.model.Phase;
import database.model.Subject;
import database.model.Teacher;
import utils.SubjectUtil;

public class ImportService {

	public ImportData readImportFile(String path) {
		try {

			InputStream inputStream = new FileInputStream(path);

			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

			DigestInputStream digestInputStream = new DigestInputStream(inputStream, messageDigest);

			InputStreamReader inputStreamReader = new InputStreamReader(digestInputStream);
			BufferedReader buffer = new BufferedReader(inputStreamReader);

			String line;

			Course course = new Course();
			List<Phase> phases = new ArrayList<>();
			List<Subject> subject = new ArrayList<>();
			List<Teacher> teachers = new ArrayList<>();
			FileHash fileHash = new FileHash();

			while ((line = buffer.readLine()) != null) {
				char recordType = line.charAt(0);

				switch (recordType) {
				case '0':
					String courseName = line.substring(1, 51).trim();
					course.setName(courseName);
					String processingDate = line.substring(51, 59).trim();

					startPhase = line.substring(59, 66).trim();
					endPhase = line.substring(66, 73).trim();
					sequence = Integer.parseInt(line.substring(73, 80).trim());
					layout = line.substring(80, 83).trim();
					break;

				case '1':
					String phaseName = line.substring(1, 8).trim();
					subjectCount = Integer.parseInt(line.substring(8, 10).trim());
					teachersCount = Integer.parseInt(line.substring(10, 12).trim());
					phases.add(new Phase(phaseName, subjectCount, teachersCount, course));

					break;

				case '2':
					String subjectCode = line.substring(1, 7).trim();
					int dayOfWeek = Integer.parseInt(line.substring(7, 9).trim());
					teachersQuantitiy.add(Integer.parseInt(line.substring(9, 11).trim()));
					Phase subjectPhase = phases.getFirst();
					String subjectName = SubjectUtil.getSubjectNameByCode(subjectCode);
					subject.add(
							new Subject(subjectCode, subjectName, dayOfWeek, teachersQuantitiy.size(), subjectPhase));
					break;

				case '3':
					String teacherName = line.substring(1, 41).trim();
					int teacherTitle = Integer.parseInt(line.substring(41, 43).trim());
					teachers.add(new Teacher(teacherName, teacherTitle));
					break;

				case '9':
					typeOfImport = 9;
					totalOfImports = Integer.parseInt(line.substring(1, 12).trim());
					break;
				}
			}

			buffer.close();

			fileHash = FileHashController.insert(messageDigest);

			ImportData importData = new ImportData(course, processingDate, startPhase, endPhase, sequence, layout,
					phases, subjectCount, teachersCount, subject, teachersQuantitiy, teachers, typeOfImport,
					totalOfImports, fileHash);

			return importData;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}