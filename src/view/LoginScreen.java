package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.UserController;
import database.model.User;

public class LoginScreen {

    private JPanel pnlLogin;
    private JLabel lblName, lblPassword;
    private JTextField txfName;
    private JPasswordField txfPassword;
    private JButton btnLogin, btnCreateAccount;
    private User userOn;

    public JPanel createLoginPanel(MainScreen mainScreen) {
        pnlLogin = new JPanel();
        pnlLogin.setBorder(BorderFactory.createTitledBorder("Login"));
        pnlLogin.setLayout(null);
        pnlLogin.setVisible(true);
        pnlLogin.setBounds(210, 120, 450, 280);

        lblName = new JLabel("Username:");
        lblName.setBounds(80, 80, 100, 20);
        pnlLogin.add(lblName);
        txfName = new JTextField();
        txfName.setBounds(180, 80, 180, 20);
        pnlLogin.add(txfName);

        lblPassword = new JLabel("Password:");
        lblPassword.setBounds(80, 110, 100, 20);
        pnlLogin.add(lblPassword);
        txfPassword = new JPasswordField();
        txfPassword.setBounds(180, 110, 180, 20);
        pnlLogin.add(txfPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(90, 170, 120, 30);
        pnlLogin.add(btnLogin);

        btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.setBounds(220, 170, 140, 30);
        pnlLogin.add(btnCreateAccount);

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txfName.getText();
                String password = new String(txfPassword.getPassword());

                User user = null;
                try {
                    user = UserController.login(username, password);

                if (user != null) {
                    MainScreen.setLoggedUser(user);
                    mainScreen.updateButtonVisibility();
                    mainScreen.setWelcomePanel();
                } else {
                    JOptionPane.showMessageDialog(null, "Fill in all fields.");
                }
                
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "Error in login.", e1.getMessage(),
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnCreateAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txfName.getText();
                String password = new String(txfPassword.getPassword());

                try {
                    if (name.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Fill in all fields.");
                        return;
                    }
                    userOn =  UserController.insert(name, password);
                    MainScreen.setLoggedUser(userOn);
                    JOptionPane.showMessageDialog(null, "User created successfully. You can now log in.");
                    txfName.setText("");
                    txfPassword.setText("");
                } catch (Exception ex) {
                	JOptionPane.showMessageDialog(null, ex.getMessage(), "Error creating account.",
                            JOptionPane.ERROR_MESSAGE);
                    
                }
            }
        });
        
        return pnlLogin;
        
    }
}
