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

public class ResultsActivity extends Activity {

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
        if (minute == 0) {
            extra = "0";
        }
        if (hour >=13) {
            hour-=12;
        }
        firstBox.setText("The results for the open classrooms in: " + building + " on " + day +
                " at " + hour + ":" + minute + extra);
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
            TextView tv = new TextView(this);
            tv.setBackgroundResource(color);
            tv.setText(allClassrooms.get(i).toString());
            ll.addView(tv);
        }

        this.setContentView(sv);

    }



}
