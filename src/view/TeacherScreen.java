package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
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

import controller.PhaseController;
import controller.SubjectController;
import controller.TeacherController;
import database.model.Phase;
import database.model.Subject;
import database.model.Teacher;
import utils.TablesUtil;

public class TeacherScreen {

    private MainScreen mainScreen;
    private JPanel pnlTeachers;
    private String action;
    private JPanel panel;
    private JLabel lblId, lblName, lblTitle;
    private JTextField txfId, txfName, txfTitle;
    private JTable table;
    private JScrollPane scroll;
    private JButton btnExit, btnConfirmAdd, btnConfirm;
    private String name, title, id;
    private JComboBox<String> cbTitles;
    private JComboBox<String> cbSubjects;
    private String[] titles = {
    	"Pós-Graduação",
    	"Mestrado",
    	"Doutorado",
    	"Pós-Doutorado"
    };

    private DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public TeacherScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public JPanel createPanel() throws SQLException {
        pnlTeachers = new JPanel();
        pnlTeachers.setBorder(BorderFactory.createTitledBorder("Teachers"));
        pnlTeachers.setLayout(null);
        pnlTeachers.setVisible(true);
        pnlTeachers.setBounds(210, 120, 450, 280);
        action = MainScreen.getActionSelected();

        btnExit = new JButton("<--");
        btnExit.setBounds(20, 20, 50, 20);
        pnlTeachers.add(btnExit);

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
            model.addColumn("Title");
            model.addColumn("Subject");
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<Teacher> teacherList = TeacherController.list();
            for (Teacher t : teacherList) {
                model.addRow(new String[]{
                        t.getIdAsString(t.getId()), t.getName(), t.getTitleAsString(t.getTitle()), t.getSubjectIdAsString(t.getSubjectId())
                });
            }

            scroll.setBounds(50, 50, 350, 150);
            pnlTeachers.add(scroll);
            return pnlTeachers;

        } else if (action.equals("Add")) {

        	JLabel lblName = new JLabel("Name:");
            lblName.setBounds(50, 50, 100, 20);
            pnlTeachers.add(lblName);
            txfName = new JTextField();
            txfName.setBounds(160, 50, 200, 20);
            pnlTeachers.add(txfName);

            JLabel lblTitle = new JLabel("Title:");
            lblTitle.setBounds(50, 80, 100, 20);
            pnlTeachers.add(lblTitle);
            cbTitles = new JComboBox<>(titles);
            cbTitles.setBounds(160, 80, 200, 20);
            pnlTeachers.add(cbTitles);

            JLabel lblSubject = new JLabel("Subject:");
            lblSubject.setBounds(50, 110, 100, 20);
            pnlTeachers.add(lblSubject);
            cbSubjects = new JComboBox<>();
            cbSubjects.setBounds(160, 110, 200, 20);
            pnlTeachers.add(cbSubjects);

            List<Subject> subjects = SubjectController.list();
            for (Subject s : subjects) cbSubjects.addItem(s.getName());

            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 150, 120, 30);
            pnlTeachers.add(btnConfirm);

            btnConfirm.addActionListener(new ActionListener() {
                

				@Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String name = txfName.getText();
                        int title = cbTitles.getSelectedIndex() + 1;
                        Subject selectedSubject = (Subject) cbSubjects.getSelectedItem();
                        TeacherController.insert(name, title, selectedSubject.getId());
                        JOptionPane.showMessageDialog(pnlTeachers, "Teacher added.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pnlTeachers, "Error: " + ex.getMessage());
                    }
                }
            });

            return pnlTeachers;

        } else if (action.equals("Remove")) {

            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Title");
            model.addColumn("Subject");
            table = new JTable(model);
            scroll = new JScrollPane(table);
            pnlTeachers.add(scroll);

            List<Teacher> teacherList = TeacherController.list();
            for (Teacher t : teacherList) {
                model.addRow(new String[]{
                        t.getIdAsString(t.getId()), t.getName(), t.getTitleAsString(t.getTitle()), t.getSubjectIdAsString(t.getSubjectId())
                });
            
            }
            
            lblId = new JLabel("Select ID to remove:");
            lblId.setBounds(100, 210, 200, 20);
            pnlTeachers.add(lblId);
            txfId = new JTextField();
            txfId.setBounds(250, 210, 100, 20);
            pnlTeachers.add(txfId);

            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 240, 150, 30);
            pnlTeachers.add(btnConfirm);
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        id = txfId.getText();
                        int intId = Integer.parseInt(id);
                        TeacherController.delete(intId);
                        JOptionPane.showMessageDialog(btnConfirm, "Teacher with ID " + id + " removed.");
                        txfId.setText(null);
                        TablesUtil.refreshTable(model, TeacherController.list(), t -> new String[]{
                                t.getIdAsString(t.getId()), t.getName(), t.getTitleAsString(t.getTitle()), t.getSubjectIdAsString(t.getSubjectId())
                        });
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            scroll.setBounds(50, 50, 350, 150);
            pnlTeachers.add(scroll);

            return pnlTeachers;

        } else {
            return pnlTeachers;
        }
    }
}
