package puntozero.liftoff.prefabs;

import pxp.engine.core.GameObject;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Collider;
import pxp.engine.core.component.Component;
import pxp.engine.data.Vector2;

public class NapkinSlot extends GameObject {
    private class NapkinSlotHandler extends Component {
        @Override
        public void triggerEnter(Collider collider) {
            if (!(collider.gameObject instanceof Napkin napkin)) return;

            correctNapkin++;
        }

        @Override
        public void triggerExit(Collider collider) {
            if (!(collider.gameObject instanceof Napkin napkin)) return;

            correctNapkin--;
        }
    }

    public static int correctNapkin = 0;
    public NapkinSlot(Vector2 position){
        super("napkinSlot");

        this.setComponents(new Component[] {
                new NapkinSlotHandler(),
                new BoxCollider(new Vector2(0,0), new Vector2(1f,1f)) {{
                    trigger = true;
                }}
        });

        transform = new Transform(position);

    }
}
