package com.porty.swing.service;

import com.porty.swing.dialog.BlockingProgressDialog;
import com.porty.swing.util.WindowUtils;
import java.awt.Component;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * Abstract implementation of the background service with some UI work prepared.
 * Has no actual execution code.
 *
 * @author iportyankin
 */
public abstract class AbstractBackgroundTaskService implements BackgroundTaskService {

    @Override
    public void executeIndeterminate(final Runnable work, String message, final Component... components) {
        final Future<Component> progressTask = createGenericProgressBarTask(message);
        // disable the components
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (null != components) {
                    // disable components
                    for (Component next : components) {
                        next.setEnabled(false);
                    }
                }
            }
        });
        Runnable proxy = new Runnable() {

            @Override
            public void run() {
                try {
                    work.run();
                } finally {
                    // hide the progress in any case
                    finishGenericProgressBarTask(progressTask);
                    // enable the components back
                    SwingUtilities.invokeLater(new Runnable() {

                        @Override
                        public void run() {
                            if (null != components) {
                                // disable components
                                for (Component next : components) {
                                    next.setEnabled(true);
                                }
                            }
                        }
                    });
                }
            }
        };
        // execute proxy with actual work
        execute(proxy);
    }

    private Future<Component> createGenericProgressBarTask(final String message) {
        // component work may not even be created when task finishes
        Callable<Component> uiWork = new Callable<Component>() {

            @Override
            public Component call() throws Exception {
                return createProgressComponent(message);
            }
        };
        final FutureTask futureTask = new FutureTask(uiWork);
        SwingUtilities.invokeLater(futureTask);

        return futureTask;
    }

    private void finishGenericProgressBarTask(Future<Component> progressTask) {
        try {
            // ui is ready
            final Component c = progressTask.get();
            // destroy on UI thread
            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    destroyProgressComponent(c);
                }
            });

        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractBackgroundTaskService.class.getName()).log(Level.SEVERE, "cannot obtain progress UI component", ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(AbstractBackgroundTaskService.class.getName()).log(Level.SEVERE, "cannot obtain progress UI component", ex.getCause());
        }
    }

    protected Component createProgressComponent(String message) {
        BlockingProgressDialog dialog = new BlockingProgressDialog(null, message);
        dialog.setVisible(true);
        WindowUtils.centerWindow(dialog);
        return dialog;
    }

    protected void destroyProgressComponent(Component c) {
        ((BlockingProgressDialog) c).dispose();
    }
}
