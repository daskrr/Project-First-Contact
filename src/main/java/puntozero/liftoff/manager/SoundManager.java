package puntozero.liftoff.manager;

import pxp.engine.core.GameObject;
import pxp.engine.core.GameProcess;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SoundEmitter;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SoundAsset;

public class SoundManager {
    public static void playSound(String sound) {
        GameProcess.getInstance().getCurrentScene().addGameObject(createSound(sound));
    }

    public static GameObject createSound(String sound) {
        return new GameObject("sound"+ sound, new Component[] {
            new SoundEmitter(AssetManager.get(sound, SoundAsset.class)) {{
                autoPlay = true;
            }}
        });
    }
}
