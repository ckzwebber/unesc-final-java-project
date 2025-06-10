package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.postgresql.core.ConnectionFactory;
import org.postgresql.core.QueryExecutor;
import org.postgresql.util.HostSpec;

import database.dao.UserDAO;
import database.model.User;

public class ImportScreen extends JFrame{
	
	private JLabel lblSystem;
	private JButton btnView, btnImport, btnEdit, btnRemove, btnImportFile;
	private JMenuBar menuBar;
	private JMenu helpMenu;
	private JPanel pnlVoid;
	DefaultTableModel model = new DefaultTableModel();
	Integer id = -1;
	
	
public ImportScreen() {
		
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
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		helpMenu = new JMenu("Help");
		getContentPane().add(helpMenu);
		helpMenu.setBounds(750, 0, 45, 15);
		menuBar.add(helpMenu);
		
		lblSystem = new JLabel("Register system");
		lblSystem.setBounds(20, 20, 150, 40);
		getContentPane().add(lblSystem);
		
		btnView = new JButton("View");
		btnView.setBounds(20, 150, 100, 40);
		getContentPane().add(btnView);
		btnView.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	

				JTable table = new JTable();
				
				JScrollPane scroll = new JScrollPane();
				JButton btnAdd = new JButton("Add");
				JPanel pnlUsers = new JPanel();
				
				pnlUsers.setBounds(250, 120, 400, 280);
				pnlUsers.setBorder( BorderFactory.createTitledBorder("Users"));
				pnlUsers.setLayout(null);
				
				model = new DefaultTableModel();
				model.addColumn("ID");
				model.addColumn("Username");
				
				table = new JTable(model);
				scroll = new JScrollPane(table);
				scroll.setBounds(30, 30, 300, 150);
				pnlUsers.add(scroll);
				
				btnAdd = new JButton("Add"); 
				btnAdd.setBounds(280, 180, 100, 30);
				pnlUsers.add(btnAdd);
				
				try {
					UserDAO userDAO = new UserDAO();
					ArrayList<User> userList = userDAO.selectAll();
					for(int i=0; i < userList.size(); i++) {
						User user = userList.get(i);
						Integer id = user.getId();
						String strID = Integer.toString(id);
						model.addRow( new String[] {strID, user.getUsername()});
					}
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
		        btnAdd.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	
		            	id = id + 1;
		            	
		            	String name = JOptionPane.showInputDialog("Name to be added: ");
		 
		            	String SId = Integer.toString(id);
		            	
		                model.addRow(new String[]{ SId, name});
		            }
		        });
				
		        getContentPane().remove(pnlVoid);
		        getContentPane().add(pnlUsers);
		        repaint();
		        revalidate();

			}
		});
		
		btnImport = new JButton("Import");
		btnImport.setBounds(20, 210, 100, 40);
		getContentPane().add(btnImport);
		
		btnEdit = new JButton("Edit");
		btnEdit.setBounds(20, 270, 100, 40);
		getContentPane().add(btnEdit);
		
		btnRemove = new JButton("Remove");
		btnRemove.setBounds(20, 330, 100, 40);
		getContentPane().add(btnRemove);
		
		btnImportFile = new JButton("Import file");
		btnImportFile.setBounds(750, 200, 100, 100);
		getContentPane().add(btnImportFile);
		
		pnlVoid = new JPanel();
		pnlVoid.setBounds(250, 120, 400, 280);
		pnlVoid.setBorder( BorderFactory.createTitledBorder(""));
		pnlVoid.setLayout(null);
		getContentPane().add(pnlVoid);
		JLabel lblChoose = new JLabel("Choose an option in the left.");
		lblChoose.setBounds(120, 130, 160, 20);
		pnlVoid.add(lblChoose);
		
		
	}
	
}
