package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.CourseController;
import controller.SubjectController;
import controller.PhaseController;
import database.model.Course;
import database.model.Phase;
import utils.PhaseUtil;
import utils.TablesUtil;

public class PhaseScreen {

    private MainScreen mainScreen;
    private JPanel pnlPhases;
    private String action;
    private JPanel panel;
    private JLabel lblId, lblName, lblCourseId;
    private JTextField txfId, txfName, txfCourseId;
    private JTable table;
    private JScrollPane scroll;
    private JButton btnExit, btnConfirmAdd, btnConfirm;
    private String name, id, courseId;
    private String disciplinesByPhase;
    private JTextField txfLabel;
	private JTextField txfSubjectCount;
	private JTextField txfTeacherCount;
	private JComboBox<Course> cbCourses;

    private DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
	

    public PhaseScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public JPanel createPanel() throws SQLException {
        pnlPhases = new JPanel();
        pnlPhases.setBorder(BorderFactory.createTitledBorder("Phases"));
        pnlPhases.setLayout(null);
        pnlPhases.setVisible(true);
        pnlPhases.setBounds(210, 120, 450, 280);
        action = MainScreen.getActionSelected();

        btnExit = new JButton("<--");
        btnExit.setBounds(20, 20, 50, 20);
        pnlPhases.add(btnExit);

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
            model.addColumn("Subjects");
            model.addColumn("Teachers");
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<Phase> phaseList = PhaseController.list();
            for (Phase p : phaseList) {
            	Course c = CourseController.getById(p.getCourseId());
                model.addRow(new String[]{
                        p.getIdAsString(p.getId()), p.getName() + " - " + c.getName(), p.getSubjectCountAsString(p.getSubjectCount()), p.getTeacherCountAsString(p.getTeacherCount())
                });
            }

            scroll.setBounds(50, 50, 350, 150);
            pnlPhases.add(scroll);
            return pnlPhases;

        } else if (action.equals("Add")) {

        	JLabel lblLabel = new JLabel("Name(000-Fase):");
            lblLabel.setBounds(50, 50, 100, 20);
            pnlPhases.add(lblLabel);
            txfLabel = new JTextField();
            txfLabel.setBounds(160, 50, 200, 20);
            pnlPhases.add(txfLabel);

            JLabel lblSubj = new JLabel("Subject Count:");
            lblSubj.setBounds(50, 80, 100, 20);
            pnlPhases.add(lblSubj);
            txfSubjectCount = new JTextField();
            txfSubjectCount.setBounds(160, 80, 200, 20);
            pnlPhases.add(txfSubjectCount);

            JLabel lblTeach = new JLabel("Teacher Count:");
            lblTeach.setBounds(50, 110, 100, 20);
            pnlPhases.add(lblTeach);
            txfTeacherCount = new JTextField();
            txfTeacherCount.setBounds(160, 110, 200, 20);
            pnlPhases.add(txfTeacherCount);

            JLabel lblCourse = new JLabel("Course:");
            lblCourse.setBounds(50, 140, 100, 20);
            pnlPhases.add(lblCourse);
            cbCourses = new JComboBox<>();
            List<Course> courses = CourseController.list();
            for (Course c : courses) {
                cbCourses.addItem(c); 
            }
            cbCourses.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Course) {
                        setText(((Course) value).getName());
                    }
                    return this;
                }
            });

            cbCourses.setBounds(160, 140, 200, 20);
            pnlPhases.add(cbCourses);

 

            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 180, 120, 30);
            pnlPhases.add(btnConfirm);

            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String label = txfLabel.getText();
                        int subjCount = Integer.parseInt(txfSubjectCount.getText());
                        int teachCount = Integer.parseInt(txfTeacherCount.getText());
                        Course selectedCourse = (Course) cbCourses.getSelectedItem();
                        PhaseController.insert(label, subjCount, teachCount, selectedCourse.getId());
                        JOptionPane.showMessageDialog(pnlPhases, "Phase added.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pnlPhases, "Error: " + ex.getMessage());
                    }
                }
            });

            return pnlPhases;
        

        } else if (action.equals("Remove")) {

        	model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Subjects");
            model.addColumn("Teachers");
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<Phase> phaseList = PhaseController.list();
            for (Phase p : phaseList) {
            	Course c = CourseController.getById(p.getCourseId());
                model.addRow(new String[]{
                        p.getIdAsString(p.getId()), p.getName() + " - " + c.getName(), p.getSubjectCountAsString(p.getSubjectCount()), p.getTeacherCountAsString(p.getTeacherCount())
                });
            }

            lblId = new JLabel("Select ID to remove:");
            lblId.setBounds(100, 210, 200, 20);
            pnlPhases.add(lblId);
            txfId = new JTextField();
            txfId.setBounds(250, 210, 100, 20);
            pnlPhases.add(txfId);

            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 240, 150, 30);
            pnlPhases.add(btnConfirm);
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        id = txfId.getText();
                        int intId = Integer.parseInt(id);
                        PhaseController.delete(intId);
                        JOptionPane.showMessageDialog(btnConfirm, "Phase with ID " + id + " removed.");
                        txfId.setText(null);
                        Phase phase = new Phase();
                    	Course c = CourseController.getById(phase.getId());
                        TablesUtil.refreshTable(model, PhaseController.list(), p -> new String[]{
                        		 p.getIdAsString(p.getId()), p.getName() + " - " + c.getName(), p.getSubjectCountAsString(p.getSubjectCount()), p.getTeacherCountAsString(p.getTeacherCount())                        });
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            scroll.setBounds(50, 50, 350, 150);
            pnlPhases.add(scroll);

            return pnlPhases;

        } else {
            return pnlPhases;
        }
    }
}
