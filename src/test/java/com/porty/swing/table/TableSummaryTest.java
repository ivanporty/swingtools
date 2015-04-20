package com.porty.swing.table;

import com.porty.swing.table.model.BeanPropertyTableModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import junit.framework.TestCase;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.edt.GuiTask;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.timing.Pause;

/**
 * Tests construction of dynamic tables based on data class.
 * This tests require UI and uses FEST to manipulate Swing components and data.
 * 
 * @author iportyankin
 * @version 1.1
 */
public class TableSummaryTest extends TestCase {

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

    // tests the summary behaviour using the common shared component
    public void testSummaryTablePanel() {
        // prepare test table set
        GuiActionRunner.execute(new GuiTask() {

            @Override
            protected void executeInEDT() throws Throwable {
                // create main table
                BeanPropertyTableModel<IntegerTableBean> tableModel = new BeanPropertyTableModel<IntegerTableBean>(IntegerTableBean.class);
                BaseTable table = new BaseTable();
                table.setModel(tableModel);
                table.setName("maintable");
                table.setHorizontalScrollEnabled(true);
                tableModel.setData(IntegerTableBean.create(100));

                // summary table
                BeanPropertyTableModel<IntegerTableBean> sumTableModel = new BeanPropertyTableModel<IntegerTableBean>(IntegerTableBean.class);
                BaseTable summaryTable = new BaseTable();
                summaryTable.setModel(sumTableModel);
                sumTableModel.setData(IntegerTableBean.create(1));
                summaryTable.setHorizontalScrollEnabled(true);

                ((JFrame)frameFixture.target).getContentPane().removeAll();
                frameFixture.target.add(new SummaryTablePanel(table, summaryTable));
                frameFixture.target.validate();
                
                table.packAll();
            }
        });
        Pause.pause(5000);
    }

    @Override
    protected void tearDown() throws Exception {
        // has to execute this manually as filter header messes up with removal
        GuiActionRunner.execute(new GuiTask() {

            @Override
            protected void executeInEDT() throws Throwable {
                ((BaseTable) frameFixture.table("maintable").target).setFilterHeaderEnabled(false);
                frameFixture.target.removeAll();
            }
        });
        frameFixture.cleanUp();
    }

    class TestFrame extends JFrame {

        TestFrame() {
            setSize(700, 300);
            setTitle("TestSummaryPanel");

            JScrollPane jScrollPane = new JScrollPane();
            jScrollPane.setName("mainScroll");

            add(jScrollPane);

            setVisible(true);
        }
    }

    public static class IntegerTableBean {
        private Integer credit, debit, remaining, creditLimitAsDefinedByOurBank, blockedInATMTransactions, loanAmountAverageOnTheDate;

        public Integer getCredit() {
            return credit;
        }

        public void setCredit(Integer credit) {
            this.credit = credit;
        }

        public Integer getDebit() {
            return debit;
        }

        public void setDebit(Integer debit) {
            this.debit = debit;
        }

        public Integer getRemaining() {
            return remaining;
        }

        public void setRemaining(Integer remaining) {
            this.remaining = remaining;
        }

        public Integer getBlockedInATMTransactions() {
            return blockedInATMTransactions;
        }

        public void setBlockedInATMTransactions(Integer blockedInATMTransactions) {
            this.blockedInATMTransactions = blockedInATMTransactions;
        }

        public Integer getCreditLimitAsDefinedByOurBank() {
            return creditLimitAsDefinedByOurBank;
        }

        public void setCreditLimitAsDefinedByOurBank(Integer creditLimitAsDefinedByOurBank) {
            this.creditLimitAsDefinedByOurBank = creditLimitAsDefinedByOurBank;
        }

        public Integer getLoanAmountAverageOnTheDate() {
            return loanAmountAverageOnTheDate;
        }

        public void setLoanAmountAverageOnTheDate(Integer loanAmountAverageOnTheDate) {
            this.loanAmountAverageOnTheDate = loanAmountAverageOnTheDate;
        }


        

        static List<IntegerTableBean> create(int rows) {
            List<IntegerTableBean> list = new ArrayList<IntegerTableBean>();
            for (int i = 0; i < rows; i++) {
                IntegerTableBean bean = new IntegerTableBean();
                bean.setCredit((int)(Math.random()*100));
                bean.setDebit(- (int)(Math.random()*100));
                bean.setRemaining((int)(Math.random()*200));
                bean.setBlockedInATMTransactions(-(int)(Math.random()*100));
                bean.setCreditLimitAsDefinedByOurBank((int)(Math.random()*10000));
                bean.setLoanAmountAverageOnTheDate((int)(Math.random()*100000));

                list.add(bean);
            }
            return list;
        }
        
    }

}
