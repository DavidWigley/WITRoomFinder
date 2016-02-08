package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ClassDetailsActivity extends Activity {

    private static final int PADDING = 15;
    ArrayList rawResultsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);
        AssetManager manager;
        manager = getAssets();
        InputStream inputStream = null;
        try {
            //inputStream = manager.open("fall2015.txt");
            inputStream = manager.open("spring2016.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = getIntent();
        String classroom = intent.getStringExtra(ResultsActivity.CLASSROOM_STRING);
        String day = intent.getStringExtra(MyActivity.DAY_STRING);
        MyHandler myHandler = new MyHandler(classroom,day,inputStream);
        myHandler.skipTimeSearch();
        rawResultsList = myHandler.getDetailedRooms();
        ArrayList timeResultsList = getTrimmedResults(rawResultsList);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);
        TextView firstBox = new TextView(this);
        firstBox.setText("The classroom: " + classroom + " on " + day + " has these classes");
        ll.addView(firstBox);
        for (int i =0; i < timeResultsList.size(); i++){
            System.out.println("Got this: " + timeResultsList.get(i).toString());
            TextView tv = new TextView(this);
            //tv.setBackgroundResource(color);
            tv.setText(timeResultsList.get(i).toString());
            tv.setPadding(0, PADDING, 0, PADDING);
            tv.setTag(i);
            ll.addView(tv);
        }
        this.setContentView(sv);
    }

    //add the minute in too
    
    public ArrayList getTrimmedResults(ArrayList rawList) {
        ArrayList modifiedList = new ArrayList();
        for (int i = 0; i < rawList.size(); i++){
            String currentEntry = rawList.get(i).toString();
            int colonLocation = currentEntry.indexOf(":");
            //this should return the first instance of the colon
            char[] firstTime = {currentEntry.charAt(colonLocation - 2), currentEntry.charAt(colonLocation - 1)};
            //ok so this adds the numbers I actually want to just take on that num at the end
            String time = Character.toString(firstTime[0]) + Character.toString(firstTime[1]);
            char[] secondTime = {currentEntry.charAt(colonLocation + 7), currentEntry.charAt(colonLocation + 8)};
            String time2 = Character.toString(secondTime[0]) + Character.toString(secondTime[1]);
            if (time.startsWith(" ")) {
                time = Character.toString(firstTime[1]);
            }
            if (time2.startsWith(" ")) {
                time2 = Character.toString(secondTime[1]);
            }
            int startHour = Integer.parseInt(time);
            int endHour = Integer.parseInt(time2);
            modifiedList.add(startHour + ":00 " + " - " + endHour + ":00");
        }
        return modifiedList;
    }

}
