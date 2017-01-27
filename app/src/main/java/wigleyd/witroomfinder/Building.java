package wigleyd.witroomfinder;

import java.util.Arrays;

/**
 * Created by frascog on 2/22/16.
 */
public enum Building {
    Annex_Central,
    Annex_North,
    Annex_South,
    Ira_Allen,
    Beatty,
    Wentworth_Hall,
    Dobbs_Hall,
    Kingman_Hall,
    Waston_Hall,
    Rubenstein,
    Williston_Hall,
    Willson_Hall,
    blank;


    @Override
    public String toString() {
        if(super.toString().equals("blank")){
            return "";
        }
        return super.toString().replaceAll("_"," ");
    }

    public static Building getValue(String building){
        if(contains(building,Keywords.annexCentral)){
            return Annex_Central;
        } else if(contains(building,Keywords.annexNorth)){
            return Annex_North;
        } else if(contains(building,Keywords.annexSouth)){
            return Annex_South;
        } else if(contains(building,Keywords.iraAllen)){
            return Ira_Allen;
        } else if(contains(building,Keywords.beatty)){
            return Beatty;
        } else if(contains(building,Keywords.wentworthHall)){
            return Wentworth_Hall;
        } else if(contains(building,Keywords.dobbsHall)){
            return Dobbs_Hall;
        } else if(contains(building,Keywords.kingman)){
            return Kingman_Hall;
        } else if(contains(building,Keywords.wastonHall)){
            return Waston_Hall;
        } else if(contains(building,Keywords.rubenstein)){
            return Rubenstein;
        } else if(contains(building,Keywords.willistonHall)){
            return Williston_Hall;
        } else if(contains(building,Keywords.willsonHall)){
            return Willson_Hall;
        } else {
            return blank;
        }
    }

    private static boolean contains(String value,String[] array){
        return Arrays.asList(array).contains(value);
    }
}
