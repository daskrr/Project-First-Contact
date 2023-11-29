package puntozero.liftoff.data;

public enum SceneIndex
{
    MAIN_MENU(0),
    MAP(1),
    KITCHEN(2),
    POTS(3),
    LIBRARY(4),
    BOOKS(5),
    DINING_ROOM(6),
    KEYS(7),
    DISCIPLINE_ROOM(-1),
    STORAGE_ROOM(-1);

    public final int index;

    SceneIndex(int index) {
        this.index = index;
    }
}
