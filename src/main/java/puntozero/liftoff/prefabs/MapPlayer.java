package puntozero.liftoff.prefabs;

import puntozero.liftoff.components.MapPlayerController;
import puntozero.liftoff.components.MapPlayerTrigger;
import pxp.engine.core.GameObject;
import pxp.engine.core.component.*;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public class MapPlayer extends GameObject
{
    public MapPlayerController controller;

    public MapPlayer(Vector2 position) {
        super("mapPlayer");

        this.controller = new MapPlayerController();

        this.setComponents(new Component[] {
            new SpriteRenderer(){{
                sortingLayer = "Player";
                pivot = Pivot.CENTER;
            }},
            new Animation("idle_front", AssetManager.get("mapPlayer", SpriteAsset.class), 0, 0, 1f),
            new Animation("idle_side", AssetManager.get("mapPlayer", SpriteAsset.class), 2, 2, 2f),
            new Animation("idle_back", AssetManager.get("mapPlayer", SpriteAsset.class), 1, 1, 2f),

            new Animation("walk_front", AssetManager.get("mapPlayer", SpriteAsset.class), 0, 0, 1f),
            new Animation("walk_side", AssetManager.get("mapPlayer", SpriteAsset.class), 2, 2, 1f),
            new Animation("walk_back", AssetManager.get("mapPlayer", SpriteAsset.class), 1, 1, 1f),

            new Animator("idle_front"),

            new CircleCollider(new Vector2(0,.85f), .2f),

            this.controller
        });
        this.setChildren(new GameObject[] {
            new GameObject("playerTrigger", new Component[] {
                new CircleCollider(new Vector2(0,.85f), .2f) {{
                    trigger = true;
                }},
                new MapPlayerTrigger(this.controller)
            })
        });

        this.transform.position = position;
    }
}
