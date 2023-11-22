package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.data.SceneIndex;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Collider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Button;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPSingleEvent;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;

public class Exit extends GameObject
{
    private static class ExitHandler extends Component {
        private final Image renderer;
        public ExitHandler(Image renderer) {
             this.renderer = renderer;
        }

        @Override
        public void triggerEnter(Collider col) {
            if (!col.gameObject.name.equals("levelPlayerTrigger")) return;

            renderer.color.setA(0);
        }

        @Override
        public void triggerExit(Collider col) {
            if (!col.gameObject.name.equals("levelPlayerTrigger")) return;

            renderer.color.setA(255);
        }
    }

    public Exit() {
        super("exitWrapper");

        PXPSingleEvent<MouseEvent> onClickEvent = new PXPSingleEvent<>() {
            @Override
            public void invoke(MouseEvent mouseEvent) {
                // change scene
                scene.context.setScene(SceneIndex.MAP.index);
            }
        };

        Image image = new Image(AssetManager.get("exit", SpriteAsset.class));

        this.setComponents(new Component[] {
            new Canvas(RenderMode.WORLD)
        });

        this.setChildren(new GameObject[] {
            new GameObject("exitButton", new Component[] {
                new Button(InteractableTransition.COLOR) {{
                    targetImage = image;
                    onClick = onClickEvent;
                }},
                new ExitHandler(image),
                new BoxCollider(new Vector2(), new Vector2(.5f, .5f)) {{
                    trigger = true;
                }}
            }, new GameObject[] {
                new GameObject("exitImage", new Component[] { image }) {{
                    transform = new RectTransform(
                        new Vector2(0,0),
                        new Vector3(0,0,0),
                        new Vector2(1,1),
                        new Vector2(1f, 1f)
                    );
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(0,0),
                    new Vector3(0,0,0),
                    new Vector2(1,1),
                    new Vector2(1f, 1f)
                );
            }}
        });
    }
}
