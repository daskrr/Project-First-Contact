package puntozero.liftoff.scenes;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.inventory.ItemRegistry;
import puntozero.liftoff.manager.SceneState;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.manager.SoundManager;
import puntozero.liftoff.prefabs.*;
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
import pxp.engine.data.event.PXPEvent;
import pxp.engine.data.event.PXPSingleEvent;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;

import java.util.ArrayList;
import java.util.List;

public class StorageRoomScene extends Scene {
    public static class StorageRoomSceneState implements SceneState {
        private boolean gameIntroFinished = false;
        public boolean allPotionsCollected = false;
        public boolean redPotion = false;
        public boolean greenPotion = false;
        public boolean bluePotion = false;
        public boolean craftingTable = false;
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
                    transform = new Transform(new Vector2(0f, 4.5f));
                }});
                add(PlayerInventory::create);
            }};

            if (!allPotionsCollected) {
                if (!greenPotion)
                    suppliers.add(() -> new Interactable("potionGreen",
                            new Vector2(),
                            new Vector2(1.6f, 1.6f),
                            new Image(AssetManager.get("potionGreen", SpriteAsset.class)),
                            scene.takePotion("potionGreen"))
                    {{
                        transform = new Transform(new Vector2(-2.7f, 2f));
                    }});
                if (!bluePotion)
                    suppliers.add(() -> new Interactable("potionBlue",
                            new Vector2(),
                            new Vector2(1.6f, 1.6f),
                            new Image(AssetManager.get("potionBlue", SpriteAsset.class)),
                            scene.takePotion("potionBlue"))
                    {{
                        transform = new Transform(new Vector2(-10.2f, 6.3f));
                    }});
                if (!redPotion)
                    suppliers.add(() -> new Interactable("potionRed",
                            new Vector2(),
                            new Vector2(1.6f, 1.6f),
                            new Image(AssetManager.get("potionRed", SpriteAsset.class)),
                            scene.takePotion("potionRed"))
                    {{
                        transform = new Transform(new Vector2(-7.9f, 4.15f));
                    }});
            }

            suppliers.add(() -> new GameObject("light", new Component[] {
                new SpriteRenderer(AssetManager.get("storageLight", SpriteAsset.class)) {{
                    setSortingLayer("Light");
                }}
            }));
            suppliers.add(() -> new GameObject("foreground", new Component[] {
                new SpriteRenderer(AssetManager.get("storageForeground", SpriteAsset.class)) {{
                    setSortingLayer("Foreground");
                }}
            }));

            return suppliers.toArray(new GameObjectSupplier[0]);
        }
    }

    private PXPEvent openMinigame() {
        return new PXPEvent() {
            @Override
            public void invoke() {
                getGameObject("craftingTable").destroy(); // destroy since we don't want to be able to interact again

                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
                levelPlayer.controller.destination = levelPlayer.transform.position;

                // save position
                SceneStateManager.getInstance().levelPlayerPosition = levelPlayer.transform.position;

                // change scene to minigame scene
                context.setScene(SceneIndex.CRAFTING.index);
            }
        };
    }

    private PXPEvent takePotion(String object) {
        return new PXPEvent() {
            @Override
            public void invoke() {
                if (object.equals("potionRed")) {
                    PlayerInventory.addItem(ItemRegistry.POTION_RED.item);
                    state.redPotion = true;
                }
                else if (object.equals("potionBlue")) {
                    PlayerInventory.addItem(ItemRegistry.POTION_BLUE.item);
                    state.bluePotion = true;
                }
                else if (object.equals("potionGreen")) {
                    PlayerInventory.addItem(ItemRegistry.POTION_GREEN.item);
                    state.greenPotion = true;
                }

                getGameObject(object).destroy();

                //TODO: change this
                //TODO: if all the items are collected then you get this message.
                if (state.redPotion && state.greenPotion && state.bluePotion){

                    state.allPotionsCollected = true;
                }
            }
        };
    }

    private StorageRoomSceneState state;
    public StorageRoomScene(){
        super();

        // this only executes once when the scene is created!
        this.state = SceneStateManager.getInstance().get(this, new StorageRoomSceneState());
        this.setGameObjects(state.restoreSceneState(this));
    }

    @Override
    public void load() {
        // we need to reset the game object suppliers when the scene is loaded again, in order to preserve state
        this.state = SceneStateManager.getInstance().get(this, new StorageRoomSceneState());
        this.setGameObjects(state.restoreSceneState(this));
        super.load();

        if (!state.gameIntroFinished) {
            showIntroDialogue();

            LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
            levelPlayer.controller.setLocked(true);
        }
    }

    @Override
    protected void render() {
        super.render();

        if (state.allPotionsCollected && state.gameIntroFinished && hasAllItems() && !state.craftingTable){
            Interactable in = new Interactable("craftingTable",
                    new Vector2(),
                    new Vector2(5f, 2.5f),
                    new Image(AssetManager.get("craftingTable", SpriteAsset.class)),
                    this.openMinigame())
            {{
                transform = new Transform(new Vector2(5.3f, 5f));
            }};

            addGameObject(in);
            state.craftingTable = true;
        }
    }

    private final float textShowTime = 5f;
    private void showIntroDialogue() {
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
                this.context.runLater(t, i * textShowTime, () -> {
                    SoundManager.playSound("sound1");
                    addGameObject(readNote());
                    state.gameIntroFinished = true;
                });
            }
            else{
                this.context.runLater(t, i * textShowTime, () -> {
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
                            PlayerInventory.addItem(ItemRegistry.NOTE.item);

                            getGameObject("bigNoteCanvas").destroy();

                            // show dialogue of missing part of note
                            TextBox textBox1 = new TextBox(
                                    "Oh a part of the note is missing. I should go find the missing piece of information.",
                                    17,
                                    new Vector2(600,200),
                                    new Color(30, 32, 36, 240),
                                    AssetManager.get("PressStart", FontAsset.class),
                                    Color.white(),
                                    new Vector2(550, -1)
                            );
                            addGameObject(textBox1);
                            textBox1.remove(textShowTime);


                            TextBox textBox2= new TextBox(
                                    "To see your inventory press I. To take a look at the note again press N.",
                                    17,
                                    new Vector2(600,200),
                                    new Color(30, 32, 36, 240),
                                    AssetManager.get("PressStart", FontAsset.class),
                                    Color.white(),
                                    new Vector2(550, -1)
                            );
                            ctx().runLater(textBox2, textShowTime, () -> {
                                addGameObject(textBox2);
                                textBox2.remove(textShowTime);
                            });

                            TextBox textBox3= new TextBox(
                                    "Use left click to move and right click to interact",
                                    17,
                                    new Vector2(600,200),
                                    new Color(30, 32, 36, 240),
                                    AssetManager.get("PressStart", FontAsset.class),
                                    Color.white(),
                                    new Vector2(550, -1)
                            );
                            ctx().runLater(textBox3, textShowTime*2, () -> {
                                addGameObject(textBox3);
                                textBox3.remove(textShowTime);


                                LevelPlayer levelPlayer = (LevelPlayer) getGameObject("levelPlayer");
                                levelPlayer.controller.setLocked(false);
                            });
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

    private boolean hasAllItems(){
        if (PlayerInventory.hasItem("matchBox") &&
            PlayerInventory.hasItem("pot") &&
            PlayerInventory.hasItem("napkin")){
            return true;
        }
        return false;
    }
}