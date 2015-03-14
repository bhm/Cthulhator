package com.bustiblelemons.cthulhator.character.skills.model;

import com.bustiblelemons.cthulhator.R;

/**
 * Created by hiv on 14.03.15.
 */
public enum CreationWorkflowCard {
    HEADER {
        @Override
        public int getLayoutId() {
            return R.layout.creation_workflow_header;
        }
    }, DETAILS {
        @Override
        public int getLayoutId() {
            return R.layout.creation_workflow_single_details;
        }
    }, HISTORY {
        @Override
        public int getLayoutId() {
            return R.layout.creation_workflow_single_history;
        }
    }, STATS {
        @Override
        public int getLayoutId() {
            return R.layout.creation_workflow_single_stats;
        }
    }, EQUIPMENT {
        @Override
        public int getLayoutId() {
            return R.layout.creation_workflow_equipment;
        }
    };

    public abstract int getLayoutId();
}
