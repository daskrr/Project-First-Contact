package puntozero.liftoff.inventory;

import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public enum ItemRegistry
{
    MATCHES(new InventoryItem("matchBox", AssetManager.get("item_matchBox", SpriteAsset.class))),
    POT(new InventoryItem("pot", AssetManager.get("item_pot", SpriteAsset.class))),
    BOOK_GREEN(new InventoryItem("bookGreen", AssetManager.get("book_item1", SpriteAsset.class))),
    BOOK_PURPLE(new InventoryItem("bookPurple", AssetManager.get("book_item2", SpriteAsset.class))),
    BOOK_PINK(new InventoryItem("bookPink", AssetManager.get("book_item3", SpriteAsset.class))),
    BOOK_BLUE(new InventoryItem("bookBlue", AssetManager.get("book_item4", SpriteAsset.class))),
    BOOK_ORANGE(new InventoryItem("bookOrange", AssetManager.get("book_item5", SpriteAsset.class))),
    BOOK_RED(new InventoryItem("bookRed", AssetManager.get("book_item6", SpriteAsset.class))),
    NAPKIN(new InventoryItem("napkin", AssetManager.get("item_napkin", SpriteAsset.class))),
    NOTE(new InventoryItem("note", AssetManager.get("item_note", SpriteAsset.class)));


    public final InventoryItem item;
    ItemRegistry(InventoryItem item) {
        this.item = item;
    }
}
