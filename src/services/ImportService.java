package services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import utils.CourseInfo;

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
			}

			buffer.close();
			
			
			CourseInfo courseInfo = new CourseInfo(course.toString(), date.toString(), initialPeriod.toString(), lastPeriod.toString(), layout.toString());
			return courseInfo;

		} catch (Exception e) {
			throw new Error(e);
		}
	}

}
