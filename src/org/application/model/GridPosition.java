
package org.application.model;

/**
 * This class is responsible for storing element relative position within given 2D grid
 * @author E.S
 */
public class GridPosition {
    
    private int rowNumber;
    private int columnNumber;

    public GridPosition() {
    }

    /**
     * @param rowNumber - row number of an element within grid
     * @param columnNumner  - column number of an element within grid
     */
    
    public GridPosition(int rowNumber, int columnNumner) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumner;
    }

    //----------------------GETTERS & SETTERS
    public int getColumnNumner() { return columnNumber; }
    public void setColumnNumner(int columnNumner) { this.columnNumber = columnNumner; }
    public int getRowNumber() { return rowNumber; }
    public void setRowNumber(int rowNumber) { this.rowNumber = rowNumber; }
    
}
