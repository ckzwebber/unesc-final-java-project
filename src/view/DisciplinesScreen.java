package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javax.swing.plaf.ComboBoxUI;
import javax.swing.table.DefaultTableModel;

import org.postgresql.core.Utils;

import controller.SubjectController;
import controller.PhaseController;
import database.model.Course;
import database.model.Phase;
import database.model.Subject;
import utils.SubjectUtil;
import utils.TablesUtil;

public class DisciplinesScreen {

    private MainScreen mainScreen;
    private JPanel pnlDisciplines;
    private String action;
    private JPanel panel;
    private JLabel lblCode, lblName, lblWeekDay, lblPhase;
    private JTextField txfCode, txfName, txfWeekDay, txfPhaseId;
    private JTable table;
    private JScrollPane scroll;
    private JButton btnExit, btnConfirmAdd, btnConfirm;
    private String code, name, weekDay, day;
    private JComboBox<Map.Entry<Integer, String>> cbWeekdays;
	private JComboBox<Phase> cbPhases;
    private Phase selectedPhase;

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

            List<Subject> subjectList = SubjectController.list();
            for (Subject s : subjectList) {
                model.addRow(new String[] {
                        s.getCode(), s.getName(), SubjectUtil.getDayByCode(s.getWeekDay()), s.getPhaseIdAsString()
                });
            }

            scroll.setBounds(50, 50, 350, 150);
            pnlDisciplines.add(scroll);
            return pnlDisciplines;

        } else if (action.equals("Add")) {

        	JLabel lblCode = new JLabel("Code:");
            lblCode.setBounds(50, 50, 100, 20);
            pnlDisciplines.add(lblCode);
            txfCode = new JTextField();
            txfCode.setBounds(160, 50, 200, 20);
            pnlDisciplines.add(txfCode);

            JLabel lblName = new JLabel("Name:");
            lblName.setBounds(50, 80, 100, 20);
            pnlDisciplines.add(lblName);
            txfName = new JTextField();
            txfName.setBounds(160, 80, 200, 20);
            pnlDisciplines.add(txfName);

            JLabel lblDay = new JLabel("Weekday:");
            lblDay.setBounds(50, 110, 100, 20);
            pnlDisciplines.add(lblDay);
            cbWeekdays = new JComboBox<>();
            for (Map.Entry<Integer, String> entry : SubjectUtil.days.entrySet()) {
                cbWeekdays.addItem(entry);
            }
            cbWeekdays.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Map.Entry) {
                        Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) value;
                        Map.Entry<Integer, String> selectedEntry = (Map.Entry<Integer, String>) 
                        		cbWeekdays.getSelectedItem();
            int selectedDay = selectedEntry.getKey(); 
                        setText(entry.getValue());
                    }
                    return this;
                }
            });

            cbWeekdays.setBounds(160, 110, 200, 20);
            pnlDisciplines.add(cbWeekdays);

            JLabel lblPhase = new JLabel("Phase:");
            lblPhase.setBounds(50, 140, 100, 20);
            pnlDisciplines.add(lblPhase);
            
            
            JComboBox<String> cbPhases = new JComboBox<>();
            // Map para relacionar nome -> objeto Phase
            Map<String, Phase> mapPhaseByName = new HashMap<>();
            List<Phase> phases = PhaseController.list();
            for (Phase p : phases) {
                cbPhases.addItem(p.getName());
                mapPhaseByName.put(p.getName(), p);
            }
            cbPhases.setBounds(160, 140, 200, 20);
            pnlDisciplines.add(cbPhases);


            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 200, 120, 30);
            pnlDisciplines.add(btnConfirm);

            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String code = txfCode.getText();
                        String name = txfName.getText();
                        int weekDay = cbWeekdays.getSelectedIndex();
                        String selectedName = (String) cbPhases.getSelectedItem();
                        Phase selectedPhase = mapPhaseByName.get(selectedName);
                        SubjectController.insert(code, name, weekDay, 1, selectedPhase.getId());
                        JOptionPane.showMessageDialog(pnlDisciplines, "Subject added.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pnlDisciplines, "Error: " + ex.getMessage());
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

            List<Subject> subjectList = SubjectController.list();
            for (Subject s : subjectList) {
                model.addRow(new String[] {
                        s.getCode(), s.getName(), SubjectUtil.getDayByCode(s.getWeekDay()), s.getPhaseIdAsString()
                });
            }

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
                        SubjectController.delete(intCode);
                        JOptionPane.showMessageDialog(btnConfirm, "Subject with code " + code + " removed.");
                        txfCode.setText(null);
                        TablesUtil.refreshTable(model, SubjectController.list(), s -> new String[] {
                             s.getCode(), s.getName(), SubjectUtil.getDayByCode(s.getWeekDay()), s.getPhaseIdAsString()

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
