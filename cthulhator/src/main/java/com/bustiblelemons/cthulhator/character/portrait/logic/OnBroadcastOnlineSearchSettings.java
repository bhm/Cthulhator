package com.bustiblelemons.cthulhator.character.portrait.logic;

import com.bustiblelemons.cthulhator.settings.character.CharacterSettings;

public interface OnBroadcastOnlineSearchSettings {
    void onSettingsChanged(CharacterSettings onlinePhotoSearchQuery, boolean apply);
}