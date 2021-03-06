package com.bustiblelemons.cthulhator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bustiblelemons.activities.AbsActionBarActivity;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.description.ui.RandomCharactersActivity;
import com.bustiblelemons.cthulhator.character.portrait.ui.PortraitsActivity;
import com.bustiblelemons.cthulhator.character.viewer.CharacterViewerActivity;

public class MainActivity extends AbsActionBarActivity {

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
        case R.id.action_portriats:
            return launchPortraitsChooser();
        case R.id.action_random_user:
            return launchRandomUser();
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    private boolean launchRandomUser() {
        Intent i = new Intent(this, RandomCharactersActivity.class);
        startActivity(i);
        return false;
    }

    private boolean launchCharacterViewer(int characterId) {
        Intent i = new Intent(this, CharacterViewerActivity.class);
        i.putExtra(CharacterViewerActivity.CHARACTER_ID, characterId);
        startActivity(i);
        return true;
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
