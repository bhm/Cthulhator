package com.bustiblelemons.cthulhator.configuration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bustiblelemons.activities.AbsActionBarActivity;
import com.bustiblelemons.api.model.Gender;
import com.bustiblelemons.api.random.names.randomuserdotme.RandomUserMEQuery;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.OnRandomUsersRetreived;
import com.bustiblelemons.api.random.names.randomuserdotme.asyn.RandomUserDotMeAsyn;
import com.bustiblelemons.api.random.names.randomuserdotme.model.User;
import com.bustiblelemons.cthulhator.R;
import com.bustiblelemons.cthulhator.activities.CharactersListActivity;
import com.bustiblelemons.cthulhator.cache.RandomUsersCache;
import com.bustiblelemons.model.OnlinePhotoUrl;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import at.markushi.ui.CircleButton;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by bhm on 27.09.14.
 */
public class ConfigurationActivity extends AbsActionBarActivity
        implements OnRandomUsersRetreived,
                   RandomUserDotMeAsyn.OnFinishAllQueries {

    private static final int DEFAULT_AMOUNT = 100;
    @InjectView(android.R.id.title)
    TextView     titleView;
    @InjectView(android.R.id.closeButton)
    CircleButton closeButton;

    private Set<RandomUserMEQuery> mQueries;
    private int                    mUsersCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        ButterKnife.inject(this);
    }

    private void triggerCaching(int numberOfQueries) {
        if (mQueries == null) {
            mQueries = new HashSet<RandomUserMEQuery>(numberOfQueries);
        }
        for (int i = 0; i < numberOfQueries / 3; i++) {
            RandomUserMEQuery q = new RandomUserMEQuery.Options()
                    .setResults(20).setGender(Gender.ANY).build();
            mQueries.add(q);
        }
        for (int i = 0; i < numberOfQueries / 3; i++) {
            RandomUserMEQuery q = new RandomUserMEQuery.Options()
                    .setResults(20).setGender(Gender.FEMALE).build();
            mQueries.add(q);
        }
        for (int i = 0; i < numberOfQueries / 3; i++) {
            RandomUserMEQuery q = new RandomUserMEQuery.Options()
                    .setResults(20).setGender(Gender.MALE).build();
            mQueries.add(q);
        }
        RandomUserDotMeAsyn asyn = new RandomUserDotMeAsyn(this, this);
        asyn.setOnFinishAllQueries(this);
        RandomUserMEQuery[] array = new RandomUserMEQuery[mQueries.size()];
        asyn.executeCrossPlatform(mQueries.toArray(array));
    }


    @OnClick(android.R.id.title)
    public void onTitleClick(TextView view) {
        if (closeButton != null) {
            closeButton.setVisibility(View.INVISIBLE);
        }
        triggerCaching(DEFAULT_AMOUNT);
    }

    @OnClick(android.R.id.closeButton)
    public void onDoneClick(CircleButton button) {
        Intent i = new Intent(this, CharactersListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_top);
    }

    @Override
    public int onRandomUsersRetreived(RandomUserMEQuery query, List<User> users) {
        if (users != null) {
            if (titleView != null) {
                mUsersCount = saveUsers(users);
                String downloaded = String.format(Locale.ENGLISH, "%s users saved", mUsersCount);
                titleView.setText(downloaded);
            }
        }
        return 0;
    }

    private int saveUsers(List<User> users) {
        return RandomUsersCache.saveUsers(this, users);
    }

    @Override
    public int onRandomUsersLocations(RandomUserMEQuery query, List<User> users) {
        return 0;
    }

    @Override
    public int onRandomUsersNames(RandomUserMEQuery query, List<User> users) {
        return 0;
    }

    @Override
    public int onRandomUsersPortraits(RandomUserMEQuery query, List<OnlinePhotoUrl> users) {
        return 0;
    }

    @Override
    public void onFinishAllQueries(RandomUserDotMeAsyn async, int total) {
        if (closeButton != null) {
            closeButton.setVisibility(View.VISIBLE);
        }
    }
}
