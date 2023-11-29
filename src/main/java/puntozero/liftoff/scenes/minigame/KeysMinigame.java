package puntozero.liftoff.scenes.minigame;

import puntozero.liftoff.prefabs.UIKey;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Color;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.RenderMode;

public class KeysMinigame extends Scene
{
    public KeysMinigame() {
        super();

        this.setGameObjects(new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[] {
                new Camera(7f)
            }),
            () -> new GameObject("background", new Component[] {
                new SpriteRenderer(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = new Color(30, 32, 36);
                    setOrderInLayer(0);
                }}
            }) {{
                transform = new Transform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(7, 4.5f)
                );
            }},
            () -> new GameObject("canvas", new Component[] {
                new Canvas(RenderMode.CAMERA)
            }, new GameObject[] {
                new GameObject("keys", new Component[] { }, new GameObject[] {
                    new UIKey(
                        AssetManager.get("keys/green", SpriteAsset.class),
                        AssetManager.get("keys/A", SpriteAsset.class),
                        new Vector2(-100,-100),
                        true
                    ),
                    new UIKey(
                        AssetManager.get("keys/orange", SpriteAsset.class),
                        AssetManager.get("keys/B", SpriteAsset.class),
                        new Vector2(-100,0),
                        false
                    ),
                    new UIKey(
                        AssetManager.get("keys/purple", SpriteAsset.class),
                        AssetManager.get("keys/C", SpriteAsset.class),
                        new Vector2(-100,100),
                        false
                    )
                }),
                new GameObject("keyHole", new Component[] {
                    new Image(AssetManager.get("keys/hole", SpriteAsset.class)) {{
                        preserveAspect = true;
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(200, 0),
                        new Vector3(),
                        new Vector2(1,1),
                        new Vector2(300, 100)
                    );
                }}
            })
        });
    }
}
