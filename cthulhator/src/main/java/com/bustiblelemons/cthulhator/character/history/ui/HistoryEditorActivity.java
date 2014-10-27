package com.bustiblelemons.cthulhator.character.history.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.character.history.logic.HistoryAdapter;
import com.bustiblelemons.cthulhator.character.history.logic.LoadHistoryEventsAsyn;
import com.bustiblelemons.cthulhator.character.history.logic.OnOpenHistoryEventDetails;
import com.bustiblelemons.cthulhator.character.history.logic.ReportCharacterSettings;
import com.bustiblelemons.cthulhator.character.history.model.BirthData;
import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.cthulhator.character.history.model.TimeSpan;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import org.joda.time.DateTime;

import java.util.Random;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

//import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;

/**
 * Created by bhm on 22.09.14.
 */
public class HistoryEditorActivity extends AbsCharacterCreationActivity
        implements OnOpenHistoryEventDetails,
                   LoadHistoryEventsAsyn.OnHistoryEventsLoaded,
                   HistoryEventDialog.OnHistoryEventPassedBack,
//        OnShowDatePicker,
//                   ReportCharacterSettings,
//                   CalendarDatePickerDialog.OnDateSetListener {
                   ReportCharacterSettings, View.OnClickListener {

    public static final  int    REQUEST_CODE = 8;
    private static final String sDateFormat  = "MMM dd, yyyy";
    //    private static final String sCalendarDialogTag = CalendarDatePickerDialog.class.getSimpleName();
    @InjectView(R.id.list)
    StickyListHeadersListView listView;

    private TimeSpan span = TimeSpan.EMPTY;
    private SavedCharacter mSavedCharacter;
    private HistoryAdapter mHistoryAdapter;
    private DateTime       mBirthDate;
    private DateTime       mSuggestedDate;
    private TimeSpan       mSpan;
    private Toolbar        mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_editor);
        mToolbar = (Toolbar) findViewById(R.id.header);
        if (mToolbar != null) {
            mToolbar.setNavigationOnClickListener(this);
            setSupportActionBar(mToolbar);
        }
        ButterKnife.inject(this);
        onSetActionBarToClosable();
        mSavedCharacter = getInstanceArgument();
        if (listView != null) {
            mHistoryAdapter = new HistoryAdapter(this, this);
            listView.setAdapter(mHistoryAdapter);
            listView.setOnItemClickListener(mHistoryAdapter);
        }
        setupBirthDate();
        setBirthDayView();
        long begin = mBirthDate.getMillis();
        long end = mSuggestedDate.getMillis();
        mSpan = new TimeSpan(begin, end);
    }

    private void setBirthDayView() {
        if (mToolbar != null) {
            mToolbar.setSubtitle(mBirthDate.toString(sDateFormat));
        }
    }

    private void loadHistoryAsyn() {
        if (mSavedCharacter != null) {
            LoadHistoryEventsAsyn loadHistoryAsyn = new LoadHistoryEventsAsyn(this, mSavedCharacter);
            loadHistoryAsyn.setOnHistoryEventsLoaded(this);
            loadHistoryAsyn.executeCrossPlatform(mSpan);
        }
    }

    private FadingActionBarHelper setupFadingBar() {
        FadingActionBarHelper helper = new FadingActionBarHelper()
                .actionBarBackground(R.drawable.actionbar_brp)
                .headerLayout(R.layout.header_history)
                .headerOverlayLayout(R.layout.header_history_overlay)
                .contentLayout(R.layout.activity_history_editor)
                .parallax(false)
                .lightActionBar(false);
        return helper;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.history_editor, menu);
        return menu != null && menu.size() > 0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null && item.getItemId() == R.id.pick_birth) {
            //        onShowDatePickerCallback(mBirthDate, this);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        if (event != null) {
            HistoryEventDialog dialog = HistoryEventDialog.newInstance(event);
            dialog.show(getSupportFragmentManager(), HistoryEventDialog.TAG);
        }
    }

    @Override
    public void onHistoryEventsLoaded(TimeSpan span, Set<HistoryEvent> events) {
        if (events != null) {
            if (mHistoryAdapter == null) {
                mHistoryAdapter = new HistoryAdapter(this, this);
            }
            mHistoryAdapter.refreshData(events);
        }
    }

    @OnClick(R.id.fab)
    public void onAddHistoryEvent(View view) {
        HistoryEvent suggestedEventWitDate = getSuggestedDate();
        HistoryEventDialog dialog = HistoryEventDialog.newInstance(suggestedEventWitDate);
        dialog.show(getSupportFragmentManager(), HistoryEventDialog.TAG);
    }

    private HistoryEvent getSuggestedDate() {
        HistoryEvent event = new HistoryEvent();
        event.setDate(mSuggestedDate.getMillis());
        return event;
    }

    @Override
    public void onHistoryEventEdited(HistoryEvent old, HistoryEvent newEvent) {
        mSavedCharacter.removeHistoryEvent(old);
        mSavedCharacter.addHistoryEvent(newEvent);
        loadHistoryAsyn();
    }

//    @Override
//    public void onShowDatePickerCallback(DateTime forDateTime, CalendarDatePickerDialog.OnDateSetListener callback) {
//        if (forDateTime != null) {
//            CalendarDatePickerDialog d = CalendarDatePickerDialog.newInstance(callback,
//                    forDateTime.getYear(),
//                    forDateTime.getMonthOfYear(),
//                    forDateTime.getDayOfMonth());
//            int startYear = forDateTime.getYear() - 100;
//            int endYear = forDateTime.getYear() + 100;
//            d.setYearRange(startYear, endYear);
//            d.show(getSupportFragmentManager(), sCalendarDialogTag);
//        }
//    }


    private void setupBirthDate() {
        if (mSavedCharacter != null && mSavedCharacter.getBirth() != null) {
            BirthData birthData = mSavedCharacter.getBirth();
            mBirthDate = new DateTime(birthData.getDate());
        } else {
            CharacterSettings s = Settings.getLastPortratiSettings(this);
            int defaultYear = s.getCthulhuPeriod().getDefaultYear();
            int edu = mSavedCharacter.getStatisticValue(BRPStatistic.EDU.name());
            int suggestedAge = edu + 6;
            int estimateYear = defaultYear - suggestedAge;
            Random r = new Random();
            int month = r.nextInt(12);
            int day = r.nextInt(27);
            int h = r.nextInt(23);
            mBirthDate = new DateTime(estimateYear, month, day, h, 0);
            BirthData birth = new BirthData();
            birth.setDate(mBirthDate.getMillis());
            mSavedCharacter.setBirth(birth);
        }
        long suggestedEpoch = mSavedCharacter.getSuggestedDate();
        mSuggestedDate = new DateTime(suggestedEpoch);
    }


    @Override
    public CharacterSettings onGetCharacterSettings() {
        return Settings.getLastPortratiSettings(this);
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

//    @Override
//    public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog,
//                          int year, int monthOfYear, int yearOfMonth) {
//        int hour = mBirthDate.getHourOfDay();
//        int minute = mBirthDate.getMinuteOfHour();
//        mBirthDate = new DateTime(year, monthOfYear, yearOfMonth, hour, minute);
//        setBirthDayView();
//    }
}
