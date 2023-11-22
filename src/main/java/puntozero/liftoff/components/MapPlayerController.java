package puntozero.liftoff.components;

import pxp.engine.core.Time;
import pxp.engine.core.component.Animator;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.Input;
import pxp.engine.data.MouseButton;
import pxp.engine.data.Vector2;
import pxp.engine.data.collision.Collision;
import pxp.logging.Debug;
import pxp.util.Mathf;

public class MapPlayerController extends Component
{
    public enum LookingDirection {
        TOP, RIGHT, BOTTOM, LEFT
    }

    public float speed = 5f;

    private SpriteRenderer renderer;
    private Animator animator;

    private Vector2 destination = null;
    private Vector2 direction = new Vector2();

    private LookingDirection lookingAt = LookingDirection.BOTTOM;
    private boolean moving = false;

    public boolean goThroughDoor = false;
    public boolean isNearDoor = false;
    public int doorIndex = 0;

    @Override
    public void start() {
        this.renderer = getComponentOfType(SpriteRenderer.class);
        this.animator = getComponentOfType(Animator.class);
    }

    @Override
    public void update() {
        if (Input.getMouseButtonClick(MouseButton.MB1)) {
            Vector2 goTo = ctx().getCurrentScene().getCamera().screenToWorldPosition(Input.getMousePos());
            goTo.y -= .9f;

            this.destination = goTo;
            this.calcDirection();
        }

        // only if we have a destination (which we almost always should, unless the scene just loaded)
        if (destination != null)
            // move if we didn't reach the destination
            if (this.destination.distance(transform().position) > .1f) {
                this.moving = true;
                this.transform().position.add(Vector2.multiply(this.direction, Time.deltaTime * speed));

                this.checkLookingDirection();
            }
            else
                this.moving = false;

        this.playAnimation();
    }

    @Override
    public void collisionEnter(Collision collision) {
        this.calcDirection();
    }
    @Override
    public void collisionStay(Collision collision) {
        this.calcDirection();
    }

    private void calcDirection() {
        if (destination == null) return;

        this.direction = Vector2.subtract(destination, transform().position);
        this.direction.normalize();
    }

    /**
     * Checks which cardinal direction the player is moving in, then uses the correct animation / flips the sprite
     */
    private void checkLookingDirection() {
        // since the player can walk diagonally, we need to use the biggest component of the vector
        if (Mathf.abs(direction.x) > Mathf.abs(direction.y)) {
            if (this.direction.x > Float.MIN_VALUE)
                lookingAt = LookingDirection.RIGHT;
            else
                lookingAt = LookingDirection.LEFT;
        }
        else {
            if (this.direction.y > Float.MIN_VALUE)
                lookingAt = LookingDirection.BOTTOM;
            else
                lookingAt = LookingDirection.TOP;
        }
    }

    /**
     * Plays the appropriate animation according to the moving state and lookingAt properties
     */
    private void playAnimation() {
        String movingState = moving ? "walk" : "idle";

        switch (lookingAt) {
            case TOP -> {
                animator.play(movingState + "_back");
                renderer.flipX = false;
            }
            case RIGHT -> {
                animator.play(movingState + "_side");
                renderer.flipX = false;
            }
            case BOTTOM -> {
                animator.play(movingState + "_front");
                renderer.flipX = false;
            }
            case LEFT -> {
                animator.play(movingState + "_side");
                renderer.flipX = true;
            }
        }
    }
}
