package puntozero.liftoff.scenes.minigame;

import puntozero.liftoff.prefabs.Interactable;
import puntozero.liftoff.prefabs.TextBox;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.Color;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.RenderMode;

import java.util.ArrayList;
import java.util.List;

public class CraftingScene extends Scene {
    public CraftingScene(){
        super();

        //TODO: import new images from google drive

        //TODO: replace assets to Liftoff
        AssetManager.createSprite("workbench", "crafting/workbench.png", 16);

        AssetManager.createSprite("openBlueBook", "crafting/book_blue_open.png", 16);
        AssetManager.createSprite("openGreenBook", "crafting/book_green_open.png", 16);
        AssetManager.createSprite("openOrangeBook", "crafting/book_orange_open.png", 16);
        AssetManager.createSprite("openPinkBook", "crafting/book_pink_open.png", 16);
        AssetManager.createSprite("openPurpleBook", "crafting/book_purple_open.png", 16);
        AssetManager.createSprite("openRedBook", "crafting/book_red_open.png", 16);

        AssetManager.createSprite("blueBottle", "crafting/bottle_blue.png", 16);
        AssetManager.createSprite("greenBottle", "crafting/bottle_green.png", 16);
        AssetManager.createSprite("redBottle", "crafting/bottle_red.png", 16);

        AssetManager.createSprite("matchBoxTable", "crafting/matchbox_table.png", 16);
        AssetManager.createSprite("molotov", "crafting/molotov.png", 16);
        AssetManager.createSprite("napkinTable", "crafting/napkin.png", 16);

        AssetManager.createSprite("bluePot", "crafting/blue_pot.png", 16);
        AssetManager.createSprite("greenPot", "crafting/green_pot.png", 16);
        AssetManager.createSprite("redPot", "crafting/red_pot.png", 16);
        AssetManager.createSprite("pot", "crafting/pot.png", 16);

        this.setGameObjects(new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[] {
                    new Camera(7f)
            }),
            () -> new GameObject("background", new Component[] {
                new SpriteRenderer(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = new Color(30,32,36);
                    setOrderInLayer(0);
                }}
            }) {{
                transform = new Transform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(6f, 5f)
                );
            }},
            () -> new GameObject("canvas", new Component[] {
                new Canvas(RenderMode.CAMERA)
            }, new GameObject[] {
                new GameObject("title", new Component[]{
                    new Text("Mix in the right order") {{
                        color = Color.white();
                        fontSize = 17;
                        font = AssetManager.get("PressStart", FontAsset.class);
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(0, -240f),
                        new Vector3(0,0,0),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f),
                        Anchor.CENTER
                    );
                }},
            }),
            () -> new GameObject("workbench", new Component[] {
                    new SpriteRenderer(AssetManager.get("workbench", SpriteAsset.class))
            }) {{
                transform = new Transform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(0.75f, 0.75f)
                );
            }},
            () -> new GameObject("pot", new Component[] {
                    new SpriteRenderer(AssetManager.get("pot", SpriteAsset.class))
            }),
            () -> new Interactable("potionBlue",
                    new Vector2(),
                    new Vector2(1f, 1f),
                    new Image(AssetManager.get("blueBottle", SpriteAsset.class)),
                    takePotion("potionBlue"))
            {{
                transform = new Transform(
                    new Vector2(-4.8f, -2.4f),
                    new Vector2(0.4f, 0.4f)
                );
            }},
            () -> new Interactable("potionGreen",
                    new Vector2(),
                    new Vector2(1f, 1f),
                    new Image(AssetManager.get("greenBottle", SpriteAsset.class)),
                    takePotion("potionGreen"))
            {{
                transform = new Transform(
                    new Vector2(-3.5f, -2.4f),
                    new Vector2(0.4f, 0.4f)
                );
            }},
            () -> new Interactable("potionRed",
                    new Vector2(),
                    new Vector2(1f, 1f),
                    new Image(AssetManager.get("redBottle", SpriteAsset.class)),
                    takePotion("potionRed"))
            {{
                transform = new Transform(
                    new Vector2(-2.2f, -2.4f),
                    new Vector2(0.4f, 0.4f)
                );
            }},
            //TODO: based on the book in your inventory -> I don't know how to do that
            () -> new GameObject("craftingBook", new Component[] {
                    new SpriteRenderer(AssetManager.get("openOrangeBook", SpriteAsset.class))
            }) {{
               transform = new Transform(
                   new Vector2(-3.5f, 2f),
                   new Vector2(0.7f, 0.7f)
               );
            }},
            () -> new GameObject("napkin", new Component[] {
                    new SpriteRenderer(AssetManager.get("napkinTable", SpriteAsset.class))
            }) {{
                transform = new Transform(
                        new Vector2(3f, -2f),
                        new Vector2(0.7f, 0.7f)
                );
            }},
            () -> new GameObject("matches", new Component[] {
                    new SpriteRenderer(AssetManager.get("matchBoxTable", SpriteAsset.class))
            }) {{
                transform = new Transform(
                        new Vector2(4f, 0f),
                        new Vector2(0.3f, 0.3f)
                );
            }},
        });
    }

    private PXPEvent takePotion(String object) {
        return new PXPEvent(){
            @Override
            public void invoke() {
                //TODO: Add potions to inventory (uncomment)
                if (object.equals("potionRed")) {
                    addGameObject(new GameObject("potRed", new Component[] {
                            new SpriteRenderer(AssetManager.get("redPot", SpriteAsset.class))
                    }));
                }
                else if (object.equals("potionBlue")) {
                    addGameObject(new GameObject("potBlue", new Component[] {
                            new SpriteRenderer(AssetManager.get("bluePot", SpriteAsset.class))
                    }));
                }
                else if (object.equals("potionGreen")) {
                    addGameObject(new GameObject("potGreen", new Component[] {
                            new SpriteRenderer(AssetManager.get("greenPot", SpriteAsset.class))
                    }));
                }

                getGameObject(object).destroy();
            }
        };
    }
}
