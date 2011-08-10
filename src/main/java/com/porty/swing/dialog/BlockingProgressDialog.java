package com.porty.swing.dialog;

import com.porty.swing.Resources;
import com.porty.swing.util.LayoutUtils;
import java.awt.BorderLayout;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 * Simple modal dialog which can block user activity while opened. Shows
 * progress bar, either indeterminate (default) or one can show the real progress if available. By default
 * located on top of all other windows.
 *
 * @author iportyankin
 * @version 1.0
 */
public class BlockingProgressDialog extends JDialog {
    private JLabel label;
    private String text;
    private JProgressBar progressBar;

    public BlockingProgressDialog(Window parent, String text) {
        super(parent, Resources.getMainResourceBundle().getString("blockingDialog.title"), ModalityType.MODELESS);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.text = text;
        initComponents();
        // packed size
        pack();
        // make a bit wider
        setSize(350, getHeight());
        if (parent != null) {
            // center relative to parent window
            setLocation(parent.getX() + parent.getWidth() / 2 - getWidth() / 2,
                    parent.getY() + parent.getHeight() / 2 - getHeight() / 2);
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout(LayoutUtils.getGenericRelatedGap(), LayoutUtils.getGenericRelatedGap()));
        final int gap = LayoutUtils.getGenericContainerGap();
        ((JComponent)getContentPane()).setBorder(BorderFactory.createEmptyBorder( gap, gap, gap, gap));
        label = new JLabel(text);
        add(label, "North");
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        add(progressBar);
    }

    public void setText(String text) {
        this.text = text;
        label.setText(text);
    }

    public void setBlocking(boolean block) {
        if (block) {
            setModalityType(ModalityType.APPLICATION_MODAL);
        } else {
            setModalityType(ModalityType.MODELESS);
        }
    }

}