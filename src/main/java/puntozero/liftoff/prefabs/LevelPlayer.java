package puntozero.liftoff.prefabs;

import puntozero.liftoff.components.LevelPlayerController;
import pxp.engine.core.GameObject;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.assets.AssetManager;

public class LevelPlayer extends GameObject
{

    public LevelPlayer() {
        super("levelPlayer", new Component[] {
                new SpriteRenderer(AssetManager.getSpriteFromSheet("arrow", 60)),
                new LevelPlayerController(),
        });
    }
}
