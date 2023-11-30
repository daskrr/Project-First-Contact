package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.scenes.minigame.CraftingScene;
import pxp.engine.core.GameObject;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.pointer.PointerHandlerDrag;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.logging.Debug;

public class Potion extends GameObject
{
    public static final float SIZE = 1.4f;
    private static int draggingPotion = -1;

    public enum Type {
        BLUE(AssetManager.get("blueBottle", SpriteAsset.class)),
        GREEN(AssetManager.get("greenBottle", SpriteAsset.class)),
        RED(AssetManager.get("redBottle", SpriteAsset.class));

        public final SpriteAsset sprite;
        Type(SpriteAsset sprite) {
            this.sprite = sprite;
        }
    }

    private class PotionHandler extends Component implements PointerHandlerDrag {
        private boolean isDragging = false;
        private Vector2 initialDistance = new Vector2();

        @Override
        public void setDragging(boolean b) {
            if (draggingPotion != this.hashCode() && draggingPotion != -1) return;

            this.isDragging = b;
        }

        @Override
        public boolean isDragging() {
            if (draggingPotion != this.hashCode() && draggingPotion != -1) return false;

            return this.isDragging;
        }

        @Override
        public void mouseDrag(MouseEvent mouseEvent) {
            if (draggingPotion != this.hashCode() && draggingPotion != -1) return;

            Vector2 mousePos = new Vector2(mouseEvent.getX(), mouseEvent.getY());
            mousePos = gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            mousePos = transform().worldToLocal(mousePos);

            mousePos.subtract(initialDistance);

            transform.position = transform().localToWorld(mousePos);
        }

        @Override
        public void mouseDragStart(MouseEvent mouseEvent) {
            if (draggingPotion != this.hashCode() && draggingPotion != -1) return;

            draggingPotion = this.hashCode();

            Vector2 mousePos = new Vector2(mouseEvent.getX(), mouseEvent.getY());
            mousePos = gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            mousePos = transform().worldToLocal(mousePos);
            mousePos = transform().localToWorld(mousePos);

            this.initialDistance = Vector2.subtract(mousePos, transform().position);
        }

        @Override
        public void mouseDragStop(MouseEvent mouseEvent) {
            draggingPotion = -1;

            checkPotions();
        }

        private void checkPotions() {
            if (PotionSlot.potionsInPot > 0){
                this.gameObject.isActive = false;
                if (PotionSlot.potions.getLast().type == Type.RED) {
                    instantiate(new GameObject("potRed", new Component[] {
                            new SpriteRenderer(AssetManager.get("redPot", SpriteAsset.class))
                    }));
                }
                else if (PotionSlot.potions.getLast().type == Type.GREEN) {
                    instantiate(new GameObject("potGreen", new Component[] {
                            new SpriteRenderer(AssetManager.get("greenPot", SpriteAsset.class))
                    }));
                }
                else if (PotionSlot.potions.getLast().type == Type.BLUE) {
                    instantiate(new GameObject("potBlue", new Component[] {
                            new SpriteRenderer(AssetManager.get("bluePot", SpriteAsset.class))
                    }));
                }
                PotionSlot.potionsInPot = 0;
            }
            if (PotionSlot.correctPotions != 3) return;
        }

        private boolean isHovering = false;

        @Override
        public boolean checkOverlap(Vector2 mousePos) {
            Vector2 worldPos = this.gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            return rectTransform().checkOverlap(worldPos, Pivot.CENTER);
        }

        @Override
        public void setHovering(boolean b) {
            this.isHovering = b;
        }

        @Override
        public boolean isHovering() {
            return isHovering;
        }
    }

    public final Type type;
    public Potion(Type type, Vector2 position, Vector2 scale) {
        super("potion_" + type.toString());
        this.type = type;

        this.setComponents(new Component[] {
                new SpriteRenderer(type.sprite) {{
                    setOrderInLayer(2);
                }},
                new PotionHandler(),
                new BoxCollider(new Vector2(0,0), scale) {{
                    trigger = true;
                }}
        });

        transform = new Transform(position, scale);
    }
}
