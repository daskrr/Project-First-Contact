package puntozero.liftoff.components;

import puntozero.liftoff.inventory.InventoryItem;
import puntozero.liftoff.prefabs.UIItem;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.RenderMode;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory extends Component
{
    public static final List<InventoryItem> items = new ArrayList<>();

    public GameObject container;

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
            instantiate(new UIItem(item, index++), this.container);
    }

    public static void addItem(InventoryItem item) {
        items.add(item);
    }
    public static void removeItem(String name) {
        // prevent concurrent
        for (InventoryItem item : new ArrayList<>(items))
            if (item.name.equals(name)) {
                items.remove(item);
                return;
            }
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

    public static GameObject create() {
        GameObject container = new GameObject("inventoryContainer") {{
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
