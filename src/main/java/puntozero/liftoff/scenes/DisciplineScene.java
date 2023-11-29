package puntozero.liftoff.scenes;

import puntozero.liftoff.components.PlayerInventory;
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

public class DisciplineScene extends Scene {
    public static class DisciplineSceneState implements SceneState {
        public boolean key = true;
        @Override
        public GameObjectSupplier[] restoreSceneState(Scene s) {
            DisciplineScene scene = (DisciplineScene) s;

            List<GameObjectSupplier> suppliers = new ArrayList<>() {{
                add(() -> new GameObject("camera", new Component[] {
                        new Camera(8f)
                }));
                add(() -> new GameObject("background", new Component[] {
                    new SpriteRenderer(AssetManager.get("disciplineBackground", SpriteAsset.class))
                }));
                add(() -> new Exit(new Vector2(13f, 0f)));
                add(() -> new LevelPlayer() {{
                    transform = new Transform(new Vector2(13f, 5f));
                }});
                add(PlayerInventory::create);
            }};

            if (key){
                suppliers.add(() -> new Interactable("key",
                        new Vector2(),
                        new Vector2(1.6f,1.6f),
                        new Image(AssetManager.get("key", SpriteAsset.class)),
                        scene.takeKey("key"))
                {{
                    transform = new Transform(new Vector2(7f,4.2f));
                }});
            }
            //TODO: If child comes to close to adult then reset the game

            suppliers.add(() -> new GameObject("light", new Component[] {
                    new SpriteRenderer(AssetManager.get("disciplineLight", SpriteAsset.class)) {{
                        setSortingLayer("light");
                    }}
            }));

            return suppliers.toArray(new GameObjectSupplier[0]);
        }
    }

    private PXPEvent takeKey(String object) {
        return new PXPEvent(){
            @Override
            public void invoke() {
                //TODO: uncomment this
                //PlayerInventory.addItem(ItemRegistry.KEY.item);
                getGameObject(object).destroy();
            }
        };
    }

    private final DisciplineSceneState state;
    public DisciplineScene (){
        super();

        this.state = SceneStateManager.getInstance().get(this, new DisciplineSceneState());
        this.setGameObjects(state.restoreSceneState(this));

        //TODO: replace assets to Liftoff
        AssetManager.createSprite("disciplineBackground", "disciplineRoom/background.png", 16);
        AssetManager.createSprite("disciplineLight", "disciplineRoom/background_light.png", 16);
        AssetManager.createSprite("key", "disciplineRoom/key.png", 16);
    }

    @Override
    public void load() {
        // we need to reset the game object suppliers when the scene is loaded again, in order to preserve state
        this.setGameObjects(state.restoreSceneState(this));
        super.load();
    }
}
