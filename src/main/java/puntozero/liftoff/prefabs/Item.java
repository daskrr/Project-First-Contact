package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.inventory.InventoryItem;
import puntozero.liftoff.inventory.ItemRegistry;
import pxp.engine.core.GameObject;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.pointer.PointerHandlerMouse;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.logging.Debug;

public class Item extends GameObject
{
    private class GroundItem extends Component implements PointerHandlerMouse {
        private boolean hovering = false;

        @Override
        public void mouseClick(MouseEvent mouseEvent) {
//            gameObject.isActive = false; // TODO can't destroy????
            gameObject.destroy();
            PlayerInventory.addItem(item);
        }

        @Override
        public void mouseScroll(MouseEvent mouseEvent) {}
        @Override
        public void mouseDown(MouseEvent mouseEvent) {}
        @Override
        public void mouseUp(MouseEvent mouseEvent) {}

        @Override
        public boolean checkOverlap(Vector2 mousePos) {
            Vector2 worldPos = this.gameObject.scene.getCamera().screenToWorldPosition(mousePos);
            return rectTransform().checkOverlap(worldPos, Pivot.CENTER);
        }

        @Override
        public void setHovering(boolean b) {
            this.hovering = b;
        }
        @Override
        public boolean isHovering() {
            return this.hovering;
        }
    }

    private final InventoryItem item;

    public Item(ItemRegistry reg, Vector2 position) {
        this(reg.item, position);
    }

    public Item(InventoryItem item, Vector2 position) {
        super("groundItem_" + item.name);

        this.item = item;

        this.setComponents(new Component[] {
            new SpriteRenderer(item.sprite) {{
                setSortingLayer("Objects");
            }},
            new GroundItem()
        });

        transform.position = position;
    }
}
