package puntozero.liftoff.prefabs;

import puntozero.liftoff.components.MapPlayerController;
import pxp.engine.core.GameObject;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public class Player extends GameObject
{
    public Player(Vector2 position) {
        super("player", new Component[] {
            new SpriteRenderer(AssetManager.get("testImage", SpriteAsset.class)),
            new MapPlayerController(),
            // ...
        });

        this.transform.position = position;
    }
}
