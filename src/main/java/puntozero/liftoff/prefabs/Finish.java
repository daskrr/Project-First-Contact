package puntozero.liftoff.prefabs;

import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.manager.SoundManager;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.*;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SoundAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.RenderMode;

public class Finish extends GameObject
{

    public class FinishScreenHandler extends Component {
        @Override
        public void start() {
            AssetManager.get("backgroundSound", SoundAsset.class).getSound().stop();
            SoundManager.playSound("sound11");
        }
        @Override
        public void update() {
            if (Input.getKeyOnce(Key.ENTER)) {
                SceneStateManager.getInstance().reset();
                AssetManager.get("backgroundSound", SoundAsset.class).getSound().loop();
            }
        }
    }

    public Finish() {
        super("finishScreen");

        this.setComponents(new Component[] {
            new Canvas(RenderMode.CAMERA),
            new FinishScreenHandler()
        });

        this.setChildren(new GameObject[] {
                new GameObject("background", new Component[] {
                    new Image(AssetManager.get("finish", SpriteAsset.class))
                }) {{
                    transform = new RectTransform(
                        new Vector2(),
                        new Vector3(),
                        new Vector2(1,1),
                        new Vector2(1920, 1080),
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
                            new Vector2(-200, -100),
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
