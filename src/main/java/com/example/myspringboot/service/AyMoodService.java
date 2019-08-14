package com.example.myspringboot.service;

import com.example.myspringboot.model.AyMood;

public interface AyMoodService {

    AyMood save(AyMood ayMood);

    String asynSave(AyMood ayMood);
}
