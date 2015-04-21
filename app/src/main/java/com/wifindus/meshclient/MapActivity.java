package com.wifindus.meshclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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

    ImageView mapImage;

    Double userLatOnMap;
    Double userLongOnMap;

    Bitmap deviceMarkerBitmap = null;

    //latitude and longitude of map corners
    Double mapStartLat = 34.973711;
    Double mapEndLat = 34.980847;
    Double mapStartLong = 138.843886;
    Double mapEndLong = 138.851144;

    //User position
    Double userLat = 34.976461;
    Double userLong = 138.847014;


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


        mapImage = (ImageView) findViewById(R.id.mapSource);
        loadMapImage();


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



    public void loadMapImage()
    {
        Bitmap map = GenerateMap();
        if(map != null)
        {
            mapImage.setImageBitmap(map);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "something went wrong :(", Toast.LENGTH_LONG).show();
        }
    }

    private Bitmap GenerateMap()
    {
        Bitmap mapBitmap = null;
        Bitmap newMapBitmap = null;

        mapBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.oakbank);
        Bitmap.Config config = mapBitmap.getConfig();

        if(config == null)
        {
            config = Bitmap.Config.ARGB_8888;
        }

        //Create new canvas using map image
        newMapBitmap = Bitmap.createBitmap(mapBitmap.getWidth(), mapBitmap.getHeight(), config);
        Canvas mapCanvas = new Canvas(newMapBitmap);
        mapCanvas.drawBitmap(mapBitmap, 0, 0, null);

        Double mapLatDifference = mapEndLat - mapStartLat;
        Double mapLongDifference = mapEndLong - mapStartLong;

        Double userLatDifference = userLat - mapStartLat;
        Double userLongDifference = userLong - mapStartLong;

        Double userLatPercentage = (userLatDifference/mapLatDifference);
        Double userLongPercentage = (userLongDifference/mapLongDifference);

        userLatOnMap = mapCanvas.getHeight() * userLatPercentage;
        userLongOnMap = mapCanvas.getWidth() * userLongPercentage;

        // Draw another image to canvas
        deviceMarkerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.device_marker);
        Bitmap resizedDeviceMarkerBitmap = null;
        resizedDeviceMarkerBitmap = resizeBitmap(deviceMarkerBitmap, mapCanvas.getWidth()/12, mapCanvas.getWidth()/20);
        mapCanvas.drawBitmap(resizedDeviceMarkerBitmap, userLongOnMap.intValue() - ((resizedDeviceMarkerBitmap.getWidth()/2)-10), userLatOnMap.intValue() - resizedDeviceMarkerBitmap.getHeight(), null);
        // END :: Draw another image to canvas


        String[] splitName = name.split("\\s+");
        char firstInitial = splitName[0].charAt(0);
        char lastInitial = splitName[1].charAt(0);

        //Paint Text
        String captionString = firstInitial+""+lastInitial;
        Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(30);
        paintText.setStyle(Paint.Style.FILL);

        Rect rectText = new Rect();
        paintText.getTextBounds(captionString, 0, captionString.length(), rectText);

        mapCanvas.drawText(captionString, userLongOnMap.intValue()-(resizedDeviceMarkerBitmap.getWidth()/4), userLatOnMap.intValue()-resizedDeviceMarkerBitmap.getHeight()/2-10, paintText);

        return newMapBitmap;
    }



    //==============================================================================================
    // Resize Bitmap
    //==============================================================================================
    public Bitmap resizeBitmap(Bitmap imageBitmap, int newHeight, int newWidth)
    {
        int originalWidth = imageBitmap.getWidth();
        int originalHeight = imageBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / originalWidth;
        float scaleHeight = ((float) newHeight) / originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, originalWidth, originalHeight, matrix, false);
        return resizedBitmap;
    }
    //==============================================================================================
    // END :: Resize Bitmap
    //==============================================================================================









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

/*
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        Context context = getApplicationContext();
        CharSequence text = "The user was touched by your actions";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        float x = event.getX();
        float y = event.getY();


        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //Check if the x and y position of the touch is inside the bitmap
                if( x > userLongOnMap && x < userLongOnMap + deviceMarkerBitmap.getWidth() && y > userLatOnMap && y < userLatOnMap + deviceMarkerBitmap.getHeight() )
                {
                   Context context = getApplicationContext();
                    CharSequence text = "user touched icon";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                return true;
        }
        return false;
    }
*/



}


