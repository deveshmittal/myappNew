package com.example.deveshmittal.myapplication;

import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.deveshmittal.myapplication.common.NetworkManager;
import com.example.deveshmittal.myapplication.common.NetworkUtils;
import com.example.deveshmittal.myapplication.common.RecyclerViewItem;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity implements OnItemClickedListener, NetworkManager.ConnectivityListener {
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Ion.getDefault(this.getApplicationContext()).configure().setLogging("ION_LOGS", Log.DEBUG);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.tb_top);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);

        if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.container, new HomeFragment()).commit();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!NetworkUtils.isConnected()) {
            takeUserToSettings();
            return;
        }
    }

    @Override
    public void onItemClicked(RecyclerViewItem item) {
        DetailFragment frag =new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);
        frag.setArguments(bundle);
        replaceFragment(R.id.container, frag);
    }
    public void replaceFragment(int id , Fragment frag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(id, frag, null);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }else
        super.onBackPressed();
    }

    @Override
    public void onConnectivityChanged(boolean connected, int networkType, int networkSubtype) {
        if(!connected){
            Toast.makeText(this,"Not connected",Toast.LENGTH_LONG).show();
            takeUserToSettings();
        }
    }
    private void takeUserToSettings() {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        startActivity(intent);
    }

}
