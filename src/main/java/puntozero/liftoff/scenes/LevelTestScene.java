package puntozero.liftoff.scenes;

import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.data.GameObjectSupplier;

public class LevelTestScene extends Scene
{
    public LevelTestScene() {
        super();

        GameObjectSupplier[] suppliers = new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[]{
                new Camera(.7f)
            }),
            // put the level player here :)
        };

        this.setGameObjects(suppliers);
    }
}
