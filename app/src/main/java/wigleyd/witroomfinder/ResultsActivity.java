package wigleyd.witroomfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ResultsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        Intent intent = getIntent();
        String hourString = intent.getStringExtra(MyActivity.HOUR_STRING);
        String minuteString = intent.getStringExtra(MyActivity.MINUTE_STRING);
        String building = intent.getStringExtra(MyActivity.BUILDING_STRING);
        String day = intent.getStringExtra(MyActivity.DAY_STRING);
        int hour = Integer.getInteger(hourString);
        int minute = Integer.getInteger(minuteString);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        Handler handler = new Handler(building, day, hour, minute);
        sv.addView(ll);
        for(int i = 0;i<handler.search.getNumClassrooms();i++){
            TextView tv = new TextView(this);
            tv.setText(handler.search.getClassrooms());
            ll.addView(tv);
        }
        this.setContentView(sv);

    }



}
