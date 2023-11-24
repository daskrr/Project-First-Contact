package puntozero.liftoff.inventory;

import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public enum ItemRegistry
{
    MATCHES(new InventoryItem("matchBox", AssetManager.get("item_matchBox", SpriteAsset.class)));

    public final InventoryItem item;
    ItemRegistry(InventoryItem item) {
        this.item = item;
    }
}
