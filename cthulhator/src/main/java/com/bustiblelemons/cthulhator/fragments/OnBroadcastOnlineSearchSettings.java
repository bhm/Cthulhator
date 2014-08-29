package com.bustiblelemons.cthulhator.fragments;

import com.bustiblelemons.cthulhator.model.CharacterSettings;

public interface OnBroadcastOnlineSearchSettings {
    void onSettingsChanged(CharacterSettings onlinePhotoSearchQuery, boolean apply);
}