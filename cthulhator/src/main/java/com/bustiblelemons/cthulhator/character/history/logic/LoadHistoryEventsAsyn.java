package com.bustiblelemons.cthulhator.character.history.logic;

import android.content.Context;

import com.bustiblelemons.async.AbsSimpleAsync;
import com.bustiblelemons.cthulhator.character.characterslist.model.SavedCharacter;
import com.bustiblelemons.cthulhator.character.history.model.HistoryEvent;
import com.bustiblelemons.cthulhator.character.history.model.TimeSpan;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by bhm on 29.09.14.
 */
public class LoadHistoryEventsAsyn
        extends AbsSimpleAsync<TimeSpan, Set<HistoryEvent>> {

    private final SavedCharacter        mSavedCharacter;
    private       OnHistoryEventsLoaded onHistoryEventsLoaded;
    private       TimeSpan              mTimespan;

    public LoadHistoryEventsAsyn(Context context, SavedCharacter savedCharacter) {
        super(context);
        mSavedCharacter = savedCharacter;
    }

    public void setOnHistoryEventsLoaded(OnHistoryEventsLoaded onHistoryEventsLoaded) {
        this.onHistoryEventsLoaded = onHistoryEventsLoaded;
    }

    @Override
    protected Set<HistoryEvent> call(TimeSpan... params) throws Exception {
        Set<HistoryEvent> result = new TreeSet<HistoryEvent>(HistoryComparators.DATE_DES);
        Collection<HistoryEvent> fullHistory = mSavedCharacter.getFullHistory();
        HistoryEvent birthEvent = HistoryEventFactory.from(getContext())
                .withCharacter(mSavedCharacter)
                .buildBirthEvent();
        fullHistory.add(birthEvent);
        result.addAll(fullHistory);
        publishProgress(mTimespan, result);
        return null;
    }

    @Override
    protected boolean onException(Exception e) {
        return false;
    }

    @Override
    public void onProgressUpdate(TimeSpan param, Set<HistoryEvent> result) {
        if (onHistoryEventsLoaded != null) {
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
