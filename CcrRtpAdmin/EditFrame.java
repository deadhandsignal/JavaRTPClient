//JFrame to alter schedules database through a graphical user interface

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.*;





class EditFrame extends JFrame implements ActionListener, KeyListener {
	
	JPanel mainPanel, panelOne, panelThree, panelTwo, panelProgramme,panelInfo,panelPresenter;
	JComboBox timeList,dayList;
	JLabel dayLabel,timeLabel, programmeLabel,infoLabel, presenterLabel;
	JTextField programmeText,presenterText;
	JTextArea infoText;
	JButton okChoice, updateField, viewChange;
    String dayName,day,timeName, newTimeName,s1,s2,s3, resultProg, resultInfo, resultPresenter,dbName,dbHost,dbUsername,dbPassword;;
    int newDay,timeInt;
    Connection con;
	Statement s;

	
	  public EditFrame()
   {
   	try{
   		//set parameters for database connection
		dbName =("ucccampusradio");
		dbUsername =("root");
		dbPassword =("");
		dbHost = ("//localhost/");

   		
  	
   Class.forName("com.mysql.jdbc.Driver").newInstance();

   //Make connection to the database
   con = DriverManager.getConnection("jdbc:mysql:"+dbHost+dbName+"?"+"user="+dbUsername+"&password="+dbPassword);
   System.out.println("ok");
	}
	catch (Exception err) 
  {
   System.out.println("ERROR: " + err);
  }
	//arrays to populate the combo boxes
   		String[] dayStrings = { "----day----","Monday", "Tuesday", "Wednesday", "Thursday", "Friday","Saturday","Sunday" };
   		String[] timeStrings = { "----time----", "0730","0800","0830","0900","0930","1000","1030","1100","1130","1200","1230",
   	   							"1300","1330","1400","1430","1500","1530","1600","1630","1700","1730","1800","1830","1900","1930" };


//panel to allow user to choose the day and time of programme they wish to edit
	    panelOne = new JPanel();
		panelOne.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("1) Choose Day/Time of Show"),
                      BorderFactory.createEmptyBorder(5,5,5,5)));
	    
	    dayLabel= new JLabel("Day:");
        dayLabel.setToolTipText("Choose day from list");

	    timeLabel= new JLabel("Time:");
        timeLabel.setToolTipText("Choose day from list");

	    
	    dayList = new JComboBox(dayStrings);
	    dayList.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        dayName= (String)cb.getSelectedItem();      
    		
    		}
		});
		dayList.setSelectedIndex(0);
   	
		timeList = new JComboBox(timeStrings);
		timeList.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        timeName= (String)cb.getSelectedItem();
        newTimeName =(timeName+"00");
        
   			 }
		});
		timeList.setSelectedIndex(0);
		
		okChoice= new JButton( "OK");
        okChoice.setBorder(BorderFactory.createRaisedBevelBorder());
        okChoice.addActionListener(this);

		// panel where the user chooses the day and the time of the programme they wish to edit
		panelOne.add(dayLabel);
		panelOne.add(dayList);
		panelOne.add(timeLabel);
		panelOne.add(timeList);
		panelOne.add( okChoice);

	
		//panel to hold textfield where user will enter programme name
		panelProgramme = new JPanel();
		programmeLabel= new JLabel("Programme:");
	    programmeText = new JTextField(18);
	    programmeText.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 13));
		panelProgramme.add(programmeLabel);
		panelProgramme.add( programmeText);
		
		//panel to hold text area for programme information
		panelInfo = new JPanel();
		infoLabel = new JLabel("Information:");
	    infoText = new JTextArea(3,18);
	    infoText.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 13));
	    infoText.setWrapStyleWord(true);
	    infoText.setLineWrap(true);
	    panelInfo.add(infoLabel);
		panelInfo.add( infoText);
		
		//panel to hold text field for presenter information
		panelPresenter = new JPanel();
	    presenterLabel= new JLabel("Presenters:");
		presenterText = new JTextField(18);
		presenterText.setFont(new Font("Arial", Font.BOLD+Font.ITALIC, 13));
		panelPresenter.add(presenterLabel);
		panelPresenter.add( presenterText);

		//panel to hold the 'Update Schedule' and the 'View Change' buttons
		updateField= new JButton( "Update Schedule");
        updateField.setBorder(BorderFactory.createRaisedBevelBorder());
        
        panelTwo = new JPanel();
		panelTwo.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("2) Edit Fields"),
                      BorderFactory.createEmptyBorder(5,5,5,5)));

		//panel that holds the 3 'text field/area'panels created above.
		panelTwo.setLayout(new GridLayout(3,1));
		panelTwo.add(panelProgramme);
		panelTwo.add(panelInfo);
		panelTwo.add(panelPresenter);

		updateField= new JButton( "Update Schedule");
        updateField.setBorder(BorderFactory.createRaisedBevelBorder());
        updateField.addActionListener(this);
        
        
        viewChange = new JButton( "View Change");
        viewChange.setBorder(BorderFactory.createRaisedBevelBorder());
        viewChange.addActionListener(this);
       
		//panel that holds the two buttons 'Update Schedule' and 'View Change'
		panelThree = new JPanel();
		panelThree.add(updateField);
		panelThree.add(viewChange);
		
		
		mainPanel = new JPanel();
		mainPanel.add(panelOne, BorderLayout.NORTH);
		mainPanel.add(panelTwo, BorderLayout.CENTER);
		mainPanel.add(panelThree, BorderLayout.SOUTH);

		getContentPane().add(mainPanel);
				
		pack();
		setTitle( "Edit Schedule..");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    setLocation(620,240);
		setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
		setSize (400,420);
		setResizable(false);
        setVisible( true);
               
}

        public void keyPressed( KeyEvent event) {}
    
    
    	public void keyReleased( KeyEvent event) {}
       
    	public void keyTyped( KeyEvent event) {}
  



public void actionPerformed( ActionEvent event) {
        Object source= event.getSource();


		if( source == okChoice) {
	try{		
	
	/*dayName variable resolved with it's Java GregorianCalendar Object's field equivalant then used to select the 
	 * correct day's schedule*/
	 
	 if (dayName == "Sunday"){  
  	newDay = (1);
  }	else if(dayName =="Monday"){  
  	newDay = (2);
  }	else if(dayName =="Tuesday"){  
  	newDay = (3);
  }	else if(dayName =="Wednesday"){  
  	newDay = (4);
  }	else if(dayName =="Thursday"){  
  	newDay = (5);
  }	else if(dayName =="Friday"){  
  	newDay = (6);
  }	else if(dayName =="Saturday"){  
  	newDay = (7);
  }
	
	
	//query statement created and executed and data assigned to rs result set
   s = con.createStatement(); 
   s.execute("SELECT Programme,Information,Presenters FROM schedules WHERE day = '"+newDay+"' AND hour = '"+newTimeName+"'"); 
   ResultSet rs = s.getResultSet();  
   
   //data obtained from 'schedules' table written to text fields/area in GUI panel
   if (rs != null)  
   while (rs.next())  
   {
   	 s1 = rs.getString("Programme");
   	 s2 = rs.getString("Information");
   	 s3 = rs.getString("Presenters");
    programmeText.setText(s1);
    infoText.setText(s2);
    presenterText.setText(s3);
   }
		}
		
	catch (Exception err) 
  {
  		JOptionPane.showMessageDialog(this, "Schedule Database is not available! ", "ERROR", JOptionPane.ERROR_MESSAGE);
	    System.out.println("ERROR: " + err);
  }
    }
    //Values obtained from the text field/s area and inserted into database using a 'REPLACE INTO'statement
    if( source == updateField) {
    	
    	     	s1 = programmeText.getText();
   	 			s2 = infoText.getText();
   	 			s3 = presenterText.getText();
   	 			String s4 ="";

    	
    	JOptionPane dialog = new JOptionPane();
		int result=	dialog.showConfirmDialog( this, "Are You Sure You Want To Edit The Schedule?",
					"Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null );
		try{			

        switch( result ) { 
            case JOptionPane.YES_OPTION:
            
   	 			if(s1.equals(s4)){
    		 	 s = con.createStatement(); 
			  	s.execute("REPLACE INTO schedules VALUES ( '"+newDay+"', '"+newTimeName+"',NULL,NULL,NULL)");
				}
				else{
			    	s = con.createStatement(); 
			 		s.execute("REPLACE INTO schedules VALUES ( '"+newDay+"', '"+newTimeName+"','"+s1+"','"+s2+"','"+s3+"')");
				}
            break;

            case JOptionPane.NO_OPTION:
            // No button was pressed.
            System.out.println( "No button" );
            break;
    		}
		}
			catch (Exception err) 
 			{
  	  		JOptionPane.showMessageDialog(this, "Schedule Database is not available! ", "ERROR", JOptionPane.ERROR_MESSAGE);
   			System.out.println("ERROR: " + err);
  			}

}
    	
    /*user can view the schedule for the day on which they have indicate in combo box in first panel,JTable 
     * displayed by using a sub class of CcrSchedule class */
    if( source == viewChange) {
    	
   try{
    	SubCcrSched dBTable = new SubCcrSched();
        dBTable.setDBConnection("schedules",dbUsername,dbPassword);
        dBTable.setResultSet();
        dBTable.showResultSet();
        dBTable.closeAll();
        dBTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	  	dBTable.setTitle ("CCR Schedule -- "+dayName +"'s "+"Schedule");
	  	dBTable.setLocation(65,42);
		dBTable.setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
        dBTable.pack();
      	dBTable.setVisible(true);
      	
    }
    catch(Exception e){ 
        		JOptionPane.showMessageDialog(this, "Schedule Database is not available! ", "ERROR", JOptionPane.ERROR_MESSAGE);
		        System.err.println(e.getMessage()); }
	    }
        
}
//sub class of CcrSchedule class where the returnQuery() method is over ridden to access dayOfWeek variable from
//combo box above

class SubCcrSched  extends CcrSchedule

{
  public  SubCcrSched ( )
  {
    super ();

  }
  
  	public String returnQuery(){
  		
 	dayOfWeek = newDay;
    
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
    
  	query = "SELECT * FROM schedules WHERE Programme IS NOT NULL AND day = '"+dayOfWeek+"' GROUP BY day,hour ";

return query;

}
    }
    



    







}