//Displays the Cork Campus Radio schedule allowing the user to choose which day to view

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;

class DisplayFrame extends JFrame  implements ActionListener
{
   public Connection connection;
   public ResultSet resultSet;
   public Statement statement;
   
	String query,dbName,dbHost,dbUsername,dbPassword;
	int columnCount;
	JTable dbTable;
	JMenuBar menuBar;
	JMenu menuFile, menuTools;
	JMenuItem menuItemMon, menuItemTue, menuItemWed, menuItemThur,menuItemFri,menuItemSat,menuItemSun;
	JScrollPane scrollPane;

//Set up GUI
   public DisplayFrame()
   {
   	   	//set parameters for database connection
		dbName =("ucccampusradio");
		dbUsername =("root");
		dbPassword =("");
		dbHost = ("//localhost/");

   	    menuBar = new JMenuBar();
    	this.setJMenuBar(menuBar);
        menuFile = new JMenu("             Choose Day to View                     ");
        menuFile.setBorder(BorderFactory.createRaisedBevelBorder());
        menuBar.add(menuFile);
        
        menuItemMon = new JMenuItem("          Monday           ");	
        menuFile.add(menuItemMon);
        menuItemMon.addActionListener( this);
        
        menuItemTue = new JMenuItem("          Tuesday          ");
        menuFile.add(menuItemTue);
        menuItemTue.addActionListener( this);
        
        menuItemWed = new JMenuItem("          Wednesday          ");
        menuFile.add(menuItemWed);
        menuItemWed.addActionListener(this);
        
        menuItemThur = new JMenuItem("          Thursday          ");
        menuFile.add(menuItemThur);
        menuItemThur.addActionListener(this);
        
        menuItemFri = new JMenuItem("          Friday          ");
        menuFile.add(menuItemFri);
        menuItemFri.addActionListener(this);
        
        menuItemSat = new JMenuItem("          Saturday          ");
        menuFile.add(menuItemSat);
        menuItemSat.addActionListener(this);
        
        menuItemSun = new JMenuItem("          Sunday          ");
        menuFile.add(menuItemSun);
        menuItemSun.addActionListener(this);
   
		setLocation(250,260);
		setIconImage ( new ImageIcon("images/logoReduced.jpg" ).getImage () );
		setTitle("CCR Schedule");
      	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      	setLocation(65,460);
	    setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
        pack();
        setVisible(true);


      System.out.println("Database being accessed...");
		
//make the connection to the database table.
      try
      {
         setDBConnection("schedules",dbUsername,dbPassword);
      }
      catch(Exception e)
      { 
        		JOptionPane.showMessageDialog(this, "Schedule Database is not available! ", "ERROR", JOptionPane.ERROR_MESSAGE);

      System.err.println(e.getMessage()); }

      System.out.println("Database processed...");

      
   }
	  //make the connection to the database table.

   public void setDBConnection(String theDataBase,String username,String password)
      throws SQLException,ClassNotFoundException

   {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql:"+dbHost+dbName+"?"+"user="+dbUsername+"&password="+dbPassword);

   }
	   //obtain information from the database
      public void setResultSet(String query) throws SQLException
   {	
      statement = connection.createStatement();
      resultSet = statement.executeQuery(query);
   }

	//show the data from the database through a JTable
   public void showResultSet()
      throws SQLException
   {
      ResultSetMetaData rsmd = resultSet.getMetaData();
      int columnCount = rsmd.getColumnCount();
      Vector columnNames = new Vector();

      for (int i = 2; i<=columnCount; i++)
         columnNames.add(rsmd.getColumnName(i));         

      Vector rows = new Vector();
      while (resultSet.next())
      {
         Vector aRow = new Vector();
         for (int i = 2; i<=columnCount; i++)
            switch( rsmd.getColumnType(i) )
            {
               case Types.INTEGER:
                  aRow.add(new Integer(resultSet.getInt(i)));
                  break;
                  case Types.TIME:
                  aRow.add(resultSet.getTime(i));
                  break;
               case Types.VARCHAR:
                  aRow.add(resultSet.getString(i));
                  break;
               default:
                  
            }
         rows.add(aRow);
      }
      setTable(rows, columnNames);
   }
	//close the connection to the database and the statement
   public void closeAll()
      throws SQLException
   {
      connection.close();
      statement.close();
   }
   //populate the JTable with the data from the resultSet() object
   public void setTable(Vector rows, Vector columnNames)
   {
      dbTable = new JTable(rows, columnNames);
      dbTable.setEnabled(false);
      int Myrows =dbTable.getRowCount();
      int Mycols = dbTable.getColumnCount();

      dbTable.getTableHeader().setFont(new Font("Times New Roman", Font.BOLD, 14));
      dbTable.setRowHeight(16);
      dbTable.setShowHorizontalLines(false);
      dbTable.setPreferredScrollableViewportSize(new Dimension(765,142));//w,h
      dbTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      
   dbTable.getColumnModel().getColumn(1).setCellRenderer(new TkRenderer());
      	
   
    int vColIndex1 = 0;
    TableColumn col1 = dbTable.getColumnModel().getColumn(vColIndex1);
    int width1 = 43;
    col1.setPreferredWidth(width1);
    
    int vColIndex2 = 1;
    TableColumn col2 = dbTable.getColumnModel().getColumn(vColIndex2);
    int width2 = 150;
    col2.setPreferredWidth(width2);
    
    int vColIndex3 = 2;
    TableColumn col3 = dbTable.getColumnModel().getColumn(vColIndex3);
    int width3 = 400;
    col3.setPreferredWidth(width3);
    
    int vColIndex4 = 3;
    TableColumn col4 = dbTable.getColumnModel().getColumn(vColIndex4);
    int width4 = 170;
    col4.setPreferredWidth(width4);
    
		if(scrollPane!=null){
	   getContentPane().remove(scrollPane);						}
       scrollPane = new JScrollPane(dbTable);
       getContentPane().add(scrollPane, BorderLayout.CENTER);
       this.pack();
       this.validate();
   }
   
   
   	//use the two methods above in one method call for convenience
   public void runner()
   {
   	try{
   			setResultSet(query);
            showResultSet();
	}
        catch(Exception e)
      {  		
     	 JOptionPane.showMessageDialog(this, "Schedule Database is not available! ", "ERROR", JOptionPane.ERROR_MESSAGE);
    	 System.err.println(e.getMessage()); }
    }
    
    
  public void actionPerformed( ActionEvent event) {
        Object source= event.getSource();
        
        if (source ==  menuItemMon){		        
        		query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '2' GROUP BY day,hour ";
        		this.setTitle("CCR Schedule - Monday");
				runner();        	
        }
        if (source ==  menuItemTue){
        	query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '3' GROUP BY day,hour ";
        	this.setTitle("CCR Schedule - Tuesday");
        	runner();
        }
        if (source ==  menuItemWed){
        	query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '4' GROUP BY day,hour ";
        	this.setTitle("CCR Schedule - Wednesday");
        	runner();
        }
        if (source ==  menuItemThur){
        	query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '5' GROUP BY day,hour ";
        	this.setTitle("CCR Schedule - Thursday");        	
        	runner();
        }
        if (source ==  menuItemFri){
        	query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '6' GROUP BY day,hour ";
            this.setTitle("CCR Schedule - Friday");
        	runner();
        }
        if (source ==  menuItemSat){
        	query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '7' GROUP BY day,hour ";
        	this.setTitle("CCR Schedule - Saturday");
        	runner();
        }
        if (source ==  menuItemSun){
        	query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '1' GROUP BY day,hour ";
        	this.setTitle("CCR Schedule - Sunday");
        	runner();
        }
}
 

	//subclass of the class that controls the rendering of the cells within the JTable
   class TkRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent(JTable table, Object value,
                              boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
            
            this.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 13));
            return this;
        }
                
   
  }

}







