package com.bustiblelemons.cthulhator.character.history.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.creation.ui.AbsCharacterCreationActivity;
import com.bustiblelemons.cthulhator.character.history.logic.HistoryAdapter;
import com.bustiblelemons.cthulhator.character.history.logic.LoadHistoryEventsAsyn;
import com.bustiblelemons.cthulhator.character.history.logic.OnOpenHistoryEventDetails;
import com.bustiblelemons.cthulhator.character.history.logic.OnShowDatePicker;
import com.bustiblelemons.cthulhator.character.history.logic.ReportCharacterSettings;
import com.bustiblelemons.cthulhator.character.history.model.BirthData;
import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.cthulhator.character.history.model.TimeSpan;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import org.joda.time.DateTime;

import java.util.Random;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * Created by bhm on 22.09.14.
 */
public class HistoryEditorActivity extends AbsCharacterCreationActivity
        implements OnOpenHistoryEventDetails,
                   LoadHistoryEventsAsyn.OnHistoryEventsLoaded,
                   HistoryEventDialog.OnHistoryEventPassedBack,
                   OnShowDatePicker,
                   ReportCharacterSettings,
                   CalendarDatePickerDialog.OnDateSetListener {

    public static final  int    REQUEST_CODE       = 8;
    private static final String sDateFormat        = "MMM dd, yyyy";
    private static final String sCalendarDialogTag = CalendarDatePickerDialog.class.getSimpleName();
    @InjectView(R.id.list)
    StickyListHeadersListView listView;
    @InjectView(R.id.pick_birth)
    TextView                  pickBirthView;
    private TimeSpan span = TimeSpan.EMPTY;
    private SavedCharacter        mSavedCharacter;
    private HistoryAdapter        mHistoryAdapter;
    private LoadHistoryEventsAsyn mLoadHistoryAsyn;
    private DateTime              mBirthDate;
    private DateTime          mSuggestedDate;
    private Set<HistoryEvent> mFullHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onSetActionBarToClosable();
        setContentView(setupFadingBar().createView(this));
        ButterKnife.inject(this);
        onSetActionBarToClosable();
        mSavedCharacter = getInstanceArgument();
        if (listView != null) {
            mHistoryAdapter = new HistoryAdapter(this, this);
            listView.setAdapter(mHistoryAdapter);
            listView.setOnItemClickListener(mHistoryAdapter);
        }
        if (pickBirthView != null) {
            setupBirthDate();
            setBirthDayView();
        }
        loadHistoryAsyn();
    }

    private void setBirthDayView() {
        pickBirthView.setText(mBirthDate.toString(sDateFormat));
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

    private void loadHistoryAsyn() {
        if (mSavedCharacter != null) {
            mLoadHistoryAsyn = new LoadHistoryEventsAsyn(this, mSavedCharacter);
            mLoadHistoryAsyn.setOnHistoryEventsLoaded(this);
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
    public void onHistoryEventsLoaded(TimeSpan span, Set<HistoryEvent> events) {
        if (span != null && events != null) {
            if (mHistoryAdapter == null) {
                mHistoryAdapter = new HistoryAdapter(this, this);
            } else {
                mHistoryAdapter.removeAll();
            }
            mHistoryAdapter.addItems(events);
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
        if (mFullHistory == null) {
            mFullHistory = mSavedCharacter.getFullHistory();
        }
        if (mHistoryAdapter == null) {
            mHistoryAdapter = new HistoryAdapter(this, this);
            listView.setAdapter(mHistoryAdapter);
        }
        if (old != null) {
            mFullHistory.remove(old);
            mHistoryAdapter.removeItem(old);
        }
        mHistoryAdapter.addFirst(newEvent);
        mFullHistory.add(newEvent);
    }

    @Override
    public void onShowDatePickerCallback(DateTime forDateTime, CalendarDatePickerDialog.OnDateSetListener callback) {
        if (forDateTime != null) {
            CalendarDatePickerDialog d = CalendarDatePickerDialog.newInstance(callback,
                    forDateTime.getYear(),
                    forDateTime.getMonthOfYear(),
                    forDateTime.getDayOfMonth());
            int startYear = forDateTime.getYear() - 100;
            int endYear = forDateTime.getYear() + 100;
            d.setYearRange(startYear, endYear);
            d.show(getSupportFragmentManager(), sCalendarDialogTag);
        }
    }

    @OnClick(R.id.pick_birth)
    public void onPickBirthday(View view) {
        onShowDatePickerCallback(mBirthDate, this);
    }

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
        long suggestedEpoch = mSavedCharacter.getSuggestedDateForEvent();
        mSuggestedDate = new DateTime(suggestedEpoch);
    }


    @Override
    public CharacterSettings onGetCharacterSettings() {
        return Settings.getLastPortratiSettings(this);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog,
                          int year, int monthOfYear, int yearOfMonth) {
        int hour = mBirthDate.getHourOfDay();
        int minute = mBirthDate.getMinuteOfHour();
        mBirthDate = new DateTime(year, monthOfYear, yearOfMonth, hour, minute);
        setBirthDayView();
    }
}
