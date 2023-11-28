package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.inventory.InventoryItem;
import puntozero.liftoff.inventory.ItemRegistry;
import pxp.engine.core.GameObject;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.pointer.PointerHandlerMouse;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public class Book extends GameObject
{
    // hardcoded, yes :)
    public static final Type CORRECT_TYPE = Type.ORANGE;
    public static final float SIZE = 1.5f;

    public enum Type {
        GREEN(AssetManager.get("book1", SpriteAsset.class), ItemRegistry.BOOK_GREEN.item),
        PURPLE(AssetManager.get("book2", SpriteAsset.class), ItemRegistry.BOOK_PURPLE.item),
        PINK(AssetManager.get("book3", SpriteAsset.class), ItemRegistry.BOOK_PINK.item),
        BLUE(AssetManager.get("book4", SpriteAsset.class), ItemRegistry.BOOK_BLUE.item),
        ORANGE(AssetManager.get("book5", SpriteAsset.class), ItemRegistry.BOOK_ORANGE.item),
        RED(AssetManager.get("book6", SpriteAsset.class), ItemRegistry.BOOK_RED.item);

        public final SpriteAsset sprite;
        public final InventoryItem item;

        Type(SpriteAsset sprite, InventoryItem item) {
            this.sprite = sprite;
            this.item = item;
        }
    }

    private class BookHandler extends Component implements PointerHandlerMouse {
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

        @Override
        public void mouseClick(MouseEvent mouseEvent) {
            PlayerInventory.addItem(type.item);
            context().setScene(SceneIndex.LIBRARY.index);
        }

        @Override
        public void mouseScroll(MouseEvent mouseEvent) { }
        @Override
        public void mouseDown(MouseEvent mouseEvent) { }
        @Override
        public void mouseUp(MouseEvent mouseEvent) { }
    }

    public Type type;

    public Book(Type type, Vector2 position) {
        super("book_" + type.toString());
        this.type = type;

        this.setComponents(new Component[] {
                new SpriteRenderer(type.sprite) {{
                    setOrderInLayer(2);
                }},
                new BookHandler(),
                new BoxCollider(new Vector2(0,0), new Vector2(1f, 1f)) {{
                    trigger = true;
                }}
        });

        transform = new Transform(position, new Vector2(.5f, .5f));
//        transform = new RectTransform(
//            position,
//            new Vector3(0,0,0),
//            new Vector2(1,1),
//            new Vector2(SIZE, SIZE),
//            Anchor.CENTER_LEFT
//        );
    }
}
