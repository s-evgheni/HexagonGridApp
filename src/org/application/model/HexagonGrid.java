
package org.application.model;


import java.awt.Canvas;
import java.awt.event.*;
import org.application.util.DescriptionGenerator;

/**
 * This class is responsible for creation of the hexagon grid with the size of nXm.
 * Where n represents number of rows and m represents number of columns in a 2D grid
 * @author E.S
 */
public class HexagonGrid {
    
    private int gridRows; //number of rows in this grid
    private int gridColumns; //number of columns in this grid
    private int xCoordinateOfTheGridTopLeftCorner; // x coordinate of the grid's top left corner 
    private int yCoordinateOfTheGridTopLeftCorner; // y coordinate of the grid's top left corner
    private GridElement gridElements[][]; //matrix which will contain all elements of this grid
    //lenghts of circle radiuses for single hexagon
    private double circumscribedCircleRadius;
    private double inscribedCircleRadius;
    private Canvas canvas;// canvas on which elements of this grid will be draw
    
    /**
     * 
     * @param canvas - reference to canvas on which elements of this grid will be draw
     * @param gridRows - number of rows for this grid
     * @param gridColumns - number of columns for this grid
     * @param xCoordinateOfTheGridTopLeftCorner - x coordinate of the grid's top left corner 
     * @param yCoordinateOfTheGridTopLeftCorner - y coordinate of the grid's top left corner
     * @param lenghtOfTheHexagonEdge - length of the single hexagon edge
     */
    public HexagonGrid(Canvas canvas, int gridRows, int gridColumns, int xCoordinateOfTheGridTopLeftCorner, int yCoordinateOfTheGridTopLeftCorner, double lenghtOfTheHexagonEdge) {
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;
        //singe lenght of the regular hexagon edge is equal to the radius of the circle circumscribed around it
        this.circumscribedCircleRadius = lenghtOfTheHexagonEdge;
        this.canvas = canvas;
        this.xCoordinateOfTheGridTopLeftCorner = xCoordinateOfTheGridTopLeftCorner;
        this.yCoordinateOfTheGridTopLeftCorner = yCoordinateOfTheGridTopLeftCorner;
        //in regular hexagon radius of the inscribed circle is equal to the (V3/2) X radius of the circumscribed circle
        this.inscribedCircleRadius = (Math.sqrt(3)/2)*circumscribedCircleRadius;
        
    }
    
    /**
     * This method populate given grid with elements.
     * @return matrix which will contain all hexagons and their position within given grid
     */
    public GridElement[][] generateGridElements()
    {
        if(gridRows>=1 && gridColumns>=1)
        {
            gridElements = new GridElement[gridColumns][gridRows];
            
            DescriptionGenerator generator = new DescriptionGenerator();
            double x,y;
            Hexagon hexagon;
            for(int i = 0; i<gridColumns; i++)
            {
                for(int j=0; j<gridRows; j++)
                {
                    /*Below formulas can be derived from the following facts:
                     * 1. Center coordinates of the first hexagon in the grid can be calculated as:
                     *     X0 = xCoordinateOfTheGridTopLeftCorner+R where R- circumscribed Circle Radius of this hexagon
                     *     Y0 = yCoordinateOfTheGridTopLeftCorner+r where r - inscribedCircleRadius of this hexagon
                     * 2. To move from center of one hexagon to another along X axis:
                     *     Xchange = X0+(R+0.5R)*column#
                     * 3. To move from center of one hexagon to another along Y axis:
                     *     Ychange (for odd rows)  =  Y0+2r*row#
                     *     Ychange (for even rows) = Y0+3r*row#
                     */
                    x = (xCoordinateOfTheGridTopLeftCorner+circumscribedCircleRadius)+(circumscribedCircleRadius+circumscribedCircleRadius*0.5)*i;
                    if(i==0 || i%2==0)
                        y = (yCoordinateOfTheGridTopLeftCorner+inscribedCircleRadius) + (2*inscribedCircleRadius*j);
                    else
                        y = (yCoordinateOfTheGridTopLeftCorner+2*inscribedCircleRadius) + (2*inscribedCircleRadius*j);
          
                    hexagon = new Hexagon(canvas, x, y, circumscribedCircleRadius, generator.pickRandomItem(), this);
                    gridElements[i][j] = new GridElement(hexagon, new GridPosition(j, i));
                  }
              }
            }
            //at this point we have center coordinates for each hexagon in our grid.
            // we need to calculate 6 other points for each corner of the hexagon within this grid
            gridElements = calculatePolygonCornerPoints(gridElements);
            gridElements = addListenersToGridElements(gridElements);
            return gridElements;
    }
    
    /*
     * This method calculates x&y coordinates for each hexagon vertex in a given grid
     * by using center coordinates of each hexagon and lenght of the circumscribed circle radius
     */
    private GridElement[][] calculatePolygonCornerPoints(GridElement[][] gridElements) {
        
        if(gridElements.length!=0)
        {
            
            for(int i = 0; i<gridColumns; i++)
            {
                for(int j=0; j<gridRows; j++)
                {
                    Hexagon hexagon = (Hexagon)gridElements[i][j].getElement();
                    int x,y;
                    //calculate corner points for current hexagon
                    for(int p=0; p<6; p++) {
                        x=(int)Math.round(hexagon.getX0()+ hexagon.getRadius()*Math.cos(p*2*Math.PI/6));
                        y=(int)Math.round(hexagon.getY0()+hexagon.getRadius()*Math.sin(p*2*Math.PI/6));
                        hexagon.addPoint(x,y);
                   }
                }
            }
        }
        return gridElements;
    }
    
    /*
     * This method adds key & mouse listeners to each grid element
     */
    private GridElement[][] addListenersToGridElements(GridElement[][] gridElements) {
        if(gridElements.length!=0)
        {
            
            for(int i = 0; i<gridColumns; i++)
            {
                for(int j=0; j<gridRows; j++)
                {
                      Hexagon hexagon = (Hexagon)gridElements[i][j].getElement();
                      // add mouse & key listeners to hexagon
                      hexagon.addMouseListener(new MouseAdapter() { });
                      hexagon.addKeyListener(new KeyAdapter() { });
                }
            }
        }
        return gridElements;
    }

    public int getGridColumns() {
        return gridColumns;
    }

    public GridElement[][] getGridElements() {
        return gridElements;
    }

    public int getGridRows() {
        return gridRows;
    }
    

}
