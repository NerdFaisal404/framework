package com.dreampany.frame.data.manager;

/**
 * Created by nuc on 7/5/2017.
 */

public class FileManager extends Manager {

    public FileManager() {

    }

    @Override
    protected boolean looping() throws InterruptedException {
        return false;
    }
}
