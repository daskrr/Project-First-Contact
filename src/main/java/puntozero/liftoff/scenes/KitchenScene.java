package puntozero.liftoff.scenes;

import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.inventory.ItemRegistry;
import puntozero.liftoff.manager.SceneState;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.prefabs.*;
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
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;
import pxp.logging.Debug;

import java.util.ArrayList;
import java.util.List;

public class KitchenScene extends Scene
{
    public static class KitchenSceneState implements SceneState {

        public boolean doorLeft = true;
        public boolean doorRight = true;
        public boolean drawerLeft = true;
        public boolean drawerRight = true;
        public boolean minigameDoor = true;

        @Override
        public GameObjectSupplier[] restoreSceneState(Scene s) {
            KitchenScene scene = (KitchenScene) s;

            List<GameObjectSupplier> suppliers = new ArrayList<>() {{
                add(() -> new GameObject("camera", new Component[] {
                    new Camera(8f)
                }));
                add(() -> new GameObject("background", new Component[] {
                    new SpriteRenderer(AssetManager.get("kitchenBackground", SpriteAsset.class))
                }));
                add(() -> new Exit(new Vector2(13f, 0f)));
                add(() -> new LevelPlayer() {{
                    transform = new Transform(new Vector2(13f, 5f));
                }});
                add(PlayerInventory::create);
            }};

            if (doorLeft)
                suppliers.add(() -> new Interactable("doorLeft",
                    new Vector2(),
                    new Vector2(1.6f, 1.6f),
                    new Image(AssetManager.get("doorLeft", SpriteAsset.class)),
                    scene.openCabinet("doorLeft"))
                {{
                    transform = new Transform(new Vector2(-7.9f, 5.05f));
                }});
            if (doorRight)
                suppliers.add(() -> new Interactable("doorRight",
                    new Vector2(),
                    new Vector2(1.6f, 1.6f),
                    new Image(AssetManager.get("doorRight", SpriteAsset.class)),
                    scene.openCabinet("doorRight"))
                {{
                    transform = new Transform(new Vector2(-4.7f, 5.05f));
                }});
            if (drawerLeft)
                suppliers.add(() -> new Interactable("drawerLeft",
                    new Vector2(0f, 2.5f),
                    new Vector2(2.4f, 4f),
                    new Image(AssetManager.get("drawerLeft", SpriteAsset.class)) {{
                        color = new Color(255,255,255, 0);
                    }},
                    scene.openDrawer("drawerLeft"))
                {{
                    transform = new Transform(new Vector2(-12.25f, 2.2f));
                }});
            if (drawerRight)
                suppliers.add(() -> new Interactable("drawerRight",
                    new Vector2(0f, 2.5f),
                    new Vector2(3.2f, 4f),
                    new Image(AssetManager.get("drawerRight", SpriteAsset.class)) {{
                        color = new Color(255,255,255, 0);
                    }},
                    scene.openDrawer("drawerRight"))
                {{
                    transform = new Transform(new Vector2(-6.3f, 2.2f));
                }});

            if (minigameDoor)
                suppliers.add(() -> new Interactable("minigameDoor",
                    new Vector2(0f, 0f),
                    new Vector2(2.4f, 2.4f),
                    new Image(AssetManager.get("minigameDoor", SpriteAsset.class)) {{
                        color = new Color(255,255,255, 0);
                    }},
                    scene.openMinigame())
                {{
                    transform = new Transform(new Vector2(-12.25f, 5f));
                }});

            suppliers.add(() -> new GameObject("light", new Component[] {
                new SpriteRenderer(AssetManager.get("kitchenLight", SpriteAsset.class)) {{
                    setSortingLayer("light");
                }}
            }));

            return suppliers.toArray(new GameObjectSupplier[0]);
        }
    }

    private final KitchenSceneState state;

    public KitchenScene() {
        super();

        // this only executes once when the scene is created!
        this.state = SceneStateManager.getInstance().get(this, new KitchenSceneState());
        this.setGameObjects(state.restoreSceneState(this));
    }

    @Override
    public void load() {
        // we need to reset the game object suppliers when the scene is loaded again, in order to preserve state
        this.setGameObjects(state.restoreSceneState(this));
        super.load();
    }

    private PXPEvent openCabinet(String object) {
        return new PXPEvent() {
            @Override
            public void invoke() {
                getGameObject(object).destroy();
                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
                levelPlayer.controller.destination = levelPlayer.transform.position;

                if (object.equals("doorLeft"))
                    state.doorLeft = false;
                else {
                    state.doorRight = false;

                    // show matches
                    addGameObject(new Item(ItemRegistry.MATCHES, new Vector2(-4.8f, 5f)));
                }
            }
        };
    }
    private PXPEvent openDrawer(String object) {
        return new PXPEvent() {
            @Override
            public void invoke() {
                TextBox text = new TextBox(
                    "It's too heavy for you to open!",
                    17,
                    new Vector2(600,200),
                    new Color(30, 32, 36, 240),
                    AssetManager.get("PressStart", FontAsset.class),
                    Color.white()
                );

                // display message
                addGameObject(text);
                text.remove(5f);
                getGameObject(object).destroy(); // destroy since we don't want to be able to interact again

                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
                levelPlayer.controller.destination = levelPlayer.transform.position;

                if (object.equals("drawerLeft"))
                    state.drawerLeft = false;
                else
                    state.drawerRight = false;
            }
        };
    }

    private PXPEvent openMinigame() {
        return new PXPEvent() {
            @Override
            public void invoke() {
                getGameObject("minigameDoor").destroy(); // destroy since we don't want to be able to interact again

                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
                levelPlayer.controller.destination = levelPlayer.transform.position;

                state.minigameDoor = false;

                // save position
                SceneStateManager.getInstance().levelPlayerPosition = levelPlayer.transform.position;

                // change scene to minigame scene
                context.setScene(SceneIndex.POTS.index);
            }
        };
    }
}
