package service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import database.model.ImportData;
import database.model.Course;
import database.model.Discipline;
import database.model.Phase;
import database.model.Teacher;
import utils.DisciplineUtil;
import utils.ImportServiceUtil;

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
			String processDate = "";
			String phaseInitialPeriod = "";
			String phaseLastPeriod = "";
			int fileSequence = 0;
			String fileLayout = "";
			List<Phase> phases = new ArrayList<>();
			int quantityOfDisciplines = 0;
			int quantityOfTeachers = 0;
			List<Discipline> disciplines = new ArrayList<>();
			List<Integer> quantityOfTeachersInDiscipline = new ArrayList<>();
			List<Teacher> teachers = new ArrayList<>();
			int typeOfImport = 0;
			int totalOfImports = 0;
			String fileHash = "";

			while ((line = buffer.readLine()) != null) {
				char recordType = line.charAt(0);

				switch (recordType) {
					case '0':
						String courseName = line.substring(1, 51).trim();
						course.setName(courseName);
						processDate = line.substring(51, 59).trim();
						phaseInitialPeriod = line.substring(59, 66).trim();
						phaseLastPeriod = line.substring(66, 73).trim();
						fileSequence = Integer.parseInt(line.substring(73, 80).trim());
						fileLayout = line.substring(80, 83).trim();
						break;

					case '1':
						String phaseName = line.substring(2, 9).trim();
						phases.add(new Phase(phaseName, course));
						quantityOfDisciplines = Integer.parseInt(line.substring(9, 11).trim());
						quantityOfTeachers = Integer.parseInt(line.substring(11, 13).trim());
						break;

					case '2':
						String disciplineCode = line.substring(2, 8).trim();
						int dayOfWeek = Integer.parseInt(line.substring(8, 10).trim());
						quantityOfTeachersInDiscipline.add(Integer.parseInt(line.substring(10, 12).trim()));
						Phase disciplinePhase = phases.get(dayOfWeek - 1);
						String disciplineName = DisciplineUtil.getDisciplineNameByCode(disciplineCode);
						disciplines.add(new Discipline(disciplineCode, disciplineName, dayOfWeek, disciplinePhase));
						break;

					case '3':
						String teacherName = line.substring(2, 42).trim();
						int teacherTitle = Integer.parseInt(line.substring(42, 44).trim());
						teachers.add(new Teacher(teacherName, teacherTitle));
						break;

					case '9':
						totalOfImports = Integer.parseInt(line.substring(2, 13).trim());
						typeOfImport = Integer.parseInt(line.substring(13, 14).trim());
						break;
				}
			}

			buffer.close();

			fileHash = ImportServiceUtil.getAndVerifyFileHash(messageDigest);

			ImportData importData = new ImportData(course, processDate, phaseInitialPeriod, phaseLastPeriod,
					fileSequence, fileLayout, phases, quantityOfDisciplines, quantityOfTeachers, disciplines,
					quantityOfTeachersInDiscipline, teachers, typeOfImport, totalOfImports, fileHash);

			return importData;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
