package puntozero.liftoff.data;

public enum SceneIndex
{
    MAIN_MENU(0),
    MAP(1),
    KITCHEN(2),
    POTS(3),
    LIBRARY(4),
    BOOKS(5);

    public final int index;

    SceneIndex(int index) {
        this.index = index;
    }
}
