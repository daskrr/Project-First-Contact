package puntozero.liftoff.prefabs;

import processing.event.MouseEvent;
import puntozero.liftoff.components.PlayerInventory;
import puntozero.liftoff.inventory.InventoryItem;
import puntozero.liftoff.inventory.ItemRegistry;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.pointer.PointerHandlerMouse;
import pxp.engine.core.component.ui.Button;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.Color;
import pxp.engine.data.Pivot;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.event.PXPSingleEvent;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;

public class UIItem extends GameObject
{
    public static final int SIZE = 80;

    public InventoryItem item;

    public UIItem(InventoryItem item, int index) {
        super(item.name);

        this.item = item;

        this.setComponents(new Component[] {
            new Image(item.sprite) {{
                setSortingLayer("_UICanvas");
            }}
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
