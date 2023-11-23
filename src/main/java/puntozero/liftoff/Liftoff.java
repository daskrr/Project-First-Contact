package puntozero.liftoff;

import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.scenes.KitchenScene;
import puntozero.liftoff.scenes.LevelTestScene;
import puntozero.liftoff.scenes.MapScene;
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
        AssetManager.createSprite("test", "image.png", 16);
        AssetManager.createSpriteSheet("mapPlayer", "map_player.png", 16, 10, 6);
        AssetManager.createSprite("map", "map.png", 16);
        AssetManager.createSprite("fill", "fill.png", 1);
        AssetManager.createSprite("blank", "blank.png", 1);
        AssetManager.createSprite("exit", "exit.png", 16);
        AssetManager.createSprite("circle", "circle.png", 1000);

        AssetManager.createSprite("kitchenBackground", "kitchen/background.png", 16);
        AssetManager.createSprite("doorLeft", "kitchen/door_left.png", 16);
        AssetManager.createSprite("doorRight", "kitchen/door_right.png", 16);
        AssetManager.createSprite("drawerRight", "kitchen/drawer_right.png", 16);
        AssetManager.createSprite("drawerLeft", "kitchen/drawer_left.png", 16);
        AssetManager.createSprite("minigameDoor", "kitchen/minigameDoor.png", 16);

        AssetManager.createSpriteSheet("levelPlayer", "level_player.png", 16, 6, 15);
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
                "Enemies",
                "Player",
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
            new MapScene(),
            new KitchenScene(),
//                new LevelTestScene()
        };
    }

    // we need to start the game somehow, right?
    public static void main(String[] args) {
        new Liftoff();
    }
}
