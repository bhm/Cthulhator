package com.bustiblelemons.cthulhator.creation.history.logic;

import android.content.Context;

import com.bustiblelemons.cthulhator.creation.history.model.TimeSpan;
import com.bustiblelemons.cthulhator.model.HistoryEvent;
import com.bustiblelemons.cthulhator.model.cache.SavedCharacter;

import java.util.Set;
import java.util.TreeSet;

import io.github.scottmaclure.character.traits.asyn.AbsAsynTask;

/**
 * Created by bhm on 29.09.14.
 */
public class LoadHistoryEventsAsyn
        extends AbsAsynTask<TimeSpan, Set<HistoryEvent>> {

    private final SavedCharacter        mSavedCharacter;
    private       OnHistoryEventsLoaded onHistoryEventsLoaded;

    public LoadHistoryEventsAsyn(Context context, SavedCharacter savedCharacter) {
        super(context);
        mSavedCharacter = savedCharacter;
    }

    public void setOnHistoryEventsLoaded(OnHistoryEventsLoaded onHistoryEventsLoaded) {
        this.onHistoryEventsLoaded = onHistoryEventsLoaded;
    }

    @Override
    protected Set<HistoryEvent> call(TimeSpan... params) throws Exception {
        for (TimeSpan timespan : params) {
            if (timespan != null) {
                Set<HistoryEvent> sorted = new TreeSet<HistoryEvent>(HistoryComparators.DATE_DES);
                sorted.addAll(mSavedCharacter.getFullHistory());
                Set<HistoryEvent> result = new TreeSet<HistoryEvent>(HistoryComparators.DATE_DES);
                for (HistoryEvent event : sorted) {
                    if (event != null) {
                        if (event.getDate() >= timespan.getBeginEpoch()
                                && event.getDate() <= timespan.getEndEpoch()) {
                            result.add(event);
                        }
                    }
                }
                publishProgress(timespan, result);
            }
        }
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(TimeSpan param, Set<HistoryEvent> result) {
        if (onHistoryEventsLoaded == null) {
            onHistoryEventsLoaded.onHistoryEventsLoaded(param, result);
        }
    }

    public interface OnHistoryEventsLoaded {
        /**
         * @param events set of sorted events by date;
         */
        void onHistoryEventsLoaded(TimeSpan span, Set<HistoryEvent> events);
    }
}
