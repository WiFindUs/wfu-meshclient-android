package com.wifindus.meshclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MapActivity extends ActionBarActivity {

    private Toolbar toolbar;
    String organisation;
    String name;
    int profilePicture;

    //==============================================================================================
    // Drawer List Variables
    //==============================================================================================

    // Section Headers
    private final static String[] headings = new String[]{"", "Find", "WiFindUs", " "};

    // Section Contents
    private final static String[] myStuff = new String[]{"My Device", "My Incidents"};
    private final static String[] find = new String[]{"Map", "People", "Locations"};
    private final static String[] wiFindUs = new String[]{"Nodes", "Log"};
    private final static String[] aboutApp = new String[]{"About App"};

    public NavigationDrawerFragment drawerFragment;
    //==============================================================================================
    // END :: Drawer List Variables
    //==============================================================================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawerFragment= (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        Intent intent = getIntent();
        organisation = intent.getStringExtra("organisation");
        name = intent.getStringExtra("name");
        profilePicture = intent.getIntExtra("profilePicture", 0);

        // Circular Profile Picture
        ImageView imageViewRound;
        imageViewRound=(ImageView)findViewById(R.id.imageView_round);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), profilePicture);
        imageViewRound.setImageBitmap(icon);

        //Set User and Organisation Name
        TextView user_name =(TextView)findViewById(R.id.user_name);
        user_name.setText(name);
        TextView organisation_name =(TextView)findViewById(R.id.organisation_name);
        organisation_name.setText(organisation);



        //==========================================================================================
        // Drawer List
        //==========================================================================================
        DrawerListAdapter adapter = new DrawerListAdapter(this);

        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, R.layout.list_item, myStuff);
        adapter.addSection(headings[0], listAdapter);

        ArrayAdapter<String> listAdapter2 = new ArrayAdapter<>(this, R.layout.list_item, find);
        adapter.addSection(headings[1], listAdapter2);

        ArrayAdapter<String> listAdapter3 = new ArrayAdapter<>(this, R.layout.list_item, wiFindUs);
        adapter.addSection(headings[2], listAdapter3);

        ArrayAdapter<String> listAdapter4 = new ArrayAdapter<>(this, R.layout.list_item, aboutApp);
        adapter.addSection(headings[3], listAdapter4);

        // Get a reference to the ListView holder
        ListView optionsList = (ListView) this.findViewById(R.id.list_journal);

        // Set the adapter on the ListView holder
        optionsList.setAdapter(adapter);

        // Listen for Click events
        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration) {
                changeActivity(position);
            }
        });
        //==========================================================================================
        // END :: Drawer List
        //==========================================================================================


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




    //==============================================================================================
    // Select new activity in drawer
    //==============================================================================================
    public void changeActivity(int activityNumber)
    {
        switch (activityNumber)
        {
            case(1):
                Intent intent = new Intent(this, MyDeviceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("organisation", organisation);
                intent.putExtra("name", name);
                intent.putExtra("profilePicture", profilePicture);
                startActivity(intent);
                break;
            case(2):
                Intent incident_activity_intent = new Intent(this, MyIncidentsActivity.class);
                incident_activity_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                incident_activity_intent.putExtra("organisation", organisation);
                incident_activity_intent.putExtra("name", name);
                incident_activity_intent.putExtra("profilePicture", profilePicture);
                startActivity(incident_activity_intent);
                break;
            case(4):
                Intent map_activity_intent = new Intent(this, MapActivity.class);
                map_activity_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                map_activity_intent.putExtra("organisation", organisation);
                map_activity_intent.putExtra("name", name);
                map_activity_intent.putExtra("profilePicture", profilePicture);
                startActivity(map_activity_intent);
                break;
            case(5):
                Intent people_activity_intent = new Intent(this, PeopleActivity.class);
                people_activity_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                people_activity_intent.putExtra("organisation", organisation);
                people_activity_intent.putExtra("name", name);
                people_activity_intent.putExtra("profilePicture", profilePicture);
                startActivity(people_activity_intent);
                break;
            case(6):
                Intent locations_activity_intent = new Intent(this, LocationsActivity.class);
                locations_activity_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                locations_activity_intent.putExtra("organisation", organisation);
                locations_activity_intent.putExtra("name", name);
                locations_activity_intent.putExtra("profilePicture", profilePicture);
                startActivity(locations_activity_intent);
                break;
            case(8):
                Intent nodes_activity_intent = new Intent(this, NodesActivity.class);
                nodes_activity_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                nodes_activity_intent.putExtra("organisation", organisation);
                nodes_activity_intent.putExtra("name", name);
                nodes_activity_intent.putExtra("profilePicture", profilePicture);
                startActivity(nodes_activity_intent);
                break;
            case(9):
                Intent log_activity_intent = new Intent(this, LogActivity.class);
                log_activity_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                log_activity_intent.putExtra("organisation", organisation);
                log_activity_intent.putExtra("name", name);
                log_activity_intent.putExtra("profilePicture", profilePicture);
                startActivity(log_activity_intent);
                break;
            case(11):
                Intent about_app_activity_intent = new Intent(this, AboutAppActivity.class);
                about_app_activity_intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                about_app_activity_intent.putExtra("organisation", organisation);
                about_app_activity_intent.putExtra("name", name);
                about_app_activity_intent.putExtra("profilePicture", profilePicture);
                startActivity(about_app_activity_intent);
                break;
        }
    }
    //==============================================================================================
    // END :: Select new activity in drawer
    //==============================================================================================

}


