package puntozero.liftoff.prefabs;

import puntozero.liftoff.data.SceneIndex;
import pxp.engine.core.GameObject;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Collider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;

public class PotSlot extends GameObject
{
    private class PotSlotHandler extends Component {
        @Override
        public void triggerEnter(Collider collider) {
            if (!(collider.gameObject instanceof Pot pot)) return;

            if (pot.size == correctSize)
                correctPots++;
        }
        @Override
        public void triggerExit(Collider collider) {
            if (!(collider.gameObject instanceof Pot pot)) return;

            if (pot.size == correctSize)
                correctPots--;
        }
    }

    public static int correctPots = 0;

    public final Pot.Size correctSize;

    public PotSlot(Pot.Size size, Vector2 position) {
        super("potSlot_" + size.toString());
        this.correctSize = size;

        this.setComponents(new Component[]{
            new SpriteRenderer(AssetManager.get("potSlot", SpriteAsset.class)) {{
                setOrderInLayer(1);
            }},
            new PotSlotHandler(),
            new BoxCollider(new Vector2(0,0), new Vector2(1f, 1f)) {{
                trigger = true;
            }}
        });

        transform = new Transform(position);
//        transform = new RectTransform(
//            position,
//            new Vector3(0, 0, 0),
//            new Vector2(1, 1),
//            new Vector2(Pot.SIZE, Pot.SIZE),
//            Anchor.CENTER_LEFT
//        );
    }
}
