//Small Frame to give Schedule administrator choice of whether to alter or view the Schedule


import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;



public class EditDisplayJFrame extends JFrame implements ActionListener{
	
	JButton editSchedule,displaySchedule;
	JPanel mainPanel;
	EditFrame nowEdit;
	DisplayFrame newDisp;
	
	
	  public EditDisplayJFrame()
   {
   	
   		editSchedule = new JButton ("Edit Schedule");
        editSchedule.setToolTipText("Edit Campus Radio Schedule");
        editSchedule.addActionListener(this);
        
        displaySchedule = new JButton ("Display Schedules");
        displaySchedule.setToolTipText("Display a particular day's Schedule");
        displaySchedule.addActionListener(this);


		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		
		mainPanel.add(editSchedule);
		mainPanel.add(displaySchedule);

		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		pack();
		setTitle( "Please Choose an Option...");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(270,459);
		setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
		setSize (350,120);
		setResizable(false);
        setVisible( true);      
		
}

public void actionPerformed( ActionEvent event) {
        Object source= event.getSource();


		if( source == editSchedule) {
			
				  nowEdit = new EditFrame();  
				  this.dispose(); 
		}

		if( source == displaySchedule) {
			
				  DisplayFrame newDisp = new DisplayFrame();
				  this.dispose(); 
		}


	}
}