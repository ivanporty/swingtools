package com.porty.swing.table;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.table.TableCellRenderer;
import net.coderazzi.filters.gui.TableFilterHeader;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 * Base table extends SwingX JXTable and provides a few more functions which are
 * used frequently for the tables, including additional events automatic action binding,
 * table filter header etc.
 *
 * Double click actions should be added into action map with name "doubleClick".
 *
 * @author iportyankin
 * @version 1.0
 */
public class BaseTable extends JXTable {
    /** Filter header used in the table */
    private TableFilterHeader tableFilterHeader;
    /** Flag to enable or disable filter header */
    private boolean filterHeaderEnabled;

    public BaseTable() {
        // always enable column control and striping
        setColumnControlVisible(true);
        setHighlighters(HighlighterFactory.createSimpleStriping());
    }

    public boolean isFilterHeaderEnabled() {
        return filterHeaderEnabled;
    }

    /**
     * Enables or disables and hides filtering header
     * @param filterHeaderEnabled True to enable and attach filter header component
     */
    public void setFilterHeaderEnabled(boolean filterHeaderEnabled) {
        // if filter was off and enabled, create and attach it
        if (filterHeaderEnabled && !this.filterHeaderEnabled && tableFilterHeader == null) {
            // enable the filter header
            tableFilterHeader = new TableFilterHeader(this);
        }
        if (tableFilterHeader != null) {
            tableFilterHeader.setVisible(filterHeaderEnabled);
            tableFilterHeader.setEnabled(filterHeaderEnabled);
        }
        this.filterHeaderEnabled = filterHeaderEnabled;
    }

    /**
     *
     * @return Filter header component used in the table or null if never used
     */
    public TableFilterHeader getTableFilterHeader() {
        return tableFilterHeader;
    }


    /**
     * Overriden to set the tooltip text for the label-based renderers.
     * @param renderer
     * @param rowIndex
     * @param vColIndex
     * @return
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer,
            int rowIndex, int vColIndex) {
        Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
        if (c instanceof JLabel) {
            JLabel label = (JLabel) c;
            label.setToolTipText(label.getText());
        }
        return c;
    }
}
