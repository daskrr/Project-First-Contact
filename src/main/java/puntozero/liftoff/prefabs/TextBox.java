package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Button;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.*;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPSingleEvent;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;
import pxp.logging.Debug;

public class TextBox extends GameObject
{
    private String text;
    private int size;
    private Vector2 boxSize;
    private Color boxColor;
    private FontAsset fontAsset;
    private Color textColor;
    private Vector2 textBoxSize;

    private boolean hasOptions = false;
    private String option1;
    private String option2;

    private PXPSingleEvent<MouseEvent> optionCallback1;
    private PXPSingleEvent<MouseEvent> optionCallback2;


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

        this.text = text;
        this.size = size;
        this.boxSize = boxSize;
        this.boxColor = boxColor;
        this.fontAsset = fontAsset;
        this.textColor = textColor;
        this.textBoxSize = textBoxSize;

        this.setComponents(new Component[] {
            new Canvas(RenderMode.CAMERA)
        });

        this.setChildren(createChildren());
    }

    private GameObject[] createChildren() {
        GameObject[] gos = new GameObject[] {
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
        };

        if (!hasOptions)
            return gos;

        GameObject[] extra = new GameObject[] {
            new GameObject("button1", new Component[] {
                new Button(InteractableTransition.COLOR) {{
                    normalColor = Color.white();
                    onClick = optionCallback1;
                }}
            }, new GameObject[] {
                new GameObject("option1", new Component[] {
                    new Text(option1) {{
                        pivot = Pivot.CENTER;
                        color = textColor;
                        fontSize = size - 2;
                        font = fontAsset;
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(0, 0f),
                        new Vector3(),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f)
                    );
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(-200f,0),
                    new Vector3(0,0,0),
                    new Vector2(1,1),
                    new Vector2(300f, 50f)
                );
            }},
            new GameObject("button2", new Component[] {
                new Button(InteractableTransition.COLOR) {{
                    normalColor = Color.white();
                    onClick = optionCallback2;
                }}
            }, new GameObject[] {
                new GameObject("option2", new Component[] {
                    new Text(option2) {{
                        pivot = Pivot.CENTER;
                        color = textColor;
                        fontSize = size - 2;
                        font = fontAsset;
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(0, 0f),
                        new Vector3(),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f)
                    );
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(200f,0),
                    new Vector3(0,0,0),
                    new Vector2(1,1),
                    new Vector2(300f, 50f)
                );
            }}
        };

        GameObject[] allGos = new GameObject[gos.length + extra.length];
        System.arraycopy(gos, 0, allGos, 0, gos.length);
        System.arraycopy(extra, 0, allGos, gos.length, extra.length);

//        Debug.log(allGos);

        return allGos;
    }

    public void setOptions(String option1, String option2) {
        this.hasOptions = true;
        this.option1 = option1;
        this.option2 = option2;
    }

    public void setOptionCallbacks(PXPSingleEvent<MouseEvent> option1, PXPSingleEvent<MouseEvent> option2) {
        this.optionCallback1 = option1;
        this.optionCallback2 = option2;
    }

    /**
     * Only to be used before this game object is added to the game.
     */
    public void refresh() {
        this.setChildren(createChildren());
    }

    public void remove(float time) {
        scene.context.runLater(this, time, () -> {
            if (this.isDestroyed) return;

            this.destroy();
        });
    }
}
