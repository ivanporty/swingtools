package com.porty.swing.service;

import java.awt.Component;
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

    /**
     * Executes runnable showing the progress bar with the message until the task is complete.
     * @param work Work to be executed.
     * @param message Message to be shown
     * @param components Components to be disabled while task is executing
     */
    void executeIndeterminate(Runnable work, String message, Component... components);
}
