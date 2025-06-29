/*package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import database.model.CourseInfo;

public class ImportService {

	public static CourseInfo readImportFile() {
		try {
			InputStream is = new FileInputStream(new File("File"));
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader buffer = new BufferedReader(isr);

			String line = null;

			StringBuilder course = new StringBuilder();
			StringBuilder date = new StringBuilder();
			StringBuilder initialPeriod = new StringBuilder();
			StringBuilder lastPeriod = new StringBuilder();
			StringBuilder sequence = new StringBuilder();
			StringBuilder layout = new StringBuilder();

			while ((line = buffer.readLine()) != null) {
				if (line.startsWith("0")) {
					course.append(line, 1, 51);
					date.append(line, 51, 59);
					initialPeriod.append(line, 59, 66);
					lastPeriod.append(line, 66, 73);
					sequence.append(line, 73, 80);
					layout.append(line, 80, 83);
				}

				// continuar linhas do arquivo
			}

			buffer.close();

			CourseInfo courseInfo = new CourseInfo(course.toString(), date.toString(), initialPeriod.toString(),
					lastPeriod.toString(), layout.toString());
			return courseInfo;

		} catch (Exception e) {
			throw new Error(e);
		}
	}

}*/
/*package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import database.model.CourseInfo;

public class ImportService {

	public static CourseInfo readImportFile() {
		try {
			 readImportFile(File file) {
			    InputStream is = new FileInputStream(path);
			}

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader buffer = new BufferedReader(isr);

			String line;

			StringBuilder course = new StringBuilder();
			StringBuilder date = new StringBuilder();
			StringBuilder initialPeriod = new StringBuilder();
			StringBuilder lastPeriod = new StringBuilder();
			StringBuilder sequence = new StringBuilder();
			StringBuilder layout = new StringBuilder();

			List<PhaseInfo> phases = new ArrayList<>();
			List<DisciplineInfo> disciplines = new ArrayList<>();
			List<ProfessorInfo> professors = new ArrayList<>();

			while ((line = buffer.readLine()) != null) {
				char recordType = line.charAt(0);

				switch (recordType) {
					case '0':
						course.append(line, 1, 51);
						date.append(line, 51, 59);
						initialPeriod.append(line, 59, 66);
						lastPeriod.append(line, 66, 73);
						sequence.append(line, 73, 80);
						layout.append(line, 80, 83);
						break;

					case '1':
						String phase = line.substring(2, 9);
						int qtdDisc = Integer.parseInt(line.substring(9, 11));
						int qtdProf = Integer.parseInt(line.substring(11, 13));
						phases.add(new PhaseInfo(phase, qtdDisc, qtdProf));
						break;

					case '2':
						String code = line.substring(2, 8);
						int day = Integer.parseInt(line.substring(8, 10));
						int qtdP = Integer.parseInt(line.substring(10, 12));
						disciplines.add(new DisciplineInfo(code, day, qtdP));
						break;

					case '3':
						String name = line.substring(2, 42).trim();
						String title = line.substring(42, 44);
						professors.add(new ProfessorInfo(name, title));
						break;

					case '9':
						int totalReg = Integer.parseInt(line.substring(2, 13));
						// você pode validar a contagem se necessário
						break;
				}
			}

			buffer.close();

			CourseInfo courseInfo = new CourseInfo(course.toString(), date.toString(), initialPeriod.toString(),
					lastPeriod.toString(), layout.toString());
			courseInfo.setPhases(phases);
			courseInfo.setDisciplines(disciplines);
			courseInfo.setProfessors(professors);
			return courseInfo;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}*/


