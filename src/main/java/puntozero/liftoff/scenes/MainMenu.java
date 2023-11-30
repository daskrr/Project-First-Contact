package puntozero.liftoff.scenes;

import processing.event.MouseEvent;
import puntozero.liftoff.data.SceneIndex;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.component.Camera;
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
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;

public class MainMenu extends Scene
{
    public MainMenu() {
        super();

        this.setGameObjects(new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[] {
                new Camera(7f)
            }),
            () -> new GameObject("menu", new Component[] {
                new Canvas(RenderMode.CAMERA)
            }, new GameObject[] {
                new GameObject("menuBackground", new Component[] {
                    new Image(AssetManager.get("mainMenu", SpriteAsset.class))
                }) {{
                    transform = new RectTransform(
                        new Vector2(),
                        new Vector3(),
                        new Vector2(1,1),
                        context.windowSize,
                        Anchor.CENTER
                    );
                }},
                new GameObject("buttonsWrapper", new Component[] {}, new GameObject[] {
                    createButton("NEW GAME", new Vector2(0, 0), new PXPSingleEvent<>() {
                        @Override
                        public void invoke(MouseEvent mouseEvent) {
                            // activate player inventory
                            //context.setScene(SceneIndex.MAP.index);
                            context.setScene(SceneIndex.STORAGE_ROOM.index);
                        }
                    }),
                    createButton("LOAD GAME", new Vector2(0, 70), new PXPSingleEvent<>() { @Override public void invoke(MouseEvent mouseEvent) { } }),
                    createButton("OPTIONS", new Vector2(0, 140), new PXPSingleEvent<>() { @Override public void invoke(MouseEvent mouseEvent) { } }),
                    createButton("EXIT", new Vector2(0, 210), new PXPSingleEvent<>() {
                        @Override
                        public void invoke(MouseEvent mouseEvent) {
                            context.exit();
                        }
                    })
                }) {{
                    transform = new RectTransform(
                        new Vector2(330f, 220f),
                        new Vector3(),
                        new Vector2(1,1),
                        new Vector2(300f, 400f),
                        Anchor.CENTER_LEFT
                    );
                }}
            })
        });
    }

    private GameObject createButton(String text, Vector2 position, PXPSingleEvent<MouseEvent> onClickEvent) {
        Text textUI = new Text(text, Pivot.CENTER) {{
            font = AssetManager.get("PressStart", FontAsset.class);
            fontSize = 30;
            color = Color.white();
        }};
        return new GameObject("button", new Component[] {
            new Button(InteractableTransition.COLOR) {{
                onClick = onClickEvent;
                color = Color.white();
                targetGraphic = textUI;
                normalColor = Color.white();
                hoverColor = new Color(199,199,199,255);
                pressedColor = new Color(170,170,170,255);
            }}
        }, new GameObject[] {
            new GameObject("buttonText", new Component[] {
                textUI
            }) {{
                transform = new RectTransform(
                    new Vector2(0, 40f), // for some reason it's not centering properly, fuck if I know
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(-1f, -1f),
                    Anchor.CENTER_LEFT
                );
            }}
        }) {{
            transform = new RectTransform(
                position,
                new Vector3(),
                new Vector2(1,1),
                new Vector2(300, 50),
                Anchor.TOP_CENTER
            );
        }};
    }
}
