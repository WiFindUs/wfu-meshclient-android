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
import android.widget.Toast;


public class LogActivity extends ActionBarActivity {

    private Toolbar toolbar;

    //==============================================================================================
    // Drawer List Variables
    //==============================================================================================
    public final static String ITEM_TITLE = "title";
    public final static String ITEM_CAPTION = "caption";

    // SectionHeaders
    private final static String[] days = new String[]{"", "Find", "WiFindUs", " "};

    // Section Contents
    private final static String[] myStuff = new String[]{"My Device", "My Incidents"};
    private final static String[] find = new String[]{"Map", "People", "Locations"};
    private final static String[] wiFindUs = new String[]{"Nodes", "Log"};
    private final static String[] aboutApp = new String[]{"About App"};

    // MENU - ListView
    private ListView addJournalEntryItem;

    // Adapter for ListView Contents
    private DrawerListAdapter adapter;

    // ListView Contents
    private ListView journalListView;

    public    NavigationDrawerFragment drawerFragment;
    //==============================================================================================
    // END :: Drawer List Variables
    //==============================================================================================


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_incidents_layout);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        drawerFragment= (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        // Circular Profile Picture
        ImageView imageViewRound;
        imageViewRound=(ImageView)findViewById(R.id.imageView_round);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.mitchell_templeton);
        imageViewRound.setImageBitmap(icon);



        //==========================================================================================
        // Drawer List
        //==========================================================================================



        adapter = new DrawerListAdapter(this);

        ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this, R.layout.list_item, myStuff);
        adapter.addSection(days[0], listadapter);

        ArrayAdapter<String> listadapter2 = new ArrayAdapter<String>(this, R.layout.list_item, find);
        adapter.addSection(days[1], listadapter2);

        ArrayAdapter<String> listadapter3 = new ArrayAdapter<String>(this, R.layout.list_item, wiFindUs);
        adapter.addSection(days[2], listadapter3);

        ArrayAdapter<String> listadapter4 = new ArrayAdapter<String>(this, R.layout.list_item, aboutApp);
        adapter.addSection(days[3], listadapter4);

        // Get a reference to the ListView holder
        journalListView = (ListView) this.findViewById(R.id.list_journal);

        // Set the adapter on the ListView holder
        journalListView.setAdapter(adapter);

        // Listen for Click events
        journalListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long duration)
            {
                //String item = (String) adapter.getItem(position);

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




    public void changeActivity(int activityNumber)
    {
        switch (activityNumber)
        {
            case(1):
                Intent intent = new Intent(this, MyDeviceActivity.class);
                startActivity(intent);
                break;
            case(2):
                Intent incident_activity_intent = new Intent(this, MyIncidentsActivity.class);
                startActivity(incident_activity_intent);
                break;
            case(4):
                Intent map_activity_intent = new Intent(this, MapActivity.class);
                startActivity(map_activity_intent);
                break;
            case(5):
                Intent people_activity_intent = new Intent(this, PeopleActivity.class);
                startActivity(people_activity_intent);
                break;
            case(6):
                Intent locations_activity_intent = new Intent(this, LocationsActivity.class);
                startActivity(locations_activity_intent);
                break;
            case(8):
                Intent nodes_activity_intent = new Intent(this, NodesActivity.class);
                startActivity(nodes_activity_intent);
                break;
            /*case(9):
                Intent log_activity_intent = new Intent(this, LogActivity.class);
                startActivity(log_activity_intent);
                break;*/
            case(11):
                Intent about_app_activity_intent = new Intent(this, AboutAppActivity.class);
                startActivity(about_app_activity_intent);
                break;
        }
    }

}


