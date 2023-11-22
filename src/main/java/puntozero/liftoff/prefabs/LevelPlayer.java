package puntozero.liftoff.prefabs;

import puntozero.liftoff.components.LevelPlayerController;
import pxp.engine.core.GameObject;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;

public class LevelPlayer extends GameObject
{

    public LevelPlayer() {
        super("levelPlayer", new Component[] {
            new SpriteRenderer(AssetManager.getSpriteFromSheet("levelPlayer", 0)),
            new LevelPlayerController(),
            new BoxCollider(new Vector2(), new Vector2(1f,1f)) {{
                trigger = true;
            }}
        });
    }
}
