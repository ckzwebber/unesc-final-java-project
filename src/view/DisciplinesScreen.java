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

import controller.DisciplineController;
import database.model.Discipline;
import utils.TablesUtil;

public class DisciplinesScreen {

    private MainScreen mainScreen;
    private JPanel pnlDisciplines;
    private String action;
    private JPanel panel;
    private JLabel lblCode, lblName, lblWeekDay, lblPhaseId;
    private JTextField txfCode, txfName, txfWeekDay, txfPhaseId;
    private JTable table;
    private JScrollPane scroll;
    private JButton btnExit, btnConfirmAdd, btnConfirm;
    private String code, name, weekDay, phaseId;

    private DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public DisciplinesScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public JPanel createPanel() throws SQLException {
        pnlDisciplines = new JPanel();
        pnlDisciplines.setBorder(BorderFactory.createTitledBorder("Disciplines"));
        pnlDisciplines.setLayout(null);
        pnlDisciplines.setVisible(true);
        pnlDisciplines.setBounds(210, 120, 450, 280);
        action = MainScreen.getActionSelected();

        btnExit = new JButton("<--");
        btnExit.setBounds(20, 20, 50, 20);
        pnlDisciplines.add(btnExit);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectTableScreen selectScreen = new SelectTableScreen();
                panel = selectScreen.createComponentsSelectTable(mainScreen);
                mainScreen.setPanel(panel);
            }
        });

        if (action.equals("View")) {

            model.addColumn("Code");
            model.addColumn("Name");
            model.addColumn("Week Day");
            model.addColumn("Phase ID");
            table = new JTable(model);
            scroll = new JScrollPane(table);

        /*    List<Discipline> disciplineList = DisciplineController.list();
            for (Discipline d : disciplineList) {
                model.addRow(new String[]{
                        d.getCodeAsString(), d.getName(), d.getWeekDay(), d.getPhaseIdAsString()
                });
            }*/

            scroll.setBounds(50, 50, 350, 150);
            pnlDisciplines.add(scroll);
            return pnlDisciplines;

        } else if (action.equals("Add")) {

            lblName = new JLabel("Name:");
            lblName.setBounds(50, 60, 100, 20);
            pnlDisciplines.add(lblName);
            txfName = new JTextField();
            txfName.setBounds(160, 60, 200, 20);
            pnlDisciplines.add(txfName);

            lblWeekDay = new JLabel("Week Day:");
            lblWeekDay.setBounds(50, 90, 100, 20);
            pnlDisciplines.add(lblWeekDay);
            txfWeekDay = new JTextField();
            txfWeekDay.setBounds(160, 90, 200, 20);
            pnlDisciplines.add(txfWeekDay);

            lblPhaseId = new JLabel("Phase ID:");
            lblPhaseId.setBounds(50, 120, 100, 20);
            pnlDisciplines.add(lblPhaseId);
            txfPhaseId = new JTextField();
            txfPhaseId.setBounds(160, 120, 200, 20);
            pnlDisciplines.add(txfPhaseId);

            btnConfirmAdd = new JButton("Confirm");
            btnConfirmAdd.setBounds(150, 180, 150, 30);
            pnlDisciplines.add(btnConfirmAdd);
            btnConfirmAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        name = txfName.getText();
                        weekDay = txfWeekDay.getText();
                        int phaseIdInt = Integer.parseInt(txfPhaseId.getText());
                        DisciplineController.insert(name, weekDay, phaseIdInt);
                        JOptionPane.showMessageDialog(btnConfirmAdd, "Discipline: " + name + " was added.");
                        txfName.setText("");
                        txfWeekDay.setText("");
                        txfPhaseId.setText("");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            return pnlDisciplines;

        } else if (action.equals("Remove")) {

            model.addColumn("Code");
            model.addColumn("Name");
            model.addColumn("Week Day");
            model.addColumn("Phase ID");
            table = new JTable(model);
            scroll = new JScrollPane(table);

     /*       List<Discipline> disciplineList = DisciplineController.list();
            for (Discipline d : disciplineList) {
                model.addRow(new String[]{
                        d.getCodeAsString(), d.getName(), d.getWeekDay(), d.getPhaseIdAsString()
                });
            }*/

            lblCode = new JLabel("Select Code to remove:");
            lblCode.setBounds(100, 210, 200, 20);
            pnlDisciplines.add(lblCode);
            txfCode = new JTextField();
            txfCode.setBounds(250, 210, 100, 20);
            pnlDisciplines.add(txfCode);

            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 240, 150, 30);
            pnlDisciplines.add(btnConfirm);
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        code = txfCode.getText();
                        int intCode = Integer.parseInt(code);
                        DisciplineController.delete(intCode);
                        JOptionPane.showMessageDialog(btnConfirm, "Discipline with code " + code + " removed.");
                        txfCode.setText(null);
                        TablesUtil.refreshTable(model, DisciplineController.list(), d -> new String[]{
                                d.getCodeAsString(), d.getName(), d.getWeekDay(), d.getPhaseIdAsString()
                        });
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            scroll.setBounds(50, 50, 350, 150);
            pnlDisciplines.add(scroll);

            return pnlDisciplines;

        } else {
            return pnlDisciplines;
        }
    }
}
