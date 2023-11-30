package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.inventory.ItemRegistry;
import puntozero.liftoff.manager.SceneStateManager;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.pointer.PointerHandlerDrag;
import pxp.engine.core.component.pointer.PointerHandlerMouse;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.SpriteAsset;

public class UIKey extends GameObject
{
    private class KeyHandler extends Component implements PointerHandlerMouse {

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
            if (isCorrect) {
                SceneStateManager.getInstance().libraryUnlocked = true;
                PlayerInventory.removeItem("keys");
                ctx().setScene(SceneIndex.LIBRARY.index);
            }
            else {
                Monologue monologue = new Monologue("That doesn't seem to fit.");
                instantiate(monologue);
                monologue.remove(2f);
            }
        }

        @Override
        public void mouseScroll(MouseEvent mouseEvent) { }
        @Override
        public void mouseDown(MouseEvent mouseEvent) { }
        @Override
        public void mouseUp(MouseEvent mouseEvent) { }
    }

    private boolean isCorrect = false;

    public UIKey(SpriteAsset sprite, SpriteAsset number, Vector2 position, boolean isCorrect) {
        super("key");

        this.isCorrect = isCorrect;

        this.setComponents(new Component[] {
            new Image(sprite) {{
                preserveAspect = true;
            }},
            new KeyHandler()
        });

        this.setChildren(new GameObject[] {
            new GameObject("number", new Component[] {
                new Image(number) {{
                    preserveAspect = true;
                }},
                new KeyHandler()
            }) {{
                transform = new RectTransform(
                    new Vector2(-100, 0),
                    new Vector3(),
                    new Vector2(1,1),
                    new Vector2(100, 100)
                );
            }}
        });

        transform = new RectTransform(
            position,
            new Vector3(),
            new Vector2(1,1),
            new Vector2(300, 100)
        );
    }
}
