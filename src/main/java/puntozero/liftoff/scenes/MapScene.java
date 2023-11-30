package puntozero.liftoff.scenes;

import processing.event.MouseEvent;
import puntozero.liftoff.components.AdultHandler;
import puntozero.liftoff.components.MapPlayerController;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.prefabs.Door;
import puntozero.liftoff.prefabs.MapPlayer;
import puntozero.liftoff.prefabs.Monologue;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.*;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPSingleEvent;

public class MapScene extends Scene
{
    public MapScene() {
        super();

        GameObjectSupplier[] suppliers = new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[]{
                new Camera(7f)
            }),
            () -> new GameObject("mapBackground", new Component[] {
                new SpriteRenderer(AssetManager.get("map", SpriteAsset.class)),
                // perimeter
                new BoxCollider(new Vector2(), new Vector2(7.45f,4.15f)),

                // room left top
                new BoxCollider(new Vector2(-5.6f,-2.55f), new Vector2(1.87f, 1.626f)),
                // room middle top
                new BoxCollider(new Vector2(-1.86f,-2.55f), new Vector2(1.87f, 1.626f)),
                // room left bottom
                new BoxCollider(new Vector2(-5.6f,2.55f), new Vector2(1.87f, 1.626f)),
                // room middle bottom
                new BoxCollider(new Vector2(-1.86f,2.55f), new Vector2(1.87f, 1.626f)),

                // right top
                new BoxCollider(new Vector2(5f,-2.55f), new Vector2(2.47f, 1.58f)),
                // right bottom
                new BoxCollider(new Vector2(5f,2.55f), new Vector2(2.47f, 1.58f)),
            }) {{
                transform = new Transform(new Vector2(0,0));
            }},
            // middle bottom door
            () -> new Door(SceneIndex.DISCIPLINE_ROOM.index, new Vector2(-1.4f, .95f), onClick(SceneIndex.DISCIPLINE_ROOM.index)),
            // left bottom door
            () -> new Door(SceneIndex.LIBRARY.index, new Vector2(-5.1f, .95f), onClick(SceneIndex.LIBRARY.index)),

            // middle top door
            () -> new Door(SceneIndex.KITCHEN.index, new Vector2(-2.9f, -.95f), onClick(SceneIndex.KITCHEN.index)),
            // left top door
            () -> new Door(SceneIndex.DINING_ROOM.index, new Vector2(-6.8f, -.95f), onClick(SceneIndex.DINING_ROOM.index)),

            // right top door
            () -> new Door(SceneIndex.STORAGE_ROOM.index, new Vector2(6.65f, -.95f), onClick(SceneIndex.STORAGE_ROOM.index)),

            () -> new MapPlayer(new Vector2(6.65f,-.5f)),
            () -> new GameObject("adult", new Component[] {
                new SpriteRenderer(AssetManager.get("mapAdult", SpriteAsset.class)),
                new AdultHandler(),
                new CircleCollider(new Vector2(0,.1f), .3f)
            }) {{
                transform = new Transform(new Vector2(1.2f, -3f));
            }},

            PlayerInventory::create
        };

        this.setGameObjects(suppliers);
    }

    private PXPSingleEvent<MouseEvent> onClick(int index) {
        return new PXPSingleEvent<>() {
            @Override
            public void invoke(MouseEvent event) {
                GameObject player = context.getCurrentScene().getGameObjectDeep("mapPlayer");
                if (player == null) return;
                if (!(player instanceof MapPlayer mapPlayer)) return;

                mapPlayer.controller.goThroughDoor = true;
                mapPlayer.controller.targetedDoorIndex = index;

                // if the player is already near a door, go through it
                if (mapPlayer.controller.isNearDoor && mapPlayer.controller.doorIndex == index) {
                    boolean canGo = true;

                    // check if player can enter room
                    if (index == SceneIndex.LIBRARY.index) {
                        if (!SceneStateManager.getInstance().libraryUnlocked)
                            canGo = false;
                    }
                    else if (index == SceneIndex.DISCIPLINE_ROOM.index)
                        if (!SceneStateManager.getInstance().disciplineUnlocked)
                            canGo = false;

                    if (canGo) {
                        SceneStateManager.getInstance().mapPlayerPosition = mapPlayer.transform.position;
                        context.setScene(mapPlayer.controller.doorIndex);
                    }
                    else {
                        if (index == SceneIndex.LIBRARY.index) {
                            if (PlayerInventory.hasItem("keys")) {
                                SceneStateManager.getInstance().mapPlayerPosition = mapPlayer.transform.position;
                                context.setScene(SceneIndex.KEYS.index);
                            }
                            else {
                                Monologue monologue = new Monologue("Oh, the library is locked.\nWhere can I find the key?\nThe adults always carry them in their pockets…");
                                addGameObject(monologue);
                                monologue.remove(5f);
                            }
                        }
                        else {
                            Monologue monologue = new Monologue("Mh... locked...\nNoone misbehaved today so\nthe discipline room is locked.");
                            addGameObject(monologue);
                            monologue.remove(3.5f);
                        }
                    }
                }
            }
        };
    }
}
