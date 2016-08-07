package wigleyd.witroomfinder;

import java.util.ArrayList;

/**
 * Created by frascog on 2/22/16.
 * Made terrible by wigleyd on 8/6/16.
 */
public class Classroom {

    private Building building;
    private String room;
    private boolean open;
    private String original;
    private ArrayList startTimes, endTimes;
    private int timeWanted, nearestTime;
    private int available;

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

    public Classroom(boolean open, String name, ArrayList startTimes, ArrayList endTimes, int timeWanted, int nearestTime) {
        this.open = open;
        String[] items = name.split(" ");
        setBuilding(Building.getValue(name));
        setRoom(items[1]);
        this.original = name;
        this.startTimes = startTimes;
        this.timeWanted = timeWanted;
        this.endTimes = endTimes;
        this.nearestTime = nearestTime;
    }


    public String getAvailability() {
        if (startTimes.isEmpty()) {
            return "Forever";
        } else {
            //fault protection something tripped after WENTW207. Odd
            if (nearestTime >= startTimes.size()) {
                nearestTime = startTimes.size()-1;
            }
            int closestHour = Integer.parseInt(startTimes.get(nearestTime).toString());
            //I spit it into military time
            if (closestHour >=1 && closestHour <=7){
                closestHour +=12;
            }
            if (timeWanted < closestHour) {
                //ie its currently open so I get the start time
                available = closestHour;
            } else {
                //its currently taken so I get the later time
                available = Integer.parseInt(endTimes.get(nearestTime).toString());
            }
            //convert back to 12hr format
            if (available > 12){
                available-=12;
            }
            return Integer.toString(available);
        }
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

    public String getName() {
        return this.building + " " + room;
    }

    public String getOriginal() {
        return original;
    }
}
