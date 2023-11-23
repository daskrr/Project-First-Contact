package puntozero.liftoff.components;

import puntozero.liftoff.inventory.InventoryItem;
import puntozero.liftoff.prefabs.Item;
import pxp.engine.core.GameObject;
import pxp.engine.core.component.Component;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventory extends Component
{
    private static PlayerInventory instance = null;
    public static PlayerInventory getInstance() {
        return instance;
    }

    public PlayerInventory() {
        if (instance == null)
            instance = this;
    }

    public GameObject container;
    public final List<InventoryItem> items = new ArrayList<>();

    public void updateUI() {
        container.removeGameObjects();
        int index = 0;
        for (InventoryItem item : this.items)
            instantiate(new Item(item, index));
    }

    public void addItem(InventoryItem item) {
        this.items.add(item);
        this.updateUI();
    }
    public void removeItem(String name) {
        // prevent concurrent
        for (InventoryItem item : new ArrayList<>(this.items))
            if (item.name.equals(name)) {
                this.items.remove(item);
                this.updateUI();
                return;
            }
    }

    public InventoryItem getItem(String name) {
        for (InventoryItem item : this.items)
            if (item.name.equals(name))
                return item;

        return null;
    }
    public boolean hasItem(String name) {
        for (InventoryItem item : this.items)
            if (item.name.equals(name))
                return true;

        return false;
    }
}
