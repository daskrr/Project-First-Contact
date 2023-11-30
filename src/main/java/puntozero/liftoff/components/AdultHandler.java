package puntozero.liftoff.components;

import processing.event.MouseEvent;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.prefabs.Finish;
import puntozero.liftoff.prefabs.Monologue;
import pxp.engine.core.component.*;
import pxp.engine.core.component.pointer.PointerHandlerMouse;
import pxp.engine.data.Color;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;

public class AdultHandler extends Component implements PointerHandlerMouse
{
    @Override
    public void start() {
        if (!SceneStateManager.getInstance().adult) {
            this.getComponentOfType(SpriteRenderer.class).color = new Color(255,255,255,0);
            CircleCollider collider = this.getComponentOfType(CircleCollider.class);
            collider.trigger = true;
            collider.radius = 1.5f;
        }
    }

    @Override
    public void triggerEnter(Collider col) {
        if (!SceneStateManager.getInstance().adult) {
            instantiate(new Finish());
        }
    }

    @Override
    public void mouseClick(MouseEvent mouseEvent) {
        if (!SceneStateManager.getInstance().adult)
            return;


        Monologue monologue = new Monologue("Hello young Will. You should stay downstairs.");
        instantiate(monologue);
        monologue.remove(3f);
    }

    @Override
    public void mouseScroll(MouseEvent mouseEvent) { }
    @Override
    public void mouseDown(MouseEvent mouseEvent) { }
    @Override
    public void mouseUp(MouseEvent mouseEvent) { }

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
