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

import java.awt.*;

public class Napkin extends GameObject {
    public static final float SIZE = 1.4f;
    private static int draggingNapkin = -1;

    private class NapkinHandler extends Component implements PointerHandlerDrag {
        private boolean isDragging = false;
        private Vector2 initialDistance = new Vector2();

        @Override
        public void setDragging(boolean b) {
            if (draggingNapkin != this.hashCode() && draggingNapkin != -1) return;

            this.isDragging = b;
        }

        @Override
        public boolean isDragging() {
            if (draggingNapkin != this.hashCode() && draggingNapkin != -1) return false;

            return this.isDragging;
        }

        @Override
        public void mouseDrag(MouseEvent mouseEvent) {
            if (draggingNapkin != this.hashCode() && draggingNapkin != -1) return;

            if (PotionSlot.correctPotions != 3) return;

            Vector2 mousePos = new Vector2(mouseEvent.getX(), mouseEvent.getY());
            mousePos = gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            mousePos = transform().worldToLocal(mousePos);

            mousePos.subtract(initialDistance);

            transform.position = transform().localToWorld(mousePos);
        }

        @Override
        public void mouseDragStart(MouseEvent mouseEvent) {
            if (draggingNapkin != this.hashCode() && draggingNapkin != -1) return;



            draggingNapkin = this.hashCode();

            Vector2 mousePos = new Vector2(mouseEvent.getX(), mouseEvent.getY());
            mousePos = gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            mousePos = transform().worldToLocal(mousePos);
            mousePos = transform().localToWorld(mousePos);

            this.initialDistance = Vector2.subtract(mousePos, transform().position);
        }
        @Override
        public void mouseDragStop(MouseEvent mouseEvent) {
            draggingNapkin = -1;

            checkNapkin();
        }

        private void checkNapkin() {
            if (NapkinSlot.correctNapkin != 1) return;

            this.gameObject.destroy();
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

    public Napkin(Vector2 position, Vector2 scale) {
        super("napkin");

        this.setComponents(new Component[] {
                new SpriteRenderer(AssetManager.get("napkinTable", SpriteAsset.class)) {{
                    setOrderInLayer(2);
                }},
                new Napkin.NapkinHandler(),
                new BoxCollider(new Vector2(0,0), scale) {{
                    trigger = true;
                }}
        });

        transform = new Transform(position, scale);
    }
}
