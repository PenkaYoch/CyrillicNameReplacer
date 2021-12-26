package connection;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import tables.NamesPanel;
import tables.ReplaceNamePanel;

public class MyFrame extends JFrame{
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	MyModel model = null;
	int id = -1; //selected id
	JTabbedPane tab = new JTabbedPane();

	NamesPanel panel2 = new NamesPanel(conn, state, result, model);
	ReplaceNamePanel panel3 = new ReplaceNamePanel(conn, state, result, model);

//	JTable table = new JTable();
//	JScrollPane scroller = new JScrollPane(table);
//
//	JPanel upPanel = new JPanel();
//	JPanel midPanel = new JPanel();
//	JPanel downPanel = new JPanel();
//	
//	JButton addButton = new JButton("Add");
//	JButton delButton = new JButton("Delete");
//	JButton editButton = new JButton("Edit");
//	
//	JLabel fNameLabel = new JLabel("First Name:");
//	JLabel lNameLabel = new JLabel("Last Name:");
//	//JLabel gradeLabel = new JLabel("Avarage Grade");
//	JTextField fNameTField = new JTextField();
//	JTextField lNameTField = new JTextField();
//	JLabel genderLabel = new JLabel("Gender:");
//	//JTextField gradeTField = new JTextField();
//	String[] genders = {"Female","Male"};
//	JComboBox<String> genderCombo = new JComboBox<>(genders);
	
	public MyFrame() {
		this.setVisible(true);
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setLayout(new GridLayout(3,1));
		tab.add("Имена", panel2);
		tab.add("Замени име", panel3);

		this.add(tab);
//		this.add(upPanel);
//		this.add(midPanel);
//		this.add(downPanel);
//		
//		//upPanel
//		upPanel.setLayout(new GridLayout(4,2));
//		upPanel.add(fNameLabel);
//		upPanel.add(fNameTField);
//		upPanel.add(lNameLabel);
//		upPanel.add(fNameTField);
//		//upPanel.add(ageLabel);
//		//upPanel.add(ageTField);
//		//upPanel.add(gradeLabel);
//		//upPanel.add(gradeTField);
//		upPanel.add(genderLabel);
//		upPanel.add(genderCombo);
//		//midPanel
//		midPanel.add(addButton);
//		midPanel.add(delButton);
//		midPanel.add(editButton);
//		addButton.addActionListener(new AddAction());
//		delButton.addActionListener(new DeleteAction());
//		//downPanel
//		scroller.setPreferredSize(new Dimension(300,100));
//		downPanel.add(scroller);
		refreshTable(panel2.getTableName(), panel2.getJTable());
//		table.addMouseListener(new MouseAction());
//		
	}//end constructor
	
	public void refreshTable(String tableName, JTable table) {
		conn = DBConnector.getConnection();
		String sql = "select * from " + tableName;
		
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
			table.setModel(model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
//	class DeleteAction implements ActionListener{
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			
//			conn = DBConnector.getConnection();
//			String sql = "delete from students where id=?";
//			try {
//				state = conn.prepareStatement(sql);
//				state.setInt(1, id);
//				state.execute();
//				refreshTable("students");
//				id = -1;
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
//		
//	}
//	
//	class MouseAction implements MouseListener{
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			int row = table.getSelectedRow();
//			id = Integer.parseInt(table.getValueAt(row, 0).toString());
//			if(e.getClickCount() > 1) {
//				fNameTField.setText(table.getValueAt(row, 1).toString());
//				lNameTField.setText(table.getValueAt(row, 2).toString());
//				//gradeTField.setText(table.getValueAt(row, 3).toString());
//				if(table.getValueAt(row, 3).equals("f")) {
//					genderCombo.setSelectedIndex(0);
//				}else {
//					genderCombo.setSelectedIndex(1);
//				}
//			}
//		}
//
//		@Override
//		public void mouseEntered(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseExited(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mousePressed(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//
//		@Override
//		public void mouseReleased(MouseEvent e) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	}
//	
//	class AddAction implements ActionListener{
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			String fName = fNameTField.getText();
//			String lName = lNameTField.getText();
////			int age = Integer.parseInt(ageTField.getText());
//			//float avrGrade = Float.parseFloat(gradeTField.getText());
//			String gender;
//			if(genderCombo.getSelectedIndex() == 0) {
//				gender = "f";
//			}else {
//				gender = "m";
//			}
//			
//			conn = DBConnector.getConnection();
//			String query = "insert into items values(null,?,?,?,?);";
//			
//			try {
//				state = conn.prepareStatement(query);
//				state.setString(1, fName);
//				state.setString(2, lName);
//				//state.setFloat(3, avrGrade);
//				state.setString(4, gender);
//				state.execute();
//				refreshTable("customers");
//				clearForm();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			
//		}//end method
//		
//	}//end AddAction
//	
//	public void clearForm() {
//		fNameTField.setText("");
//		lNameTField.setText("");
//		//gradeTField.setText("");
//	}
	
}//end class MyFrame
