package com.porty.swing.service;

import com.porty.swing.BackgroundTaskServiceFactory;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import junit.framework.TestCase;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

/**
 * Tests various background tasks services.
 * 
 * @author iportyankin
 */
public class TestBackgroundTaskService extends TestCase {

    private FrameFixture frameFixture;
    TestFrame frame;

    @Override
    public void setUp() {
        frame = GuiActionRunner.execute(new GuiQuery<TestFrame>() {

            @Override
            protected TestFrame executeInEDT() throws Throwable {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                FailOnThreadViolationRepaintManager.install();
                return new TestFrame();
            }
        });
        frameFixture = new FrameFixture(frame);
    }

    // tests running the task and UI construction
    public void testBackgroundTaskWithUI() {

        // pause on start
        Pause.pause(1000);
        // push the button and start the process
        frameFixture.button("runSlow").click();
        // this should disable the button
        Pause.pause(200);
        frameFixture.button("runSlow").requireDisabled();
        // and show the dialog with indeterminate progress bar
        frameFixture.dialog().requireVisible();
        frameFixture.dialog().progressBar().requireIndeterminate();

        Pause.pause(3000);

        // run the quick - appear and disappear, hard to test
        frameFixture.button("runQuick").click();
        Pause.pause(50);

        Pause.pause(5000);
    }

    class TestFrame extends JFrame {

        TestFrame() {
            setSize(400, 300);
            final JButton b = new JButton("Run!");
            add(b, "North");
            b.setName("runSlow");
            b.setAction(new AbstractAction("Run!") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {

                        @Override
                        protected String doInBackground() throws Exception {
                            System.out.println("doInBackground" + Thread.currentThread());
                            Thread.sleep(1000);
                            publish();
                            Thread.sleep(1000);
                            return "";
                        }

                        @Override
                        protected void done() {
                            System.out.println("done!" + Thread.currentThread());
                        }

                        @Override
                        protected void process(List<Void> chunks) {
                            System.out.println("processing..." + Thread.currentThread());
                        }
                    };
                    BackgroundTaskServiceFactory.getInstance().getBackgroundTaskService().executeIndeterminate(
                            worker, "Working slow...", b);
                }
            });

            final JButton b2 = new JButton("Run Quick!");
            add(b2, "South");
            b2.setName("runQuick");
            b2.setAction(new AbstractAction("Run Quick!") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {

                        @Override
                        protected String doInBackground() throws Exception {
                            System.out.println("doInBackground quick" + Thread.currentThread());
                            Thread.sleep(10);
                            return "";
                        }
                    };
                    BackgroundTaskServiceFactory.getInstance().getBackgroundTaskService().executeIndeterminate(
                            worker, "Working quick...", b);
                }
            });
            setVisible(true);
        }
    }
}
