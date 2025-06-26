package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SelectTableScreen extends JFrame{

	private MainScreen mainScreen;
    private JPanel pnlScreen;
    private JButton btnCourses, btnDisciplines, btnPhases, btnTeachers, btnUsers;
    private JButton btnExit;
    private JLabel lblChoose;
    

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
        btnCourses.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CoursesScreen courseScreen = new CoursesScreen(mainScreen);
				pnlScreen = courseScreen.createPanel();
				mainScreen.setPanel(pnlScreen);
				
			}
		});

        btnDisciplines = new JButton("Disciplines");
        btnDisciplines.setBounds(70, 130, 120, 30);
        pnlScreen.add(btnDisciplines);

        btnPhases = new JButton("Phases");
        btnPhases.setBounds(70, 180, 120, 30);
        pnlScreen.add(btnPhases);

        btnTeachers = new JButton("Teachers");
        btnTeachers.setBounds(250, 80, 120, 30);
        pnlScreen.add(btnTeachers);

        btnUsers = new JButton("Users");
        btnUsers.setBounds(250, 130, 120, 30);
        pnlScreen.add(btnUsers);

        btnExit = new JButton("<--");
        btnExit.setBounds(20, 20, 50, 20);
        pnlScreen.add(btnExit);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel voidPanel = createPanel();
                mainScreen.setPanel(voidPanel);
            }
        });

        return pnlScreen;
    }

	public JPanel createPanel() {
        JPanel pnlVoid = new JPanel();
        pnlVoid.setBounds(210, 120, 450, 280);
        pnlVoid.setBorder(BorderFactory.createTitledBorder("Choose an option:"));
        pnlVoid.setLayout(null);

        lblChoose = new JLabel("Choose an option on the left.");
        lblChoose.setBounds(120, 130, 200, 20);
        pnlVoid.add(lblChoose);

        return pnlVoid;
    }
}
