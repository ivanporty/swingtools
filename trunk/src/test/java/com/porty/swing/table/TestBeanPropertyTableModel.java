package com.porty.swing.table;

import com.porty.swing.table.model.BeanPropertyTableModel;
import com.porty.swing.util.TestDataGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import junit.framework.TestCase;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

/**
 * Set of tests for dynamically constructed bean table model, including simple performance test.
 * 
 * @author iportyankin
 */
public class TestBeanPropertyTableModel extends TestCase {

    private FrameFixture frameFixture;

    public void testLargeSet() {
        // prepare test table set
        for (int i = 0; i < 5; i++) {

            GuiActionRunner.execute(new GuiTask() {

                @Override
                protected void executeInEDT() throws Throwable {
                    JFrame f = new JFrame("Test");
                    frameFixture = new FrameFixture(f);
                    f.setSize(600, 400);
                    final BeanPropertyTableModel model = new BeanPropertyTableModel(TestTableBean.class);
                    JTable table = new JTable(model);
                    f.add(new JScrollPane(table));
                    f.setVisible(true);

                    long t1 = System.currentTimeMillis();
                    model.setData(getDataSet(1000));
                    long t2 = System.currentTimeMillis();
                    System.out.println("populate time: " + (t2 - t1));
                }
            });
            Pause.pause(2000);
            frameFixture.cleanUp();
        }
    }

    private List<TestTableBean> getDataSet(int size) {
        List<TestTableBean> data = new ArrayList<TestTableBean>();
        for (int i = 0; i < size; i++) {
            TestTableBean testBean = new TestTableBean();
            try {
                TestDataGenerator.populateBean(testBean);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            data.add(testBean);
        }
        return data;
    }
}
