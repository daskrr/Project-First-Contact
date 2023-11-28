package puntozero.liftoff.scenes.minigame;

import puntozero.liftoff.prefabs.Book;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.Color;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.RenderMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CraftingScene extends Scene {
    public CraftingScene(){
        super();

        this.setGameObjects(new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[] {
                    new Camera(7f)
            }),
            () -> new GameObject("craftingTable", new Component[] { }, createCraftingTable()),
            () -> new GameObject("background", new Component[] {
                new SpriteRenderer(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = new Color(30,32,36);
                    setOrderInLayer(0);
                }}
            }) {{
                transform = new Transform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(5.8f, 5.8f)
                );
            }},
            () -> new GameObject("canvas", new Component[] {
                new Canvas(RenderMode.CAMERA)
            }, new GameObject[] {
                new GameObject("title", new Component[]{
                    new Text("Mix in the right order") {{
                        color = Color.white();
                        fontSize = 17;
                        font = AssetManager.get("PressStart", FontAsset.class);
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(0, -240f),
                        new Vector3(0,0,0),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f),
                        Anchor.CENTER
                    );
                }}
            })
        });
    }

    private GameObject[] createCraftingTable() {
        List<GameObject> craftables = new ArrayList<>() {{

        }};

        return craftables.toArray(new GameObject[0]);
    }
}
