
package org.main.applicationLauncher;
import org.application.gui.ApplicationWindow;


/**
 * Main class responsible for application launch
 * @author E.S
 */
public class HexagonGridApp {
    
    public static void main(String[] args) {
        int width  = args[0].length() > 0 ? Integer.parseInt(args[0]) : 4;
        int length = args[1].length() > 0 ? Integer.parseInt(args[1]) : 5;
        
        //Create main application window with a 4X5 hexagon grid
        ApplicationWindow window = new ApplicationWindow("Main Application Window", width, length);
        window.draw();
    }
}
