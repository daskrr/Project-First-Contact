package puntozero.liftoff.scenes;

import processing.event.MouseEvent;
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
import pxp.engine.data.event.PXPSingleEvent;
import pxp.logging.Debug;

import java.util.ArrayList;
import java.util.List;

public class DiningRoomScene extends Scene {
    public static class DiningRoomState implements SceneState {

        public boolean napkin = true;
        public boolean plate = true;
        public boolean isOnChair = false;
        public float defaultY = 0f;

        @Override
        public GameObjectSupplier[] restoreSceneState(Scene s) {
            DiningRoomScene scene = (DiningRoomScene) s;

            List<GameObjectSupplier> suppliers = new ArrayList<>() {{
                add(() -> new GameObject("camera", new Component[] {
                        new Camera(8f)
                }));
                add(() -> new GameObject("background", new Component[] {
                    new SpriteRenderer(AssetManager.get("diningBackground", SpriteAsset.class))
                }));
                add(() -> new Exit(new Vector2(13f,0f)));
                add(() -> new LevelPlayer() {{
                    transform = new Transform(new Vector2(13f, 5f));
                }});
                add(() -> new Interactable("chairLeft",
                        new Vector2(),
                        new Vector2(1.6f, 1.6f),
                        new Image(AssetManager.get("chairLeft", SpriteAsset.class)),
                        scene.climbChair("chairLeft"))
                {{
                    transform = new Transform(new Vector2(-11.7f,5.35f));
                }});
                add(() -> new Interactable("chairRight",
                        new Vector2(),
                        new Vector2(1.6f, 1.6f),
                        new Image(AssetManager.get("chairRight", SpriteAsset.class)),
                        scene.climbChair("chairRight"))
                {{
                    transform = new Transform(new Vector2(-1.82f,5.35f));
                }});
            }};

            if (napkin)
                suppliers.add(() -> new Interactable("napkin",
                        new Vector2(),
                        new Vector2(1.6f, 1.6f),
                        new Image(AssetManager.get("napkin", SpriteAsset.class)),
                        scene.takeNapkin("napkin"))
                {{
                    transform = new Transform(new Vector2(-12.9f, 1.2f));
                }});

            if (plate)
                suppliers.add(() -> new Interactable("plate",
                        new Vector2(),
                        new Vector2(1.6f, 1.6f),
                        new Image(AssetManager.get("plate", SpriteAsset.class)),
                        scene.takePlate("plate"))
                {{
                    transform = new Transform(new Vector2(-1.5f, -.05f));
                }});

            return suppliers.toArray(new GameObjectSupplier[0]);
        }
    }

    //TODO: make mouse event???
    private PXPEvent climbChair(String object) {
        return new PXPEvent(){
            @Override
            public void invoke() {
                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");

                state.isOnChair = !state.isOnChair;

                if (state.isOnChair){
                    levelPlayer.transform.position.y = levelPlayer.transform.position.y - 3.9f;
                }
                else {
                    levelPlayer.transform.position.y = state.defaultY;
                }

                Debug.log(object + " clicked");
                Debug.log(state.isOnChair);
            }
        };
    }

    private PXPEvent takePlate(String object) {
        return new PXPEvent(){
            @Override
            public void invoke() {
                //only do this when standing on chair
                if (state.isOnChair){
                    getGameObject(object).destroy();
                    //TODO: do I need this here?
//                    LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
//                    levelPlayer.controller.destination = levelPlayer.transform.position;

                    state.plate = false;
                }
            }
        };
    }

    private PXPEvent takeNapkin(String object) {
        return new PXPEvent() {
            @Override
            public void invoke() {
                //only do this when standing on chair
                if (state.isOnChair) {
                    getGameObject(object).destroy();
                    //TODO: do I need this here?
//                    LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
//                    levelPlayer.controller.destination = levelPlayer.transform.position;

                    state.napkin = false;
                }
            }
        };
    }

    private final DiningRoomState state;
    public DiningRoomScene(){
        super();

        this.state = SceneStateManager.getInstance().get(this, new DiningRoomState());
        this.setGameObjects(state.restoreSceneState(this));

        //TODO: replace assets to Liftoff
        AssetManager.createSprite("diningBackground", "diningRoom/background.png", 16);
        AssetManager.createSprite("napkin", "diningRoom/napkin.png", 16);
        AssetManager.createSprite("chairLeft", "diningRoom/chair_left.png", 16);
        AssetManager.createSprite("chairRight", "diningRoom/chair_right.png", 16);
        AssetManager.createSprite("plate", "diningRoom/plate.png", 16);
    }

    @Override
    public void load() {
        // we need to reset the game object suppliers when the scene is loaded again, in order to preserve state
        this.setGameObjects(state.restoreSceneState(this));
        super.load();

        LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
        this.state.defaultY = levelPlayer.transform.position.y;
    }
}
