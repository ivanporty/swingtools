package com.porty.swing.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingWorker;

/**
 * 
 * @author iportyankin
 */
public class CachedBackgroundTaskService implements BackgroundTaskService {

    private ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public void execute(SwingWorker<?, ?> worker) {
        executorService.execute(worker);
    }

    @Override
    public void execute(Runnable work) {
        executorService.execute(work);
    }
}
