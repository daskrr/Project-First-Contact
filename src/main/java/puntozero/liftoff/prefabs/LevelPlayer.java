package puntozero.liftoff.prefabs;

import puntozero.liftoff.components.LevelPlayerController;
import pxp.engine.core.GameObject;
import pxp.engine.core.component.*;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public class LevelPlayer extends GameObject
{
    public LevelPlayerController controller;

    public LevelPlayer() {
        super("levelPlayer");

        this.controller = new LevelPlayerController();

        this.setComponents(new Component[] {
            new SpriteRenderer(AssetManager.getSpriteFromSheet("levelPlayer", 0)) {{
                setSortingLayer("Player");
            }},
            new Animation("idle", AssetManager.get("levelPlayer", SpriteAsset.class), 0, 5, 2f),
            new Animation("walk", AssetManager.get("levelPlayer", SpriteAsset.class), 6, 13, 1f),
            new Animator("idle"),
            this.controller,
            new BoxCollider(new Vector2(), new Vector2(1f, 3f)) {{
                trigger = true;
            }}
        });
    }
}
