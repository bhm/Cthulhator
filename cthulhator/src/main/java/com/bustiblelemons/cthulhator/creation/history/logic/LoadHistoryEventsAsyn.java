package com.bustiblelemons.cthulhator.creation.history.logic;

import android.content.Context;
import android.util.Pair;

import com.bustiblelemons.cthulhator.creation.history.model.HistoryEventHeader;
import com.bustiblelemons.cthulhator.creation.history.model.TimeSpan;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import org.joda.time.LocalDate;

import java.util.Set;
import java.util.TreeSet;

import io.github.scottmaclure.character.traits.asyn.AbsAsynTask;

/**
 * Created by bhm on 29.09.14.
 */
public class LoadHistoryEventsAsyn
        extends AbsAsynTask<TimeSpan, Pair<HistoryEventHeader, Set<HistoryEvent>>> {

    private final SavedCharacter        mSavedCharacter;
    private       OnHistoryEventsLoaded onHistoryEventsLoaded;

    public LoadHistoryEventsAsyn(Context context, SavedCharacter savedCharacter) {
        super(context);
        mSavedCharacter = savedCharacter;
    }

    @Override
    protected Pair<HistoryEventHeader, Set<HistoryEvent>> call(TimeSpan... params) throws
                                                                                   Exception {
        for (TimeSpan timeSpan : params) {
            if (timeSpan == null) {
                Set<HistoryEvent> sorted = new TreeSet<HistoryEvent>(HistoryEvent.COMPARATOR_DES);
                sorted.addAll(mSavedCharacter.getFullHistory());
                HistoryEventHeader header = new HistoryEventHeader();
                LocalDate headingDate = null;
                LocalDate nextDate = null;
                Set<HistoryEvent> splitByDates = new TreeSet<HistoryEvent>(
                        HistoryEvent.COMPARATOR_DES);
                for (HistoryEvent event : sorted) {
                    if (event != null) {
                        long date = event.getDate();
                        if (headingDate == null) {
                            headingDate = new LocalDate(event.getDate());
                            header = new HistoryEventHeader();
                            header.setTitle(
                                    headingDate.toString(HistoryEventHeader.DATE_FORMAT_LONG));
                            header.setDate(date);
                            continue;
                        }
                        nextDate = new LocalDate(date);
                        if (nextDate.monthOfYear() != null && headingDate.monthOfYear() != null) {
                            if (nextDate.monthOfYear().equals(headingDate.monthOfYear())) {
                                splitByDates.add(event);
                            } else {
                                headingDate = new LocalDate(event.getDate());
                                Pair<HistoryEventHeader, Set<HistoryEvent>> pair = Pair.create(
                                        header, splitByDates);
                                publishProgress(timeSpan, pair);
                                splitByDates = new TreeSet<HistoryEvent>(
                                        HistoryEvent.COMPARATOR_DES);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(TimeSpan param, Pair<HistoryEventHeader, Set<HistoryEvent>> pairResult) {
        if (onHistoryEventsLoaded == null) {
            Set<HistoryEvent> result = pairResult.second;
            HistoryEventHeader header = pairResult.first;
            onHistoryEventsLoaded.onHistoryEventsLoaded(param, header, result);
        }
    }

    public interface OnHistoryEventsLoaded {
        /**
         * @param events set of sorted events by date;
         */
        void onHistoryEventsLoaded(TimeSpan span, HistoryEventHeader header, Set<HistoryEvent> events);
    }
}
