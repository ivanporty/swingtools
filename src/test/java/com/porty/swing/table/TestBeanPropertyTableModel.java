package com.porty.swing.table;

import com.porty.swing.util.TestDataGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import junit.framework.TestCase;

/**
 *
 * @author iportyankin
 */
public class TestBeanPropertyTableModel extends TestCase {

    public void testLargeSet() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame f = new JFrame("Test");
                f.setSize(600, 400);
                final BeanPropertyTableModel model = new BeanPropertyTableModel(TestTableBean.class);
                JTable table = new JTable(model);
                f.add(new JScrollPane(table));
                f.setVisible(true);

                long t1 = System.currentTimeMillis();
                model.setData(getDataSet(1000));
                long t2 = System.currentTimeMillis();
                System.out.println("populate time: " + (t2 - t1) );
            }
        });
        Thread.sleep(2000);
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
