package immobile;

import javax.swing.text.Position;

public interface Collectible {
    Position pos = null;

    default Position getPosition(){
        return pos;
    }

}
