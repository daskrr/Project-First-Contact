package puntozero.liftoff.prefabs;

import puntozero.liftoff.manager.SceneStateManager;
import pxp.engine.core.GameObject;
import pxp.engine.core.Transform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Collider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.data.Color;
import pxp.engine.data.Vector2;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPEvent;
import pxp.logging.Debug;

public class AdultDeath extends GameObject {
    private class AdultDeathHandler extends Component {
        @Override
        public void triggerEnter(Collider collider) {
            // GAME OVER when you get to close to the adult
            //TODO: uncomment this
//            DeathScreen deathScreen = new DeathScreen("The adult saw you. You were taken back to the storage room...", new PXPEvent() {
//                @Override
//                public void invoke() {
//                    SceneStateManager.getInstance().reset();
//                }
//            });
//            ctx().getCurrentScene().addGameObject(deathScreen);
        }
    }

    public AdultDeath(Vector2 position){
        super("adultDeath");

        this.setComponents(new Component[] {
            new SpriteRenderer(AssetManager.get("blank", SpriteAsset.class)){{
                color = new Color(255,255,255,0);
            }},
            new AdultDeathHandler(),
                new BoxCollider(new Vector2(0,0), new Vector2(6f,3f)) {{
                    trigger = true;
                }}
        });

        transform = new Transform(position);
    }
}
