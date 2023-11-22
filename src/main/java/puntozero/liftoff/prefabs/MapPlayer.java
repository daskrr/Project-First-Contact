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
                sortingLayer = "Default";
                pivot = Pivot.CENTER;
            }},
            new Animation("idle_front", AssetManager.get("mapPlayer", SpriteAsset.class), 0, 5, 2f),
            new Animation("idle_side", AssetManager.get("mapPlayer", SpriteAsset.class), 6, 11, 2f),
            new Animation("idle_back", AssetManager.get("mapPlayer", SpriteAsset.class), 12, 17, 2f),

            new Animation("walk_front", AssetManager.get("mapPlayer", SpriteAsset.class), 18, 23, 1f),
            new Animation("walk_side", AssetManager.get("mapPlayer", SpriteAsset.class), 24, 29, 1f),
            new Animation("walk_back", AssetManager.get("mapPlayer", SpriteAsset.class), 30, 35, 1f),

            new Animator("idle_front"),

            new CircleCollider(new Vector2(0,.85f), .2f) {{
                drawGizmos = true;
            }},

            this.controller
        });
        this.setChildren(new GameObject[] {
            new GameObject("playerTrigger", new Component[] {
                new CircleCollider(new Vector2(0,.85f), .2f) {{
                    drawGizmos = true;
                    trigger = true;
                }},
                new MapPlayerTrigger(this.controller)
            })
        });

        this.transform.position = position;
    }
}
