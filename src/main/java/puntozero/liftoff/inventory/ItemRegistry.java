package puntozero.liftoff.inventory;

import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public enum ItemRegistry
{
    MATCHES(new InventoryItem("matchBox", "Match Box", AssetManager.get("item_matchBox", SpriteAsset.class))),
    POT(new InventoryItem("pot", "Pot", AssetManager.get("item_pot", SpriteAsset.class))),
    BOOK_GREEN(new InventoryItem("bookGreen", "Book", AssetManager.get("book_item1", SpriteAsset.class))),
    BOOK_PURPLE(new InventoryItem("bookPurple", "Book", AssetManager.get("book_item2", SpriteAsset.class))),
    BOOK_PINK(new InventoryItem("bookPink", "Book", AssetManager.get("book_item3", SpriteAsset.class))),
    BOOK_BLUE(new InventoryItem("bookBlue", "Book", AssetManager.get("book_item4", SpriteAsset.class))),
    BOOK_ORANGE(new InventoryItem("bookOrange", "Book", AssetManager.get("book_item5", SpriteAsset.class))),
    BOOK_RED(new InventoryItem("bookRed", "Book", AssetManager.get("book_item6", SpriteAsset.class))),
    NAPKIN(new InventoryItem("napkin", "Napkin", AssetManager.get("item_napkin", SpriteAsset.class))),
    NOTE(new InventoryItem("note", "Note", AssetManager.get("item_note", SpriteAsset.class))),
    KEYS(new InventoryItem("keys", "Key Chain", AssetManager.get("item_keys", SpriteAsset.class))),
    POTION_BLUE(new InventoryItem("potionBlue", "Blue Potion", AssetManager.get("item_potion_blue", SpriteAsset.class))),
    POTION_RED(new InventoryItem("potionRed", "Red Potion", AssetManager.get("item_potion_red", SpriteAsset.class))),
    POTION_GREEN(new InventoryItem("potionGreen", "Green Potion", AssetManager.get("item_potion_green", SpriteAsset.class))),
    MOLOTOV(new InventoryItem("molotov", "Molotov", AssetManager.get("molotov", SpriteAsset.class)));


    public final InventoryItem item;
    ItemRegistry(InventoryItem item) {
        this.item = item;
    }
}
