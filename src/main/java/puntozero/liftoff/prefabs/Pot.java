package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.inventory.ItemRegistry;
import pxp.engine.core.GameObject;
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

public class Pot extends GameObject
{
    public static final float SIZE = 2.2f;

    private static int draggingPot = -1;

    public enum Size {
        BIG(AssetManager.get("bigPot", SpriteAsset.class)),
        MEDIUM(AssetManager.get("mediumPot", SpriteAsset.class)),
        SMALL(AssetManager.get("smallPot", SpriteAsset.class)),
        EXTRA_SMALL(AssetManager.get("extraSmallPot", SpriteAsset.class));

        public final SpriteAsset sprite;
        Size(SpriteAsset sprite) {
            this.sprite = sprite;
        }
    }

    private class PotHandler extends Component implements PointerHandlerDrag {
        private boolean isDragging = false;
        private Vector2 initialDistance = new Vector2();

        @Override
        public void setDragging(boolean b) {
            if (draggingPot != this.hashCode() && draggingPot != -1) return;

            this.isDragging = b;
        }

        @Override
        public boolean isDragging() {
            if (draggingPot != this.hashCode() && draggingPot != -1) return false;

            return this.isDragging;
        }

        @Override
        public void mouseDrag(MouseEvent mouseEvent) {
            if (draggingPot != this.hashCode() && draggingPot != -1) return;

            Vector2 mousePos = new Vector2(mouseEvent.getX(), mouseEvent.getY());
            mousePos = gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            mousePos = transform().worldToLocal(mousePos);

            mousePos.subtract(initialDistance);

            transform.position = transform().localToWorld(mousePos);
        }

        @Override
        public void mouseDragStart(MouseEvent mouseEvent) {
            if (draggingPot != this.hashCode() && draggingPot != -1) return;

            draggingPot = this.hashCode();

            Vector2 mousePos = new Vector2(mouseEvent.getX(), mouseEvent.getY());
            mousePos = gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            mousePos = transform().worldToLocal(mousePos);
            mousePos = transform().localToWorld(mousePos);

            this.initialDistance = Vector2.subtract(mousePos, transform().position);
        }
        @Override
        public void mouseDragStop(MouseEvent mouseEvent) {
            draggingPot = -1;

            checkPots();
        }

        private void checkPots() {
            if (PotSlot.correctPots != 4) return;

            PlayerInventory.addItem(ItemRegistry.POT.item);
            ctx().setScene(SceneIndex.KITCHEN.index);
            GameObject timer = ctx().getCurrentScene().getGameObjectDeep("timer");
            ctx().removeAllRoutines(timer);
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

    public final Size size;

    public Pot(Size size, Vector2 position) {
        super("pot_" + size.toString());
        this.size = size;

        this.setComponents(new Component[] {
            new SpriteRenderer(size.sprite) {{
                setOrderInLayer(2);
            }},
            new PotHandler(),
            new BoxCollider(new Vector2(0,0), new Vector2(1f, 1f)) {{
                trigger = true;
            }}
        });

        transform = new Transform(position);
//        transform = new RectTransform(
//            position,
//            new Vector3(0,0,0),
//            new Vector2(1,1),
//            new Vector2(SIZE, SIZE),
//            Anchor.CENTER_LEFT
//        );
    }
}
