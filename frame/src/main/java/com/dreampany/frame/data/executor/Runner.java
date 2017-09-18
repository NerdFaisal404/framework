package com.dreampany.frame.data.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nuc on 6/15/2017.
 */

public class Runner {

    private final ExecutorService executor;

    public Runner() {
        executor = Executors.newSingleThreadExecutor();
       // executor.
    }


}
