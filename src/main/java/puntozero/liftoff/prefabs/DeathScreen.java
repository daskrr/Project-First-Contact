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
import pxp.engine.data.event.PXPEvent;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.RenderMode;

public class DeathScreen extends GameObject
{
    public String text;

    public PXPEvent callback;

    public class DeathScreenHandler extends Component {
        @Override
        public void update() {
            if (Input.getKeyOnce(Key.ENTER)) {
                callback.invoke();
            }
        }
    }

    public DeathScreen(String text, PXPEvent onEnter) {
        super("deathScreen");

        this.text = text;
        this.callback = onEnter;

        this.setComponents(new Component[] {
            new Canvas(RenderMode.CAMERA),
            new DeathScreenHandler()
        });

        this.setChildren(new GameObject[] {
            new GameObject("background", new Component[] {
                new Image(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = Color.black();
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(1920, 1080),
                    Anchor.CENTER
                );
            }},
            new GameObject("text", new Component[] {
                new Text(text) {{
                    pivot = Pivot.CENTER;
                    color = Color.white();
                    fontSize = 20;
                    font = AssetManager.get("PressStart", FontAsset.class);
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(-1f, -1f),
                    Anchor.CENTER
                );
            }},
            new GameObject("enter", new Component[] {
                new Text("Press ENTER") {{
                    pivot = Pivot.CENTER;
                    color = Color.white();
                    fontSize = 16;
                    font = AssetManager.get("PressStart", FontAsset.class);
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(-100, -100),
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(-1f, -1f),
                    Anchor.BOTTOM_RIGHT
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
