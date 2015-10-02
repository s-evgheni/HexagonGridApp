
package org.main.applicationLauncher;
import org.application.gui.ApplicationWindow;


/**
 * Main class responsible for application launch
 * @author E.S
 */
public class HexagonGridApp {
    
    public static void main(String[] args) {
        
        //Create main application window with a 4X5 hexagon grid
        ApplicationWindow window = new ApplicationWindow("Main Application Window", 4, 5);
        window.draw();
    }
}