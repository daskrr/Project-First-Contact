package puntozero.liftoff.manager;

import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.prefabs.*;
import pxp.engine.core.GameProcess;
import pxp.engine.core.Scene;
import pxp.engine.data.Vector2;

import java.util.ArrayList;
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

    public boolean adult = true;
    public boolean ending = false;

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
        this.adult = true;
        PlayerInventory.reset();
        PotSlot.correctPots = 0;
        NapkinSlot.correctNapkin = 0;
        PotionSlot.correctPotions = 0;
        PotionSlot.potionsInPot = 0;
        PotionSlot.potions = new ArrayList<>();

        GameProcess.getInstance().setScene(0);
    }
}
