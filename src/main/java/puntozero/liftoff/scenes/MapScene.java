package puntozero.liftoff.scenes;

import puntozero.liftoff.prefabs.Player;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;

public class MapScene extends Scene
{
    public MapScene() {
        super();

        GameObjectSupplier[] suppliers = new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[]{
                new Camera(.7f)
            }),
            () -> new Player(new Vector2(0,0)),
        };

        this.setGameObjects(suppliers);
    }
}
