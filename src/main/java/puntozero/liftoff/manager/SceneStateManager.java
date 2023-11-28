package puntozero.liftoff.manager;

import pxp.engine.core.Scene;
import pxp.engine.data.Vector2;

import java.util.HashMap;
import java.util.Map;

public class SceneStateManager
{
    private static SceneStateManager instance = null;
    public static SceneStateManager getInstance() {
        return instance;
    }

    public Map<Integer, SceneState> states = new HashMap<>();
    public Vector2 mapPlayerPosition = null;
    public Vector2 levelPlayerPosition = null;

    public boolean libraryUnlocked = false;
    public boolean disciplineUnlocked = false;

    public SceneStateManager() {
        if (instance == null)
            instance = this;
    }

    private void registerScene(Scene scene, SceneState sceneState) {
        this.states.put(scene.hashCode(), sceneState);
    }

    public <T extends SceneState> T get(Scene scene, T sceneStateType) {
        if (!states.containsKey(scene.hashCode()))
            registerScene(scene, sceneStateType);

        return (T) states.get(scene.hashCode());
    }

    public void reset() {
        this.states.clear();
        this.mapPlayerPosition = null;
        this.levelPlayerPosition = null;
    }
}
