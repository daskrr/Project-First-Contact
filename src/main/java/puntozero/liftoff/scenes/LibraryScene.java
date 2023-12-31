package puntozero.liftoff.scenes;

import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.manager.SceneState;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.prefabs.Exit;
import puntozero.liftoff.prefabs.Interactable;
import puntozero.liftoff.prefabs.LevelPlayer;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Color;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;

import java.util.ArrayList;
import java.util.List;

public class LibraryScene extends Scene
{
    public static class LibrarySceneState implements SceneState
    {
        public boolean minigame = true;

        @Override
        public GameObjectSupplier[] restoreSceneState(Scene s) {
            LibraryScene scene = (LibraryScene) s;
            List<GameObjectSupplier> suppliers = new ArrayList<>() {{
                add(() -> new GameObject("camera", new Component[] {
                    new Camera(8f)
                }));
                add(() -> new GameObject("background", new Component[] {
                    new SpriteRenderer(AssetManager.get("libraryBackground", SpriteAsset.class))
                }));
                add(() -> new Exit(new Vector2(13f, 0f)));
                add(() -> new LevelPlayer() {{
                    transform = new Transform(new Vector2(13f, 4.5f));
                }});
                add(PlayerInventory::create);
            }};

            if (minigame)
                suppliers.add(() -> new Interactable("books",
                    new Vector2(),
                    new Vector2(3.5f, 1f),
                    new Image(AssetManager.get("books", SpriteAsset.class)) {{
                        color = new Color(255,255,255,0);
                    }},
                    scene.openMinigame())
                {{
                    transform = new Transform(new Vector2(-9.5f, 4.65f));
                }});

            suppliers.add(() -> new GameObject("light", new Component[] {
                new SpriteRenderer(AssetManager.get("libraryLight", SpriteAsset.class)) {{
                    setSortingLayer("Light");
                }}
            }));
            suppliers.add(() -> new GameObject("foreground", new Component[] {
                new SpriteRenderer(AssetManager.get("libraryForeground", SpriteAsset.class)) {{
                    setSortingLayer("Foreground");
                }}
            }));

            return suppliers.toArray(new GameObjectSupplier[0]);
        }
    }

    private LibrarySceneState state;

    public LibraryScene() {
        super();

        // this only executes once when the scene is created!
        this.state = SceneStateManager.getInstance().get(this, new LibrarySceneState());
        this.setGameObjects(state.restoreSceneState(this));
    }

    @Override
    public void load() {
        // we need to reset the game object suppliers when the scene is loaded again, in order to preserve state
        this.state = SceneStateManager.getInstance().get(this, new LibrarySceneState());
        this.setGameObjects(state.restoreSceneState(this));
        super.load();
    }

    private PXPEvent openMinigame() {
        return new PXPEvent() {
            @Override
            public void invoke() {
                // we don't destroy as this minigame is replayable
                // we need to check if the player has a book in their inventory
                PlayerInventory.removeBooks();

                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
                levelPlayer.controller.destination = levelPlayer.transform.position;

                // not needed anymore
                //state.minigame = false;

                // save position
                SceneStateManager.getInstance().levelPlayerPosition = levelPlayer.transform.position;

                // change scene to minigame scene
                context.setScene(SceneIndex.BOOKS.index);
            }
        };
    }
}
