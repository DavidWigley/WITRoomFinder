package wigleyd.witroomfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.view.View.OnClickListener;

import java.util.Calendar;


public class MyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public final static String HOUR_STRING = "HOUR_STRING";
    public final static String MINUTE_STRING = "MINUTE_STRING";
    public final static String BUILDING_STRING = "BUILDING_STRING";
    public final static String DAY_STRING = "DAY_STRING";

    private Spinner buildingSpinner, daySpinner, hourSpinner;
    private static final String[] buildings = {"Annex Central", "Annex North", "Annex South", "Beatty", "Dobbs Hall",
            "Ira Allen", "Kingman Hall", "Rubenstein Hall", "Watson Hall", "Wentworth Hall", "Willison Hall", "Williston Hall"};
    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    //I can do 1-12 because classes start at 8am and at 8pm so no need to use 24hr format
    private static final String[] hourChoices = {"8am","9am","10am","11am","12pm","1pm","2pm","3pm","4pm","5pm","6pm","7pm"};
    private static final String[] hours = {"8","9","10","11","12","1","2","3","4","5","6","7"};
    private Button submit;
    String buildingChosen, dayChosen, hourChosen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


        submit = (Button) findViewById(R.id.submit_button);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getBaseContext(), ResultsActivity.class);
                String minute;
                //theres a bug with the minute not being 0. it fucks up the logic. Damn it
                minute="0";
                myIntent.putExtra(HOUR_STRING, hourChosen);
                myIntent.putExtra(MINUTE_STRING, minute);
                myIntent.putExtra(BUILDING_STRING, buildingChosen);
                myIntent.putExtra(DAY_STRING, dayChosen);
                startActivity(myIntent);
            }
        });

        buildingSpinner = (Spinner) findViewById(R.id.building_spinner);
        ArrayAdapter<String> buildingAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, buildings);

        daySpinner = (Spinner) findViewById(R.id.day_spinner);
        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, days);

        hourSpinner = (Spinner) findViewById(R.id.hourSpinner);
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, hourChoices);

        buildingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        buildingSpinner.setAdapter(buildingAdapter);
        buildingSpinner.setOnItemSelectedListener(this);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);
        daySpinner.setOnItemSelectedListener(this);
        hourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourSpinner.setAdapter(hourAdapter);
        hourSpinner.setOnItemSelectedListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (parent.getId()) {
            case R.id.building_spinner:
                buildingChosen = buildings[position];
                break;
            case R.id.day_spinner:
                dayChosen = days[position];
                break;
            case R.id.hourSpinner:
                hourChosen = hours[position];
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
