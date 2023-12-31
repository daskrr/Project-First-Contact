package puntozero.liftoff;

import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.scenes.*;
import puntozero.liftoff.scenes.minigame.BooksScene;
import puntozero.liftoff.scenes.minigame.CraftingScene;
import puntozero.liftoff.scenes.minigame.KeysMinigame;
import puntozero.liftoff.scenes.minigame.PotsScene;
import pxp.engine.core.Game;
import pxp.engine.core.GameProcess;
import pxp.engine.core.Scene;
import pxp.engine.data.Color;
import pxp.engine.data.GameSettings;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SoundAsset;
import pxp.util.Pair;

import java.util.ArrayList;

public class OnTheRun extends Game
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
        AssetManager.createSprite("kitchenForeground", "kitchen/foreground.png", 16);
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
        AssetManager.createSprite("libraryBackground", "library/background.png", 16);
        AssetManager.createSprite("libraryLight", "library/light.png", 16);
        AssetManager.createSprite("libraryForeground", "library/foreground.png", 16);
        AssetManager.createSprite("book1", "library/book1.png", 16);
        AssetManager.createSprite("book2", "library/book2.png", 16);
        AssetManager.createSprite("book3", "library/book3.png", 16);
        AssetManager.createSprite("book4", "library/book4.png", 16);
        AssetManager.createSprite("book5", "library/book5.png", 16);
        AssetManager.createSprite("book6", "library/book6.png", 16);
        AssetManager.createSprite("books", "library/books.png", 16);

        // book items
        AssetManager.createSprite("book_item1", "library/book_item1.png", 16);
        AssetManager.createSprite("book_item2", "library/book_item2.png", 16);
        AssetManager.createSprite("book_item3", "library/book_item3.png", 16);
        AssetManager.createSprite("book_item4", "library/book_item4.png", 16);
        AssetManager.createSprite("book_item5", "library/book_item5.png", 16);
        AssetManager.createSprite("book_item6", "library/book_item6.png", 16);

        // dining room
        AssetManager.createSprite("diningBackground", "diningRoom/background.png", 16);
        AssetManager.createSprite("diningLight", "diningRoom/light.png", 16);
        AssetManager.createSprite("diningForeground", "diningRoom/foreground.png", 16);
        AssetManager.createSprite("napkin", "diningRoom/napkin.png", 16);
        AssetManager.createSprite("chairLeft", "diningRoom/chair_left.png", 16);
        AssetManager.createSprite("chairRight", "diningRoom/chair_right.png", 16);
        AssetManager.createSprite("plate", "items/plate.png", 16);
        AssetManager.createSprite("brokenPlate", "items/broken_plate.png", 16);
        AssetManager.createSprite("adult", "diningRoom/adult.png", 16);
        AssetManager.createSprite("kid", "diningRoom/boy.png", 16);

        // discipline
        AssetManager.createSprite("disciplineBackground", "disciplineRoom/background.png", 16);
        AssetManager.createSprite("disciplineLight", "disciplineRoom/background_light.png", 16);
        AssetManager.createSprite("disciplineForeground", "disciplineRoom/foreground.png", 16);
        AssetManager.createSprite("key", "disciplineRoom/key.png", 16);
        AssetManager.createSprite("coatKey", "disciplineRoom/coat_key.png", 16);

        // storage
        AssetManager.createSprite("storageBackground", "storageRoom/background.png", 16);
        AssetManager.createSprite("storageLight", "storageRoom/light.png", 16);
        AssetManager.createSprite("storageForeground", "storageRoom/foreground.png", 16);
        AssetManager.createSprite("noteBig", "storageRoom/note_big.png", 16);
        AssetManager.createSprite("noteSmall", "storageRoom/note_small.png", 16);
        AssetManager.createSprite("potionBlue", "storageRoom/potion_blue.png", 16);
        AssetManager.createSprite("potionGreen", "storageRoom/potion_green.png", 16);
        AssetManager.createSprite("potionRed", "storageRoom/potion_red.png", 16);
        AssetManager.createSprite("craftingTable", "storageRoom/craftingtable.png", 16);

        // crafting
        AssetManager.createSprite("workbench", "crafting/workbench.png", 16);

        AssetManager.createSprite("openBlueBook", "crafting/book_blue_open.png", 16);
        AssetManager.createSprite("openGreenBook", "crafting/book_green_open.png", 16);
        AssetManager.createSprite("openOrangeBook", "crafting/book_orange_open.png", 16);
        AssetManager.createSprite("openPinkBook", "crafting/book_pink_open.png", 16);
        AssetManager.createSprite("openPurpleBook", "crafting/book_purple_open.png", 16);
        AssetManager.createSprite("openRedBook", "crafting/book_red_open.png", 16);

        AssetManager.createSprite("blueBottle", "crafting/bottle_blue.png", 16);
        AssetManager.createSprite("greenBottle", "crafting/bottle_green.png", 16);
        AssetManager.createSprite("redBottle", "crafting/bottle_red.png", 16);

        AssetManager.createSprite("matchBoxTable", "crafting/matchbox_table.png", 16);
        AssetManager.createSprite("molotov", "crafting/molotov.png", 16);
        AssetManager.createSprite("napkinTable", "crafting/napkin.png", 16);

        AssetManager.createSprite("bluePot", "crafting/blue_pot.png", 16);
        AssetManager.createSprite("greenPot", "crafting/green_pot.png", 16);
        AssetManager.createSprite("redPot", "crafting/red_pot.png", 16);
        AssetManager.createSprite("pot", "crafting/pot.png", 16);

        // items
        AssetManager.createSprite("item_matchBox", "items/matchBox.png", 16);
        AssetManager.createSprite("item_pot", "items/pot.png", 16);
        AssetManager.createSprite("item_napkin", "items/napkin.png", 16);
        AssetManager.createSprite("item_note", "items/note.png", 16);
        AssetManager.createSprite("item_keys", "items/keys.png", 16);
        AssetManager.createSprite("item_potion_blue", "items/blue_potion.png", 16);
        AssetManager.createSprite("item_potion_red", "items/red_potion.png", 16);
        AssetManager.createSprite("item_potion_green", "items/green_potion.png", 16);

        // keys
        AssetManager.createSprite("keys/A", "keys/A.png", 16);
        AssetManager.createSprite("keys/B", "keys/B.png", 16);
        AssetManager.createSprite("keys/C", "keys/C.png", 16);
        AssetManager.createSprite("keys/green", "keys/key_green.png", 16);
        AssetManager.createSprite("keys/orange", "keys/key_orange.png", 16);
        AssetManager.createSprite("keys/purple", "keys/key_purple.png", 16);
        AssetManager.createSprite("keys/hole", "keys/key_hole.png", 16);

        // player
        AssetManager.createSpriteSheet("levelPlayer", "level_player.png", 16, 1, 3);
        AssetManager.createSpriteSheet("mapPlayer", "map_player.png", 16, 1, 3);
        AssetManager.createSprite("mapAdult", "adultMap.png", 16);
        AssetManager.createSprite("finish", "finish.png", 100);

        // font
        AssetManager.createFont("PressStart", new FontAsset("fonts/PressStart2P-Regular.ttf", null,null,null, 40, true));

        // sounds
        AssetManager.createSound("backgroundSound", "sound/background.wav", 1f);
        AssetManager.createSound("sound1", "sound/sound1.mp3", 1f);
        AssetManager.createSound("sound2", "sound/sound2.mp3", 1f);
        AssetManager.createSound("sound3", "sound/sound3.mp3", 1f);
        AssetManager.createSound("sound4", "sound/sound4.mp3", 1f);
        AssetManager.createSound("sound6", "sound/sound6.mp3", 1f);
        AssetManager.createSound("sound7", "sound/sound7.mp3", 1f);
        AssetManager.createSound("sound8", "sound/sound8.mp3", 1f);
        AssetManager.createSound("sound9", "sound/sound9.mp3", 1f);
        AssetManager.createSound("sound10", "sound/sound10.mp3", 1f);
        AssetManager.createSound("sound11", "sound/sound11.mp3", 1f);

        // the asset path doesn't need to contain 'data/', but the
        // asset needs to be placed in a data directory

        // needs to return the game settings
        return new GameSettings() {{
            size = new Vector2(1920,1080);
            fullscreen = true;

            targetFPS = 140;
            background = new Color(0,0,0,255);

//            forceDrawGizmos = true;

            // the sorting layers need to contain "Default"
            // if they don't the "Default" layer will be placed automatically
            // at the 0th index
            sortingLayers = new String[] {
                "Default",
                "Objects",
                "People",
                "Player",
                "Light",
                "Foreground",
                "UI"
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
    public void setup() {
        GameProcess.nextFrame(() -> {
            AssetManager.get("backgroundSound", SoundAsset.class).getSound().loop();
        });
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
            new KeysMinigame(),
            new StorageRoomScene(),
            new CraftingScene(),
            new DisciplineScene()
        };
    }

    // we need to start the game somehow, right?
    public static void main(String[] args) {
        new OnTheRun();
    }
}
