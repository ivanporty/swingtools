package com.porty.swing;

import com.porty.swing.service.BackgroundTaskService;
import com.porty.swing.service.CachedBackgroundTaskService;

/**
 * Factory which provides access to current implementation of background task service.
 * It's instance could be substituted in order to change default library implementation.
 *
 * @author iportyankin
 */
public class BackgroundTaskServiceFactory {
    private static BackgroundTaskServiceFactory instance;
    // we use this implementation by default
    private CachedBackgroundTaskService taskService = new CachedBackgroundTaskService();

    public static BackgroundTaskServiceFactory getInstance() {
        if ( instance == null ) {
            instance = new BackgroundTaskServiceFactory();
        }
        return instance;
    }

    public static void setInstance(BackgroundTaskServiceFactory instance) {
        BackgroundTaskServiceFactory.instance = instance;
    }

    /**
     * Obtains currently used implementation of background task service.
     * @return Background task service
     */
    public BackgroundTaskService getBackgroundTaskService() {
        return taskService;
    }
}
