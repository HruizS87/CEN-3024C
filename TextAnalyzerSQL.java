import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Date: 04/11/2022
 * This is an application that revises text files and returns word count in descending order
 * @author Hector E Ruiz Silva
 * @version 1.0
 * 
 */
public class TextAnalyzer extends JFrame implements ActionListener {

	/** GUI button to used to open file */
	JButton btnOpenFile;
	/** Text area used to view word search result */
	JTextArea ta;
	/** Text area used to display path of file being opened */
	JTextArea taPath;
	/** GUI button used to view all words */
	JButton btnViewdAllWords;
	/** GUI button used to view top 20 words */
	JButton btnViewTop20Words;

	TextAnalyzer() {



     //  create a line border with the specified color and width
		Border border = BorderFactory.createLineBorder(Color.BLUE, 2);

		btnOpenFile = new JButton("Open File");
		btnOpenFile.addActionListener(this);
		btnOpenFile.setBounds(0, 0, 100, 20);

		taPath = new JTextArea(100, 20);
		taPath.setBounds(120, 0, 400, 20);
		taPath.setBorder(border);
		taPath.setEditable(false);

		btnViewdAllWords = new JButton("View All Words");
		btnViewdAllWords.addActionListener(this);
		btnViewdAllWords.setBounds(0, 50, 150, 20);

		btnViewTop20Words = new JButton("View Top 20 Words");
		btnViewTop20Words.addActionListener(this);
		btnViewTop20Words.setBounds(250, 50, 150, 20);

		ta = new JTextArea(500, 500);
		ta.setBounds(0, 80, 500, 500);
		ta.setEditable(false);

		JScrollPane scrollBar = new JScrollPane(ta);

		

		// Create a window using JFrame
		JFrame frame = new JFrame();
		
	
		// Set default close operation for JFrame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// add created controls into JFrame
				frame.add(scrollBar);

				frame.add(btnOpenFile);
				frame.add(ta);
				frame.add(taPath);
				frame.add(btnViewdAllWords);
				frame.add(btnViewTop20Words);
				frame.add(scrollBar);

		// Set JFrame size
		frame.setSize(500, 500);

		// Make JFrame get to center
		frame.setLocationRelativeTo(null);

		frame.setLayout(null);
		 
		// Make JFrame visible
		frame.setVisible(true);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpenFile) {
			JFileChooser fc = new JFileChooser();
			int i = fc.showOpenDialog(this);
			if (i == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				String filepath = f.getPath();
				taPath.setText(filepath);
				try {
					BufferedReader br = new BufferedReader(new FileReader(filepath));
					String s1 = "", s2 = "";
					while ((s1 = br.readLine()) != null) {
						s2 += s1 + "\n";
					}
					ta.setText(s2);
					br.close();
					//Store the words from the file in to database
					storeInDB(filepath);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} else if (e.getSource() == btnViewdAllWords) {
			if (!taPath.getText().equals(null) && !taPath.getText().equals("")) {// If no file selected show error
																					// message,
				try { // otherwise call the function to display words
					// display Words from the database
					displayWordsFromDB(taPath.getText(), true);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Select text file first.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == btnViewTop20Words) {

			if (!taPath.getText().equals(null) && !taPath.getText().equals("")) {// If no file selected show error
																					// message
				try {
					// display Words from the database
					displayWordsFromDB(taPath.getText(), false);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Select text file first.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	/**
	 * This method is used to display the text files words on the on GUI
	 * @param fileName Used to create scanner file
	 * @param isAllWords Sets conditions for text being read
	 * @throws FileNotFoundException Signals if any error happens when opening file
	 */
	public void displayWords(String fileName, boolean isAllWords) throws FileNotFoundException {
		
		
		/** Reading file line by line */
		File file = new File(fileName);
		Scanner scan = new Scanner(file);
		Connection con=getConnection();
		PreparedStatement ps=null;
		/**
		 * map to store key value pair key : word value: frequency of the word
		 */
		List<String> al = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		while (scan.hasNextLine()) {
			String val = scan.nextLine(); // reading line by line
			String str[] = val.split(" ");
			for (String word : str)
				if (word != null && word != " " && word.length() > 1)
					al.add(word);
			for (String value : al) {
				if (map.containsKey(value) == false) // if the string is not inserted in the map yet then insert by
														// setting the frequency as 1
					map.put(value, 1);
				else // otherwise remove the entry from map and again insert by adding 1 in the
						// frequency
				{
					int count = (int) (map.get(value)); // finding the current frequency of the word
					map.remove(value); // removing the entry from the map
					map.put(value, count + 1); // reinserting the word and increase frequency by 1
				}
			}
		}
		map.remove(" ");
	
		Set<Map.Entry<String, Integer>> set = map.entrySet(); // retrieving the map contents
		List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>(set); // make an
																										// array
																										// list
		Collections.sort(sortedList, new Comparator<Map.Entry<String, Integer>>() // sorting the array list
		{
			public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b) // comparator function
																							// for
																							// sorting
			{
				return (b.getValue()).compareTo(a.getValue()); // for descending order
			}
		});

		if (isAllWords) {// Using same function, using boolean value separates output
			String textAreaContent = "";
			for (Map.Entry<String, Integer> i : sortedList) {
				textAreaContent = textAreaContent + i.getKey() + " -> " + i.getValue() + "\n";
			}
			ta.setText("");
			ta.setText(textAreaContent);
		} else {
			String textAreaContent = "";
			int j = 0;
			for (Map.Entry<String, Integer> i : sortedList) {
				if (j == 20)
					break;
				textAreaContent = textAreaContent + i.getKey() + " " + i.getValue() + "\n";
				j++;
			}
			ta.setText("");
			ta.setText(textAreaContent);
		}

	}
	
	public Connection getConnection() {
		Connection con=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/WordOccurrences?serverTimezone=UTC", "root","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/**
	 * This method is used to insert words from file
	 * @param fileName Used to create scanner file
	 * @throws FileNotFoundException Signals if any error happens when opening file
	 */
	public void storeInDB(String fileName)throws FileNotFoundException {
		Connection con=getConnection();
		PreparedStatement ps=null;
		
		if(con==null){
			return;
		}
			
			/**
			 * map to store key value pair key : word value: frequency of the word
			 */
		List<String> al = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		File file = new File(fileName);
		Scanner scan = new Scanner(file);
		while (scan.hasNextLine()) {
			String val = scan.nextLine(); // reading line by line
			String str[] = val.split(" ");
			for (String word : str)
				if (word != null && word != " " && word.length() > 1)
					al.add(word);
			for (String value : al) {
				if (map.containsKey(value) == false) // if the string is not inserted in the map yet then insert by
														// setting the frequency as 1
					map.put(value, 1);
				else // otherwise remove the entry from map and again insert by adding 1 in the
						// frequency
				{
					int count = (int) (map.get(value)); // finding the current frequency of the word
					map.remove(value); // removing the entry from the map
					map.put(value, count + 1); // reinserting the word and increase frequency by 1
				}
			}
		}
			map.remove(" ");
			
			try {
				//Delete all previous Words
				ps=con.prepareStatement("delete from WordCount");
				ps.execute();
				ps.close();
				ps=con.prepareStatement("insert into WordCount (word,count) values(?,?)");
				for(String key:map.keySet()) {
					ps.setString(1, key);
					ps.setInt(2, map.get(key));
					ps.addBatch();
				}
				ps.executeBatch();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * This method is used to display words from Database on the on GUI
	 * @param fileName Used to create scanner file
	 * @param isAllWords Sets conditions for text being read
	 * @throws FileNotFoundException Signals if any error happens when opening file
	 */
	public void displayWordsFromDB(String fileName, boolean isAllWords)throws FileNotFoundException {
		Connection con=getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			if (isAllWords) {// Using same function, using boolean value separates output
				String textAreaContent = "";
				ps=con.prepareStatement("select * from WordCount order by count desc");
				rs=ps.executeQuery();
				while(rs.next()) {
					textAreaContent = textAreaContent + rs.getString("word") + " -> " + rs.getInt("count") + "\n";
				}
				
				ta.setText("");
				ta.setText(textAreaContent);
			} else {
				String textAreaContent = "";
				ps=con.prepareStatement("select * from WordCount order by count desc limit 20");
				rs=ps.executeQuery();
				while(rs.next()) {
					textAreaContent = textAreaContent + rs.getString("word") + " -> " + rs.getInt("count") + "\n";
				}
				ta.setText("");
				ta.setText(textAreaContent);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param args Argument parameter for Main method
	 */
	public static void main(String[] args) {
		TextAnalyzer om = new TextAnalyzer();
	}
}
