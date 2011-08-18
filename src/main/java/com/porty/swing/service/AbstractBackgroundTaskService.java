package com.porty.swing.service;

import com.porty.swing.dialog.BlockingProgressDialog;
import com.porty.swing.util.WindowUtils;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;

/**
 * Abstract implementation of the background service with some UI work prepared.
 * Has no actual execution code.
 *
 * @author iportyankin
 */
public abstract class AbstractBackgroundTaskService implements BackgroundTaskService {

    @Override
    public void executeIndeterminate(final Runnable work, final String message, final Component... components) {
        final List<Component> uiProgressComponent = new ArrayList<Component>(1);
        // disable the components
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (null != components) {
                    // disable components
                    for (Component next : components) {
                        next.setEnabled(false);
                    }
                    // create a progress component
                    uiProgressComponent.add(createProgressComponent(message));
                }
            }
        });
        Runnable proxy = new Runnable() {

            @Override
            public void run() {
                try {
                    work.run();
                } finally {
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
                            // dispose progress component
                            destroyProgressComponent(uiProgressComponent.get(0));
                        }
                    });
                }
            }
        };
        // execute proxy with actual work
        execute(proxy);
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
