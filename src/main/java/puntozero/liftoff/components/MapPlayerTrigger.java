package puntozero.liftoff.components;

import puntozero.liftoff.manager.SceneStateManager;
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

        this.controller.isNearDoor = true;
        this.controller.doorIndex = door.index;

        if (this.controller.goThroughDoor) {
            SceneStateManager.getInstance().mapPlayerPosition = controller.transform().position;
            ctx().setScene(controller.doorIndex);
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
