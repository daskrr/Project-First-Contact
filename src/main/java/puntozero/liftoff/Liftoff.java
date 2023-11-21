package puntozero.liftoff;

import puntozero.liftoff.scenes.MapScene;
import pxp.engine.core.Game;
import pxp.engine.core.Scene;
import pxp.engine.data.Color;
import pxp.engine.data.GameSettings;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.util.Pair;

import java.util.ArrayList;

public class Liftoff extends Game
{
    @Override
    public GameSettings startup() {
        // adding assets to the game
        AssetManager.createSprite("testImage", "data/image.png", 16);
        // the asset path doesn't need to contain 'data/', but the
        // asset needs to be placed in a data directory

        // needs to return the game settings
        return new GameSettings() {{
            size = new Vector2(1280,720);
            fullscreen = false;

            targetFPS = 140;
            background = new Color(0,0,0,255);

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
        // here ALL the scenes of the game are created
        // they can be created locally, using functional programming, but this can
        // get messy easily.

        // so, the suggested method is creating scenes individually as super classes,
        // then instantiating them here.
        return new Scene[] {
            new MapScene(),
        };
    }

    // we need to start the game somehow, right?
    public static void main(String[] args) {
        new Liftoff();
    }
}
