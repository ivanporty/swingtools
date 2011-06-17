
package com.porty.swing.table;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author iportyankin
 */
public class SimpleTableModel extends AbstractTableModel {

    private List data = new ArrayList();

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                // either toString() or a slightly different data processing
                return data.get(rowIndex).toString();
                // many more case,case,case for each column...
            default:
                return "";
        }
    }
}
