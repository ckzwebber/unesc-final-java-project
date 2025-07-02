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

import controller.UserController;
import database.model.User;
import utils.TablesUtil;

public class UsersScreen {

    private MainScreen mainScreen;
    private JPanel pnlUsers;
    private String action;
    private JPanel panel;
    private JLabel lblId, lblName;
    private JTextField txfId, txfName;
    private JTable table;
    private JScrollPane scroll;
    private JButton btnExit, btnConfirmAdd, btnConfirm;
    private String name, id;

    private DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public UsersScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    public JPanel createPanel() throws SQLException {
        pnlUsers = new JPanel();
        pnlUsers.setBorder(BorderFactory.createTitledBorder("Users"));
        pnlUsers.setLayout(null);
        pnlUsers.setVisible(true);
        pnlUsers.setBounds(210, 120, 450, 280);
        action = MainScreen.getActionSelected();

        btnExit = new JButton("<--");
        btnExit.setBounds(20, 20, 50, 20);
        pnlUsers.add(btnExit);

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
            table = new JTable(model);
            scroll = new JScrollPane(table);

            List<User> userList = UserController.list();
            for (User u : userList) {
                model.addRow(new String[]{
                    u.getIdAsString(), u.getUsername()
                });
            }

            scroll.setBounds(50, 50, 350, 150);
            pnlUsers.add(scroll);
            return pnlUsers;

        } else if (action.equals("Add")) {

            lblName = new JLabel("Name:");
            lblName.setBounds(50, 100, 100, 20);
            pnlUsers.add(lblName);
            txfName = new JTextField();
            txfName.setBounds(160, 100, 200, 20);
            pnlUsers.add(txfName);

            btnConfirmAdd = new JButton("Confirm");
            btnConfirmAdd.setBounds(150, 180, 150, 30);
            pnlUsers.add(btnConfirmAdd);
            btnConfirmAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        name = txfName.getText();
                        UserController.insert(name);
                        JOptionPane.showMessageDialog(btnConfirmAdd, "User: " + name + " added.");
                        txfName.setText("");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            return pnlUsers;

        } else if (action.equals("Remove")) {

            model.addColumn("ID");
            model.addColumn("Name");
            table = new JTable(model);
            scroll = new JScrollPane(table);

           List<User> userList = UserController.list();
            for (User u : userList) {
                model.addRow(new String[]{
                    u.getIdAsString(), u.getUsername()
                });
            }

            lblId = new JLabel("Select ID to remove:");
            lblId.setBounds(100, 210, 200, 20);
            pnlUsers.add(lblId);
            txfId = new JTextField();
            txfId.setBounds(250, 210, 100, 20);
            pnlUsers.add(txfId);

            btnConfirm = new JButton("Confirm");
            btnConfirm.setBounds(150, 240, 150, 30);
            pnlUsers.add(btnConfirm);
            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        id = txfId.getText();
                        int intId = Integer.parseInt(id);
                        UserController.delete(intId);
                        JOptionPane.showMessageDialog(btnConfirm, "User with ID " + id + " removed.");
                        txfId.setText(null);
                        TablesUtil.refreshTable(model, UserController.list(), u -> new String[]{
                            u.getIdAsString(), u.getUsername()
                        });
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            scroll.setBounds(50, 50, 350, 150);
            pnlUsers.add(scroll);

            return pnlUsers;

        } else {
            return pnlUsers;
        }
    }
}
