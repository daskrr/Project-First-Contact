package puntozero.liftoff.prefabs;

import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.Color;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.RenderMode;

public class Monologue extends GameObject
{

    public Monologue(String text) {
        super("monologueBox");

        this.setComponents(new Component[] {
            new Canvas(RenderMode.CAMERA)
        });

        this.setChildren(new GameObject[] {
            new GameObject("monologueBoxWrapper", new Component[] {
                new Image(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = new Color(69, 73, 82, 210);
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(0, 300f),
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(800, 150)
                );
            }},
            // had to place this outside of the wrapper, as the text makes the background transparent????
            new GameObject("monologueBoxText", new Component[] {
                new Text(text) {{
                    pivot = Pivot.CENTER;
                    color = Color.white();
                    fontSize = 17;
                    font = AssetManager.get("PressStart", FontAsset.class);
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(0, 300f),
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(700f, -1f)
                );
            }}
        });
    }

    public void remove(float time) {
        scene.context.runLater(this, time, () -> {
            if (this.isDestroyed) return;

            this.destroy();
        });
    }
}
