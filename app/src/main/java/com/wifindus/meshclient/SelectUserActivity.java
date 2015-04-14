package com.wifindus.meshclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class SelectUserActivity extends ActionBarActivity {

    private Toolbar toolbar;

    String wiFindUsUserNames[] = {"Hussein Al Hammad","Mark Gillard","Travis Grund","Benjamin Morris","Mateus Nolasco","Ben Quast","Greg Stevens","Mitchell Templeton" };
    int[] wiFindUsUserPictures = new int[]{R.drawable.hussein_al_hammad, R.drawable.mark_gillard, R.drawable.travis_grund,R.drawable.logo,R.drawable.mateus_nolasco,R.drawable.ben_quast,R.drawable.greg_stevens,R.drawable.mitchell_templeton};
    String organisation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_user);
        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

           // Setting toolbar as the ActionBar with setSupportActionBar() call



        Intent intent = getIntent();
        organisation = intent.getStringExtra("organisation");
        String password = intent.getStringExtra("password");


        Context context = getApplicationContext();
        CharSequence text = organisation+"   "+password;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        //==========================================================================================
        // Recycler View for user selection
        //==========================================================================================
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        // Circular Profile Picture
        /*ImageView imageViewRound;
        imageViewRound=(ImageView)findViewById(R.id.imageView_round);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.mitchell_templeton);
        imageViewRound.setImageBitmap(icon);*/


        // this is data fro recycler view
        SelectUserData wiFindUsUsers[] = { new SelectUserData(wiFindUsUserNames[0],wiFindUsUserPictures[0]),
                new SelectUserData(wiFindUsUserNames[1],wiFindUsUserPictures[1]),
                new SelectUserData(wiFindUsUserNames[2],wiFindUsUserPictures[2]),
                new SelectUserData(wiFindUsUserNames[3],wiFindUsUserPictures[3]),
                new SelectUserData(wiFindUsUserNames[4],wiFindUsUserPictures[4]),
                new SelectUserData(wiFindUsUserNames[5],wiFindUsUserPictures[5]),
                new SelectUserData(wiFindUsUserNames[6],wiFindUsUserPictures[6]),
                new SelectUserData(wiFindUsUserNames[7],wiFindUsUserPictures[7])


        };





        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 3. create an adapter
        SelectUserViewAdapter mAdapter = new SelectUserViewAdapter(wiFindUsUsers);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position)
                    {
                        String name = wiFindUsUserNames[position];
                        int profilePicture = wiFindUsUserPictures[position];
                        userSelected(name, profilePicture);

                    }
                })
        );
        //==========================================================================================
        // END :: Recycler View for user selection
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


    public void userSelected(String name, int profilePicture)
    {
        Intent intent = new Intent(this, MyDeviceActivity.class);
        intent.putExtra("organisation", organisation);
        intent.putExtra("name", name);
        intent.putExtra("profilePicture", profilePicture);
        startActivity(intent);
    }







}


