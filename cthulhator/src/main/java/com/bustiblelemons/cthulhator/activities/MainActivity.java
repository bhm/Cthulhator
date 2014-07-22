package com.bustiblelemons.cthulhator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bustiblelemons.activities.BaseFragmentActivity;
import com.bustiblelemons.cthulhator.R;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_character:
                return launchCharacterViewer(-1);
            case R.id.action_test:
                return launchTestActivity();
            case R.id.action_portriats:
                return launchPortraitsChooser();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean launchCharacterViewer(int characterId) {
        Intent i = new Intent(this, CharcterViewerActivity.class);
        i.putExtra(CharcterViewerActivity.CHARCTER_ID, characterId);
        startActivity(i);
        return true;
    }

    private boolean launchTestActivity() {
        Intent i = new Intent(this, TestActivity.class);
        startActivity(i);
        return false;
    }

    private boolean launchPortraitsChooser() {
        Intent i = new Intent(this, PortraitsActivity.class);
        startActivity(i);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        new MenuInflater(this).inflate(R.menu.main, menu);
        return menu != null;
    }
}
