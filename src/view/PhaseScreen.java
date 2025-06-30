package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.CourseController;
import controller.DisciplineController;
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
            model.addColumn("Course ID");
            model.addColumn("Disciplines");
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<Phase> phaseList = PhaseController.list();
            for (Phase p : phaseList) {
            	Course c = CourseController.getById(p.getId());
            	disciplinesByPhase = PhaseUtil.groupDiscpilinesByPhaseId(p.getId());
                model.addRow(new String[]{
                        p.getIdAsString(), p.getName() + " - " + c.getName(), c.getIdAsString(), disciplinesByPhase
                });
            }

            scroll.setBounds(50, 50, 350, 150);
            pnlPhases.add(scroll);
            return pnlPhases;

        } else if (action.equals("Add")) {

            lblName = new JLabel("Name:");
            lblName.setBounds(50, 80, 100, 20);
            pnlPhases.add(lblName);
            txfName = new JTextField();
            txfName.setBounds(160, 80, 200, 20);
            pnlPhases.add(txfName);

            lblCourseId = new JLabel("Course ID:");
            lblCourseId.setBounds(50, 110, 100, 20);
            pnlPhases.add(lblCourseId);
            txfCourseId = new JTextField();
            txfCourseId.setBounds(160, 110, 200, 20);
            pnlPhases.add(txfCourseId);

            btnConfirmAdd = new JButton("Confirm");
            btnConfirmAdd.setBounds(150, 180, 150, 30);
            pnlPhases.add(btnConfirmAdd);
            btnConfirmAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                     /*   name = txfName.getText();
                        int courseIdInt = Integer.parseInt(txfCourseId.getText());*/
                        Course c = new Course();
                        PhaseController.insert(name, courseIdInt);
                        JOptionPane.showMessageDialog(btnConfirmAdd, "Phase: " + name + " was added.");
                        txfName.setText("");
                        txfCourseId.setText("");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            return pnlPhases;

        } else if (action.equals("Remove")) {

        	model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Course ID");
            model.addColumn("Disciplines");
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<Phase> phaseList = PhaseController.list();
            for (Phase p : phaseList) {
            	Course c = CourseController.getById(p.getId());
            	disciplinesByPhase = PhaseUtil.groupDiscpilinesByPhaseId(p.getId());
                model.addRow(new String[]{
                        p.getIdAsString(), p.getName() + " - " + c.getName(), c.getIdAsString(), disciplinesByPhase
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
                                phase.getIdAsString(), phase.getName() + " - " + c.getName(), c.getIdAsString(), disciplinesByPhase
                        });
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
