package puntozero.liftoff.prefabs;

import pxp.engine.core.GameObject;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Collider;
import pxp.engine.core.component.Component;
import pxp.engine.data.Vector2;

import java.util.ArrayList;
import java.util.List;

public class PotionSlot extends GameObject {
    private class PotionSlotHandler extends Component {
        @Override
        public void triggerEnter(Collider collider) {
            if (!(collider.gameObject instanceof Potion potion)) return;
            if (potion.type == correctType){
                correctPotions++;
                potionsInPot++;
                potions.add(potion);
            }
        }

        @Override
        public void triggerExit(Collider collider) {
            if (!(collider.gameObject instanceof Potion potion)) return;
            if (potion.type == correctType){
                correctPotions--;
                potionsInPot--;
                potions.remove(potion);
            }
        }
    }
    public static int correctPotions = 0;
    public static int potionsInPot = 0;
    public static List<Potion> potions = new ArrayList<>();
    public final Potion.Type correctType;

    public PotionSlot(Potion.Type type, Vector2 position) {
        super("potSlot_" + type.toString());
        this.correctType = type;

        this.setComponents(new Component[] {
                new PotionSlotHandler(),
                new BoxCollider(new Vector2(0,0), new Vector2(1f,1f)) {{
                    trigger = true;
                }}
        });

        transform = new Transform(position);
    }
}
