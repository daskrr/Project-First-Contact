package puntozero.liftoff.manager;

import pxp.engine.core.Scene;
import pxp.engine.data.GameObjectSupplier;

public interface SceneState {
    GameObjectSupplier[] restoreSceneState(Scene scene);
}
