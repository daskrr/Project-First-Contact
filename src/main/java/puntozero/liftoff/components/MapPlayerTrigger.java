package puntozero.liftoff.components;

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

        if (this.controller.goThroughDoor)
            ctx().setScene(controller.doorIndex);
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
