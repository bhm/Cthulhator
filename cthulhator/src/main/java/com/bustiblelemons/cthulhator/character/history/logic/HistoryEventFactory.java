package com.bustiblelemons.cthulhator.character.history.logic;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.cthulhator.character.persistance.CharacterWrappper;
import com.bustiblelemons.cthulhator.settings.Settings;
import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;
import com.bustiblelemons.cthulhator.system.brp.statistics.BRPStatistic;
import com.bustiblelemons.randomuserdotme.model.Location;

import org.joda.time.DateTime;

import java.util.Random;

/**
 * Created by hiv on 13.11.14.
 */
public class HistoryEventFactory {

    private static HistoryEventFactory INSTANCE;
    private        Context             mContext;
    private        CharacterWrappper   mSavedCharacter;

    public HistoryEventFactory(Context context) {
        mContext = context;
    }

    public static HistoryEventFactory from(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new HistoryEventFactory(context);
        } else if (INSTANCE != null) {
            if (INSTANCE.mContext != null && !INSTANCE.mContext.equals(context)) {
                return INSTANCE = new HistoryEventFactory(context);
            }
        }
        return INSTANCE;
    }

    public HistoryEventFactory withCharacter(CharacterWrappper mSavedCharacter) {
        this.mSavedCharacter = mSavedCharacter;
        return this;
    }

    public HistoryEvent buildBirthEvent() {
        DateTime dateTime = getBirthDateTime();
        HistoryEvent birthEvent = new HistoryEvent();
        birthEvent.setDate(dateTime.getMillis());
        String name = mContext.getResources().getString(R.string.born);
        birthEvent.setName(name);
        if (mSavedCharacter != null && mSavedCharacter.getDescription() != null) {
            Location birthLocation = mSavedCharacter.getDescription().getLocation();
            if (birthLocation != null) {
                birthEvent.setLocation(birthLocation);
                String description = birthLocation.toString();
                birthEvent.setDescription(description);
            }
        }
        return birthEvent;
    }

    private DateTime getBirthDateTime() {
        CharacterSettings s = Settings.getLastPortraitSettings(mContext);
        int defaultYear = s.getCthulhuPeriod().getDefaultYear();
        int edu = 4;
        if (mSavedCharacter != null) {
            mSavedCharacter.getStatisticValue(BRPStatistic.EDU.name());
        }
        int suggestedAge = edu + 6;
        int estimateYear = defaultYear - suggestedAge;
        Random r = new Random();
        int month = r.nextInt(12);
        int day = r.nextInt(27);
        int h = r.nextInt(23);
        return new DateTime(estimateYear, month, day, h, 0);
    }
}
