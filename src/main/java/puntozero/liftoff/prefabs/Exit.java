package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.manager.SceneStateManager;
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
import pxp.logging.Debug;

public class Exit extends GameObject
{
    private static class ExitHandler extends Component {
        private final GameObject exitButton;
        private final Image renderer;
        public ExitHandler(GameObject exitButton, Image renderer) {
            this.exitButton = exitButton;
            this.renderer = renderer;
        }

        @Override
        public void triggerEnter(Collider col) {
            if (!col.gameObject.name.equals("levelPlayer")) return;

            renderer.color.setA(255);
            this.exitButton.isActive = true;
        }

        @Override
        public void triggerExit(Collider col) {
            if (!col.gameObject.name.equals("levelPlayer")) return;

            renderer.color.setA(0);
            this.exitButton.isActive = false;
        }
    }

    public Exit(Vector2 position) {
        super("exitWrapper");

        Image image = new Image(AssetManager.get("exit", SpriteAsset.class)) {{
            color.setA(0);
        }};
        GameObject exitButton = createButton(image);

        this.setComponents(new Component[] {
            new Canvas(RenderMode.WORLD),
            new ExitHandler(exitButton, image),
            new BoxCollider(new Vector2(), new Vector2(1.5f, 8f)) {{
                trigger = true;
            }}
        });

        this.setChildren(new GameObject[] {
            exitButton
        });

        this.transform.position = position;
    }

    private GameObject createButton(Image image) {
        PXPSingleEvent<MouseEvent> onClickEvent = new PXPSingleEvent<>() {
            @Override
            public void invoke(MouseEvent mouseEvent) {
            // save position
            SceneStateManager.getInstance().levelPlayerPosition = scene.getGameObject("levelPlayer").transform.position;

            // change scene
            scene.context.setScene(SceneIndex.MAP.index);
            }
        };

        return new GameObject("exitButton", new Component[] {
                new Button(InteractableTransition.COLOR) {{
                    targetImage = image;
                    onClick = onClickEvent;
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
                isActive = false;
            }};
    }
}
