package tables;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import connection.DBConnector;
import connection.MyModel;

public class ReplaceNamePanel extends JPanel {

	final String regex = "([А-Я][а-я]*)";
	final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
	String tableName = "names";
	JTable table = new JTable();
	JScrollPane scroller = new JScrollPane(table);

	JPanel upPanel = new JPanel();
	JPanel midPanel = new JPanel();
	JPanel downPanel = new JPanel();

	JButton replaceButton = new JButton("Замени");
	JLabel textLabel = new JLabel("Моля, въведете текст съдържащ български имена:");
	JLabel newTextLabel = new JLabel("Нов текст:");
	JTextArea textArea = new JTextArea(5, 20);
	JTextArea newTextArea = new JTextArea(5, 20);

	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result = null;
	MyModel model = null;
	int id = -1; // selected id

	public ReplaceNamePanel(Connection conn, PreparedStatement state, ResultSet result, MyModel model) {
		this.conn = conn;
		this.state = state;
		this.result = result;
		this.model = model;
		this.add(upPanel);
		this.add(midPanel);
		this.add(downPanel);

		// upPanel
		upPanel.setLayout(new GridLayout(3, 3));
		JScrollPane scrollPane = new JScrollPane(textArea);
		textArea.setEditable(true);
//		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setPreferredSize(new Dimension(300, 300));
		textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		upPanel.add(textLabel);
//		upPanel.add(scrollPane);
		upPanel.add(textArea);
		midPanel.add(replaceButton); 
		newTextArea.setEditable(false);
		newTextArea.setPreferredSize(new Dimension(300, 100));
		newTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		downPanel.add(newTextLabel);
		downPanel.add(newTextArea);
		// downPanel
		replaceButton.addActionListener(new ReplaceAction());
		scroller.setPreferredSize(new Dimension(300, 100));
		downPanel.add(scroller);

		this.readFromDatabase();
	}

	public JTable getJTable() {
		return table;
	}

	class ReplaceAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			conn = DBConnector.getConnection();
			String sql = "select * from " + tableName;

			try {
				state = conn.prepareStatement(sql);
				result = state.executeQuery();
				model = new MyModel(result);
				table.setModel(model);
				int rowCount = table.getRowCount();
				// Pattern MY_PATTERN = Pattern.compile("\\[(.*?)\\]");
				String textAreaText = textArea.getText();
				List<String> listMatches = new ArrayList<String>();
				Matcher m = pattern.matcher(textAreaText);

				while (m.find()) {
					String s = m.group(1);
					// s now contains "BAR"
					listMatches.add(s);
				}

				for (int i = 1; i < rowCount; i++) {
					String searchName = table.getValueAt(i, 1).toString();
					String latinName = table.getValueAt(i, 2).toString();
					if(listMatches.contains(searchName)) {
						textAreaText = textAreaText.replaceAll(searchName, latinName);
					}
				}
				newTextArea.setText(textAreaText);
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}// end method

	}// end ReplaceAction

	private void readFromDatabase() {
		conn = DBConnector.getConnection();
		String sql = "select * from " + tableName;

		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			model = new MyModel(result);
			table.setModel(model);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
}
