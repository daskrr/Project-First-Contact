package puntozero.liftoff.components;

import puntozero.liftoff.inventory.InventoryItem;
import puntozero.liftoff.inventory.ItemRegistry;
import puntozero.liftoff.prefabs.Monologue;
import puntozero.liftoff.prefabs.UIItem;
import pxp.engine.core.GameObject;
import pxp.engine.core.GameProcess;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Input;
import pxp.engine.data.Key;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.Anchor;
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
