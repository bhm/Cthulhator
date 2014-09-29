package com.bustiblelemons.cthulhator.creation.history.ui;

import android.os.Bundle;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.creation.history.logic.HistoryAdapter;
import com.bustiblelemons.cthulhator.creation.history.logic.LoadHistoryEventsAsyn;
import com.bustiblelemons.cthulhator.creation.history.logic.OnOpenHistoryEventDetails;
import com.bustiblelemons.cthulhator.creation.history.model.HistoryEventHeader;
import com.bustiblelemons.cthulhator.creation.history.model.TimeSpan;
import com.bustiblelemons.cthulhator.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.Set;

import butterknife.InjectView;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by bhm on 22.09.14.
 */
public class HistoryEditorActivity extends AbsCharacterCreationActivity
        implements OnOpenHistoryEventDetails,
                   LoadHistoryEventsAsyn.OnHistoryEventsLoaded {

    public static final int REQUEST_CODE = 8;
    @InjectView(android.R.id.list)
    StickyListHeadersListView listView;
    private TimeSpan span = TimeSpan.EMPTY;
    private SavedCharacter        mSavedCharacter;
    private HistoryAdapter        mHistoryAdapter;
    private LoadHistoryEventsAsyn mLoadHistoryAsyn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(R.layout.activity_history_editor);
        onSetActionBarToClosable();
        mSavedCharacter = getInstanceArgument();
        if (listView != null) {
            listView.setAdapter(mHistoryAdapter);
            listView.setOnItemClickListener(mHistoryAdapter);
        }
        loadHistoryAsyn();
    }

    private void loadHistoryAsyn() {
        if (mSavedCharacter != null) {
            mLoadHistoryAsyn = new LoadHistoryEventsAsyn(this, mSavedCharacter);
            if (mHistoryAdapter != null) {
                mHistoryAdapter.removeAll();
            }
            mLoadHistoryAsyn.executeCrossPlatform();

        }
    }

    @Override
    protected void onInstanceArgumentRead(SavedCharacter arg) {
        mSavedCharacter = arg;
        setupHistory();
    }

    private void setupHistory() {
        loadHistoryAsyn();
    }


    @Override
    public void onOpenHistoryEventDetails(HistoryEvent event) {
        if (event == null) {
            HistoryEventDialog dialog = HistoryEventDialog.newInstance(event);
            dialog.show(getSupportFragmentManager(), HistoryEventDialog.TAG);
        }
    }

    @Override
    public void onHistoryEventsLoaded(TimeSpan span,
                                      HistoryEventHeader header, Set<HistoryEvent> events) {
        if (span != null && events != null) {
            if (mHistoryAdapter == null) {
                mHistoryAdapter = new HistoryAdapter(this, this);
            }
            mHistoryAdapter.addData(header, events);
        }
    }
}
