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
import javax.swing.plaf.ComboBoxUI;
import javax.swing.table.DefaultTableModel;

import org.postgresql.core.Utils;

import controller.DisciplineController;
import controller.PhaseController;
import database.model.Course;
import database.model.Discipline;
import database.model.Phase;
import utils.DisciplineUtil;
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
    private String code, name, weekDay;
    private JComboBox<Phase> cbPhase;
    JComboBox<String> cbWeekDays;
    private Phase selectedPhase;
    private String[] week = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday"
    };

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

            List<Discipline> disciplineList = DisciplineController.list();
            for (Discipline d : disciplineList) {
                model.addRow(new String[] {
                        d.getIdAsString(), d.getName(), DisciplineUtil.getDayByCode(d.getWeekDay()),
                        d.getPhase().getName()
                });
            }

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
            JComboBox<String> cbWeekDays = new JComboBox<>(week);
            cbWeekDays.setBounds(160, 90, 200, 20);
            cbWeekDays.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof String) {
                        String day = (String) value;
                        setText(day);
                    }
                    //prgar dia selecionado e conventer para numero
                    return this;
                }
            });
            pnlDisciplines.add(cbWeekDays);

            lblPhase = new JLabel("Phase:");
            lblPhase.setBounds(50, 120, 100, 20);
            pnlDisciplines.add(lblPhase);

            try {
                List<Phase> phaseList = PhaseController.list();
                cbPhase = new JComboBox<>(new Vector<>(phaseList));
                cbPhase.setBounds(160, 120, 200, 20);

                cbPhase.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                        setText("ID - name, course");
                        if (value instanceof Phase) {
                            Phase phase = (Phase) value;
                            setText(phase.getIdAsString() + " – " + phase.getName() + ", " + phase.getCourse());
                        }
                        selectedPhase = (Phase) cbPhase.getSelectedItem();

                        return this;
                    }
                });

                pnlDisciplines.add(cbPhase);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(pnlDisciplines,
                        "Error on load phases:\n" + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

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
                        int weekDayCode = DisciplineUtil.getCodeByDay(weekDay);
                        DisciplineController.insert(code, name, weekDayCode, phaseIdInt);
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

            List<Discipline> disciplineList = DisciplineController.list();
            for (Discipline d : disciplineList) {
                model.addRow(new String[] {
                        d.getIdAsString(), d.getName(), DisciplineUtil.getDayByCode(d.getWeekDay()),
                        d.getPhase().getName()
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
                        DisciplineController.delete(intCode);
                        JOptionPane.showMessageDialog(btnConfirm, "Discipline with code " + code + " removed.");
                        txfCode.setText(null);
                        TablesUtil.refreshTable(model, DisciplineController.list(), d -> new String[] {
                                d.getIdAsString(), d.getName(), DisciplineUtil.getDayByCode(d.getWeekDay()),
                                d.getPhase().getName()
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
