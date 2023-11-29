package puntozero.liftoff.components;

import pxp.engine.core.component.Component;
import pxp.engine.data.assets.Asset;
import pxp.engine.data.assets.AssetManager;
import pxp.engine.data.assets.SpriteAsset;
import pxp.logging.Debug;

public class TableBook extends Component {

    public SpriteAsset book = null;

    public TableBook(){
        start();
    }

    @Override
    public void start() {
        if (PlayerInventory.hasItem("bookOrange")){
            book = AssetManager.get("openOrangeBook", SpriteAsset.class);
        }
        else if (PlayerInventory.hasItem("bookBlue")){
            book = AssetManager.get("openBlueBook", SpriteAsset.class);
        }
        else if (PlayerInventory.hasItem("bookRed")){
            book = AssetManager.get("openRedBook", SpriteAsset.class);
        }
        else if (PlayerInventory.hasItem("bookGreen")){
            book = AssetManager.get("openGreenBook", SpriteAsset.class);
        }
        else if (PlayerInventory.hasItem("bookPurple")){
            book = AssetManager.get("openPurpleBook", SpriteAsset.class);
        }
        else if (PlayerInventory.hasItem("bookPink")){
            book = AssetManager.get("openPinkBook", SpriteAsset.class);
        }
    }
}
