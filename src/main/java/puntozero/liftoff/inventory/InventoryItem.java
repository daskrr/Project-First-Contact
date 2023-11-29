package puntozero.liftoff.inventory;

import pxp.engine.data.assets.SpriteAsset;

public class InventoryItem
{
    public String name;
    public String humanName;
    public SpriteAsset sprite;

    public InventoryItem(String name, String humanName, SpriteAsset sprite) {
        this.name = name;
        this.humanName = humanName;
        this.sprite = sprite;
    }
}
