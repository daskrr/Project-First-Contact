package puntozero.liftoff.scenes;

import puntozero.liftoff.prefabs.Exit;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public class KitchenScene extends Scene
{
    public KitchenScene() {
        super();

        GameObjectSupplier[] suppliers = new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[]{
                new Camera(8f)
            }),

            () -> new GameObject("background", new Component[] {
                new SpriteRenderer(AssetManager.get("kitchenBackground", SpriteAsset.class))
            }),

            () -> new Exit(new Vector2(13f, 0f))
        };

        this.setGameObjects(suppliers);
    }
}