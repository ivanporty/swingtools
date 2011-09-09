package demo.table;

import com.porty.swing.table.model.BeanPropertyTableModel;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

/**
 * Demoes usage of bean property table model.
 * @author iportyankin
 */
public class SimpleReflectionTable extends JFrame {

    public SimpleReflectionTable() {
        super("Bean Property Table Model Demo");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 300);
        final JTable table = new JTable();
        add(new JScrollPane(table));
        BeanPropertyTableModel<TableBean> model = new BeanPropertyTableModel<TableBean>(TableBean.class);
        model.setOrderedProperties(Arrays.asList("name","surname","date"));
        model.setData(TableBean.generateList(100));
        table.setModel(model);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new SimpleReflectionTable();
            }
        });
    }

}
