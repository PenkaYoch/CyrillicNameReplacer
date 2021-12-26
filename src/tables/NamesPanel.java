package tables;

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
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import connection.DBConnector;
import connection.MyModel;

public class NamesPanel extends JPanel {

	final String regex = "([А-Я][а-я]*)";
	final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
	String tableName = "names";
	JTable table = new JTable();
	JScrollPane scroller = new JScrollPane(table);

	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	JButton addButton = new JButton("Добави");
	JButton delButton = new JButton("Изтрий");
	// JButton editButton = new JButton("Edit");

	JLabel cNameLabel = new JLabel("Българско име:");
	JLabel lNameLabel = new JLabel("Английско име:");

	JTextField cNameTField = new JTextField();
	JTextField lNameTField = new JTextField();
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	MyModel model = null;
	int id = -1; // selected id

	public NamesPanel(Connection conn, PreparedStatement state, ResultSet result, MyModel model) {
//		this.conn = conn;
//		this.state = state;
//		this.result = result;
//		this.model = model;
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);

		this.setLayout(new GridLayout(3, 1));

		// upPanel
		upPanel.setLayout(new GridLayout(6, 2));
		upPanel.add(cNameLabel);
		upPanel.add(cNameTField);
		upPanel.add(lNameLabel);
		upPanel.add(lNameTField);

		// midPanel
		midPanel.add(addButton);
		midPanel.add(delButton);
		// midPanel.add(editButton);
		addButton.addActionListener(new AddAction());
		delButton.addActionListener(new DeleteAction());
		// downPanel
		scroller.setPreferredSize(new Dimension(300, 100));
		downPanel.add(scroller);

		table.addMouseListener(new MouseAction());
	}

	public void refreshTable(String tableName) {
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

	class DeleteAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			conn = DBConnector.getConnection();
			String sql = "delete from names where id=?";
			try {
				state = conn.prepareStatement(sql);
				state.setInt(1, id);
				state.execute();
				id = -1;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	class MouseAction implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			id = Integer.parseInt(table.getValueAt(row, 0).toString());
			if (e.getClickCount() > 1) {
				cNameTField.setText(table.getValueAt(row, 1).toString());
				lNameTField.setText(table.getValueAt(row, 2).toString());
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class AddAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String cName = cNameTField.getText();
			String lName = lNameTField.getText();

			conn = DBConnector.getConnection();
			String query = "insert into names values(null,?,?);";

			try {
				Matcher m1 = pattern.matcher(cName);
				Matcher m2 = pattern.matcher(cName);
				if (m1.find() && m2.find()) {
					state = conn.prepareStatement(query);
					state.setString(1, cName);
					state.setString(2, lName);
					state.execute();
					refreshTable("names");
					clearForm();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}// end method

	}// end AddAction

	public void clearForm() {
		cNameTField.setText("");
		lNameTField.setText("");
		// gradeTField.setText("");
	}

	public String getTableName() {
		return this.tableName;
	}

	public JTable getJTable() {
		return table;
	}
}
