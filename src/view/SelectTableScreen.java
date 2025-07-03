package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class SelectTableScreen extends JFrame {

	private MainScreen mainScreen;
	private JPanel pnlScreen;
	private JButton btnCourses, btnDisciplines, btnPhases, btnTeachers;
	private JButton btnExit;

	public void SelectScreen(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}

	public JPanel createComponentsSelectTable(MainScreen mainScreen) {
		pnlScreen = new JPanel();
		pnlScreen.setBounds(210, 120, 450, 280);
		pnlScreen.setBorder(BorderFactory.createTitledBorder("Select a table:"));
		pnlScreen.setLayout(null);

		btnCourses = new JButton("Courses");
		btnCourses.setBounds(70, 80, 120, 30);
		pnlScreen.add(btnCourses);
		btnCourses.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CoursesScreen courseScreen = new CoursesScreen(mainScreen);
				try {
					pnlScreen = courseScreen.createPanel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainScreen.setPanel(pnlScreen);

			}
		});

		btnDisciplines = new JButton("Disciplines");
		btnDisciplines.setBounds(70, 130, 120, 30);
		pnlScreen.add(btnDisciplines);
		btnDisciplines.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DisciplinesScreen disciplinesScreen = new DisciplinesScreen(mainScreen);
				try {
					pnlScreen = disciplinesScreen.createPanel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainScreen.setPanel(pnlScreen);
			}
		});

		btnPhases = new JButton("Phases");
		btnPhases.setBounds(250, 130, 120, 30);
		pnlScreen.add(btnPhases);
		btnPhases.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PhaseScreen phasesScreen = new PhaseScreen(mainScreen);
				try {
					pnlScreen = phasesScreen.createPanel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainScreen.setPanel(pnlScreen);
			}
		});

		btnTeachers = new JButton("Teachers");
		btnTeachers.setBounds(250, 80, 120, 30);
		pnlScreen.add(btnTeachers);
		btnTeachers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TeacherScreen teacherScreen = new TeacherScreen(mainScreen);
				try {
					pnlScreen = teacherScreen.createPanel();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				mainScreen.setPanel(pnlScreen);
			}
		});

		btnExit = new JButton("<--");
		btnExit.setBounds(20, 20, 50, 20);
		pnlScreen.add(btnExit);

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WelcomeScreen welcomeScreen = new WelcomeScreen();
				JPanel welcomePanel = welcomeScreen.createWelcomePanel(mainScreen, getName());
				mainScreen.setPanel(welcomePanel);
			}
		});

		return pnlScreen;
	}
}
