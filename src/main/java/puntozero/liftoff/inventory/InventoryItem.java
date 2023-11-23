package puntozero.liftoff.inventory;

import pxp.engine.data.assets.SpriteAsset;

public abstract class InventoryItem
{
    public String name;
    public SpriteAsset sprite;

    public InventoryItem(String name, SpriteAsset sprite) {
        this.name = name;
        this.sprite = sprite;
    }
}
