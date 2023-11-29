package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.inventory.ItemRegistry;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.pointer.PointerHandlerMouse;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Color;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.RenderMode;

public class Molotov extends GameObject {
    public static final float SIZE = 2.4f;

    private class MolotovHandler extends Component implements PointerHandlerMouse {

        @Override
        public void mouseClick(MouseEvent mouseEvent) {
            PlayerInventory.addItem(ItemRegistry.MOLOTOV.item);
            this.gameObject.destroy();
            ctx().runLater(this.gameObject, 2f, () -> {

                ctx().getCurrentScene().addGameObject(new GameObject("blackCanvas", new Component[] {
                    new Canvas(RenderMode.CAMERA)
                }, new GameObject[] {
                    new GameObject("blackScreen", new Component[] {
                        new Image(AssetManager.get("blank", SpriteAsset.class)) {{
                            color = Color.black();
                        }}
                    }) {{
                        transform = new RectTransform(
                            new Vector2(),
                            new Vector3(),
                            new Vector2(1,1),
                            new Vector2(1920, 1080),
                            Anchor.CENTER
                        );
                    }}
                }));

                ctx().getCurrentScene().getGameObject("canvas").destroy();

                ctx().runLater(this.gameObject, 1f, () -> {
                    ctx().setScene(SceneIndex.MAP.index);
                });
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

    public Molotov(Vector2 position, Vector2 scale) {
        super("molotov");

        this.setComponents(new Component[] {
            new SpriteRenderer(AssetManager.get("molotov", SpriteAsset.class)) {{
                setOrderInLayer(2);
            }},
            new MolotovHandler(),
            new BoxCollider(new Vector2(0,0), scale) {{
                trigger = true;
            }}
        });

        transform = new Transform(position, scale);
    }
}
