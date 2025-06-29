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

import controller.CourseController;
import database.model.Course;
import utils.TablesUtil;

public class CoursesScreen {
	
	private MainScreen mainScreen;
	private JPanel pnlCourses;
	private String action;
	private JPanel panel;
	private JLabel lblId, lblName;
	private JTextField txfId, txfName;
	private JTable tabel;
    private JScrollPane scroll;
	private JButton btnExit, btnConfirmAdd, btnConfirm;
	private String name;
	private String id;
	
	private DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
	
	public CoursesScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }
	
	public JPanel createPanel() throws SQLException {
		pnlCourses = new JPanel();
		pnlCourses.setBorder(BorderFactory.createTitledBorder("Courses"));
		pnlCourses.setLayout(null);
		pnlCourses.setVisible(true);
		pnlCourses.setBounds(210, 120, 450, 280);
		action = MainScreen.getActionSelected();
		
		btnExit = new JButton("<--");
        btnExit.setBounds(20, 20, 50, 20);
        pnlCourses.add(btnExit);

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SelectTableScreen selectScreen = new SelectTableScreen();
                panel = selectScreen.createComponentsSelectTable(mainScreen);
                mainScreen.setPanel(panel);
            }
        });
		
		if( action.equals("View")) {
			
		model.addColumn("ID");
		model.addColumn("Name");
		tabel = new JTable(model);
		scroll = new JScrollPane(tabel);
		
		List<Course> courseList = CourseController.list();
		for(Course c : courseList) {
			id = c.getIdAsString();
			name = c.getName();
			
			model.addRow(new String[] {
					id, name
			});
		}
		
		scroll.setBounds(100, 50, 250, 150);
		pnlCourses.add(scroll);
		
		return pnlCourses;
			
		} else if( action.equals("Add")) {
			
			lblName = new JLabel("Name of the course to be added:");
			lblName.setBounds(130, 100, 200, 20);
			pnlCourses.add(lblName);
			txfName = new JTextField();
			txfName.setBounds(150, 120, 150, 20);
			pnlCourses.add(txfName); 
			
			btnConfirmAdd = new JButton("Confirm");
	        btnConfirmAdd.setBounds(150, 240, 150, 30);
	        pnlCourses.add(btnConfirmAdd);
	        btnConfirmAdd.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
			
					try {
						name = txfName.getText();
						CourseController.insert(name);
						JOptionPane.showMessageDialog(btnConfirm, "Course: " + name + ", was added.");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			});
			
			return pnlCourses;
			
		} else if( action.equals("Remove")) {
			
			model.addColumn("ID");
			model.addColumn("Name");
			tabel = new JTable(model);
			scroll = new JScrollPane(tabel);
			
			List<Course> courseList = CourseController.list();
			for(Course c : courseList) {
				id = c.getIdAsString();
				name = c.getName();
				
				model.addRow(new String[] {
						id, name
				});
			}
			
			lblId = new JLabel("Select ID to be removed:");
			lblId.setBounds(100, 210, 200, 20);
			pnlCourses.add(lblId);
			txfId = new JTextField();
			txfId.setBounds(250, 210, 100, 20);
			pnlCourses.add(txfId);
			btnConfirm = new JButton("Confirm");
	        btnConfirm.setBounds(150, 240, 150, 30);
	        pnlCourses.add(btnConfirm);
			btnConfirm.addActionListener( new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						id = txfId.getText();
						int intId = Integer.parseInt(id);
						CourseController.delete(intId);
						JOptionPane.showMessageDialog(btnConfirm, "The course with ID " + id + " was removed.");
						txfId.setText(null);
						TablesUtil.refreshTable(model, CourseController.list(), c -> new String[] {
				                c.getIdAsString(), c.getName()
				            });
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				
				}
			});
			
			scroll.setBounds(100, 50, 250, 150);
			pnlCourses.add(scroll);
			
			return pnlCourses;
			
		} else {
			
			return pnlCourses;
			
		}
		
	}

}
