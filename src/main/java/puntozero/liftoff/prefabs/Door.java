package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.components.DoorHandler;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Button;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Color;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPSingleEvent;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;

public class Door extends GameObject
{
    public Door(int index, Vector2 position, PXPSingleEvent<MouseEvent> onClickEvent) {
        super("doorWrapper", new Component[] {
            new Canvas(RenderMode.WORLD)
        });

        this.transform = new RectTransform(position, new Vector3(), new Vector2(1,1), new Vector2(1000,1000));

        Image image = new Image(AssetManager.get("test", SpriteAsset.class)) {{
            color = new Color(255,255,255,255);
        }};

        this.setChildren(new GameObject[] {
            new GameObject("doorButton", new Component[] {
                new Button(InteractableTransition.COLOR) {{
                    targetImage = image;
                    // hoverSprite = AssetManager.get("test", SpriteAsset.class); TODO in case we want the doors to be buttons
                    onClick = onClickEvent;
                }},
                new DoorHandler(index),
                new BoxCollider(new Vector2(0,0), new Vector2(.5f, .15f)) {{
                    trigger = true;
                }}
            }, new GameObject[] {
                new GameObject("doorImage", new Component[] { image }) {{
                    transform = new RectTransform(
                        new Vector2(0,0),
                        new Vector3(0,0,0),
                        new Vector2(1,1),
                        new Vector2(1f, .3f)
                    );
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(0,0),
                    new Vector3(0,0,0),
                    new Vector2(1,1),
                    new Vector2(1f, .3f)
                );
            }}
        });
    }
}
