package view;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginScreen {
	
	private JButton btnLogin, btnCreate, btnConfirm;
	private String password, name, confirmPassword;
	private JTextField txfName, txfPassword, txfConfirmPassword;
	private JLabel lblName, lblPassword, lblConfirmPassword;
	private JPanel pnlLogin, pnlCreateAccount;

	public LoginScreen() {
		createLoginPanel();
		
	}

	private void createLoginPanel() {
		
		pnlLogin = new JPanel();
        pnlLogin.setBounds(210, 120, 450, 280);
        pnlLogin.setBorder(BorderFactory.createTitledBorder("Login:"));
        pnlLogin.setLayout(null);
        
        lblName = new JLabel();
        lblName.setBounds(80, 120, 100, 20);
        pnlLogin.add(lblName);
        txfName = new JTextField();
        txfName.setBounds(160, 120, 100, 20);
        pnlLogin.add(txfName);
        
        pnlCreateAccount = new JPanel();
        pnlCreateAccount.setBounds(210, 120, 450, 280);
        pnlCreateAccount.setBorder(BorderFactory.createTitledBorder("Create account:"));
        pnlCreateAccount.setLayout(null);
        pnlCreateAccount.setVisible(false);
		
		
	}
}
