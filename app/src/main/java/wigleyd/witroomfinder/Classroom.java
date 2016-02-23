package wigleyd.witroomfinder;

/**
 * Created by frascog on 2/22/16.
 */
public class Classroom {

    private Building building;
    private String room;
    private boolean open;
    private String original;

    public Classroom(boolean open, Building building, String room) {
        this.open = open;
        this.building = building;
        this.room = room;
    }

    public Classroom(boolean open, String name) {
        this.open = open;
        String[] items = name.split(" ");
        setBuilding(Building.getValue(name));
        setRoom(items[1]);
        this.original = name;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getName(){
        return this.building + " " + room;
    }

    public String getOriginal() {
        return original;
    }
}
