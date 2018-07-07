
import javax.media.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MediaFilePlayer extends JFrame {


    public Player player = null;
    public JTabbedPane tabPane = null;
     
    public MediaFilePlayer() {
    	
        setTitle("CCR Media Player");
        setLocation(200, 162);
        setSize(500, 100);
        setIconImage ( new ImageIcon("images/UccIcon.jpg" ).getImage () );

        tabPane = new JTabbedPane();
        getContentPane().add(tabPane);
        addWindowListener(
         new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
            		closeCurrentPlayer();

            		}
         		});

	}	
	// main panel containing all the controls for the audio file
    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        mainPanel.setLayout(gbl);
        boolean visualComponentExists = false;

        // if the visual component exists, add it to the newly created panel.
        if (player.getVisualComponent() != null) {
            visualComponentExists = true;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.BOTH;
            mainPanel.add(player.getVisualComponent(), gbc);
        }

        // if the gain control component exists, add it to the new panel.
        if ((player.getGainControl() != null) &&
            (player.getGainControl().getControlComponent() != null)) {
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 0;
            gbc.weighty = 1;
            gbc.gridheight = 2;
            gbc.fill = GridBagConstraints.VERTICAL;
            mainPanel.add(player.getGainControl().getControlComponent(), gbc);
        }

        // Add the control panel component if it exists (it should exists in 
        // all cases.)
        if (player.getControlPanelComponent() != null) {
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = 1;
            gbc.gridheight = 1;

            if (visualComponentExists) {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.weighty = 0;
            } else {
                gbc.fill = GridBagConstraints.BOTH;
                gbc.weighty = 1;
            }

            mainPanel.add(player.getControlPanelComponent(), gbc);
        }

        return mainPanel;
    }
	
    public void setMediaLocator(MediaLocator locator) throws IOException, 
        NoPlayerException, CannotRealizeException {
        setPlayer(Manager.createRealizedPlayer(locator));
    }

    public void setPlayer(Player newPlayer) {
        // close the current player
        closeCurrentPlayer();

        player = newPlayer;

        // refresh the tabbed pane.
        tabPane.removeAll();

        if (player == null) return;

        // add the new main panel
        tabPane.add("CCR Control Panel", createMainPanel());
        Control[] controls = player.getControls();
        for (int i = 0; i < controls.length; i++) {
            if (controls[i].getControlComponent() != null) {
                tabPane.add(controls[i].getControlComponent());
            }
        }
    }

    public void closeCurrentPlayer() {
        if (player != null) {
            player.stop();
            player.close();
            
            
        }
        
    }
}

