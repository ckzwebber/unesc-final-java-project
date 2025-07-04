package view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import controller.SubjectController;
import controller.PhaseController;
import database.model.Phase;
import database.model.Subject;
import utils.SubjectUtil;
import utils.TablesUtil;

public class DisciplinesScreen {

    private MainScreen mainScreen;
    private JPanel pnlDisciplines;
    private String action;
    private JPanel panel;
    private JTextField txfCode, txfName;
    private JTable table;
    private JScrollPane scroll;
    private JButton btnExit, btnConfirm;
    private JComboBox<Map.Entry<Integer, String>> cbWeekdays;
    private JLabel lblId;
    private JTextField txfId;

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
            model.addColumn("Phase");
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<Subject> subjectList = SubjectController.list();
            for (Subject s : subjectList) {
                Phase subjectPhase = PhaseController.getById(s.getPhaseId());
                model.addRow(new String[] {
                        s.getCode(), s.getName(), SubjectUtil.getDayByCode(s.getWeekDay()), subjectPhase.getName(),
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
                        @SuppressWarnings("unchecked")
                        Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) value;
                        @SuppressWarnings("unchecked")
                        Map.Entry<Integer, String> selectedEntry = (Map.Entry<Integer, String>) cbWeekdays
                                .getSelectedItem();
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
                        int weekDay = cbWeekdays.getSelectedIndex() + 1;
                        String selectedName = (String) cbPhases.getSelectedItem();
                        Phase selectedPhase = mapPhaseByName.get(selectedName);
                        SubjectController.insert(code, name, weekDay, 1, selectedPhase.getId());
                        JOptionPane.showMessageDialog(pnlDisciplines, "Subject added.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pnlDisciplines, "Error: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            return pnlDisciplines;

        } else if (action.equals("Remove")) {

            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Code");
            model.addColumn("Week day");
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<Subject> subjectList = SubjectController.list();
            for (Subject s : subjectList) {
                model.addRow(new String[] {
                        s.getIdAsString(), s.getName(), s.getCode(), SubjectUtil.getDayByCode(s.getWeekDay()),
                });
            }

            lblId = new JLabel("Select ID to remove:");
            lblId.setBounds(100, 210, 200, 20);
            pnlDisciplines.add(lblId);
            txfId = new JTextField();
            txfId.setBounds(250, 210, 100, 20);
            pnlDisciplines.add(txfId);

            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 240, 150, 30);
            pnlDisciplines.add(btnConfirm);
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int id = Integer.parseInt(txfId.getText());
                        SubjectController.delete(id);
                        txfCode.setText(null);
                        TablesUtil.refreshTable(model, SubjectController.list(), s -> new String[] {
                                s.getIdAsString(), s.getName(), s.getCode(), SubjectUtil.getDayByCode(s.getWeekDay()),

                        });
                        JOptionPane.showMessageDialog(btnConfirm, "Subject with ID " + id + " removed.");

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(pnlDisciplines, "Error: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
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
