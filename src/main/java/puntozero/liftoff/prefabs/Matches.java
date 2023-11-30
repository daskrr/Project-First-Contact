package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.scenes.minigame.CraftingScene;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.pointer.PointerHandlerDrag;
import pxp.engine.core.component.pointer.PointerHandlerMouse;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;
import pxp.logging.Debug;

public class Matches extends GameObject {
    public static final float SIZE = 1.4f;
    private class MatchesHandler extends Component implements PointerHandlerMouse {

        @Override
        public void mouseClick(MouseEvent mouseEvent) {

            if (PotionSlot.correctPotions != 3 || NapkinSlot.correctNapkin != 1) return;

            ctx().runLater(this.gameObject, 1f, () -> {
                if (PotionSlot.potions.get(0).type == Potion.Type.BLUE &&
                PotionSlot.potions.get(1).type == Potion.Type.RED &&
                PotionSlot.potions.get(2).type == Potion.Type.GREEN) {
                    ctx().getCurrentScene().addGameObject(new Molotov(new Vector2(0,0), new Vector2(1,1)));
                    ctx().getCurrentScene().addGameObject(new GameObject("bombPot", new Component[] {
                            new SpriteRenderer(AssetManager.get("pot", SpriteAsset.class))
                    }));
                }
                else {
                    // if potion mix wasn't in the right order you die
                    DeathScreen deathScreen = new DeathScreen("The bomb exploded and you died...", new PXPEvent() {
                        @Override
                        public void invoke() {
                            SceneStateManager.getInstance().reset();
                        }
                    });

                    ctx().getCurrentScene().getGameObject("canvas").destroy();

                    instantiate(deathScreen);
                }
            });
        }

        @Override
        public void mouseScroll(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseDown(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseUp(MouseEvent mouseEvent) {

        }

        @Override
        public boolean checkOverlap(Vector2 mousePos) {
            Vector2 worldPos = this.gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            return rectTransform().checkOverlap(worldPos, Pivot.CENTER);
        }

        @Override
        public void setHovering(boolean b) {

        }

        @Override
        public boolean isHovering() {
            return false;
        }
    }

    public Matches(Vector2 position, Vector2 scale) {
        super("matches");

        this.setComponents(new Component[] {
            new SpriteRenderer(AssetManager.get("matchBoxTable", SpriteAsset.class)){{
                setOrderInLayer(2);
            }},
            new MatchesHandler(),
            new BoxCollider(new Vector2(0,0), scale) {{
                trigger = true;
            }}
        });

        transform = new Transform(position, scale);
    }
}
