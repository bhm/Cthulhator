package com.bustiblelemons.cthulhator.creation.history.ui;

import android.os.Bundle;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

/**
 * Created by bhm on 22.09.14.
 */
public class HistoryEditorActivity extends AbsCharacterCreationActivity {

    public static final int REQUEST_CODE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_editor);
        onSetActionBarToClosable();
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {

    }
}
