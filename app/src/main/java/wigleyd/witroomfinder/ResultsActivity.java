package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ResultsActivity extends Activity implements View.OnClickListener {

    private static final int PADDING = 15;
    private int tagNumber=0;
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
        String day = intent.getStringExtra(MyActivity.DAY_STRING);
        System.out.println("I was given this: " + hourString);

        int hour = Integer.parseInt(hourString);
        int minute = Integer.parseInt(minuteString);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        MyHandler myHandler = new MyHandler(building, day, hour, minute, inputStream);
        sv.addView(ll);
        ArrayList results = myHandler.getResults();
        ArrayList allClassrooms = myHandler.getAllClassrooms();
        TextView firstBox = new TextView(this);
        String extra = "";
        //handles case when there is not an additional 0 following the minute. Fixes the format.
        if (minute < 10) {
            extra = "0";
        }
        if (hour >=13) {
            hour-=12;
        }
        firstBox.setText("The results for the open classrooms in: " + building + " on " + day +
                " at " + hour + ":" + extra + minute);
        ll.addView(firstBox);
        for (int i =0; i < allClassrooms.size(); i++) {
            int color = 0;
            for (int j = 0; j < results.size(); j++){
                color = 0;
                if (allClassrooms.get(i).toString().contains(results.get(j).toString())){
                    //its open
                    color = R.color.green;
                    break;
                }else if (!allClassrooms.get(i).toString().contains(results.get(j).toString()) &&
                        color != R.color.green) {
                    //its closed
                    color = R.color.red;
                }
            }
            //deals with case that all classrooms are filled so results
            //array is empty, therefore everything should be filled/red.
            if(results.size() == 0) {
                color = R.color.red;
            }
            TextView tv = new TextView(this);
            tv.setBackgroundResource(color);
            tv.setText(allClassrooms.get(i).toString());
            tv.setPadding(0, PADDING, 0, PADDING);
            tv.setTag(i);
            tv.setOnClickListener(this);
            ll.addView(tv);
        }
        this.setContentView(sv);

    }


    @Override
    public void onClick(View v) {

        //This actually works for detecting which box I clicked on
        String tag = v.getTag().toString();
        System.out.println("I clicked on a classroom");
        System.out.println("The tag I clicked on was: " + tag);
        tagNumber = Integer.parseInt(tag);
        getTextBoxClicked(tagNumber);

    }

    public int getTextBoxClicked(int box) {
        return box;
    }

    public void initializeDetails(ArrayList classes){
        Intent detailsIntent = new Intent(getBaseContext(), ResultsActivity.class);
        //detailsIntent.putExtra("Class name", allClassrooms.get(tag));
    }

}
