package view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomeScreen {

    private JPanel pnlWelcome;
    private JLabel lblWelcome, lblUserInfo, lblImage;

    public JPanel createWelcomePanel(MainScreen mainScreen, String username) {
        pnlWelcome = new JPanel();
        pnlWelcome.setBorder(BorderFactory.createTitledBorder("Welcome"));
        pnlWelcome.setLayout(null);
        pnlWelcome.setVisible(true);
        pnlWelcome.setBounds(210, 120, 450, 280);

        lblWelcome = new JLabel("Welcome, " + username + "!");
        lblWelcome.setBounds(180, 20, 200, 30);
        pnlWelcome.add(lblWelcome);

        lblUserInfo = new JLabel("Enjoy our system!");
        lblUserInfo.setBounds(180, 60, 250, 20);
        pnlWelcome.add(lblUserInfo);

        ImageIcon icon = new ImageIcon("src/assets/welcome.png");
        lblImage = new JLabel(icon);
        lblImage.setBounds(75, 90, 300, 150);
        pnlWelcome.add(lblImage);

        return pnlWelcome;
    }
}
