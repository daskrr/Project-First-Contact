package puntozero.liftoff.components;

import puntozero.liftoff.manager.SceneStateManager;
import pxp.engine.core.Time;
import pxp.engine.core.component.Animator;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.Input;
import pxp.engine.data.MouseButton;
import pxp.engine.data.Vector2;
import pxp.logging.Debug;
import pxp.util.Mathf;

public class LevelPlayerController extends Component
{
    public float speed = 5f;
    public Vector2 destination = null;
    private float directionX = 0;

    private boolean locked = false;

    private SpriteRenderer renderer;
    private Animator animator;

    @Override
    public void start() {
        // note:
        // better to take the reference to them once so that we don't loop through all components trying to find the one
        // we need every frame or every time we need it
        this.renderer = getComponentOfType(SpriteRenderer.class);
        this.animator = getComponentOfType(Animator.class);

        renderer.flipX = true;

        Vector2 position = SceneStateManager.getInstance().levelPlayerPosition;
        if (position != null)
            transform().position = position.clone();
    }

    @Override
    public void update() {
        if (locked) return;

        if (Input.getMouseButtonClick(MouseButton.MB1) || Input.getMouseButtonClick(MouseButton.MB2)) {
            Vector2 worldPos = ctx().getCurrentScene().getCamera().screenToWorldPosition(Input.getMousePos());
            this.destination = worldPos;
            this.calcDirection();
        }

        if (destination != null){
            if (Mathf.abs(transform().position.x - this.destination.x) > .1f) {
                if (directionX < Float.MIN_VALUE){
                    this.transform().position.x -= speed * Time.deltaTime;
                }
                else {
                    this.transform().position.x += speed * Time.deltaTime;
                }

                this.checkLookingDirection();
                animator.play("walk");
            }
            else
                animator.play("idle");
        }
        else
            animator.play("idle");
    }

    private void checkLookingDirection() {
        boolean isFacingLeft;
        if (this.directionX > Float.MIN_VALUE)
            isFacingLeft = false;
        else
            isFacingLeft = true;

        renderer.flipX = isFacingLeft;
    }

    private void calcDirection() {
        if (destination == null) return;

        this.directionX = destination.x - transform().position.x;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;

        if (locked)
            animator.play("idle");
    }
}