package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreen extends JFrame {

    private JLabel lblSystem;
    private JButton btnView, btnAdd, btnRemove, btnImportFile;
    private JPanel voidPanel;
    private static String actionSelected;
    
  
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
        
        SelectTableScreen screen = new SelectTableScreen();
        
        voidPanel = screen.createPanel();
        
        //getContentPane().add(voidPanel); 

        lblSystem = new JLabel("Register system");
        lblSystem.setBounds(20, 20, 150, 40);
        getContentPane().add(lblSystem);

        btnView = new JButton("View");
        btnView.setBounds(20, 180, 100, 40);
        getContentPane().add(btnView);
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setActionSelected("View");
                getContentPane().remove(voidPanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                voidPanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(voidPanel);
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
            	setActionSelected("Add");
                getContentPane().remove(voidPanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                voidPanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(voidPanel);
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
            	setActionSelected("Remove");
                getContentPane().remove(voidPanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                voidPanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(voidPanel);
                repaint();
                revalidate();
            }
        });

        btnImportFile = new JButton("Import file");
        btnImportFile.setBounds(750, 200, 100, 100);
        getContentPane().add(btnImportFile);
    }

    public void setVoidPanel() {
		SelectTableScreen screen = new SelectTableScreen();
        voidPanel = screen.createPanel();
        getContentPane().add(voidPanel);
	}

    public void setPanel(JPanel newPanel) {
        getContentPane().remove(voidPanel);
        voidPanel = newPanel;
        getContentPane().add(voidPanel);
        repaint();
        revalidate();
    }
}
