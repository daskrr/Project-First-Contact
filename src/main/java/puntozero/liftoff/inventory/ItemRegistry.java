package puntozero.liftoff.inventory;

import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public enum ItemRegistry
{
    MATCHES(new InventoryItem("matchBox", AssetManager.get("item_matchBox", SpriteAsset.class))),
    POT(new InventoryItem("pot", AssetManager.get("item_pot", SpriteAsset.class))),
    BOOK(new InventoryItem("book", AssetManager.get("book5", SpriteAsset.class)));

    public final InventoryItem item;
    ItemRegistry(InventoryItem item) {
        this.item = item;
    }
}
