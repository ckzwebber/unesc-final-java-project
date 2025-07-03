package view;

import javax.swing.*;

import database.model.ImportData;
import database.model.User;
import service.ImportService;
import utils.ImportUtil;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainScreen extends JFrame {

    private JLabel lblSystem;
    private JButton btnView, btnAdd, btnRemove, btnImportFile;
    private JPanel welcomePanel;
    private static String actionSelected;
    private JFileChooser fileChooser;
    private User userOn;
    private JPanel loginPanel;

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
        lblSystem = new JLabel("Register system");
        lblSystem.setBounds(20, 20, 150, 40);
        getContentPane().add(lblSystem);

        btnView = new JButton("View");
        btnView.setBounds(20, 180, 100, 40);
        getContentPane().add(btnView);

        btnAdd = new JButton("Add");
        btnAdd.setBounds(20, 230, 100, 40);
        getContentPane().add(btnAdd);

        btnRemove = new JButton("Remove");
        btnRemove.setBounds(20, 280, 100, 40);
        getContentPane().add(btnRemove);

        btnImportFile = new JButton("Import file");
        btnImportFile.setBounds(750, 200, 100, 100);
        getContentPane().add(btnImportFile);

        updateButtonVisibility();

        if (getLoggedUser() == null) {
            setLoginPanel();
        } else {
            setWelcomePanel();
        }
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAdd.setBackground(UIManager.getColor("Button.background"));
                btnRemove.setBackground(UIManager.getColor("Button.background"));
                btnImportFile.setBackground(UIManager.getColor("Button.background"));
                btnView.setBackground(new Color(50, 205, 50));

                setActionSelected("View");
                getContentPane().remove(welcomePanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                welcomePanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(welcomePanel);
                repaint();
                revalidate();
            }
        });

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnView.setBackground(UIManager.getColor("Button.background"));
                btnRemove.setBackground(UIManager.getColor("Button.background"));
                btnImportFile.setBackground(UIManager.getColor("Button.background"));
                btnAdd.setBackground(new Color(50, 205, 50));

                setActionSelected("Add");
                getContentPane().remove(welcomePanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                welcomePanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(welcomePanel);
                repaint();
                revalidate();
            }
        });

        btnRemove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAdd.setBackground(UIManager.getColor("Button.background"));
                btnView.setBackground(UIManager.getColor("Button.background"));
                btnImportFile.setBackground(UIManager.getColor("Button.background"));
                btnRemove.setBackground(new Color(50, 205, 50));

                setActionSelected("Remove");
                getContentPane().remove(welcomePanel);
                SelectTableScreen selectScreen = new SelectTableScreen();
                welcomePanel = selectScreen.createComponentsSelectTable(MainScreen.this);
                getContentPane().add(welcomePanel);
                repaint();
                revalidate();
            }
        });

        btnImportFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnAdd.setBackground(UIManager.getColor("Button.background"));
                btnView.setBackground(UIManager.getColor("Button.background"));
                btnRemove.setBackground(UIManager.getColor("Button.background"));
                btnImportFile.setBackground(new Color(50, 205, 50));

                fileChooser = new JFileChooser();

                if (e.getSource() == btnImportFile) {
                    int i = fileChooser.showOpenDialog(MainScreen.this);
                    if (i == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        ImportService is = new ImportService();
                        try {
                            ImportData importData = is.readImportFile(file.getPath().toString());

                            String previewImportString = ImportUtil.generatePreviewImportString(importData);

                            int confirmImportOption = JOptionPane.showConfirmDialog(MainScreen.this,
                                    previewImportString,
                                    "Confirm Import?", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);

                            if (confirmImportOption == JOptionPane.YES_OPTION) {
                                ImportData importedData = is.importDataFile(importData);
                                if (importedData != null) {
                                    JOptionPane.showMessageDialog(MainScreen.this, "Import completed successfully!");
                                    setPanel(welcomePanel);
                                } else {
                                    JOptionPane.showMessageDialog(MainScreen.this, "Error importing data.",
                                            "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(MainScreen.this, "Import cancelled by user.");
                            }

                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(MainScreen.this, "Error: " + ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    public void setLoginPanel() {
        if (welcomePanel != null) {
            getContentPane().remove(welcomePanel);
        }

        LoginScreen loginScreen = new LoginScreen();
        loginPanel = loginScreen.createLoginPanel(MainScreen.this);
        getContentPane().add(loginPanel);
        repaint();
        revalidate();
    }

    public void setWelcomePanel() {
        if (loginPanel != null) {
            getContentPane().remove(loginPanel);
            loginPanel = null;
        }

        WelcomeScreen welcomeScreen = new WelcomeScreen();
        welcomePanel = welcomeScreen.createWelcomePanel(MainScreen.this);
        getContentPane().add(welcomePanel);
        repaint();
        revalidate();
    }

    public void setPanel(JPanel newPanel) {
        if (welcomePanel != null) {
            getContentPane().remove(welcomePanel);
        }
        if (loginPanel != null) {
            getContentPane().remove(loginPanel);
            loginPanel = null;
        }

        welcomePanel = newPanel;
        getContentPane().add(welcomePanel);
        repaint();
        revalidate();
    }

    private static User loggedUser;

    public static User getLoggedUser() {
        return loggedUser;
    }

    public static void setLoggedUser(User user) {
        loggedUser = user;
    }

    void updateButtonVisibility() {
        boolean isLoggedIn = getLoggedUser() != null;

        btnView.setVisible(isLoggedIn);
        btnAdd.setVisible(isLoggedIn);
        btnRemove.setVisible(isLoggedIn);
        btnImportFile.setVisible(isLoggedIn);
    }
}