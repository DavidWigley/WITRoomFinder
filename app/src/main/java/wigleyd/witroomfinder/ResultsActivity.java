package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ResultsActivity extends Activity implements View.OnClickListener {


    private int [] nearestTimeIndex;
    private int tagNumber=0, hour, timeIndexer;
    private String day;
    public final static String DAY_STRING = "DAY_STRING";
    public final static String CLASSROOM_STRING = "CLASSROOM_STRING";
    public final static String HOUR_STRING = "HOUR_STRING";
    private ArrayList allClassrooms;
    private ArrayList classrooms;

    private static final int LENGTH_OF_TIME_DESCRIPTION = 17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        AssetManager manager;
        manager = getAssets();
        InputStream inputStream = null;
        try {
            //inputStream = manager.open("fall2015.txt");
            inputStream = manager.open("summer2016.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        String hourString = intent.getStringExtra(MyActivity.HOUR_STRING);
        String minuteString = intent.getStringExtra(MyActivity.MINUTE_STRING);
        String building = intent.getStringExtra(MyActivity.BUILDING_STRING);
        day = intent.getStringExtra(MyActivity.DAY_STRING);
        hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);

        MyHandler myHandler = new MyHandler(building, day, hour, minute, inputStream);
        ArrayList rawScannerData = myHandler.getRawScannerData();
        ArrayList results = myHandler.getResults();
        allClassrooms = myHandler.getAllClassrooms();
        nearestTimeIndex = new int[allClassrooms.size()];
        setClassrooms(allClassrooms);

        final List<Classroom> classroomList = new ArrayList<Classroom>();
        ArrayList timesList;
        TwoDimentionalArrayList<String> startingTimeResultString = new TwoDimentionalArrayList<String>();
        TwoDimentionalArrayList<String> endingTimeResultString = new TwoDimentionalArrayList<String>();
        myHandler = null;
        inputStream = null;
        for (int i = 0; i < allClassrooms.size(); i++,timeIndexer++) {
            try {
                //inputStream = manager.open("fall2015.txt");
                inputStream = manager.open("summer2016.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //This is sorta shitty I don't like constantly reading the file over and over but... eh whatever, avg guy won't care.
            //Ok now it doesn't reread all that data, I changed it. It annoyed me too much. I read once then pass it everytime. More efficient.
            MyHandler individualHandler = new MyHandler(allClassrooms.get(i).toString(),day,inputStream,rawScannerData);
            individualHandler.skipTimeSearch();
            ArrayList rawResultsList = individualHandler.getDetailedRooms();
            timesList = getTrimmedResults(rawResultsList);

            timesList = getOrderedLists(timesList);

            //Ok I add all the time results to a two dimensional arrayList. First index is a classroom, second is a time entry
            for (int k =0; k <timesList.size(); k++){
                char[] startingHoursArray = {timesList.get(k).toString().charAt(0), timesList.get(k).toString().charAt(1)};
                String startHour = Character.toString(startingHoursArray[0]) + Character.toString(startingHoursArray[1]);
                startingTimeResultString.addToInnerArray(i,k,startHour);

                char[] endingHoursArray = {timesList.get(k).toString().charAt(9), timesList.get(k).toString().charAt(10)};
                String endHour = Character.toString(startingHoursArray[0]) + Character.toString(startingHoursArray[1]);
                int endTime = Integer.parseInt(endHour);
                //Checking if the times is ##:50 which basically means it rounds up
                if ("5".equalsIgnoreCase(String.valueOf(timesList.get(k).toString().charAt(12)))) {
                    endTime++;
                }
                endingTimeResultString.addToInnerArray(i,k,Integer.toString(endTime));

            }
            boolean open = false;
            for (int j = 0; j < results.size(); j++) {
                if (allClassrooms.get(i).toString().contains(results.get(j).toString())) {
                    //its open
                    open = true;
                    break;
                } else if (!allClassrooms.get(i).toString().contains(results.get(j).toString())) {
                    //its closed
                    open = false;
                }
            }
            //deals with case that all classrooms are filled so results
            //array is empty, therefore everything should be filled/red.
            if (results.size() == 0) {
                open = false;
            }
            //fault protection for having a classroom with no classes. Great idea Wentworth
            int index =0;
            if (timesList.size() == 0) {
                index = 0;
                startingTimeResultString.add(0,new ArrayList<String>());
            }else {
                index = i;
            }
            System.out.println("I passed:  " + nearestTimeIndex[i]);
            classroomList.add(new Classroom(open, allClassrooms.get(i).toString(), startingTimeResultString.get(index),endingTimeResultString.get(index), hour, nearestTimeIndex[i]));
            // Create ListItemAdapter
            ClassroomList adapter;
            adapter = new ClassroomList(this, 0, classroomList);
            // Assign ListItemAdapter to ListView
            ListView listView = (ListView) findViewById(R.id.ListView01);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Classroom classroom = classroomList.get(position);
                    Intent detailsIntent = new Intent(getBaseContext(), ClassDetailsActivity.class);
                    detailsIntent.putExtra(CLASSROOM_STRING, ResultsActivity.this.allClassrooms.get(position).toString());
                    detailsIntent.putExtra(DAY_STRING, day);
                    detailsIntent.putExtra(HOUR_STRING,hour);
                    startActivity(detailsIntent);
                }
            });
            listView.setAdapter(adapter);
        }
    }


    @Override
    public void onClick(View v) {

        //This actually works for detecting which box I clicked on
        String tag = v.getTag().toString();
        tagNumber = Integer.parseInt(tag);
        getTextBoxClicked(tagNumber);

        /*start other intent
        Intent detailsIntent = new Intent(getBaseContext(), ClassDetailsActivity.class);
        detailsIntent.putExtra(CLASSROOM_STRING, classrooms.get(tagNumber).toString());
        detailsIntent.putExtra(DAY_STRING, day);
        detailsIntent.putExtra(HOUR_STRING,hour);
        startActivity(detailsIntent);*/
    }

    public int getTextBoxClicked(int box) {
        return box;
    }

    /**
     * COPIED FROM CLASSDETAILS ACTIVITY
     * Method that will take raw class outputs and return only the times.
     * @param rawList
     * @return modifiedList which contains only the times in a format I specify.
     */
    public ArrayList getTrimmedResults(ArrayList rawList) {
        ArrayList modifiedList = new ArrayList();
        for (int i = 0; i < rawList.size(); i++){
            String currentEntry = rawList.get(i).toString();
            int colonLocation = currentEntry.indexOf(":");
            char[] totalTimeArray = new char[LENGTH_OF_TIME_DESCRIPTION];
            for (int currentPosition = 0; currentPosition < totalTimeArray.length; currentPosition++){
                totalTimeArray[currentPosition] = currentEntry.charAt(colonLocation+(currentPosition-2));
            }
            String totalTimeString = Character.toString(totalTimeArray[0]) + Character.toString(totalTimeArray[1]) +
                    Character.toString(totalTimeArray[2]) + Character.toString(totalTimeArray[3]) + Character.toString(totalTimeArray[4]) +
                    Character.toString(totalTimeArray[5]) + Character.toString(totalTimeArray[6]) + Character.toString(totalTimeArray[7]) +
                    Character.toString(totalTimeArray[8]) + Character.toString(totalTimeArray[9]) + Character.toString(totalTimeArray[10]) +
                    Character.toString(totalTimeArray[11]) + Character.toString(totalTimeArray[12]) + Character.toString(totalTimeArray[13]) +
                    Character.toString(totalTimeArray[14]) +  Character.toString(totalTimeArray[15]) +  Character.toString(totalTimeArray[16]);

            modifiedList.add(totalTimeString);
        }
        return modifiedList;
    }

    /**
     * COPIED FROM CLASSDETAILS ACTIVITY
     * Orders the list from early times to late times. 8am to whatever
     * @param inputList
     * @return
     */
    public ArrayList getOrderedLists(ArrayList inputList) {
        int[] originalTime = new int[inputList.size()];
        int[] sortedTime = new int[inputList.size()];
        for (int i =0; i < inputList.size(); i++) {
            String currentEntry = inputList.get(i).toString();
            char[] hourArray = {currentEntry.charAt(0), currentEntry.charAt(1)};
            String hour = Character.toString(hourArray[0]) + Character.toString(hourArray[1]);
            int time = Integer.parseInt(hour);
            //I spit it into military time just so I can do logical checks faster.
            if (time >=1 && time <=7){
                time +=12;
            }
            originalTime[i] = time;
            sortedTime[i] = time;
        }
        Arrays.sort(sortedTime);
        //ok so I want to remove duplicates from my sortedTime list.
        //A set does not allow duplicates so I dump everything into a set then spit back out into an array
        Set<Integer> timeSet = new HashSet<>();
        for (int i = 0; i <sortedTime.length; i++){
            timeSet.add(sortedTime[i]);
        }
        //recycling variable
        sortedTime = new int[timeSet.size()];
        int count = 0;
        for(int i : timeSet){
            sortedTime[count]= i;
            count++;
        }
        Arrays.sort(sortedTime);
        ArrayList sortedList = new ArrayList();
        for (int sorted =0;  sorted< sortedTime.length; sorted++) {
            for (int orig = 0; orig < originalTime.length; orig++) {
                if (originalTime[orig] == sortedTime[sorted] ) {
                    sortedList.add(inputList.get(orig));
                    break;
                }
            }
            if (sortedTime[sorted] < hour){
                System.out.println("I am manipulating the color this many ");
                nearestTimeIndex[timeIndexer]++;
            }
        }
        return sortedList;
    }
    public void setClassrooms(ArrayList rooms){
        classrooms = rooms;
    }

}
