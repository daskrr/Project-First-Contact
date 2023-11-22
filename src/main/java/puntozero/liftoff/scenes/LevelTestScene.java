package puntozero.liftoff.scenes;

import puntozero.liftoff.components.LevelPlayerController;
import puntozero.liftoff.prefabs.LevelPlayer;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Animation;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.*;
import pxp.engine.data.Color;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.InteractableTransition;

public class LevelTestScene extends Scene
{
    public LevelTestScene() {
        super();

        AssetManager.createSprite("kitchenBackground", "data/MINI_map.png", 16);
        AssetManager.createSpriteSheet("arrow", "data/IconsFlat-16.png", 16, 10, 10);

        GameObjectSupplier[] suppliers = new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[]{
                new Camera(7f) {{
                    //setFollowing("levelPlayer");
                }}
            }),
                () -> new GameObject("background", new Component[]{
                        new SpriteRenderer(AssetManager.get("kitchenBackground", SpriteAsset.class)),
                }),
            () -> new LevelPlayer() {{
               transform = new Transform(new Vector2(0,0));
            }},

        };

        this.setGameObjects(suppliers);
    }
}
