/**
 * 
 */
package org.kroz.activerecord;

import java.sql.Timestamp;
import java.util.List;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.test.fixtures.TestConst;
import org.kroz.activerecord.test.fixtures.User;
import org.kroz.activerecord.test.fixtures.UserData;

import android.content.Context;
import android.test.AndroidTestCase;

/**
 * @author VKROZ
 * 
 */
public class EntityTest extends AndroidTestCase {

	// ----------------- Fixture START --------------------//
	int counter;
	String _dbName;
	Context _ctx;
	DatabaseBuilder _builder;

	// ----------------- Fixture END--------------------//

	protected void setUp() throws Exception {
		super.setUp();
		_dbName = TestConst.DB_NAME;
		_ctx = getContext();
		_builder = new DatabaseBuilder(TestConst.DB_NAME);
		_builder.addClass(User.class);
		_builder.addClass(UserData.class);
		Database.setBuilder(_builder);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link org.kroz.activerecord.ActiveRecordBase#test()}.
	 */
	public void testCreateEntity() {
		ActiveRecordBase conn = null;

		// Open DB
		try {
			conn = ActiveRecordBase.open(_ctx, _dbName, 1);
		} catch (ActiveRecordException e) {
			fail(e.getLocalizedMessage());
		}

		// Insert records
		try {
			User usr1 = conn.newEntity(User.class);
			usr1.firstName = "John";
			usr1.lastName = "Smith";
			usr1.registrationDate = new Timestamp(System.currentTimeMillis());
			usr1.ssn = 1234567890;
			usr1.save();

			User usr2 = conn.newEntity(User.class);
			usr2.firstName = "John2";
			usr2.save();

			usr2.firstName = "Some3";
			usr2.save();

		} catch (ActiveRecordException e) {
			fail(e.getLocalizedMessage());
		}

		// Find all records
		try {
			List<User> u = conn.findAll(User.class);
			assertNotNull(u);
			assertEquals(2, u.size());
		} catch (ActiveRecordException e) {
			fail(e.getLocalizedMessage());
		}

		// Close DB
		conn.close();

	}

	/**
	 * Test method for {@link org.kroz.activerecord.ActiveRecordBase#test()}.
	 */
	public void testFindEntity() {
		// Open DB
		try {

			ActiveRecordBase conn = ActiveRecordBase.open(_ctx, _dbName, 1);
			List<User> u = conn.findAll(User.class);
			assertNotNull(u);
			conn.close();
		} catch (ActiveRecordException e) {
			fail(e.getLocalizedMessage());
		}

	}

}
