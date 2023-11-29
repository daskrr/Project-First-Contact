package puntozero.liftoff.components;

import puntozero.liftoff.data.SceneIndex;
import puntozero.liftoff.manager.SceneStateManager;
import puntozero.liftoff.prefabs.Monologue;
import pxp.engine.core.component.Collider;
import pxp.engine.core.component.Component;

public class MapPlayerTrigger extends Component
{
    public MapPlayerController controller;

    public MapPlayerTrigger(MapPlayerController controller) {
        this.controller = controller;
    }

    @Override
    public void triggerEnter(Collider collider) {
        DoorHandler door = collider.getComponentOfType(DoorHandler.class);
        if (door == null) return;

        boolean canGo = true;

        // check if player can enter room
        if (door.index == SceneIndex.LIBRARY.index) {
            if (!SceneStateManager.getInstance().libraryUnlocked)
                canGo = false;
        }
        else if (door.index == SceneIndex.DISCIPLINE_ROOM.index)
            if (!SceneStateManager.getInstance().disciplineUnlocked)
                canGo = false;

        this.controller.isNearDoor = true;
        this.controller.doorIndex = door.index;

        if (this.controller.goThroughDoor && door.index == this.controller.targetedDoorIndex) {
            if (canGo) {
                SceneStateManager.getInstance().mapPlayerPosition = controller.transform().position;
                ctx().setScene(controller.doorIndex);
            }
            else {
                if (door.index == SceneIndex.LIBRARY.index) {
                    if (PlayerInventory.hasItem("keys")) {
                        SceneStateManager.getInstance().mapPlayerPosition = controller.transform().position;
                        ctx().setScene(SceneIndex.KEYS.index);
                    }
                    else {
                        Monologue monologue = new Monologue("Oh, the library is locked.\nWhere can I find the key?\nThe adults always carry them in their pockets…");
                        instantiate(monologue);
                        monologue.remove(3.5f);
                    }
                }
                else {
                    Monologue monologue = new Monologue("Mh..locked...Noone misbehaved today so the discipline room is locked.");
                    instantiate(monologue);
                    monologue.remove(3.5f);
                }
            }
        }
    }

    @Override
    public void triggerExit(Collider collider) {
        DoorHandler door = collider.getComponentOfType(DoorHandler.class);
        if (door == null) return;
        if (!this.controller.isNearDoor) return;
        if (this.controller.doorIndex != door.index) return;

        this.controller.isNearDoor = false;
    }
}
