package dao.oracle.testrunner;

import dao.oracle.OracleDAOBidsTest;
import dao.oracle.OracleDAOItemsTest;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
/**
 * In this class using construction 
 * "sute.addTest("<test_Name>") for greater understanding
 * @author Denis_Shipilov
 *
 */
public class RunTests {

	public static void suite() {
		TestRunner runner = new TestRunner();
		TestSuite suite = new TestSuite("Test for dao.oracle.testrunner");
		//$JUnit-BEGIN$
		//tests for items
		suite.addTest(new OracleDAOItemsTest("testSelectItems"));
		suite.addTest(new OracleDAOItemsTest("testDeleteItem"));
		suite.addTest(new OracleDAOItemsTest("testFindItemDescription"));
		suite.addTest(new OracleDAOItemsTest("testFindItemTitle"));
		suite.addTest(new OracleDAOItemsTest("testFindItemId"));
		suite.addTest(new OracleDAOItemsTest("testInsertItem"));
		//tests for bids
		suite.addTest(new OracleDAOBidsTest("testInsertBid"));
		suite.addTest(new OracleDAOBidsTest("testSelfInsert"));
		suite.addTest(new OracleDAOBidsTest("testLittleBid"));
		suite.addTest(new OracleDAOBidsTest("testTradesCompleted"));
		suite.addTest(new OracleDAOBidsTest("testBuyItNow"));
		//$JUnit-END$
		runner.doRun(suite);
	}

}
