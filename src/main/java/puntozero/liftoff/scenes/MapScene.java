package puntozero.liftoff.scenes;

import processing.event.MouseEvent;
import puntozero.liftoff.components.MapPlayerController;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.prefabs.Door;
import puntozero.liftoff.prefabs.MapPlayer;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPSingleEvent;

public class MapScene extends Scene
{
    public MapScene() {
        super();

        PXPSingleEvent<MouseEvent> onClick = new PXPSingleEvent<>() {
            @Override
            public void invoke(MouseEvent event) {
                GameObject player = context.getCurrentScene().getGameObjectDeep("mapPlayer");
                if (player == null) return;
                if (!(player instanceof MapPlayer mapPlayer)) return;

                mapPlayer.controller.goThroughDoor = true;

                // if the player is already near a door, go through it
                if (mapPlayer.controller.isNearDoor)
                    context.setScene(mapPlayer.controller.doorIndex);
            }
        };

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
            () -> new Door(0, new Vector2(-1.4f, .95f), onClick),
            // left bottom door
            () -> new Door(0, new Vector2(-5.1f, .95f), onClick),

            // middle top door
            () -> new Door(SceneIndex.KITCHEN.index, new Vector2(-2.9f, -.95f), onClick),
            // left top door
            () -> new Door(0, new Vector2(-6.8f, -.95f), onClick),

            // right top door
            () -> new Door(0, new Vector2(6.65f, -.95f), onClick),

            () -> new MapPlayer(new Vector2(1.2f,2f)),
        };

        this.setGameObjects(suppliers);
    }
}
