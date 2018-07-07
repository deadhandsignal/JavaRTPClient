import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.awt.event.*;

class CcrSchedule extends JFrame  
{
   public Connection connection;
   public ResultSet resultSet;
   public Statement statement;
   public String query;
   public int columnCount;
   public int hour24,min,dayOfWeek,newMinutes,month,date;
   public Calendar cal;
   public String day,newMonth,newDate,dbName,dbHost,dbUsername,dbPassword;

   public JTable dbTable;

   public CcrSchedule()
   {
   	
   	//set parameters for database connection
		dbName =("ucccampusradio");
		dbUsername =("root");
		dbPassword =("");
		dbHost = ("//localhost/");

   	
//Use GregorianCalendar to get current time variables  	
   		cal = new GregorianCalendar();
		hour24 = cal.get(Calendar.HOUR_OF_DAY);     // 0..23
   		min = cal.get(Calendar.MINUTE);             // 0..59
    	month = cal.get(Calendar.MONTH);
    	dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	date =cal.get(Calendar.DATE);
    	this.setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );

//connect to db, get data from db table,display data in a JTable, close connection.    
      try
      {	
         setDBConnection("schedules",dbUsername,dbPassword);
         setResultSet();
         showResultSet();
         closeAll();
      }
      catch(Exception e)
      {
      	JOptionPane.showMessageDialog(null, "The Campus Radio Schedule cannot be accessed at this time.","SORRY!",
    	JOptionPane.WARNING_MESSAGE);
		this.dispose();
      	System.err.println(e.getMessage());
      	  }

      System.out.println("Database processed...");
      
   }

//method to connect to the database with native driver
   public void setDBConnection(String theDataBase,String username,String password)
      throws SQLException,ClassNotFoundException

   {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql:"+dbHost+dbName+"?"+"user="+dbUsername+"&password="+dbPassword);
   }

/**method to return the query used to access the db table,shedules. Uses the calendar object to accquire current day
*and month and date to set the title of the JFrame */

	public String returnQuery(){	
    
    if(dayOfWeek == 1){  
  	day = ("Sunday");
  }	else if(dayOfWeek == 2){  
  	day = ("Monday");
  }	else if(dayOfWeek == 3){  
  	day = ("Tuesday");
  }	else if(dayOfWeek == 4){  
  	day = ("Wednesday");
  }	else if(dayOfWeek == 5){  
  	day = ("Thursday");
  }	else if(dayOfWeek == 6){  
  	day = ("Friday");
  }	else if(dayOfWeek == 7){  
  	day = ("Saturday");
  	}
    
    if(month == 0){  
  	newMonth = ("January");
  }	else if(month == 1){  
  	newMonth = ("February");
  }	else if(month == 2){  
  	newMonth = ("March");
  }	else if(month == 3){  
  	newMonth = ("April");
  }	else if(month == 4){  
  	newMonth = ("May");
  }	else if(month == 5){  
  	newMonth = ("June");
  }	else if(month == 6){  
  	newMonth = ("July");
  }	else if(month == 7){  
  	newMonth = ("August");
  }	else if(month == 8){  
  	newMonth = ("September");
  }	else if(month == 9){  
  	newMonth = ("October");
  }	else if(month == 10){  
  	newMonth = ("November");
  }	else if(month == 11){  
  	newMonth = ("December");
  }	
  
  	if(date== 1||date==21||date==31){
  	newDate = Integer.toString(date)+"st";
  } else if (date== 2||date==22){
  	newDate = Integer.toString(date)+"nd";
  }else if (date== 3||date==23){
  	newDate = Integer.toString(date)+"rd";
  }  else {
  	newDate = Integer.toString(date)+"th";
  }


	 query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '"+dayOfWeek+"' GROUP BY day,hour ";

return query;
}

//executes the query above
   public void setResultSet() throws SQLException
   {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(returnQuery());
   }
   
   

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

   public void closeAll()
      throws SQLException
   {
      connection.close();
      statement.close();
   }

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
    

      JScrollPane scrollPane = new JScrollPane(dbTable);
      getContentPane().add(scrollPane, BorderLayout.CENTER);
   }
   
class TkRenderer extends DefaultTableCellRenderer{
        public Component getTableCellRendererComponent(JTable table, Object value,
                              boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
            
            this.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 13));
            return this;
        }
        
        
   
  }
 

}