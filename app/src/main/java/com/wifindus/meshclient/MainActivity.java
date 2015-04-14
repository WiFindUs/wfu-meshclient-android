package com.wifindus.meshclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity
{
    private String array_spinner[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //spinner
        array_spinner=new String[4];
        array_spinner[0]="Organisation";
        array_spinner[1]="WiFindUs";
        array_spinner[2]="First Care Medical";
        array_spinner[3]="Hired Goons";

        Spinner s = (Spinner) findViewById(R.id.organisation);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, array_spinner);
        s.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void login(View view)
    {
        Spinner mySpinner=(Spinner) findViewById(R.id.organisation);
        String organisation = mySpinner.getSelectedItem().toString();

        EditText mEdit   = (EditText)findViewById(R.id.password);
        String password = mEdit.getText().toString();

        Intent intent = new Intent(this, SelectUserActivity.class);
        intent.putExtra("organisation", organisation);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}


