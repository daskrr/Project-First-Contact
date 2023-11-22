package puntozero.liftoff.components;

import pxp.engine.core.Time;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.Input;
import pxp.engine.data.MouseButton;
import pxp.engine.data.Vector2;
import pxp.util.Mathf;

public class LevelPlayerController extends Component {

    private boolean isFacingLeft = false;
    public float speed = 5f;
    private Vector2 destination = null;
    private float directionX = 0;

    @Override
    public void start() {

    }

    @Override
    public void update() {

        if (Input.getMouseButtonClick(MouseButton.MB1)) {
            Vector2 worldPos = ctx().getCurrentScene().getCamera().screenToWorldPosition(Input.getMousePos());
            this.destination = worldPos;
            this.calcDirection();
        }

        if (destination != null){
            if (Mathf.abs(transform().position.x - this.destination.x) > .1f){
                if (directionX < Float.MIN_VALUE){
                    this.transform().position.x -= speed * Time.deltaTime;
                }
                else {
                    this.transform().position.x += speed * Time.deltaTime;
                }

                this.checkLookingDirection();
            }
        }
    }

    private void checkLookingDirection() {
            if (this.directionX > Float.MIN_VALUE)
                isFacingLeft = false;
            else
                isFacingLeft = true;

        ((SpriteRenderer) gameObject.renderer).flipX = isFacingLeft;
    }

    private void calcDirection() {
        if (destination == null) return;

        this.directionX = destination.x - transform().position.x;
    }
}