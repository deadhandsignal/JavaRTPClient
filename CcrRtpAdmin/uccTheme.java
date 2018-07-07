import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import java.awt.Font;

public class uccTheme extends DefaultMetalTheme
{
	
		// white and off hite shades
	public final ColorUIResource primary1 		= new ColorUIResource(217, 217, 170); 
    public final ColorUIResource primary2 		= new ColorUIResource(255, 255, 240);
    public final ColorUIResource primary3 		= new ColorUIResource(240, 240, 224);
    
    public final ColorUIResource secondary1 	= new ColorUIResource(111, 111, 111);
    public final ColorUIResource secondary2 	= new ColorUIResource(159, 159, 159);
    public final ColorUIResource secondary3 	= new ColorUIResource(221, 221, 204);
    	// different fonts
    public final FontUIResource windowTitleFont = new FontUIResource("Verdana", Font.BOLD, 15);
    public final FontUIResource controlFont 	 = new FontUIResource("Verdana", Font.BOLD, 12);

		// the functions overridden from the base class => DefaultMetalTheme

    public ColorUIResource getPrimary1() { return primary1; }  
    public ColorUIResource getPrimary2() { return primary2; } 
    public ColorUIResource getPrimary3() { return primary3; } 
    
    public ColorUIResource getSecondary1() { return secondary1; }
    public ColorUIResource getSecondary2() { return secondary2; }
    public ColorUIResource getSecondary3() { return secondary3; }
    
    public FontUIResource getWindowTitleFont() { return windowTitleFont;}
    public FontUIResource getMenuTextFont() { return controlFont;}
    public FontUIResource getControlTextFont() { return controlFont;}
	
}