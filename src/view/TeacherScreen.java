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

import controller.TeacherController;
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
            table = new JTable(model);
            scroll = new JScrollPane(table);

         /*   List<Teacher> teacherList = TeacherController.list();
            for (Teacher t : teacherList) {
                model.addRow(new String[]{
                        t.getIdAsString(), t.getName(), t.getTitle()
                });*/
            

            scroll.setBounds(50, 50, 350, 150);
            pnlTeachers.add(scroll);
            return pnlTeachers;

        } else if (action.equals("Add")) {

            lblName = new JLabel("Name:");
            lblName.setBounds(50, 80, 100, 20);
            pnlTeachers.add(lblName);
            txfName = new JTextField();
            txfName.setBounds(160, 80, 200, 20);
            pnlTeachers.add(txfName);

            lblTitle = new JLabel("Title:");
            lblTitle.setBounds(50, 110, 100, 20);
            pnlTeachers.add(lblTitle);
            txfTitle = new JTextField();
            txfTitle.setBounds(160, 110, 200, 20);
            pnlTeachers.add(txfTitle);

            btnConfirmAdd = new JButton("Confirm");
            btnConfirmAdd.setBounds(150, 180, 150, 30);
            pnlTeachers.add(btnConfirmAdd);
            btnConfirmAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        name = txfName.getText();
                        title = txfTitle.getText();
                        TeacherController.insert(name, title);
                        JOptionPane.showMessageDialog(btnConfirmAdd, "Teacher: " + name + " added.");
                        txfName.setText("");
                        txfTitle.setText("");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            return pnlTeachers;

        } else if (action.equals("Remove")) {

            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Title");
            table = new JTable(model);
            scroll = new JScrollPane(table);
            pnlTeachers.add(scroll);

            /*List<Teacher> teacherList = TeacherController.list();
            for (Teacher t : teacherList) {
                model.addRow(new String[]{
                        t.getIdAsString(), t.getName(), t.getTitle()
                });*/
            

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
                            //    t.getIdAsString(), t.getName(), t.getTitle()
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
