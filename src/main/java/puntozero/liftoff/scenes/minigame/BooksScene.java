package puntozero.liftoff.scenes.minigame;

import puntozero.liftoff.prefabs.Book;
import pxp.engine.core.GameObject;
import pxp.engine.core.RectTransform;
import pxp.engine.core.Scene;
import pxp.engine.core.Transform;
import pxp.engine.core.component.Camera;
import pxp.engine.core.component.Component;
import pxp.engine.core.component.SpriteRenderer;
import pxp.engine.core.component.ui.Canvas;
import pxp.engine.core.component.ui.Text;
import pxp.engine.data.Color;
import pxp.engine.data.GameObjectSupplier;
import pxp.engine.data.Vector2;
import pxp.engine.data.Vector3;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.FontAsset;
import pxp.engine.data.assets.SpriteAsset;
import pxp.engine.data.ui.Anchor;
import pxp.engine.data.ui.RenderMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BooksScene extends Scene
{
    public BooksScene() {
        super();

        this.setGameObjects(new GameObjectSupplier[] {
            () -> new GameObject("camera", new Component[] {
                new Camera(7f)
            }),
            () -> new GameObject("books", new Component[] { }, createBooks()),
            () -> new GameObject("background", new Component[] {
                new SpriteRenderer(AssetManager.get("blank", SpriteAsset.class)) {{
                    color = new Color(30, 32, 36);
                    setOrderInLayer(0);
                }}
            }) {{
                transform = new Transform(
                    new Vector2(),
                    new Vector3(),
                    new Vector2(5.8f, 3f)
                );
//                transform = new RectTransform(
//                    new Vector2(0,0),
//                    new Vector3(0,0,0),
//                    new Vector2(1,1),
//                    new Vector2(800, 600),
//                    Anchor.CENTER
//                );
            }},
            // text outside of background, because it makes it transparent AAAH
            () -> new GameObject("canvas", new Component[] {
                new Canvas(RenderMode.CAMERA)
            }, new GameObject[] {
                new GameObject("title", new Component[] {
                    new Text("Choose one... wisely...") {{
                        color = Color.white();
                        fontSize = 17;
                        font = AssetManager.get("PressStart", FontAsset.class);
                    }}
                }) {{
                    transform = new RectTransform(
                        new Vector2(0,-135f),
                        new Vector3(0,0,0),
                        new Vector2(1,1),
                        new Vector2(-1f, -1f),
                        Anchor.CENTER
                    );
                }}
            })
        });
    }

    /**
     * Creates the books
     * @return the books
     */
    private GameObject[] createBooks() {
        // shuffle the books
        List<GameObject> books = new ArrayList<>() {{
            add(new Book(Book.Type.GREEN, new Vector2(0, 0)));
            add(new Book(Book.Type.PURPLE, new Vector2(0, 0)));
            add(new Book(Book.Type.PINK, new Vector2(0, 0)));
            add(new Book(Book.Type.BLUE, new Vector2(0, 0)));
            add(new Book(Book.Type.ORANGE, new Vector2(0, 0)));
            add(new Book(Book.Type.RED, new Vector2(0, 0)));
        }};
        Collections.shuffle(books);

        float offset = -5 * Book.SIZE / 2; // for some reason 3 centers them??

        // correct positions
        int index = 0;
        for (GameObject book : books)
            book.transform.position.x = offset + Book.SIZE * index++;

        return books.toArray(new GameObject[0]);
    }
}
