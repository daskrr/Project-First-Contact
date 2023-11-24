package puntozero.liftoff.prefabs;

import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.*;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.RenderMode;

public class TextBox extends GameObject
{
    public TextBox(String text) {
        this(text, 20);
    }
    public TextBox(String text, int size) {
        this(text, size, new Vector2(400,200));
    }
    public TextBox(String text, int size, Vector2 boxSize) {
        this(text, size, boxSize, new Color(69, 73, 82, 210));
    }
    public TextBox(String text, int size, Vector2 boxSize, Color boxColor) {
        this(text, size, boxSize, boxColor, AssetManager.getDefaultFont());
    }
    public TextBox(String text, int size, Vector2 boxSize, Color boxColor, FontAsset fontAsset) {
        this(text, size, boxSize, boxColor, fontAsset, Color.black());
    }
    public TextBox(String text, int size, Vector2 boxSize, Color boxColor, FontAsset fontAsset, Color textColor) {
        this(text, size, boxSize, boxColor, fontAsset, textColor, new Vector2(-1f, -1f));
    }

    public TextBox(String text, int size, Vector2 boxSize, Color boxColor, FontAsset fontAsset, Color textColor, Vector2 textBoxSize) {
        super("textBoxCanvas");

        this.setComponents(new Component[] {
            new Canvas(RenderMode.CAMERA)
        });

        this.setChildren(new GameObject[] {
            new GameObject("textBoxWrapper", new Component[] {
                new Image(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = boxColor;
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(0, -100f),
                    new Vector3(),
                    new Vector2(1,1),
                    boxSize
                );
            }},
            // had to place this outside of the wrapper, as the text makes the background transparent????
            new GameObject("textBoxText", new Component[] {
                new Text(text) {{
                    pivot = Pivot.CENTER;
                    color = textColor;
                    fontSize = size;
                    font = fontAsset;
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(0, -100f),
                    new Vector3(),
                    new Vector2(1,1),
                    textBoxSize
                );
            }}
        });
    }

    public void remove(float time) {
        scene.context.runLater(this, time, () -> {
            if (this.isDestroyed) return;

            this.isActive = false;
        });
    }
}
