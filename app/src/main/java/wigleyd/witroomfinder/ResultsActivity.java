package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends Activity implements View.OnClickListener {

    private static final int PADDING = 15;
    private int tagNumber=0, hour;
    private ArrayList classrooms;
    private String day;
    public final static String DAY_STRING = "DAY_STRING";
    public final static String CLASSROOM_STRING = "CLASSROOM_STRING";
    public final static String HOUR_STRING = "HOUR_STRING";
    private ArrayList allClassrooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

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
        String hourString = intent.getStringExtra(MyActivity.HOUR_STRING);
        String minuteString = intent.getStringExtra(MyActivity.MINUTE_STRING);
        String building = intent.getStringExtra(MyActivity.BUILDING_STRING);
        day = intent.getStringExtra(MyActivity.DAY_STRING);
        hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);
        //ScrollView sv = new ScrollView(this);
        //LinearLayout ll = new LinearLayout(this);
        //ll.setOrientation(LinearLayout.VERTICAL);
        MyHandler myHandler = new MyHandler(building, day, hour, minute, inputStream);
        //sv.addView(ll);
        ArrayList results = myHandler.getResults();
        allClassrooms = myHandler.getAllClassrooms();
        setClassrooms(allClassrooms);
        //TextView firstBox = new TextView(this);
        String extra = "";
        //handles case when there is not an additional 0 following the minute. Fixes the format.
        if (minute < 10) {
            extra = "0";
        }
        //firstBox.setText("The results for the open classrooms in: " + building + " on " + day +
        //" at " + hour + ":" + extra + minute);
        //ll.addView(firstBox);
        final List<Classroom> classroomList = new ArrayList<Classroom>();
        for (int i = 0; i < allClassrooms.size(); i++) {
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
            classroomList.add(new Classroom(open, allClassrooms.get(i).toString()));

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
    public void setClassrooms(ArrayList rooms){
        classrooms = rooms;
    }

}
