
package org.application.gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import javax.swing.JFrame;
import org.application.model.GridElement;
import org.application.model.Hexagon;
import org.application.model.HexagonGrid;

/**
 * This class is responsible for main application window 
 * @author Evgheni S.
 */
public class ApplicationWindow extends JFrame {
    
    private Canvas canvas;
    private GridElement gridElements[][];
    private int gridRows, gridColumns;

    /**
     * Creates main application window with a specified size of the hexagon grid
     * @param title - window title
     * @param gridRows - number of grid rows
     * @param gridColumns - number of grid columns
     * @throws HeadlessException 
     */
    public ApplicationWindow(String title, int gridRows, int gridColumns) throws HeadlessException {
        super(title);
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;
    }
    
    
    public void draw() {
        
        canvas = new Canvas() 
        {
            //this portion is called after JFrame appear on the screen. At this point code responsible for all hexagons below has been executed
             @Override
              public void paint(Graphics g) {
                super.paint(g);
                for(int i = 0; i<gridColumns; i++)
                {
                    for(int j=0; j<gridRows; j++)
                    {
                        Hexagon hexagon = (Hexagon)gridElements[i][j].getElement();
                        hexagon.paint(g);
                    }
                }
            }
        };
             
       canvas.setBackground(Color.WHITE);
       canvas.setSize(new Dimension(350,320));
       
       HexagonGrid grid = new HexagonGrid(canvas, gridRows, gridColumns, 0, 0, 40);
       gridElements = grid.generateGridElements();
       
       this.add(canvas);
       this.pack();
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
       this.setVisible(true);
    }
}
