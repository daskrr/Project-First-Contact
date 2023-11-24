package puntozero.liftoff.prefabs;

import puntozero.liftoff.inventory.InventoryItem;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.ui.Anchor;

public class UIItem extends GameObject
{
    public static final int SIZE = 80;

    public UIItem(InventoryItem item, int index) {
        super(item.name);

        this.setComponents(new Component[] {
            new Image(item.sprite)
        });

        this.transform = new RectTransform(
            new Vector2(0, -(index * SIZE)),
            new Vector3(),
            new Vector2(1,1),
            new Vector2(SIZE, SIZE),
            Anchor.BOTTOM_CENTER
        );
    }
}
