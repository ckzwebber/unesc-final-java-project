package view;

import javax.swing.*;

import database.model.User;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainScreen extends JFrame {

    private JLabel lblSystem;
    private JButton btnView, btnAdd, btnRemove, btnImportFile;
    private JPanel welcomePanel;
    private static String actionSelected;
    private JFileChooser fileChooser;
    
  
    public static String getActionSelected() {
		return actionSelected;
	}

	public void setActionSelected(String actionSelected) {
		MainScreen.actionSelected = actionSelected;
	}

	public MainScreen() {
        setTitle("Model");
        setSize(900, 600);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        createComponent();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void createComponent() {
    	
    	//os botoes nao podem aparecer enquanto o usuario nao logar
        
        SelectTableScreen screen = new SelectTableScreen();
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomePanel = welcomeScreen.createWelcomePanel(MainScreen.this, getName());
        
        LoginScreen loginScreen = new LoginScreen();
        welcomePanel = loginScreen.createLoginPanel(MainScreen.this);
        getContentPane().add(welcomePanel); 

        lblSystem = new JLabel("Register system");
        lblSystem.setBounds(20, 20, 150, 40);
        getContentPane().add(lblSystem);

        btnView = new JButton("View");
        btnView.setBounds(20, 180, 100, 40);
        getContentPane().add(btnView);
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnAdd.setBackground(UIManager.getColor("Button.background"));
            	btnRemove.setBackground(UIManager.getColor("Button.background"));
            	btnImportFile.setBackground(UIManager.getColor("Button.background"));
            	btnView.setBackground( new Color(50, 205, 50));
            	
            	setActionSelected("View");
                getContentPane().remove(welcomePanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                welcomePanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(welcomePanel);
                repaint();
                revalidate();
            }
        });

        btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 230, 100, 40);
        getContentPane().add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnView.setBackground(UIManager.getColor("Button.background"));
            	btnRemove.setBackground(UIManager.getColor("Button.background"));
            	btnImportFile.setBackground(UIManager.getColor("Button.background"));
            	btnAdd.setBackground( new Color(50, 205, 50));
            	
            	setActionSelected("Add");
                getContentPane().remove(welcomePanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                welcomePanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(welcomePanel);
                repaint();
                revalidate();
            }
        });

        btnRemove = new JButton("Remove");
        btnRemove.setBounds(20, 280, 100, 40);
        getContentPane().add(btnRemove);
        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnAdd.setBackground(UIManager.getColor("Button.background"));
            	btnView.setBackground(UIManager.getColor("Button.background"));
            	btnImportFile.setBackground(UIManager.getColor("Button.background"));
            	btnRemove.setBackground( new Color(50, 205, 50));
            	
            	setActionSelected("Remove");
                getContentPane().remove(welcomePanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                welcomePanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(welcomePanel);
                repaint();
                revalidate();
            }
        });

        btnImportFile = new JButton("Import file");
        btnImportFile.setBounds(750, 200, 100, 100);
        getContentPane().add(btnImportFile);
        btnImportFile.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnAdd.setBackground(UIManager.getColor("Button.background"));
            	btnView.setBackground(UIManager.getColor("Button.background"));
            	btnRemove.setBackground(UIManager.getColor("Button.background"));
            	btnImportFile.setBackground( new Color(50, 205, 50));
				
            	fileChooser = new JFileChooser();
				
				if( e.getSource() == btnImportFile) {
					int i = fileChooser.showOpenDialog(screen);
					if( i == JFileChooser.APPROVE_OPTION) {
						File file = fileChooser.getSelectedFile();
						//caminho 
						JOptionPane.showMessageDialog(screen, file.getPath().toString());
						//service.ImportService.readImportFile(file);
					}
				}
			}
		});
    }

    public void setWelcomePanel() {
    	WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomePanel = welcomeScreen.createWelcomePanel(MainScreen.this, getName());
        getContentPane().add(welcomePanel);
	}

    public void setPanel(JPanel newPanel) {
        getContentPane().remove(welcomePanel);
        welcomePanel = newPanel;
        getContentPane().add(welcomePanel);
        repaint();
        revalidate();
    }

}
