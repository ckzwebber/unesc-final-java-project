package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

	

public class UsersScreen extends JFrame{
	
	private JTable table;
	private DefaultTableModel model;
	private JScrollPane scroll;
	private JButton add;
	
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
			
			/*add = new JButton(new AbstractAction("Adicionar") {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					modelo.addRow(new String[] {"1", "Computação", "Fase 1", "Fase 9"});
			adicionar.setBounds(320, 10, 100, 25);
			getContentPane().add(adicionar);
		}		}
			});

	}*/
}
}
