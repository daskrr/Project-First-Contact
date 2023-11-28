package puntozero.liftoff.scenes;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.manager.SceneState;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.prefabs.Exit;
import puntozero.liftoff.prefabs.LevelPlayer;
import puntozero.liftoff.prefabs.TextBox;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
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

import java.util.ArrayList;
import java.util.List;

public class StorageRoomScene extends Scene {
    public static class StorageRoomSceneState implements SceneState {
        private boolean gameIntroFinished = false;
        public boolean hasAllItems = false;
        @Override
        public GameObjectSupplier[] restoreSceneState(Scene s) {
            StorageRoomScene scene = (StorageRoomScene) s;

            List<GameObjectSupplier> suppliers = new ArrayList<>() {{
                add(() -> new GameObject("camera", new Component[] {
                        new Camera(8f)
                }));
                add(() -> new GameObject("background", new Component[] {
                    new SpriteRenderer(AssetManager.get("storageBackground", SpriteAsset.class))
                }));
                add(() -> new Exit(new Vector2(13f, 0f)));
                add(() -> new LevelPlayer() {{
                    transform = new Transform(new Vector2(0f, 5f));
                }});
                add(PlayerInventory::create);
            }};

            return suppliers.toArray(new GameObjectSupplier[0]);
        }
    }

    private final StorageRoomSceneState state;
    public StorageRoomScene(){
        super();

        // this only executes once when the scene is created!
        this.state = SceneStateManager.getInstance().get(this, new StorageRoomSceneState());
        this.setGameObjects(state.restoreSceneState(this));

        //TODO: replace assets to Liftoff
        AssetManager.createSprite("storageBackground", "storageRoom/background.png", 16);
        AssetManager.createSprite("noteBig", "storageRoom/note_big.png", 16);
        AssetManager.createSprite("noteSmall", "storageRoom/note_small.png", 16);
    }

    @Override
    public void load() {
        // we need to reset the game object suppliers when the scene is loaded again, in order to preserve state
        this.setGameObjects(state.restoreSceneState(this));
        super.load();

        if (!state.gameIntroFinished) {
            showDialogue();
            //addGameObject(readNote());

            LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
            //TODO: player can't move while intro dialogue is showing
        }

        if (state.hasAllItems){
            //TODO: you can now interact with the table
        }
    }

    private final float textShowTime = 6f;
    private void showDialogue() {
        List<TextBox> texts = new ArrayList<>() {{
            add(new TextBox(
                "I can’t take it any longer, I have to leave.",
                17,
                new Vector2(600,200),
                new Color(30, 32, 36, 240),
                AssetManager.get("PressStart", FontAsset.class),
                Color.white(),
                new Vector2(550, -1)
            ));
            add(new TextBox(
                "I wish I would have helped Anie escape, I shouldn’t have chickened out.",
                17,
                new Vector2(600,200),
                new Color(30, 32, 36, 240),
                AssetManager.get("PressStart", FontAsset.class),
                Color.white(),
                new Vector2(550, -1)
            ));
            add(new TextBox(
                "And now it’s even worse than before, Anie disappeared as well.",
                17,
                new Vector2(600,200),
                new Color(30, 32, 36, 240),
                AssetManager.get("PressStart", FontAsset.class),
                Color.white(),
                new Vector2(550, -1)
            ));
            add(new TextBox(
                "Just like the other kids that ‘misbehaved’.",
                17,
                new Vector2(600,200),
                new Color(30, 32, 36, 240),
                AssetManager.get("PressStart", FontAsset.class),
                Color.white(),
                new Vector2(550, -1)
            ));
            add(new TextBox(
                "I don’t have another choice, I have to escape before they make me disappear as well.",
                17,
                new Vector2(600,200),
                new Color(30, 32, 36, 240),
                AssetManager.get("PressStart", FontAsset.class),
                Color.white(),
                new Vector2(550, -1)
            ));
            add(new TextBox(
                "I still have the escape plan Anie and I made but it’s upstairs… But I still have this note, maybe it can help me get upstairs.",
                17,
                new Vector2(600,200),
                new Color(30, 32, 36, 240),
                AssetManager.get("PressStart", FontAsset.class),
                Color.white(),
                new Vector2(550, -1)
            ));
        }};

        for (int i = 0; i < texts.size(); i++) {
            TextBox t = texts.get(i);
            if (i == 0){
                addGameObject(t);
                t.remove(textShowTime);
            }
            else if (i+1 >= texts.size()){
                this.context.runLater(t, i*textShowTime, () -> {
                    addGameObject(readNote());
                    state.gameIntroFinished = true;
                });
            }
            else{
                this.context.runLater(t, i*textShowTime, () -> {
                    addGameObject(t);
                    t.remove(textShowTime);
                });
            }
        }
    }

    private GameObject readNote(){
        return new GameObject("bigNoteCanvas", new Component[] {
            new Canvas(RenderMode.CAMERA)
        }, new GameObject[] {
            new GameObject("bigNote", new Component[] {
                new Image(AssetManager.get("noteBig", SpriteAsset.class))
            }) {{
                transform = new RectTransform(
                    new Vector2(),
                    new Vector2(700, 580)
                );
            }},
            new GameObject("button", new Component[] {
                new Button(InteractableTransition.COLOR) {{
                    onClick = new PXPSingleEvent<>() {
                        @Override
                        public void invoke(MouseEvent mouseEvent) {
                            //TODO: Add note to inventory

                            getGameObject("bigNoteCanvas").destroy();
                        }
                    };
                    color = Color.white();
                    normalColor = Color.white();
                    hoverColor = new Color(240,240,240,255);
                    pressedColor = new Color(230,230,230,255);
                }}
            }, new GameObject[] {
                new GameObject("buttonText", new Component[] {
                    new Text("Close", Pivot.CENTER) {{
                        font = AssetManager.get("PressStart", FontAsset.class);
                        fontSize = 20;
                        color = Color.white();
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(0, 0),
                        new Vector3(),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f),
                        Anchor.CENTER_LEFT
                    );
                }}
            }) {{
                transform = new RectTransform(
                    new Vector2(510, 225),
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(300, 50)
                );
            }}
        });
    }
}