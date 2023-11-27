package puntozero.liftoff.scenes.minigame;

import puntozero.liftoff.prefabs.Pot;
import puntozero.liftoff.prefabs.PotSlot;
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

public class PotsScene extends Scene
{
    public PotsScene() {
        super();

        this.setGameObjects(new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[] {
                new Camera(7f)
            }),
            () -> new GameObject("pots", new Component[] { }, createPots()),
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
//                transform = new RectTransform(
//                    new Vector2(0,0),
//                    new Vector3(0,0,0),
//                    new Vector2(1,1),
//                    new Vector2(800, 600),
//                    Anchor.CENTER
//                );
            }},
            // text outside of background, because it makes it transparent AAAH
            () -> new GameObject("canvas", new Component[] {
                new Canvas(RenderMode.CAMERA)
            }, new GameObject[] {
                new GameObject("title", new Component[] {
                    new Text("Order the pots from largest to smallest") {{
                        color = Color.white();
                        fontSize = 17;
                        font = AssetManager.get("PressStart", FontAsset.class);
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(0,-220f),
                        new Vector3(0,0,0),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f),
                        Anchor.CENTER
                    );
                }}
            })
        });
    }

    /**
     * Creates pots and the blank spots to place them
     * @return the pots and slots
     */
    private GameObject[] createPots() {
        // shuffle the pots
        List<GameObject> pots = new ArrayList<>() {{
            add(new Pot(Pot.Size.BIG, new Vector2(0, -1f)));
            add(new Pot(Pot.Size.MEDIUM, new Vector2(0, -1f)));
            add(new Pot(Pot.Size.SMALL, new Vector2(0, -1f)));
            add(new Pot(Pot.Size.EXTRA_SMALL, new Vector2(0, -1f)));
        }};
        Collections.shuffle(pots);

        float offset = -3 * Pot.SIZE / 2; // for some reason 3 centers them??

        // correct positions
        int index = 0;
        for (GameObject pot : pots)
            pot.transform.position.x = offset + Pot.SIZE * index++;

        for (int i = 0; i < 4; i++)
            pots.add(new PotSlot(Pot.Size.values()[i], new Vector2(offset + Pot.SIZE * i, 1.5f)));

        return pots.toArray(new GameObject[0]);
    }
}
