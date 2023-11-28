package puntozero.liftoff.scenes;

import puntozero.liftoff.components.PlayerInventory;
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
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;
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
                add(PlayerInventory::create);
                add(() -> new Interactable("chairLeft",
                    new Vector2(),
                    new Vector2(1.6f, 1.6f),
                    new Image(AssetManager.get("chairLeft", SpriteAsset.class)),
                    scene.climbChair("chairLeft"),
                    true)
                {{
                    transform = new Transform(new Vector2(-11.7f,5.35f));
                }});
                add(() -> new Interactable("chairRight",
                    new Vector2(),
                    new Vector2(1.6f, 1.6f),
                    new Image(AssetManager.get("chairRight", SpriteAsset.class)),
                    scene.climbChair("chairRight"),
                    true)
                {{
                    transform = new Transform(new Vector2(-1.82f,5.35f));
                }});
            }};

            if (napkin)
                suppliers.add(() -> new Interactable("napkin",
                    new Vector2(),
                    new Vector2(1.6f, 1.6f),
                    new Image(AssetManager.get("napkin", SpriteAsset.class)),
                    scene.takeNapkin())
                {{
                    transform = new Transform(new Vector2(-12.9f, 1.2f));
                }});

            Vector2[] platePosition = new Vector2[] { new Vector2(-1.5f, -.05f) };
            if (!plate)
                platePosition[0] = new Vector2(-1.5f, 4.05f);

            suppliers.add(() -> new Interactable("plate",
                new Vector2(),
                new Vector2(1.6f, 1.6f),
                new Image(AssetManager.get("plate", SpriteAsset.class)),
                scene.dropPlate())
            {{
                transform = new Transform(platePosition[0]);
            }});

            return suppliers.toArray(new GameObjectSupplier[0]);
        }
    }

    private final DiningRoomState state;
    public DiningRoomScene(){
        super();

        this.state = SceneStateManager.getInstance().get(this, new DiningRoomState());
        this.setGameObjects(state.restoreSceneState(this));
    }

    @Override
    public void load() {
        // we need to reset the game object suppliers when the scene is loaded again, in order to preserve state
        this.setGameObjects(state.restoreSceneState(this));
        super.load();

        LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
        this.state.defaultY = levelPlayer.transform.position.y;
    }

    private PXPEvent climbChair(String object) {
        return new PXPEvent(){
            @Override
            public void invoke() {
                GameObject chair = context.getCurrentScene().getGameObjectDeep(object);
                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");

                state.isOnChair = !state.isOnChair;

                if (state.isOnChair) {
                    levelPlayer.transform.position.x = chair.transform.position.x;
                    levelPlayer.transform.position.y -= 3.9f;
                    levelPlayer.controller.setLocked(true);
                }
                else {
                    levelPlayer.transform.position.y = state.defaultY;
                    levelPlayer.controller.setLocked(false);
                }
            }
        };
    }

    // moved methods under constructor and load
    private PXPEvent dropPlate() {
        return new PXPEvent(){
            @Override
            public void invoke() {
                // only do this when standing on chair
                if (state.isOnChair){
                    GameObject plate = getGameObject("plate");
                    //TODO: do I need this here?
//                    LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
//                    levelPlayer.controller.destination = levelPlayer.transform.position;

                    plate.transform.position = new Vector2(-1.5f, 6.75f);

                    state.plate = false;

                    // TODO trigger adult
                }
            }
        };
    }

    private PXPEvent takeNapkin() {
        return new PXPEvent() {
            @Override
            public void invoke() {
                // only do this when standing on chair
                if (state.isOnChair) {
                    getGameObject("napkin").destroy();
                    //TODO: do I need this here?
//                    LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
//                    levelPlayer.controller.destination = levelPlayer.transform.position;

                    state.napkin = false;

                    PlayerInventory.addItem(ItemRegistry.NAPKIN.item);
                }
            }
        };
    }
}
