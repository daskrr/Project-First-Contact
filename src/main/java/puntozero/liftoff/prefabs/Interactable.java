package puntozero.liftoff.prefabs;

import processing.core.PConstants;
import processing.event.MouseEvent;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.component.BoxCollider;
import pxp.engine.core.component.Collider;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.ui.Button;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Image;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.event.PXPEvent;
import pxp.engine.data.event.PXPSingleEvent;
import pxp.engine.data.ui.InteractableTransition;
import pxp.engine.data.ui.RenderMode;
import pxp.logging.Debug;

import java.util.ArrayList;
import java.util.List;

public class Interactable extends GameObject
{
    private final PXPEvent onInteract;
    private boolean canInteract = false;

    public boolean interactImmediateAfterTrigger = false;

    private class InteractableHandler extends Component {
        private boolean interacted = false;
        @Override
        public void triggerEnter(Collider collider) {
            if (!canInteract) return;
            if (!collider.gameObject.name.equals("levelPlayer")) return;

            onInteract.invoke();
            interacted = true;

            if (interactImmediateAfterTrigger) {
                canInteract = false;
                interacted = false;
            }
        }
        @Override
        public void triggerStay(Collider collider) {
            // in case the player is already in the area of the interactable, but didn't click on it
            if (interacted) return;
            if (!canInteract) return;
            if (!collider.gameObject.name.equals("levelPlayer")) return;

            onInteract.invoke();
            interacted = true;

            if (interactImmediateAfterTrigger) {
                canInteract = false;
                interacted = false;
            }
        }
        @Override
        public void triggerExit(Collider collider) {
            if (!canInteract) return;
            if (!collider.gameObject.name.equals("levelPlayer")) return;

            onInteract.invoke();
            canInteract = false;
            interacted = false;
        }
    }

    public Interactable(
            String name,
            Vector2 offset,
            Vector2 halfSize,
            Image image,
            PXPEvent onInteract) {
        this(name, offset, halfSize, image, onInteract, new Component[0], new GameObject[0]);
    }

    public Interactable(
            String name,
            Vector2 offset,
            Vector2 halfSize,
            Image image,
            PXPEvent onInteract,
            boolean interactImmediateAfterTrigger) {
        this(name, offset, halfSize, image, onInteract, new Component[0], new GameObject[0], interactImmediateAfterTrigger);
    }

    public Interactable(
            String name,
            Vector2 offset,
            Vector2 halfSize,
            Image image,
            PXPEvent onInteract,
            Component[] components,
            GameObject[] children) {
        this(name, offset, halfSize, image, onInteract, components, children, false);
    }

    public Interactable(
            String name,
            Vector2 offset,
            Vector2 halfSize,
            Image image,
            PXPEvent onInteract,
            Component[] components,
            GameObject[] children,
            boolean interactImmediateAfterTrigger) {
        super(name);

        this.interactImmediateAfterTrigger = interactImmediateAfterTrigger;
        this.onInteract = onInteract;

        Component[] intComponents = new Component[] {
            new Canvas(RenderMode.WORLD),
            new InteractableHandler(),
            new BoxCollider(offset, halfSize) {{
                trigger = true;
            }},
        };

        List<Component> allComponents = new ArrayList<>(List.of(components));
        allComponents.addAll(List.of(intComponents));
        this.setComponents(allComponents.toArray(new Component[0]));

        List<GameObject> allChildren = new ArrayList<>(List.of(children));
        allChildren.add(createButton(image));
        this.setChildren(allChildren.toArray(new GameObject[0]));
    }

    private PXPSingleEvent<MouseEvent> onClick() {
        return new PXPSingleEvent<>() {
            @Override
            public void invoke(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() != PConstants.RIGHT) return;

                canInteract = true;
            }
        };
    }

    private GameObject createButton(Image image) {
        image.usePixelsPerUnit = true;
        Vector2 size = calcImageSize(image);

        return new GameObject("intButton", new Component[] {
            new Button(InteractableTransition.COLOR) {{
                targetImage = image;
                onClick = onClick();
            }}
        }, new GameObject[] {
            new GameObject("intImage", new Component[] { image }) {{
                transform = new RectTransform(
                    new Vector2(0,0),
                    new Vector3(0,0,0),
                    new Vector2(1,1),
                    size
                );
            }}
        }) {{
            transform = new RectTransform(
                new Vector2(0,0),
                new Vector3(0,0,0),
                new Vector2(1,1),
                size
            );
        }};
    }

    private Vector2 calcImageSize(Image image) {
        Vector2 size = image.sprite.size.clone();
        size.x /= (float) image.sprite.getPixelsPerUnit();
        size.y /= (float) image.sprite.getPixelsPerUnit();

        return size;
    }
}
