package com.porty.swing.service;

import javax.swing.SwingWorker;

/**
 *
 * @author iportyankin
 */
public interface BackgroundTaskService {
    /**
     * Executes SwingWorker task.
     * @param worker Worker to be executed.
     */
    void execute(SwingWorker<?,?> worker);
    /**
     * Executes Runnable on background thread, it's up to
     * Runnable's code to call invokeLater() to update the UI. Do
     * not update or work with UI from the Runnable.
     *
     * @param work Code to execute on background thread.
     */
    void execute(Runnable work);
}
