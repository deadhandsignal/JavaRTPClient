import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.io.File;
import javax.media.*;
import javax.media.control.TrackControl;
import javax.media.control.QualityControl;
import javax.media.Format;
import javax.media.format.*;
import javax.media.datasink.*;
import javax.media.protocol.*;
import java.io.IOException;
import com.sun.media.format.*;
import com.sun.media.multiplexer.*;
import java.io.File;



public class SaveStream extends JFrame implements ActionListener, ItemListener ,ControllerListener, DataSinkListener{
		public JPanel progPanel,theOne, firPanel,firOnePanel,firTwoPanel, secPanel,threPanel, buttPanelTwo, mainTwoPanel;
		public JLabel progLabel,sourceLabel,hrsLabel,minsLabel,secsLabel,othRtp,othRtpPortLabel;
		public String sourceName, hrsName, minsName, secsName, ortps;
		public JTextField othRtpText,othRtpPort;
		public JComboBox hrsList,minsList,secsList,hsb,ssb,msb;
		public JCheckBox CcrCheck, otherRtpCheck;
		public JFrame frame;
		public JButton fcb, saveButt,cancelButt,okButt,okNow;
		public Font theFonter,minFont;
		public Processor thisProcessor,p;
		public String[] dayStrings,hrStrings,minStrings,secsStrings;
		public String outputURL, inputURL ,hrsmine,ccrIP,ccrPort;
		public int duration;
		public int returnVal, hoursNow,secsNow,minsNow ,totalSecs;
		public JFileChooser fc;
		public File myFile, file;
		public MediaLocator iml, oml, ml;
		public ExampleFileFilter filter;
		int minValue = 0;
        public JProgressBar progressBar;
		public DataSink dsink;
		public Thread thread,runner;
  		public int counter = 0;








	
	public SaveStream(){
		
		
		//parameters for CCR Stram
		ccrIP =("194.165.183.180");
		ccrPort =("4444");

		
		//file chooser to select output destination of saved stream
		fc = new JFileChooser(new File(new String("C:"+"\\" +"My Documents"+"\\"+"My Music")));
    	filter = new ExampleFileFilter();
    	filter.addExtension("mp3");
    	filter.addExtension("wav");
    	filter.setDescription("mp3 and wave files");
    	fc.setFileFilter(filter);
		fc.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
           if (returnVal == JFileChooser.APPROVE_OPTION) {
        file = fc.getSelectedFile();
        System.out.println(file);
        String OldoutputURL = file.toString();
        outputURL ="file:/"+OldoutputURL;
        System.out.println(outputURL);
        }
         else if(returnVal == JFileChooser.CANCEL_OPTION) {
          JOptionPane.showMessageDialog(
                     secPanel, "Canceled");
        }

        
  }
});
    	UIManager.put("ProgressBar.selectionBackground", Color.black);
   		UIManager.put("ProgressBar.selectionForeground", Color.white);
    	UIManager.put("ProgressBar.foreground", new Color(8, 32, 128));

   		progressBar = new JProgressBar();
    	progressBar.setMinimum(minValue);
    
		ortps = "Other RTP Source";
		theFonter= new Font("Arial", Font.BOLD+Font.ITALIC, 13);
		minFont= new Font("Arial", Font.PLAIN, 13);
		
		//String arrays for combo boxes
		String []dayStrings = { "----source----","UCC CampusRadio","Other RTP Source"};
		String []hrStrings = { "0","1","2","3","4"};
		minStrings = new String[60];
		secsStrings = new String[60];
		
		for (int i = 0; i < minStrings.length; i++) {
            minStrings[i] = Integer.toString(i);
            secsStrings[i] = Integer.toString(i);

        }
        
        //panels to select RTP source of stream
		firPanel = new JPanel();
		firPanel.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("1) Choose Source to Record from:"),
                      BorderFactory.createEmptyBorder(5,5,5,5)));
        firOnePanel = new JPanel();
        firTwoPanel = new JPanel();

	    CcrCheck = new JCheckBox("CCR: ");
	    CcrCheck.addItemListener(this);

	    otherRtpCheck = new JCheckBox("Other Rtp: ");
	    otherRtpCheck.addItemListener(this);
		
		othRtp = new JLabel("rtp://");
		
		othRtpText = new JTextField(9);
		othRtpText.setToolTipText("Eg:123.123.123.123");
		othRtpPortLabel = new JLabel("Port:");
		othRtpPort = new JTextField(3);
		
		okButt = new JButton("OK");
		okButt.addActionListener(this);

		firOnePanel.add(CcrCheck);
		firOnePanel.add(otherRtpCheck);
		firPanel.add(firOnePanel);
		
		//panel from which file chooser above is accessed to select input destination of saved stream.		
		secPanel = new JPanel();
		secPanel.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("2) Choose Destination File/Filename:"),
                      BorderFactory.createEmptyBorder(5,5,5,5)));

        fcb = new JButton("Select");
        fcb.setFont(theFonter);
        fcb.addActionListener(this);    	
        secPanel.add(fcb);

        //panel for user to choose the duration they wish to export stream for.
       	threPanel = new JPanel();
		threPanel.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("3) Choose Duration:"),
                      BorderFactory.createEmptyBorder(5,5,5,5)));

       hrsLabel= new JLabel("Hours:");
       hrsLabel.setFont(theFonter);
       hrsLabel.setToolTipText("Choose hours");
        

	   hrsList = new JComboBox(hrStrings);
	   hrsList.setSelectedIndex(0);
	   hrsList.setBackground(Color.white);

	   hrsList.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        hsb = (JComboBox)e.getSource();
        hrsName= (String)hsb.getSelectedItem();
        hoursNow = Integer.parseInt(hrsName)*3600;

  			}
		});

		minsLabel= new JLabel("Mins:");
		minsLabel.setFont(theFonter);
        minsLabel.setToolTipText("Choose destination of file/filename");

		minsList = new JComboBox(minStrings);		
		minsList.setBackground(Color.white);
	    minsList.setSelectedIndex(0);
	    minsList.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
       msb = (JComboBox)e.getSource();
       minsName= (String)msb.getSelectedItem();
       minsNow = Integer.parseInt(minsName)*60;

        
  }
});

		secsLabel= new JLabel("Secs:");
		secsLabel.setFont(theFonter);
        secsLabel.setToolTipText("Choose day from list");

		
		secsList = new JComboBox(secsStrings);
	    secsList.setSelectedIndex(0);
	    secsList.setBackground(Color.white);
	    secsList.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        ssb = (JComboBox)e.getSource();
        secsName= (String)ssb.getSelectedItem();
        secsNow = Integer.parseInt(secsName);


  }
});
		threPanel.add(hrsLabel);
		threPanel.add(hrsList);
		threPanel.add(minsLabel);
		threPanel.add(minsList);
		threPanel.add(secsLabel);
		threPanel.add(secsList);
	
		//panel for 'Save' and 'Cancel' buttons	
		buttPanelTwo = new JPanel();
		buttPanelTwo.setBorder(BorderFactory.createCompoundBorder(
                      BorderFactory.createTitledBorder("4) Save Stream..."),
                      BorderFactory.createEmptyBorder(1,5,5,5)));
                      
                      
		saveButt = new JButton("Save");
		saveButt.setFont(theFonter);
		saveButt.setAlignmentX(20);
		saveButt.addActionListener(this);
		cancelButt = new JButton("Cancel");
		cancelButt.addActionListener(this);
		cancelButt.setFont(theFonter);

		buttPanelTwo.add(saveButt);
		buttPanelTwo.add(cancelButt);

		
		
		mainTwoPanel = new JPanel(new GridLayout(2,2));
		mainTwoPanel.add(firPanel);
		mainTwoPanel.add(secPanel);
		mainTwoPanel.add(threPanel);
		mainTwoPanel.add(buttPanelTwo);
		theOne = new JPanel(new BorderLayout());
		
		//panel for progress bar when transcoding has begun
		progPanel=new JPanel();
		progLabel=new JLabel("Saving...");
		progPanel.add(progLabel);

    	theOne.add(mainTwoPanel,  BorderLayout.CENTER);
		getContentPane().add(theOne);        
		pack();
		setTitle( "Save RTP Stream..");
		setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );
	    JOptionPane.showMessageDialog(this, "Please insure the stream you wish to save is currently transmitting by playing it first with 'CCR RTP Admin'. ", "Warning!", JOptionPane.WARNING_MESSAGE);    
	    setLocation(150,460);
		setSize (612,258);
		setResizable(true);
        setVisible( true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
            	fileDone = true;
            	SaveStream.this.dispose();
            }});
}

     MediaLocator createMediaLocator(String url) {

	if (url.indexOf(":") > 0 && (ml = new MediaLocator(url)) != null)
	    return ml;

	if (url.startsWith(File.separator)) {
	    if ((ml = new MediaLocator("file:" + url)) != null)
		return ml;
	} else {
	    String file = "file:" + System.getProperty("user.dir") + File.separator + url;
	    if ((ml = new MediaLocator(file)) != null)
		return ml;
	}
	return null;
    }
    
    
    
    public boolean doIt(MediaLocator inML, MediaLocator outML, int duration) {
	try {
	    p = Manager.createProcessor(inML);
	    thisProcessor = p;
	    if(inputURL=="rtp://194.165.183.180:4444/audio"){
	    	
	    	System.out.println("- create processor for CCR");
	    }
	    else{
	    	System.out.println("- create processor for: " + inML);
		}
		} catch (Exception e) {
			    progressBar.setVisible(false);
	    		progLabel.setText("Saving RTP Stream Failed,Please Reset Parameters.");
				JOptionPane.showMessageDialog(this, "Error with RTP parameters/Stream. Please check URL address and Port number  insure stream is transmitting ", "ERROR", JOptionPane.ERROR_MESSAGE);
				p.stop();
            	p.deallocate();
            	p.close();
	    		System.err.println("Cannot create a processor from the given url: " + e);
	    return false;
	}

	p.addControllerListener(this);

	// Put the Processor into configured state.
	p.configure();

	if (!waitForState(p, p.Configured)) {
		
		progressBar.setVisible(false);
	    progLabel.setText("Saving RTP Stream Failed,Please Reset Parameters.");
		JOptionPane.showMessageDialog(this, "Error with RTP parameters/Stream. Please check parameters to insure stream is transmitting ", "ERROR", JOptionPane.ERROR_MESSAGE);
	    p.stop();
        p.deallocate();
        p.close();
	    System.err.println("Failed to configure the processor.");
	    return false;
	}

	// Set the output content descriptor based on the media locator.
	setContentDescriptor(p, outML);

	// Program the tracks.
	if (!setTrackFormats(p))
	    return false;

	// We are done with programming the processor.  Let's just
	// realize it.
	p.realize();
	if (!waitForState(p, p.Realized)) {
			progressBar.setVisible(false);
	    	progLabel.setText("Saving RTP Stream Failed,Please Reset Parameters.");
			JOptionPane.showMessageDialog(this, "There may be a problem with stream you are trying to save ", "ERROR", JOptionPane.ERROR_MESSAGE);
			p.stop();
        	p.deallocate();
       		p.close();
	    System.err.println("Failed to realize the processor.");
	    return false;
	}

	// Now, we'll need to create a DataSink.
	if ((dsink = createDataSink(p, outML)) == null) {
			progressBar.setVisible(false);
	    	progLabel.setText("Saving RTP Stream Failed,Please Reset Parameters.");
			JOptionPane.showMessageDialog(this, "Error with the Input File name/format please ensure it is of format 'somename.mp3' or 'somename.wav'", "ERROR", JOptionPane.ERROR_MESSAGE);
	    	p.stop();
        	p.deallocate();
        	p.close();
	    	System.err.println("Failed to create a DataSink for the given output MediaLocator: " + outML);
	    return false;
	}

			dsink.addDataSinkListener(this);
			fileDone = false;

	// Set the stop time if there's one set.
	if (duration > 0)
	    p.setStopTime(new Time((double)duration));

	System.err.println("start transcoding ...");

	// OK, we can now start the actual transcoding.
	try {
	    p.start();
	    dsink.start();
	} catch (IOException e) {
		progressBar.setVisible(false);
	    progLabel.setText("Saving RTP Stream Failed,Please Reset Parameters.");
		JOptionPane.showMessageDialog(this,"Error during transcoding,Please reset parameters and Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
	    p.stop();
        p.deallocate();
        p.close();
	    System.err.println("IO error during transcoding");
	    return false;
	}

	// Wait for EndOfStream event.
	waitForFileDone(duration);

	// Cleanup.
	try {
	    dsink.close();
	    p.stop();
        p.deallocate();
        p.close();
	} catch (Exception e) {}
	p.removeControllerListener(this);

	System.err.println("...done RTPExporting.");

	return true;
    }
    /**
     * Set the content descriptor based on the given output MediaLocator.
     */
    void setContentDescriptor(Processor p, MediaLocator outML) {

 	ContentDescriptor cd;

	// If the output file maps to a content type,
	// we'll try to set it on the processor.

	if ((cd = fileExtToCD(outML.getRemainder())) != null) {

	    System.err.println("- set content descriptor to: " + cd);

	    if ((p.setContentDescriptor(cd)) == null) {

		// The processor does not support the output content
		// type.  But we can set the content type to RAW and 
		// see if any DataSink supports it.

		p.setContentDescriptor(new ContentDescriptor(ContentDescriptor.RAW));
	    }
	}
    }
    /**
     * Set the target transcode format on the processor.
     */
    boolean setTrackFormats(Processor p) {

	Format supported[];

	TrackControl [] tracks = p.getTrackControls();

	// Do we have at least one track?
	if (tracks == null || tracks.length < 1) {
	    System.err.println("Couldn't find tracks in processor");
	    return false;
	}

	for (int i = 0; i < tracks.length; i++) {
	    if (tracks[i].isEnabled()) {
		supported = tracks[i].getSupportedFormats();
		if (supported.length > 0) {
		    tracks[i].setFormat(supported[0]);
		} else {
			JOptionPane.showMessageDialog(this,"Cannot transcode track [" + i + "]. Only Fortmats Supported are 'MP3' and 'WAV'", "ERROR", JOptionPane.ERROR_MESSAGE);
	    	p.stop();
        	p.deallocate();
        	p.close();
		    System.err.println("Cannot transcode track [" + i + "]");
		    tracks[i].setEnabled(false);
		    return false;
		}
	    } else {
		tracks[i].setEnabled(false);		
		return false;
	    }
	}
	return true;
    }

	// creates the datasink from a datasource from the processor
    DataSink createDataSink(Processor p, MediaLocator outML) {

	DataSource ds;

	if ((ds = p.getDataOutput()) == null) {
			JOptionPane.showMessageDialog(this,"Output DataSource not found", "ERROR", JOptionPane.ERROR_MESSAGE);
	    	p.stop();
        	p.deallocate();
        	p.close();

	    System.err.println("Something is really wrong: the processor does not have an output DataSource");
	    return null;
	}

	DataSink dsink;

	try {
	    System.err.println("- create DataSink for: " + outML);
	    dsink = Manager.createDataSink(ds, outML);
	    dsink.open();
	} catch (Exception e) {
	    System.err.println("Cannot create the DataSink: " + e);
	    JOptionPane.showMessageDialog(this,"Error during transcoding,Please reset parameters and Try again.", "ERROR", JOptionPane.ERROR_MESSAGE);
	    p.stop();
        p.deallocate();
        p.close();
	    return null;
	}

	return dsink;
    }

    
    
        Object waitSync = new Object();
    boolean stateTransitionOK = true;

    /**
     * Block until the processor has transitioned to the given state.
     * Return false if the transition failed.
     */
    boolean waitForState(Processor p, int state) {
	synchronized (waitSync) {
	    try {
		while (p.getState() < state && stateTransitionOK)
		    waitSync.wait();
	    } catch (Exception e) {}
	}
	return stateTransitionOK;
    }


	/**
     * Controller Listener.
     */
    public void controllerUpdate(ControllerEvent evt) {

	if (evt instanceof ConfigureCompleteEvent ||
	    evt instanceof RealizeCompleteEvent ||
	    evt instanceof PrefetchCompleteEvent) {
	    synchronized (waitSync) {
		stateTransitionOK = true;
		waitSync.notifyAll();
	    }
	} else if (evt instanceof ResourceUnavailableEvent) {
	    synchronized (waitSync) {
		stateTransitionOK = false;
		waitSync.notifyAll();
	    }
	} else if (evt instanceof MediaTimeSetEvent) {
	    System.err.println("- mediaTime set: " + 
		((MediaTimeSetEvent)evt).getMediaTime().getSeconds());
	} else if (evt instanceof StopAtTimeEvent) {
	    System.err.println("- stop at time: " +
		((StopAtTimeEvent)evt).getMediaTime().getSeconds());
	    evt.getSourceController().close();
	}
    }
    
    Object waitFileSync = new Object();
    boolean fileDone = false;
    boolean fileSuccess = true;


 /**
     * Block until file writing is done. 
     */
    boolean waitForFileDone(int duration) {
	System.err.print("  ");
	synchronized (waitFileSync) {
	    try {
		while (!fileDone) {
		    if(thisProcessor.getMediaTime().getSeconds() > duration)
			thisProcessor.close();
		    waitFileSync.wait(1000);
		    System.out.print(".");
		}
	    } catch (Exception e) {}
	}
	System.out.println("");
	return fileSuccess;
    }
    
    
    
    
     /**
     * Event handler for the file writer.
     */
    public void dataSinkUpdate(DataSinkEvent evt) {

	if (evt instanceof EndOfStreamEvent) {
	    synchronized (waitFileSync) {
		fileDone = true;
		waitFileSync.notifyAll();
	    }
	} else if (evt instanceof DataSinkErrorEvent) {
	    synchronized (waitFileSync) {
		fileDone = true;
		fileSuccess = false;
		waitFileSync.notifyAll();
	    }
	}
    }
    
    
    /**
     * Convert a file name to a content type.  The extension is parsed
     * to determine the content type.
     */
    ContentDescriptor fileExtToCD(String name) {

	String ext;
	int p;

	// Extract the file extension.
	if ((p = name.lastIndexOf('.')) < 0)
	    return null;

	ext = (name.substring(p + 1)).toLowerCase();

	String type;

	// Use the MimeManager to get the mime type from the file extension.
	if ( ext.equals("mp3")) {
	    type = FileTypeDescriptor.MPEG_AUDIO;
	} else 	if ( ext.equals("wav")) {
	    type = FileTypeDescriptor.WAVE;
	} 
	else {
	    if ((type = com.sun.media.MimeManager.getMimeType(ext)) == null)
		return null;
	    type = ContentDescriptor.mimeTypeToPackageName(type);
	}

	return new FileTypeDescriptor(type);
    }
    
    
    
    

public void actionPerformed( ActionEvent event) {
	        Object source= event.getSource();

	if( source == fcb) {

	 returnVal = fc.showSaveDialog(SaveStream.this);

		}
        if(source == saveButt)
        {        	        	

        	        	  
// Create the thread for transcoding using inner class below and supply it with the runnable object
        	Runnable runnable = new BasicThread2();
    		thread = new Thread(runnable);
    
// Start the thread for transcoding
		    thread.start();
    		progLabel.setText("Saving...");
    		progPanel.setVisible(true);
			progPanel.add(progressBar);
			progressBar.setVisible(true);
        	theOne.add(progPanel,  BorderLayout.SOUTH);
        	this.validate();
// Create and start the Thread for the progressbar
				 runner = new Thread() {
          public void run() {
            counter = minValue;
            try {
            Thread.sleep(5000);
            } catch (Exception ex) {
              }
            while (counter <= totalSecs) {
              Runnable runme = new Runnable() {
                public void run() {
                  progressBar.setValue(counter);
                }
              };
              SwingUtilities.invokeLater(runme);
              counter++;
              
              try {
                Thread.sleep(1000);
              } catch (Exception ex) {
              }
              
            }
          }
        };
        runner.start();



}
        
		//setting the RTP parameters in the first panel of window
		if (source == CcrCheck){	
			otherRtpCheck.setEnabled(false);	
			}
        
        if (source == okButt)
        
        {
        	String tkOne= othRtpText.getText().trim();
        	String tkTwo = othRtpPort.getText().trim();
        	inputURL ="rtp://" + tkOne +":"+ tkTwo +"/audio";
        	System.out.println(inputURL);
        }
        
        
        if (source == cancelButt){
				
            	fileDone = true;
            	SaveStream.this.dispose();
        }
    }
        
        public void itemStateChanged(ItemEvent e) {
		
       Object source = e.getItemSelectable();
        
            if (source == CcrCheck) {
            	
            	if (e.getStateChange() == ItemEvent.SELECTED){
            		otherRtpCheck.setEnabled(false);	
            		otherRtpCheck.setSelected(false);
            		inputURL = "rtp://"+ccrIP+":"+ccrPort+"/audio" ;
}
     if (e.getStateChange() == ItemEvent.DESELECTED){
     	            		otherRtpCheck.setEnabled(true);	
							CcrCheck.setSelected(false);
							inputURL = null;
     	
     	
}

}

	if (source == otherRtpCheck) {
            	if (e.getStateChange() == ItemEvent.SELECTED){
            		
            		CcrCheck.setEnabled(false);
            		CcrCheck.setSelected(false);	            		            		
        			firTwoPanel.add(othRtp);
					firTwoPanel.add(othRtpText);
					firTwoPanel.add(othRtpPortLabel);
					firTwoPanel.add(othRtpPort);
					firTwoPanel.add(okButt);
					firTwoPanel.setVisible(true);
					firPanel.add(firTwoPanel);
					firPanel.validate();			
		
		}
     if (e.getStateChange() == ItemEvent.DESELECTED){
     	
     	            CcrCheck.setEnabled(true);
     	            otherRtpCheck.setSelected(false);
     	            firTwoPanel.setVisible(false);
     	           	firTwoPanel.validate();	
     	           	inputURL = null;
		}

}



}


 class BasicThread2 implements Runnable {
        // This method is called when the thread runs
        public void run() {
        	
        	        totalSecs = hoursNow + minsNow +secsNow;
        	       	progressBar.setMaximum(totalSecs);
        	       	progressBar.setStringPainted(true);
					duration = totalSecs;
		
       
	 if (totalSecs == 0 ){
	 JOptionPane.showMessageDialog(buttPanelTwo, "No recording duration set!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        	 if (inputURL == null ){
	 JOptionPane.showMessageDialog(buttPanelTwo, "No recording source selected!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

	 if (outputURL == null ){
	 JOptionPane.showMessageDialog(buttPanelTwo, "No destination file selected!", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        
        	if (inputURL == null) {
	    System.err.println("No reception session is specified");
	}

	if (outputURL == null) {
	    System.err.println("No output url is specified");
	}

	if ((iml = createMediaLocator(inputURL)) == null) {
	    System.err.println("Cannot build media locator from: " + inputURL);
	}

	if ((oml = createMediaLocator(outputURL)) == null) {
	    System.err.println("Cannot build media locator from: " + outputURL);
	}
	
	if (!doIt(iml, oml, duration)) {
	    System.err.println("RTPExporting failed");
	    progressBar.setVisible(false);
	    progLabel.setText("Saving RTP Stream Failed,Please Reset Parameters.");

	}
	
    

        }
    }

}
