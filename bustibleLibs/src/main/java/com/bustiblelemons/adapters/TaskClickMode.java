package com.bustiblelemons.adapters;

/**
 * Created by bhm on 22/04/14.
 */
public enum TaskClickMode {
    INCREMENT_TIME {
        @Override
        public TaskClickMode getOposite() {
            return DECREMENT_TIME;
        }
    }, DECREMENT_TIME {
        @Override
        public TaskClickMode getOposite() {
            return INCREMENT_TIME;
        }
    }, VIEW {
        @Override
        public TaskClickMode getOposite() {
            return INCREMENT_TIME;
        }
    };

    public abstract TaskClickMode getOposite();
}
