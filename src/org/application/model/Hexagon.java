
package org.application.model;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Vector;

/**This class is responsible for creation of the Hexagon
 * @author E.S
 */
public class Hexagon 
         extends Polygon
         implements MouseMotionListener, MouseListener, KeyListener {
    
    boolean isActive; // flag to indicate that this hexagon has received mouse pointer
    boolean selected; //flag to indicate that this hexagon has been selected
    private double x0; //x coordinate of the hexagon center
    private double y0; //y coordinate of the hexagon center
    private double radius; //length of the circumscribed circle radius for this hexagon
    private Vector mouseListeners; //list with mouse listeners for this hexagon
    private Vector  keyListeners; //list with key listeners for this hexagon
    private final Color fillColor; //holds fill color of the unselected inactive hexagon
    private final Color lineColor; //holds line color of the unselected inactive hexagon
    private final Color fillColorInside; //holds fill color of the unselected active hexagon
    private final Color lineColorInside; //holds line color of the unselected active hexagon
    private Color selectedFillColor; //holds line color for selected hexagon
    private String description; //hexagon description
    protected Component canvas;
    private   Graphics  cacheGraphics; 
    private HexagonGrid grid; //reference to the grid to which this hexagon belong

    /**
     *  Default constructor
     * @param myCanvas - canvas on which this hexagon will be drawn
     * @param x0 -  x coordinate of the circumscribed circle center for this hexagon 
     * @param y0  - y coordinate of the circumscribed circle center for this hexagon 
     * @param edgeLenght - length of the edge for this hexagon
     * @param description - description of the hexagon
     * @param gridReference -reference to the grid to which this hexagon belongs
     */
   public Hexagon(Component myCanvas, double x0, double y0, double edgeLenght, String description, HexagonGrid gridReference) 
   {
        super();
        this.isActive = false;
        this.selected=false;
        
        this.mouseListeners = new Vector();
        this.keyListeners = new Vector();
        
        this.fillColor = Color.YELLOW;
        this.lineColor = Color.BLACK;
        this.fillColorInside = Color.BLUE;
        this.lineColorInside = Color.RED;
        this.selectedFillColor = Color.GRAY;
        
        canvas = myCanvas;
        cacheGraphics = null;
        
        this.x0 = x0;
        this.y0 = y0;
        //since hexagon side lenght = radius of the circumscribed circle for this hexagon
        this.radius = edgeLenght;
        this.description = description;
        
        this.grid = gridReference;
        
        //add listeners to canvas
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addKeyListener(this);
        canvas.setFocusable(true);
    }
  

    /**
     * Adds mouse listeners to this hexagon
     * @param mouseListener - listener to add
     */
    public void addMouseListener(MouseListener mouseListener) {
        mouseListeners.addElement(mouseListener); 
    }

    /**
     * Adds specific key listener to this hexagon
     * @param keyListener - key listener to add
     */
    public void addKeyListener(KeyListener keyListener) {
        keyListeners.addElement(keyListener);
    }

   //----------MOUSE EVENTS IMPLEMENTATIONS
   @Override
   public void mouseReleased(MouseEvent event) {
    if (isActive) {
        notifyMouseListeners(MouseEvent.MOUSE_RELEASED, event);
        }
    }
    
    @Override
   public void mouseMoved(MouseEvent e) { 
        boolean prev = isActive;
        processPoint(e.getPoint()); 
        if (!isActive && prev) 
            notifyMouseListeners(MouseEvent.MOUSE_EXITED, e);
        if (isActive && !prev) 
            notifyMouseListeners(MouseEvent.MOUSE_ENTERED, e);
    }
    
    @Override
    public void keyTyped(KeyEvent event) {
        notifyKeyListeners(KeyEvent.KEY_TYPED, event);
        processKey(event.getKeyChar());
    }
   
   public void paint(Graphics g) {
   if (g != null) {
       synchronized(g) {
            //determine fill & line color
            Color fill, line; 
            if(selected)
            {
                fill = selectedFillColor;
                line = lineColor;
            }
            else
            {
                fill = ((isActive)?(fillColorInside):(fillColor));
                line = ((isActive)?(lineColorInside):(lineColor));
            }
            
            //redraw hexagon
            if (fill != null) {
                g.setColor(fill);
                g.fillPolygon(this);
            }
            if (line != null) {
                g.setColor(line);
                g.drawPolygon(this);
            }
            //add text to hexagon
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Font font = new Font("Serif", Font.BOLD, 24);
            g2d.setColor(Color.RED);
            g2d.setFont(font);
            g2d.drawString(this.description.toUpperCase(), (float)x0-font.getSize2D()/4, (float)y0);
       }
   }
   }
   
   private void processKey(char keyChar) {
        if(description.charAt(0) == keyChar)
        {
            //redraw and change selected state of this hexagon upon key press
            if(!selected)
            {
                selected=true;
                paint(getGraphics());
            }
            //check if this is the last selected item & terminate program if true
            if(thisIsTheLastSelectedItemInAGrid(this.grid))
            {
                System.out.println("Hexagon " + description.toUpperCase()+" is the last selected hexagon in a grid");
                System.exit(0);
            }
        }
    }

   private void processPoint(Point pt) {
        if (npoints < 3) {
            isActive = false;
            return;
        }
        //redraw and change active state of this hexagon upon mouse entry
        if (contains(pt)) {
            if (!isActive) {
                isActive = true;
                paint(getGraphics());
            }
        }
        //redraw and change active state of this hexagon upon mouse exit
        else {
            if (isActive) {
                isActive = false;
                paint(getGraphics());
            }
        }
   }
   
   //-----------------------PRIVATE METHODS
   
   /*
    * This method returns graphics object from provided canvas
    */
    private Graphics getGraphics() {
        if (cacheGraphics == null) {
            if (canvas != null) {
                cacheGraphics = canvas.getGraphics();
            }
        }
        return cacheGraphics;
     }
    
     /*
      * This method dispatches appropriate action for each mouse event type
     */
     private void notifyMouseListeners(int id, MouseEvent evt) {
        MouseListener m;
        for(Enumeration e = mouseListeners.elements(); e.hasMoreElements(); ) {
            m = (MouseListener)(e.nextElement());
            
            switch (id) {
                
            case MouseEvent.MOUSE_RELEASED: 
                m.mouseReleased(evt);
                if(isActive&!selected){
                    System.out.println("Hexagon " + description.toUpperCase() + " selected via mouse click");
                    selected=true;
                }
                if(thisIsTheLastSelectedItemInAGrid(this.grid))
                {
                    System.out.println("Hexagon " + description.toUpperCase()+" is the last selected hexagon in a grid");
                    System.exit(0);
                }
                break;
                
                //additional events can be hooked to this hexagon if desired
                 case MouseEvent.MOUSE_PRESSED: m.mousePressed(evt);
                break;
                case MouseEvent.MOUSE_ENTERED: m.mouseEntered(evt);
                break;
                case MouseEvent.MOUSE_EXITED: m.mouseExited(evt);
                break;
                case MouseEvent.MOUSE_CLICKED: m.mouseClicked(evt); 
                break;
                
            default:
            break;
            }
        }
    }
     
    /*
      * This method dispatches appropriate action for each key event type
     */
   private void notifyKeyListeners(int id, KeyEvent evt) {
       KeyListener k;
       for(Enumeration e = keyListeners.elements(); e.hasMoreElements(); ) 
       {
           k = (KeyListener)(e.nextElement());
           switch (id) {
               
                case KeyEvent.KEY_TYPED: k.keyTyped(evt);
                    if(description.charAt(0) == evt.getKeyChar() & !selected)
                         System.out.println("Hexagon " + description.toUpperCase() +" has been selected via keyboard");
                break;
                    
                //additional events can be hooked to this hexagon if desired
                case  KeyEvent.KEY_PRESSED: k.keyPressed(evt);
                break;
                case KeyEvent.KEY_RELEASED: k.keyReleased(evt);
                break;
                default: break;
            }
        }
   }
   /*
    * This method is called after each mouse or key event to check
    * if there is no more items to check in a grid which belong to this hexagon
    */
    private boolean thisIsTheLastSelectedItemInAGrid(HexagonGrid grid) {
        if(grid!=null)
        {
            GridElement gridElements[][] = grid.getGridElements();
            int selectedItems = 0;
            for(int i = 0; i<grid.getGridColumns(); i++)
            {
                for(int j=0; j<grid.getGridRows(); j++)
                {
                    Hexagon hexagon = (Hexagon)gridElements[i][j].getElement();
                    if(hexagon.selected)
                        selectedItems++;
                }
            }
            if(selectedItems==grid.getGridColumns()*grid.getGridRows())
                return true;
        }
        return false;
    }
   
    //----------------------GETTERS & SETTERS
    public double getX0() { return x0; }
    public void setX0(double x0) { this.x0 = x0; }
    public double getY0() { return y0; }
    public void setY0(double y0) { this.y0 = y0; }
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    //--------------------Additional mouse events which can be hooked to this hexagon
     @Override
   public void mouseClicked(MouseEvent event) { 
//    if (isActive)
//        notifyMouseListeners(MouseEvent.MOUSE_CLICKED, event);
    }
   
    @Override
   public void mousePressed(MouseEvent event) {
//    if (isActive)
//        notifyMouseListeners(MouseEvent.MOUSE_PRESSED, event);
    }
    
    @Override
    public void mouseEntered(MouseEvent event) { 
//        boolean prev = isActive;
//        processPoint(event.getPoint());
//        if (isActive && !prev) 
//            notifyMouseListeners(MouseEvent.MOUSE_ENTERED, event);
    }
    
    @Override
    public void mouseExited(MouseEvent e) { 
//        boolean prev = isActive;
//        processPoint(e.getPoint()); 
//        if (!isActive && prev) 
//                notifyMouseListeners(MouseEvent.MOUSE_EXITED, e);
   }
    
    @Override
   public void mouseDragged(MouseEvent e) { 
//   boolean prev = isActive;
//   processPoint(e.getPoint()); 
//   if (!isActive && prev) 
//       notifyMouseListeners(MouseEvent.MOUSE_EXITED, e);
//   if (isActive && !prev) 
//       notifyMouseListeners(MouseEvent.MOUSE_ENTERED, e);
    }
    
     //--------------------Additional keybouard events which can be hooked to this hexagon
    @Override
    public void keyPressed(KeyEvent event) { }
    @Override
    public void keyReleased(KeyEvent event) { }

}
