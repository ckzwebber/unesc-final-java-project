package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import database.model.ImportData;
import database.model.Course;
import database.model.Discipline;
import database.model.Phase;
import database.model.Teacher;
import utils.DisciplineUtil;

public class ImportService {

	public static ImportData readImportFile(String path) {
		try {

			InputStream inputStream = new FileInputStream(path);

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader buffer = new BufferedReader(inputStreamReader);

			String line;

			Course course = new Course();
			String processDate;
			String phaseInitialPeriod;
			String phaseLastPeriod;
			int fileSequence;
			String fileLayout;
			List<Phase> phases;
			int quantityOfDisciplines;
			int quantityOfTeachers;
			List<Discipline> disciplines;
			List<Integer> quantityOfTeachersInDiscipline;
			List<Teacher> teachers;
			int typeOfImport;
			int totalOfImports;

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
						String disciplineCode = line.substring(2, 8);
						int disciplineCodeInt = Integer.parseInt(disciplineCode);
						int dayOfWeek = Integer.parseInt(line.substring(8, 10));
						quantityOfTeachersInDiscipline.add(Integer.parseInt(line.substring(10, 12)));
						Phase disciplinePhase = phases.get(dayOfWeek - 1);
						String disciplineName = DisciplineUtil.getDisciplineNameByCode(disciplineCodeInt);
						disciplines.add(new Discipline(disciplineCode, disciplineName, dayOfWeek, disciplinePhase));
						break;

					case '3':
						String name = line.substring(2, 42).trim();
						String title = line.substring(42, 44);
						teachers.add(new ProfessorInfo(name, title));
						break;

					case '9':
						int totalReg = Integer.parseInt(line.substring(2, 13));
						// você pode validar a contagem se necessário
						break;
				}
			}

			buffer.close();

			ImportData courseInfo = new ImportData(course.toString(), date.toString(), initialPeriod.toString(),
					lastPeriod.toString(), layout.toString());
			courseInfo.setPhases(phases);
			courseInfo.setDisciplines(disciplines);
			courseInfo.setteachers(teachers);
			return courseInfo;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
