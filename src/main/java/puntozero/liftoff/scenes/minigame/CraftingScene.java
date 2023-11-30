package puntozero.liftoff.scenes.minigame;

import puntozero.liftoff.components.TableBook;
import puntozero.liftoff.prefabs.*;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.Color;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;
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
            () -> new GameObject("background", new Component[] {
                new SpriteRenderer(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = new Color(30,32,36);
                    setOrderInLayer(0);
                }}
            }) {{
                transform = new Transform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(6f, 5f)
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
                        new Vector2(0, -310f),
                        new Vector3(0,0,0),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f),
                        Anchor.CENTER
                    );
                }},
            }),
            () -> new GameObject("workbench", new Component[] {
                    new SpriteRenderer(AssetManager.get("workbench", SpriteAsset.class))
            }) {{
                transform = new Transform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(0.75f, 0.75f)
                );
            }},
            () -> new GameObject("pot", new Component[] {
                    new SpriteRenderer(AssetManager.get("pot", SpriteAsset.class))
            }),
            () -> new GameObject("potions", new Component[] { }, createPotions()),
            () -> new GameObject("craftingBook", new Component[] {
                    new SpriteRenderer(new TableBook().book)
            }) {{
               transform = new Transform(
                   new Vector2(-3.5f, 2f),
                   new Vector2(0.7f, 0.7f)
               );
            }},
            () -> new Napkin(new Vector2(3.3f, -2f), new Vector2(0.7f,0.7f)),
            () -> new NapkinSlot(new Vector2(0f,0f)),
            () -> new Matches(new Vector2(3.3f, -0.6f), new Vector2(0.4f, 0.4f)),
        });
    }

    private GameObject[] createPotions() {
        List<GameObject> potions = new ArrayList<>() {{
            add(new Potion(Potion.Type.BLUE, new Vector2(0, -2.3f), new Vector2(0.4f,0.4f)));
            add(new Potion(Potion.Type.GREEN, new Vector2(0, -2.3f), new Vector2(0.4f,0.4f)));
            add(new Potion(Potion.Type.RED, new Vector2(0, -2.3f), new Vector2(0.4f,0.4f)));
        }};
        Collections.shuffle(potions);

        float offset = -7 * Potion.SIZE / 2;

        int index = 0;
        for (GameObject potion : potions)
            potion.transform.position.x = offset + Potion.SIZE * index++;

        for (int i = 0; i < 3; i++){
            potions.add(new PotionSlot(Potion.Type.values()[i], new Vector2(0f, 0f)));
        }

        return potions.toArray(new GameObject[0]);
    }
}
