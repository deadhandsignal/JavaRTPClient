/*Main class to create the majority of window/JFrames for the application*/
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.media.*;
import javax.media.rtp.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;




      

public class CcrRtpAdmin extends JFrame implements ActionListener,KeyListener,MouseListener,ItemListener {
    	
    	protected	Vector targets;
    	private	JList list;
		private JCheckBox uccCheckButton,otherCheckButton;
		private JLabel pictureLabel1,pictureLabel2,label;
		protected JPanel mainPanel,servicesPanel,actionsPanel,panelThree,trackerPanel;
		private JButton startRec,playButton,buttonHelp,buttonStop,remTarg,toolButSave,radioInfo,okRTP,buttonSess; 
		private JToolBar toolBar;
		private JMenuBar menuBar;
		private JMenu menuFile, menuTools, menuHelp;
		private JMenuItem menuItem,menuItemPlay,menuItemHelp,menuItemParams,menuItemRtpSess,menuItemClose,menuItemSave,menuItemOpenRtp, menuItemEditDispSchedule;
    	private JTextField tf_remote_data_port,tf_remote_address, tf_local_data_port,tf_local_host,tf_media_file;
    	private Container contentPane, contentPaneOne; 
    	private MetalTheme myColor;
    	protected JFrame othRtpFrame,sessTrackFrame,sessTracFrame,rtpSession;
    	protected String ip,port,localPort,UCCip,UCCport,UCClocalPort;
    	private JFileChooser playFile;
    	private File fileToPlay;
  		private TargetListModel listModel;
        protected AVReceiver CcrRec;
    	protected Config config;
    	protected CcrSchedule schedule;
    	private EditDisplayJFrame TkEditor;
    	private SaveStream saveFrame;
    	private ExampleFileFilter filter;
    	private MediaFilePlayer mediaPlayer;

    	CcrRtpAdmin(){
    		
    	
			//Set Look and Feel
   try{
   		   myColor = new uccTheme();
    	   MetalLookAndFeel.setCurrentTheme(myColor);
           UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
           SwingUtilities.updateComponentTreeUI(this);

   }catch(Exception e)
   {
           System.out.println(e + "Problem with Look&Feel?");
   }
   
		//use Java.sun's Config class for RTP History frame,config manager

           config= new Config();
        
		//UCC Rtp Stream variables        
        targets= config.targets;
        UCCip= "62.17.158.177";
	    UCCport="4444";
	    UCClocalPort= "4444";
        listModel= new TargetListModel( targets);
        list= new JList(listModel );//listModel
        
		//Setup Menu------------------------------------------------------------------
       	menuBar = new JMenuBar();
    	this.setJMenuBar(menuBar);
        menuFile = new JMenu("File");
        menuBar.add(menuFile);
        
        menuItemPlay = new JMenuItem("Play Audio File...");
        menuItemPlay.addActionListener(new OpenListener());
        menuFile.add(menuItemPlay);
        menuItemPlay.addActionListener(this);
        menuItemOpenRtp = new JMenuItem("Open RTP Session..");
        menuFile.add(menuItemOpenRtp);
        menuItemOpenRtp.addActionListener(this);
        menuItemClose = new JMenuItem("Exit");
        menuItemClose.addActionListener(this);
        menuFile.add(menuItemClose);

	    menuTools = new JMenu("Tools");
        menuBar.add(menuTools);
        
        menuItemRtpSess = new JMenuItem("RTP Session History");
        menuTools.add(menuItemRtpSess);
        menuItemRtpSess.addActionListener( this);
        
        menuItemEditDispSchedule = new JMenuItem("Edit/Display Schedule");
        menuTools.add(menuItemEditDispSchedule);
        menuItemEditDispSchedule.addActionListener( this);
        
        menuItemSave = new JMenuItem("Save RTP Stream");
        menuTools.add(menuItemSave);
        menuItemSave.addActionListener( this);
        
        menuHelp = new JMenu ("Help");
        menuItemHelp = new JMenuItem ("Help Guide");
        menuItemHelp.addActionListener(this);
        menuHelp.add(menuItemHelp);
        menuBar.add(menuHelp);

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        addButtons(toolBar);
 		
		servicesPanel = createServicesPanel();
		actionsPanel = createActionsPanel();
		contentPane = new JPanel();
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		
		contentPane.setLayout(new BorderLayout());
		contentPane.add(toolBar, BorderLayout.NORTH);

		contentPane.add(mainPanel, BorderLayout.CENTER);
		setContentPane(contentPane);
		
		mainPanel.add( servicesPanel);
		mainPanel.add( actionsPanel);

		pack();
		setTitle( "Cork Campus Radio RTP Admin");
		setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(270,260);
		setSize (360,200);
        setVisible( true);

	}
	
// Panel methods for Main JFrame--------------------------------------------------------
	
	//-----toolbar panel
	protected void addButtons(JToolBar toolBar) {

		//first toolbar button
       	playButton = new JButton(new ImageIcon("images/OpenFile.gif"));
        playButton.setToolTipText("Open/Play an audio file");
        toolBar.add(playButton);
        playButton.addActionListener(new OpenListener());

        //second toolbar button
        toolButSave = new JButton(new ImageIcon("images/OpenStream.gif"));
        toolButSave.setToolTipText("Save an RTP Stream");
        toolButSave.addActionListener(this);
        toolBar.add(toolButSave);
        

        //third toolbar button        
        buttonSess= new JButton(new ImageIcon("images/RtpMon.gif"));
        buttonSess.addActionListener(this);        
        buttonSess.setToolTipText("Session History");
        toolBar.add(buttonSess);
        
        //fourth toolbar button        
        buttonStop = new JButton(new ImageIcon("images/StopRec.gif"));
        buttonStop.setToolTipText("Stop Receiving RTP Stream");
        buttonStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
     try{	
            	CcrRec.close();
				CcrRec= null;		
				startRec.setText("Start Receiver...");
	   }
	catch (Exception er)
	{
		System.out.println("Not currently receiving a stream");
	}}
		});	
		
        toolBar.add(buttonStop);
        
        //fifth toolbar button
        buttonHelp = new JButton(new ImageIcon("images/RadioInfo.gif"));
        buttonHelp.setToolTipText("Help Guide");
        toolBar.add(buttonHelp);
        buttonHelp.addActionListener(this);

    }
 
		//This method creates the actions panel with the 'Start Receiver' and 'CCR Today' buttons	   
	    private JPanel createActionsPanel() {
	    
        JPanel actionPan= new JPanel();
        actionPan.setLayout(new GridLayout(2,1));
        
        actionPan.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("Services"),
                      BorderFactory.createEmptyBorder(5,5,5,5)));
                      
        startRec= new JButton( "Start Receiver...");
        startRec.setBorder(BorderFactory.createRaisedBevelBorder());
		actionPan.add( startRec);
		radioInfo = new JButton( "CCR Today...");
        radioInfo.setBorder(BorderFactory.createRaisedBevelBorder());
	    actionPan.add(radioInfo);
	    				
		startRec.addActionListener( this);
		radioInfo.addActionListener( this);
		
		return actionPan;
}

		//This method creates the Services panel with the CCR, and Other RTP check boxes
 		private JPanel createServicesPanel() {
	    	
        	JPanel servicePan= new JPanel();
        	servicePan.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("Select"),
                      BorderFactory.createEmptyBorder(1,5,5,5)));
        
        	uccCheckButton = new JCheckBox("CCR Radio");
        	uccCheckButton.setMnemonic(KeyEvent.VK_C); 
        	uccCheckButton.setSelected(false);
        	uccCheckButton.addItemListener(this);
        	
        	otherCheckButton = new JCheckBox(" Other RTP");
        	otherCheckButton.setMnemonic(KeyEvent.VK_C); 
        	otherCheckButton.setSelected(false);
        	otherCheckButton.addItemListener(this);

        	pictureLabel1 = new JLabel(new ImageIcon("images/UccIcon.jpg"));
        	pictureLabel1.setToolTipText("Click to Listen!");
	    	        	
        	pictureLabel2 = new JLabel(new ImageIcon("images/OtherRtp.gif"));
        	pictureLabel2.setToolTipText("Open an RTP Stream!");

	    	servicePan.add(uccCheckButton);
	    	servicePan.add(pictureLabel1);
	    	
	    	servicePan.add(otherCheckButton);
	    	servicePan.add(pictureLabel2);
	    	
	
	    			return servicePan;
}

	// This method creates the Session History frame	
 	private JFrame SessionTracker() {
        sessTracFrame = new JFrame();
		trackerPanel = new JPanel();

		JPanel sessHistPan = new JPanel();
		
       list.addMouseListener( this);
       list.addKeyListener( this);
       list.setPrototypeCellValue( "xxxxxxxxxxxxxxxxxxxxxxx");

        JScrollPane scrollPane= new JScrollPane( list,
                                                 ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setSize(30,2);                                        
        sessHistPan.add(scrollPane);
       
       	TitledBorder sessTitledBorder= new TitledBorder( new EtchedBorder(), "Session History");
        sessHistPan.setBorder( sessTitledBorder);
        
        JPanel buttonPanel = new JPanel();
        
        remTarg = new JButton("Remove Target");
		remTarg.addActionListener( this);
		
		JButton closer = new JButton("Close");
		closer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sessTracFrame.setVisible(false);
            	sessTracFrame.dispose();
            }
        });
		
		buttonPanel.add(remTarg);
		buttonPanel.add(closer);
		
			trackerPanel.add(sessHistPan, BorderLayout.CENTER);
			trackerPanel.add(buttonPanel, BorderLayout.SOUTH);
	        sessTracFrame.setTitle("RTP Session History");
	        sessTracFrame.setLocation(270,459);
	        sessTracFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	        sessTracFrame.setContentPane(trackerPanel);

        return sessTracFrame;                                    
                                          
       
}



	//This method creates the 'RTP Session' frame where the user can specify their own parameters for an RTP session
    private JFrame otherRtpWindow() {
         rtpSession = new JFrame();
        	
        	
        //Client IP Panel		
		JPanel clientIP = new JPanel();
		clientIP.setLayout(new FlowLayout());
		label = new JLabel ("          Your IP:");
		clientIP.add(label);
		clientIP.add(Box.createRigidArea(new Dimension(36,0)));
		JPanel texter3= new JPanel();
		texter3.setLayout(new BoxLayout(texter3, BoxLayout.X_AXIS));
  		tf_local_host= new JTextField(9);
  		tf_local_host.setEditable(false);
  		tf_local_host.setFont(new Font("Arial", Font.BOLD, 16));
		texter3.add( tf_local_host);
		texter3.add(Box.createRigidArea(new Dimension(22,0)));
		clientIP.add (texter3);


		//Sender IP Panel
		JPanel senderIP = new JPanel();
		senderIP.setLayout(new FlowLayout());
	    label= new JLabel( "       Sender IP:");
        senderIP.add( label);
        senderIP.add(Box.createRigidArea(new Dimension(36,0)));
		JPanel texter4= new JPanel();
		texter4.setLayout(new BoxLayout(texter4, BoxLayout.X_AXIS));
        tf_remote_address= new JTextField(9);
        tf_remote_address.setFont(new Font("Arial", Font.BOLD, 16));
       	texter4.add( tf_remote_address );
       	texter4.add(Box.createRigidArea(new Dimension(22,0)));
		senderIP.add(texter4);
		
        //Sender Port Panel
        JPanel senderPt = new JPanel();
        senderPt.setLayout(new FlowLayout());
        label= new JLabel( "Sender Port:");
		senderPt.add( label);
		senderPt.add(Box.createRigidArea(new Dimension(36,0)));
		JPanel texter1= new JPanel();
		texter1.setLayout(new BoxLayout(texter1, BoxLayout.X_AXIS));
		tf_remote_data_port= new JTextField(4);
		tf_remote_data_port.setFont(new Font("Arial", Font.BOLD, 16));
		texter1.add( tf_remote_data_port);
		texter1.add(Box.createRigidArea(new Dimension(70,0)));
		senderPt.add (texter1);
		
		//Local Port Panel
		JPanel localPt = new JPanel();
        localPt.setLayout(new FlowLayout());
		label= new JLabel( "   Local Port:");
		localPt.add( label);
		localPt.add(Box.createRigidArea(new Dimension(40,0)));
		JPanel texter2= new JPanel();
		texter2.setLayout(new BoxLayout(texter2, BoxLayout.X_AXIS));
		tf_local_data_port= new JTextField( 4);
		tf_local_data_port.setFont(new Font("Arial", Font.BOLD, 16));
		texter2.add( tf_local_data_port);
		texter2.add(Box.createRigidArea(new Dimension(70,0)));
		localPt.add(texter2);

		//buttons Panel
		JPanel paneButtons = new JPanel();
        paneButtons.setLayout(new GridLayout(1,1));
        okRTP = new JButton("OK");
		okRTP.addActionListener( this);
		JButton theClearer = new JButton("Clear Fields");
		theClearer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        		tf_remote_address.setText("");
        		tf_remote_data_port.setText("");
        		tf_local_data_port.setText("");
        	}});
		paneButtons.setBorder(new EtchedBorder());
		paneButtons.add(okRTP);
		paneButtons.add(theClearer);
		
		panelThree = new JPanel();
        panelThree.setLayout(new GridLayout(5,1));
        TitledBorder p3titledBorder= new TitledBorder( new EtchedBorder(), "RTP INFO");
		panelThree.setBorder( p3titledBorder);
		panelThree.add( clientIP);
		panelThree.add( senderIP);
		panelThree.add( senderPt);
		panelThree.add( localPt);
		panelThree.add( paneButtons);
		
		//set 'Your IP' field with local ip address
		try {
            String host= InetAddress.getLocalHost().getHostAddress();// get your own IP            	
	    	tf_local_host.setText( host);
	} catch( UnknownHostException e) {
	}
			contentPaneOne = new JPanel();
	        contentPaneOne.add(panelThree);
	        rtpSession.setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
	        rtpSession.setTitle("RTP Session");
	        rtpSession.setLocation(620,235);
	        rtpSession.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	        rtpSession.setContentPane(contentPaneOne);

        return rtpSession;
    }
    
	//Used in AVReceiver.java to give visual error message to the user

	public void actionPerformed( ActionEvent event) {
        Object source= event.getSource();
        
	//start Receiving the stream
        if( source == startRec) {	    
	   		if( startRec.getText().equals( "Start Receiver...")) {
	        	CcrRec= new AVReceiver( this, targets);
				startRec.setText( "Stop Receiver");
	    }  else {
					CcrRec.close();
					CcrRec= null;
					startRec.setText( "Start Receiver...");
			}
	}
		
	//Display Shedule
	else if (source == radioInfo) {
	  schedule = new CcrSchedule();
	  String newDay = schedule.day;
	  String newMonth =schedule.newMonth;
	  String newDate = schedule.newDate;
	  schedule.setTitle ("CCR Schedule -- "+newDay +" "+newMonth+" "+newDate);
	  schedule.setLocation(65,460);
	  schedule.setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
      schedule.pack();
      schedule.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
      schedule.setVisible(true); 
      if (newDay==null){
      	schedule.dispose();
      }  
    }

	//To Initialize an RTP Session and put it On the List from 'OK' button
	else if( source == okRTP) {
			
	    		ip=   tf_remote_address.getText().trim();	    
	    		port= tf_remote_data_port.getText().trim();
	    		localPort= tf_local_data_port.getText().trim();

	  			if( CcrRec != null) {
		CcrRec.addTarget( ip, port, localPort);
	    }
	    
	    addTargetToList( localPort, ip, port);
	} 
	
	//To remove from the list					
	else if( source == remTarg) {
	try{
		
	    int index= list.getSelectedIndex();

	    if( index != -1) {
		Target target= (Target) targets.elementAt( index);
		targets.removeElement( target);
		listModel.setData( targets);

		if( targets.size() == 0) {
		    remTarg.setEnabled( false);
		}

		if( targets.size() > 0) {
		    if( index > 0) {
			index--;
		    } else {
			index= 0;
		    }
		    list.setSelectedIndex( index);
		    setTargetFields();		    
		} else {
		    list.setSelectedIndex( -1);		    
		}		    
	  }
	    }
	    
			catch (NullPointerException e)
    	{
   		 }
	 }    
    
    
 	// open 'RTP Session' window
    else if (source == menuItemOpenRtp){
	
		othRtpFrame = otherRtpWindow();
		othRtpFrame.setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
		othRtpFrame.pack();
	    othRtpFrame.setSize(330,232);
	    othRtpFrame.setResizable(false);
        othRtpFrame.setVisible(true);
	}

    //Edit Schedule
    else if (source == menuItemEditDispSchedule) {
    	TkEditor = new EditDisplayJFrame();
    	
	}
	
	//Exit application
	else if (source == menuItemClose){
		System.exit(0);
	}


	//Open Save the stream dialogue window
	else if (source == toolButSave || source == menuItemSave){	
	    saveFrame = new SaveStream();
		
	}

	//Open 'Session History' window
	else if (source ==menuItemRtpSess || source == buttonSess){	
		sessTrackFrame = SessionTracker();
        sessTrackFrame.setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
		sessTrackFrame.pack();
	    sessTrackFrame.setSize(350,275);
        sessTrackFrame.setVisible(true);
        sessTrackFrame.validate();
        
    }
    else if (source==buttonStop){
    	
    		CcrRec.close();
			CcrRec= null;
			startRec.setText( "Start Receiver...");
    }
    
       
    
    //Open the Help guide
    else if (source==buttonHelp || source == menuItemHelp){
    	
    	   
		BrowserTest helpFrame = new BrowserTest();
		helpFrame.displayURL("help/MainHelp.html");

    }
}


	public void itemStateChanged(ItemEvent e) {
		
       Object source = e.getItemSelectable();
        
        //set rtp parameter for UCC RTP
            if (source == uccCheckButton) {
            	
            	if (e.getStateChange() == ItemEvent.SELECTED){
            		
            	 ip= UCCip;
	    		 port= UCCport;
	    		 localPort= UCClocalPort;
						if( CcrRec != null) {
								CcrRec.addTarget( ip, port, localPort);
	    					}
            	
	    addTargetToList( localPort, ip, port);
	    
	 }

     if (e.getStateChange() == ItemEvent.DESELECTED){
     	            	 ip= UCCip;
	    		 port= UCCport;
	    		 localPort= UCClocalPort;

            		    	
        int index= list.getSelectedIndex();
	    if( index != -1) {
		Target target= (Target) targets.elementAt( index);

		if( CcrRec != null) {
		    CcrRec.removeTarget( ip, port);
		}
		//targets.removeElement( target);
		listModel.setData( targets);

		if( targets.size() == 0) {
		    remTarg.setEnabled( false);
		}

		if( targets.size() > 0) {
		    if( index > 0) {
			index--;
		    } else {
			index= 0;
		    }

		    list.setSelectedIndex( index);

		    setTargetFields();		    
		} else {
		    list.setSelectedIndex( -1);		    
		}		    
	    }
            		    	
}	
}


//Open new 'RTP Session' window from GUI

		if( source == otherCheckButton )
 		{ 

			if (e.getStateChange() == ItemEvent.SELECTED){
	
				othRtpFrame = otherRtpWindow();
				othRtpFrame.setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
				othRtpFrame.pack();
			    othRtpFrame.setSize(330,232);
			    othRtpFrame.setResizable(false);
    		    othRtpFrame.setVisible(true);
   		 }
   		 
    	if (e.getStateChange() == ItemEvent.DESELECTED){
    					
	    		ip=   tf_remote_address.getText().trim();	    
	    		port= tf_remote_data_port.getText().trim();
	    		localPort= tf_local_data_port.getText().trim();
	    				if( CcrRec != null) {
		    CcrRec.removeTarget( ip, port);
		}


    	othRtpFrame.setVisible(false);
    	othRtpFrame.dispose();
		}
      }
        
    }
       
        public void windowClosing( WindowEvent event) {
		config.write();
	
        System.exit( 0);
    }

    public void windowClosed( WindowEvent event) {}

    public void windowDeiconified( WindowEvent event) {}

    public void windowIconified( WindowEvent event) {}

    public void windowActivated( WindowEvent event) {}

    public void windowDeactivated( WindowEvent event) {}

    public void windowOpened( WindowEvent event) {}

    
    //add RTP Target to List
    synchronized public void addTargetToList( String localPort,String ip, String port) {	
        
        
        ListUpdater listUpdater= new ListUpdater( localPort, ip, port, listModel, targets, remTarg);
        SwingUtilities.invokeLater( listUpdater);  		
    }
    
        
        public void keyPressed( KeyEvent event) {}
    
   		 public void keyReleased( KeyEvent event) {
       		 Object source= event.getSource();
				try{

					if( source == list) {
	    				int index= list.getSelectedIndex();
					}
				}
	
	    catch (NullPointerException e)
    {}
    }
    
    public void keyTyped( KeyEvent event) {}
    
    public void mousePressed( MouseEvent e) {}
    
    public void mouseReleased( MouseEvent e) {}
    
    public void mouseEntered( MouseEvent e) {}
    
    public void mouseExited( MouseEvent e) {}

    public void mouseClicked( MouseEvent e) {
        Object source= e.getSource();
			try{
				if( source == list) {
	   				 setTargetFields();
				}
			}	
	    catch (NullPointerException event)
    {}

    }

    //put parameters in target fields
    public void setTargetFields() {
		int index= list.getSelectedIndex();

		if( index != -1) {
	    Target target= (Target) targets.elementAt(index);

		if(target.ip != ip)
			{   
//If Ucc stream variables are checked insure they don't show in RTP session textfields         
		if(target.ip ==UCCip){ 	
			tf_remote_address.setText( "UCC Campus Radio");
	    	tf_remote_data_port.setText( "UCC");
	    	tf_local_data_port.setText( "UCC");
		}
	    	tf_remote_address.setText( target.ip);
	    	tf_remote_data_port.setText( target.port);
	    	tf_local_data_port.setText( target.localPort);	}	
	}
    }
        
            public static void main(String[] args) {
            	JFrame.setDefaultLookAndFeelDecorated(true);  									
            	new CcrRtpAdmin();

    }

// sets up the JList look and functioning for the 'RTP Session History' window

class TargetListModel extends AbstractListModel {
    private Vector options;

    public TargetListModel( Vector options) {
	this.options= options;
    }

    public int getSize() {
	int size;

	if( options == null) {
	    size= 0;
	} else {
	    size= options.size();
	}

	return size;
    }

    public Object getElementAt( int index) {
        String name;

        if( index < getSize()) {
	    Target o= (Target)options.elementAt( index);
	    
//again insure UCC parameters don't show on list	    
	    if((o.ip==UCCip)&&(o.port==UCCport)){
	    	name ="UCCCR"+" <--- "+ " UCC Campus Radio ";
	    }
	    else{
            name= o.localPort + " <--- " + o.ip + ":" + o.port;
        }
	} else {
	    name= null;
	}

	return name;
    }

    public void setData( Vector data) {
	options= data;

	fireContentsChanged( this, 0, data.size());
    }
}
//Listener class for 'Media Player' utility
private class OpenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
    	filter = new ExampleFileFilter();
    	filter.addExtension("mp3");
    	filter.addExtension("wav");
    	filter.setDescription("mp3,wave, files");

            	playFile = new JFileChooser(new File(new String("C:"+"\\" +"My Documents"+"\\"+"My Music"))); 
            int returnVal = playFile.showDialog(null,"Play");
try{

            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	
    	 					fileToPlay = playFile.getSelectedFile();
                			mediaPlayer = new MediaFilePlayer(); 
      						mediaPlayer.setMediaLocator(new MediaLocator(fileToPlay.toURL()));
                			mediaPlayer.setVisible(true);
            } else {
            	
            }
            }
                catch (Throwable t) {
            t.printStackTrace();
        }            

    }}

//'Thread' class to update JList

private class ListUpdater implements Runnable {
    String localPort, ip, port;
    TargetListModel listModel;
    Vector targets;
    JButton removeTarget;
    
    public ListUpdater( String localPort, String ip, String port,
			TargetListModel listModel, Vector targets,
			JButton removeTarget) {
	this.localPort= localPort;
	this.ip= ip;
	this.port= port;
	this.listModel= listModel;
	this.targets= targets;
	this.removeTarget= removeTarget;
    }
	
     public void run() {
         Target target= new Target( localPort, ip, port);
try{
	 if( !targetExists( localPort, ip, port)) {
     	     targets.addElement( target);
             listModel.setData( targets);
	     removeTarget.setEnabled( true);	     
	 }
    }
    catch (NullPointerException e)
    {
    	System.out.println("Rtp Window not Visible (ok)");
    }
    
    }

    public boolean targetExists( String localPort, String ip, String port) {
	boolean exists= false;
	
	for( int i= 0; i < targets.size(); i++) {
	    Target target= (Target) targets.elementAt( i);

	    if( target.localPort.equals( localPort)
	     && target.ip.equals( ip)
		&& target.port.equals( port)) {		
		exists= true;
	        break;
	    }
	}

	return exists;
    }
}

}
