package puntozero.liftoff.components;

import processing.event.MouseEvent;
import puntozero.liftoff.inventory.InventoryItem;
import puntozero.liftoff.inventory.ItemRegistry;
import puntozero.liftoff.prefabs.Monologue;
import puntozero.liftoff.prefabs.UIItem;
import pxp.engine.core.GameObject;
import pxp.engine.core.GameProcess;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Button;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.*;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPSingleEvent;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;
import pxp.logging.Debug;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory extends Component
{
    public static final List<InventoryItem> items = new ArrayList<>();

    public GameObject container;

    private static boolean show = false;

    public PlayerInventory(GameObject container) {
        this.container = container;
    }

    @Override
    public void update() {
        ctx()._nextFrame(this::updateUI); // this should happen at start but it doesnt work for some reason

        if (Input.getKeyOnce(Key.N)) {
            instantiate(readNote());
        }
    }

    public void updateUI() {
        container.removeGameObjects();
        int index = 0;
        for (InventoryItem item : items)
            instantiate(new UIItem(item, index++) {{
                isActive = show;
            }}, this.container);
    }

    public static void addItem(InventoryItem item) {
        items.add(item);
        Monologue monologue = new Monologue("Obtained 1x "+ item.humanName);
        GameProcess.getInstance().getCurrentScene().addGameObject(monologue);
        monologue.remove(1.5f);
    }
    public static void removeItem(String name) {
        // prevent concurrent
        for (InventoryItem item : new ArrayList<>(items))
            if (item.name.equals(name)) {
                items.remove(item);
                return;
            }
    }

    public static void removeBooks() {
        items.removeIf(item -> item.name.contains("book"));
    }

    public static InventoryItem getItem(String name) {
        for (InventoryItem item : items)
            if (item.name.equals(name))
                return item;

        return null;
    }
    public static boolean hasItem(String name) {
        for (InventoryItem item : items)
            if (item.name.equals(name))
                return true;

        return false;
    }

    public static void reset() {
        items.clear();
    }

    private static class InventoryContainer extends Component {
        @Override
        public void update() {
            if (Input.getKeyOnce(Key.I)) {
                PlayerInventory.show = !PlayerInventory.show;
            }
        }
    }

    private GameObject readNote(){
        return new GameObject("bigNoteCanvas", new Component[] {
                new Canvas(RenderMode.CAMERA)
        }, new GameObject[] {
                new GameObject("bigNote", new Component[] {
                        new Image(AssetManager.get("noteBig", SpriteAsset.class))
                }) {{
                    transform = new RectTransform(
                            new Vector2(),
                            new Vector2(700, 580)
                    );
                }},
                new GameObject("button", new Component[] {
                        new Button(InteractableTransition.COLOR) {{
                            onClick = new PXPSingleEvent<>() {
                                @Override
                                public void invoke(MouseEvent mouseEvent) {
                                    ctx().getCurrentScene().getGameObject("bigNoteCanvas").destroy();
                                }
                            };
                            color = Color.white();
                            normalColor = Color.white();
                            hoverColor = new Color(240,240,240,255);
                            pressedColor = new Color(230,230,230,255);
                        }}
                }, new GameObject[] {
                        new GameObject("buttonText", new Component[] {
                                new Text("Close", Pivot.CENTER) {{
                                    font = AssetManager.get("PressStart", FontAsset.class);
                                    fontSize = 20;
                                    color = Color.white();
                                }}
                        }) {{
                            transform = new RectTransform(
                                    new Vector2(0, 0),
                                    new Vector3(),
                                    new Vector2(1,1),
                                    new Vector2(-1f, -1f),
                                    Anchor.CENTER_LEFT
                            );
                        }}
                }) {{
                    transform = new RectTransform(
                            new Vector2(510, 225),
                            new Vector3(),
                            new Vector2(1,1),
                            new Vector2(300, 50)
                    );
                }}
        });
    }

    public static GameObject create() {
        GameObject container = new GameObject("inventoryContainer", new Component[] {
//            new Image(AssetManager.get("inventoryBackground", SpriteAsset.class)) {{
//                preserveAspect = true;
//            }}
            new InventoryContainer()
        }) {{
            transform = new RectTransform(
                new Vector2(-UIItem.SIZE / 2f,0),
                new Vector3(),
                new Vector2(1,1),
                new Vector2(UIItem.SIZE, 800),
                Anchor.BOTTOM_RIGHT
            );
        }};

        return new GameObject("playerInventory", new Component[] {
            new Canvas(RenderMode.CAMERA),
            new PlayerInventory(container),
        }, new GameObject[] {
            container
        });
    }
}
