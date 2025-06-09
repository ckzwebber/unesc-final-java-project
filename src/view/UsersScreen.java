package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

	

public class UsersScreen extends JFrame{
	
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private JButton btnAdd;
	
	public UsersScreen() {
			setSize(450,300);
			setLayout(null);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			createComponents();
			setVisible(true);
		}
		
		private void createComponents() {
			model = new DefaultTableModel();
			model.addColumn("ID");
			model.addColumn("Username");
			
			table = new JTable(model);
			scroll = new JScrollPane(table);
			scroll.setBounds(10, 10, 300, 150);
			getContentPane().add(scroll);
			
			btnAdd = new JButton("Add"); 
			btnAdd.setBounds(320, 200, 100, 100);
			getContentPane().add(btnAdd);
	        btnAdd.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                model.addRow(new String[]{"1", "Cako"});
	            }
	        });
	    }
				
				
				
			};

