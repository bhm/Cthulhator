package com.bustiblelemons.cthulhator.fragments;

import com.bustiblelemons.fragments.PagerTitle;

import java.io.Serializable;

/**
 * Created by bhm on 02.08.14.
 */
public abstract class AbsFragmentWithSerializable<A extends Serializable>
        extends AbsFragmentWithArg<A>
        implements PagerTitle {
}