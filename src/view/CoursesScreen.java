package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.CourseController;
import controller.PhaseController;
import database.model.Course;
import database.model.Phase;
import service.CourseService;
import utils.CourseUtil;
import utils.TablesUtil;

public class CoursesScreen {

	private MainScreen mainScreen;
	private JPanel pnlCourses;
	private String action;
	private JPanel panel;
	private JLabel lblId, lblName;
	private JTextField txfId, txfName;
	private JTable tabel;
	private JScrollPane scroll;
	private JButton btnExit, btnConfirmAdd, btnConfirm;
	private String name, id, endPhase, startPhase;
	private LocalDate processingDate;
	private JLabel lblDate;
	private JTextField txfDate;

	private DefaultTableModel model = new DefaultTableModel() {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};


	private JTextField txfStartPhase;
	private JTextField txfSequence;
	private JTextField txfEndPhase;
	private JTextArea txaLayout;	
	
	public CoursesScreen(MainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}

	public JPanel createPanel() throws SQLException {
		pnlCourses = new JPanel();
		pnlCourses.setBorder(BorderFactory.createTitledBorder("Courses"));
		pnlCourses.setLayout(null);
		pnlCourses.setVisible(true);
		pnlCourses.setBounds(210, 120, 450, 280);
		action = MainScreen.getActionSelected();

		btnExit = new JButton("<--");
		btnExit.setBounds(20, 20, 50, 20);
		pnlCourses.add(btnExit);

		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SelectTableScreen selectScreen = new SelectTableScreen();
				panel = selectScreen.createComponentsSelectTable(mainScreen);
				mainScreen.setPanel(panel);
			}
		});

		if (action.equals("View")) {

			model.addColumn("ID");
			model.addColumn("Name");
			model.addColumn("Date");
			model.addColumn("Phases");
			/*
			 * model.addColumn("Start phase"); model.addColumn("End phase");
			 * model.addColumn("Sequence"); model.addColumn("Layout");
			 */
			tabel = new JTable(model);
			scroll = new JScrollPane(tabel);

			List<Course> courseList = CourseController.list();
			for (Course c : courseList) {
				id = c.getIdAsString(c.getId());
				name = c.getName();
				processingDate = c.getProcessingDate();
				List<Phase> p = PhaseController.getByCourseId(c.getId());

					model.addRow(new String[] { id, name, processingDate.toString(), CourseUtil.groupPhaseByCourseId(p)
							});
			}

			scroll.setBounds(50, 50, 350, 200);
			pnlCourses.add(scroll);

			return pnlCourses;

		} else if (action.equals("Add")) {

	        JLabel lblName = new JLabel("Name:");
	        lblName.setBounds(50, 50, 100, 20);
	        pnlCourses.add(lblName);
	        txfName = new JTextField();
	        txfName.setBounds(160, 50, 200, 20);
	        pnlCourses.add(txfName);

	        JLabel lblStart = new JLabel("Start Phase:");
	        lblStart.setBounds(50, 80, 100, 20);
	        pnlCourses.add(lblStart);
	        txfStartPhase = new JTextField();
	        txfStartPhase.setBounds(160, 80, 200, 20);
	        pnlCourses.add(txfStartPhase);

	        JLabel lblEnd = new JLabel("End Phase:");
	        lblEnd.setBounds(50, 110, 100, 20);
	        pnlCourses.add(lblEnd);
	        txfEndPhase = new JTextField();
	        txfEndPhase.setBounds(160, 110, 200, 20);
	        pnlCourses.add(txfEndPhase);

	        JLabel lblSeq = new JLabel("Sequence:");
	        lblSeq.setBounds(50, 140, 100, 20);
	        pnlCourses.add(lblSeq);
	        txfSequence = new JTextField();
	        txfSequence.setBounds(160, 140, 200, 20);
	        pnlCourses.add(txfSequence);

	        JLabel lblLayout = new JLabel("Layout Version:");
	        lblLayout.setBounds(50, 170, 100, 20);
	        pnlCourses.add(lblLayout);
	        txaLayout = new JTextArea("9");
	        txaLayout.setBounds(160, 170, 200, 20);
	        pnlCourses.add(txaLayout);

	        btnConfirm = new JButton("Confirm");
	        btnConfirm.setBounds(150, 200, 120, 30);
	        pnlCourses.add(btnConfirm);

	        btnConfirm.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    String name = txfName.getText();
	                    String start = txfStartPhase.getText();
	                    String end = txfEndPhase.getText();
	                    int seq = Integer.parseInt(txfSequence.getText());
	                    String layout = "9";
	                    LocalDate today = LocalDate.now();

	                    CourseController.insert(name, today, start, end, seq, layout);
	                    JOptionPane.showMessageDialog(pnlCourses, "Course added.");
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(pnlCourses, "Error: " + ex.getMessage());
	                }
	            }
	        });

	        return pnlCourses;
	    

		} else if (action.equals("Remove")) {

			model.addColumn("ID");
			model.addColumn("Name");
			model.addColumn("Date");
			model.addColumn("Phases");
			/*
			 * model.addColumn("Start phase"); model.addColumn("End phase");
			 * model.addColumn("Sequence"); model.addColumn("Layout");
			 */
			tabel = new JTable(model);
			scroll = new JScrollPane(tabel);
			
			List<Course> courseList = CourseController.list();
			for (Course c : courseList) {
				id = c.getIdAsString(c.getId());
				name = c.getName();
				processingDate = c.getProcessingDate();
				List<Phase> p = PhaseController.getByCourseId(c.getId());

					model.addRow(new String[] { id, name, processingDate.toString(), CourseUtil.groupPhaseByCourseId(p)
							});
			}

			lblId = new JLabel("Select ID to be removed:");
			lblId.setBounds(100, 210, 200, 20);
			pnlCourses.add(lblId);
			txfId = new JTextField();
			txfId.setBounds(250, 210, 100, 20);
			pnlCourses.add(txfId);
			btnConfirm = new JButton("Confirm");
			btnConfirm.setBounds(150, 240, 150, 30);
			pnlCourses.add(btnConfirm);
			btnConfirm.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						id = txfId.getText();
						int intId = Integer.parseInt(id);
						CourseController.delete(intId);
						JOptionPane.showMessageDialog(btnConfirm, "The course with ID " + id + " was removed.");
						txfId.setText(null);
						TablesUtil.refreshTable(model, CourseController.list(),
								c -> new String[] { c.getIdAsString(c.getId()), c.getName() });
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
			});

			scroll.setBounds(50, 50, 350, 200);
			pnlCourses.add(scroll);

			return pnlCourses;

		} else {

			return pnlCourses;

		}

	}

}
