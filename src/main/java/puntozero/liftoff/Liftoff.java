package puntozero.liftoff;

import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.scenes.*;
import puntozero.liftoff.scenes.minigame.BooksScene;
import puntozero.liftoff.scenes.minigame.KeysMinigame;
import puntozero.liftoff.scenes.minigame.PotsScene;
import pxp.engine.core.Game;
import pxp.engine.core.Scene;
import pxp.engine.data.Color;
import pxp.engine.data.GameSettings;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.util.Pair;

import java.util.ArrayList;

public class Liftoff extends Game
{
    @Override
    public GameSettings startup() {
        // adding assets to the game
        // test
        AssetManager.createSprite("test", "image.png", 16);
        // menu
        AssetManager.createSprite("mainMenu", "mainMenu.png", 16);

        // map
        AssetManager.createSprite("map", "map.png", 16);

        // ui
        AssetManager.createSprite("fill", "fill.png", 1);
        AssetManager.createSprite("blank", "blank.png", 1);
        AssetManager.createSprite("exit", "exit.png", 16);
        AssetManager.createSprite("circle", "circle.png", 1000);
        AssetManager.createSprite("inventoryBackground", "inventoryBackground.png", 100);

        // kitchen
        AssetManager.createSprite("kitchenBackground", "kitchen/background.png", 16);
        AssetManager.createSprite("kitchenLight", "kitchen/light.png", 16);
        AssetManager.createSprite("doorLeft", "kitchen/door_left.png", 16);
        AssetManager.createSprite("doorRight", "kitchen/door_right.png", 16);
        AssetManager.createSprite("drawerRight", "kitchen/drawer_right.png", 16);
        AssetManager.createSprite("drawerLeft", "kitchen/drawer_left.png", 16);
        AssetManager.createSprite("minigameDoor", "kitchen/minigameDoor.png", 16);

        // pots
        AssetManager.createSprite("bigPot", "kitchen/big_pot.png", 16);
        AssetManager.createSprite("mediumPot", "kitchen/medium_pot.png", 16);
        AssetManager.createSprite("smallPot", "kitchen/small_pot.png", 16);
        AssetManager.createSprite("extraSmallPot", "kitchen/extra_small_pot.png", 16);
        AssetManager.createSprite("potSlot", "kitchen/pot_slot.png", 16);

        // library
        AssetManager.createSprite("libraryBackground", "library/background.jpg", 150);
        AssetManager.createSprite("book1", "library/book1.png", 16);
        AssetManager.createSprite("book2", "library/book2.png", 16);
        AssetManager.createSprite("book3", "library/book3.png", 16);
        AssetManager.createSprite("book4", "library/book4.png", 16);
        AssetManager.createSprite("book5", "library/book5.png", 16);
        AssetManager.createSprite("book6", "library/book6.png", 16);
        // book items
        AssetManager.createSprite("book_item1", "library/book_item1.png", 16);
        AssetManager.createSprite("book_item2", "library/book_item2.png", 16);
        AssetManager.createSprite("book_item3", "library/book_item3.png", 16);
        AssetManager.createSprite("book_item4", "library/book_item4.png", 16);
        AssetManager.createSprite("book_item5", "library/book_item5.png", 16);
        AssetManager.createSprite("book_item6", "library/book_item6.png", 16);

        // dining room
        AssetManager.createSprite("diningBackground", "diningRoom/background.png", 16);
        AssetManager.createSprite("napkin", "diningRoom/napkin.png", 16);
        AssetManager.createSprite("chairLeft", "diningRoom/chair_left.png", 16);
        AssetManager.createSprite("chairRight", "diningRoom/chair_right.png", 16);
        AssetManager.createSprite("plate", "diningRoom/plate.png", 16);
        AssetManager.createSprite("adult", "diningRoom/adult.png", 16);

        // items
        AssetManager.createSprite("item_matchBox", "items/matchBox.png", 16);
        AssetManager.createSprite("item_pot", "items/pot.png", 16);
        AssetManager.createSprite("item_napkin", "items/napkin.png", 16);
        AssetManager.createSprite("item_note", "items/note.png", 16);
        AssetManager.createSprite("item_keys", "keys/key_green.png", 16);

        // keys
        AssetManager.createSprite("keys/A", "keys/A.png", 16);
        AssetManager.createSprite("keys/B", "keys/B.png", 16);
        AssetManager.createSprite("keys/C", "keys/C.png", 16);
        AssetManager.createSprite("keys/green", "keys/key_green.png", 16);
        AssetManager.createSprite("keys/orange", "keys/key_orange.png", 16);
        AssetManager.createSprite("keys/purple", "keys/key_purple.png", 16);
        AssetManager.createSprite("keys/hole", "keys/key_hole.png", 16);

        // player
        AssetManager.createSpriteSheet("levelPlayer", "level_player.png", 16, 6, 15);
        AssetManager.createSpriteSheet("mapPlayer", "map_player.png", 16, 10, 6);
        AssetManager.createSprite("mapAdult", "adultMap.png", 16);

        // font
        AssetManager.createFont("PressStart", new FontAsset("fonts/PressStart2P-Regular.ttf", null,null,null, 40, true));

        // the asset path doesn't need to contain 'data/', but the
        // asset needs to be placed in a data directory

        // needs to return the game settings
        return new GameSettings() {{
            size = new Vector2(1600,900);
//            fullscreen = true;

            targetFPS = 140;
            background = new Color(0,0,0,255);

            forceDrawGizmos = true;

            // the sorting layers need to contain "Default"
            // if they don't the "Default" layer will be placed automatically
            // at the 0th index
            sortingLayers = new String[] {
                "Default",
                "Objects",
                "People",
                "Player",
                "Light"
            };
            // the layers are used for collisions
            // all layers collide with one another
            layers = new String[] {
                "Default",
                "Objects",
                "Entities"
            };
            // here layers that SHOULDN'T collide are mentioned in pairs
            ignoreCollisionLayers = new ArrayList<>() {{
                add(new Pair<>("Default", "Objects"));
            }};
        }};
    }

    @Override
    public Scene[] buildScenes() {
        new SceneStateManager();

        // here ALL the scenes of the game are created
        // they can be created locally, using functional programming, but this can
        // get messy easily.

        // so, the suggested method is creating scenes individually as super classes,
        // then instantiating them here.
        return new Scene[] {
            new MainMenu(),
            new MapScene(),
            new KitchenScene(),
            new PotsScene(),
            new LibraryScene(),
            new BooksScene(),
            new DiningRoomScene(),
            new KeysMinigame()
        };
    }

    // we need to start the game somehow, right?
    public static void main(String[] args) {
        new Liftoff();
    }
}
