
package org.application.model;

import java.awt.Polygon;

/**
 * This class is responsible for storing a single grid element
 * Single grid element contain a polygon and knows it's relative position within specific grid
 * @author E.S
 */
public class GridElement {
    private Polygon element;
    private GridPosition position;

    public GridElement(Polygon element, GridPosition position) {
        this.element = element;
        this.position = position;
    }

    public Polygon getElement() { return element; }
    public void setElement(Polygon element) { this.element = element; }
    public GridPosition getPosition() { return position; }
    public void setPosition(GridPosition position) { this.position = position; }
}
