package mobile;

import java.awt.Rectangle;

/**
 * Handles collisions  
 * @author Daniel Vermilya
 */
public interface Collidable {
    /**
     * Returns this object's bounding rectangle.
     * @return the collision bounds
     */
     Rectangle getBounds();
    /**
     * Responds to a collision with another object
     * @param other the object collided with
     */
    void onCollide(Collidable other);
}
