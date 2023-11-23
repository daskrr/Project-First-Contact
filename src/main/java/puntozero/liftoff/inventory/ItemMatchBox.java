package puntozero.liftoff.inventory;

import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public class ItemMatchBox extends InventoryItem
{
    public ItemMatchBox() {
        super("matchBox", AssetManager.get("item_matchBox", SpriteAsset.class));
    }
}
