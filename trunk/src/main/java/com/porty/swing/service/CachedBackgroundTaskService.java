package com.porty.swing.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingWorker;

/**
 * Background task factory which executes threads using the cached pool of threads.
 * @author iportyankin
 */
public class CachedBackgroundTaskService extends AbstractBackgroundTaskService {

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
