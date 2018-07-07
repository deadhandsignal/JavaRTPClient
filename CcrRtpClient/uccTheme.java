import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import java.awt.Font;

public class uccTheme extends DefaultMetalTheme
{
	
		// white and off hite shades
	private final ColorUIResource primary1 		= new ColorUIResource(217, 217, 170); 
    private final ColorUIResource primary2 		= new ColorUIResource(255, 255, 240);
    private final ColorUIResource primary3 		= new ColorUIResource(240, 240, 224);
    
    private final ColorUIResource secondary1 	= new ColorUIResource(111, 111, 111);
    private final ColorUIResource secondary2 	= new ColorUIResource(159, 159, 159);
    private final ColorUIResource secondary3 	= new ColorUIResource(221, 221, 204);
    	// different fonts
    private final FontUIResource windowTitleFont = new FontUIResource("Verdana", Font.BOLD, 15);
    private final FontUIResource controlFont 	 = new FontUIResource("Verdana", Font.BOLD, 12);

		// the functions overridden from the base class => DefaultMetalTheme

    protected ColorUIResource getPrimary1() { return primary1; }  
    protected ColorUIResource getPrimary2() { return primary2; } 
    protected ColorUIResource getPrimary3() { return primary3; } 
    
    protected ColorUIResource getSecondary1() { return secondary1; }
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
    
    public FontUIResource getWindowTitleFont() { return windowTitleFont;}
    public FontUIResource getMenuTextFont() { return controlFont;}
    public FontUIResource getControlTextFont() { return controlFont;}
	
}