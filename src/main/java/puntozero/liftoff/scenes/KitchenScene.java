package puntozero.liftoff.scenes;

import puntozero.liftoff.prefabs.Exit;
import puntozero.liftoff.prefabs.Interactable;
import puntozero.liftoff.prefabs.LevelPlayer;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;

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

            () -> new Exit(new Vector2(13f, 0f)),

            () -> new Interactable("doorLeft",
                new Vector2(),
                new Vector2(1.6f, 1.6f),
                new Image(AssetManager.get("doorLeft", SpriteAsset.class)),
                new PXPEvent() {
                    @Override
                    public void invoke() {
                        getGameObject("doorLeft").destroy();
                    }
                })
            {{
                transform = new Transform(new Vector2(-7.9f, 5.05f));
            }},
            () -> new Interactable("doorRight",
                new Vector2(),
                new Vector2(1.6f, 1.6f),
                new Image(AssetManager.get("doorRight", SpriteAsset.class)),
                new PXPEvent() {
                    @Override
                    public void invoke() {
                        getGameObject("doorRight").destroy();
                    }
                })
            {{
                transform = new Transform(new Vector2(-4.7f, 5.05f));
            }},

            () -> new LevelPlayer() {{
                transform = new Transform(new Vector2(0,5f));
            }},
        };

        this.setGameObjects(suppliers);
    }
}
