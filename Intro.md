'Android-Active-Record’ was created to simplify and streamline operations with Android SQLite database. It allows to define DB schema and persist your Java objects by just calling a Java APIs, whiteout boilerplate code for mapping between Java and SQL.
Actually the library combines paradigms introduced by **JavaEE Persistency Framework** and **ActiveRecord** from Ruby on Rails.

In the heart of the framework is `ActiveRecordBase` class which provides APIs to perform transparent CRUD (Create/Retrieve/Update/Delete) operations on Java classes.

### Use example ###
Assume you have a table ‘USER’ and you need to store and retrieve information in it. The table structure is

```
CREATE TABLE USER 
(
	_id integer primary key, 
	FIRST_NAME text, 
	LAST_NAME test
);
```

So, basically all you need to do for making CRUD operations on this table is to create corresponding enity class:

```
/**
 * Example entity. Class name corresponds to a database table;
 * class attributes correspond to table fields.
 */
public class User extends ActiveRecordBase {
	public String firstName;
	public String lastName;
	public User() {
	}
}
```
And manipulate it via set of simplet APIs
```
	try {
		// Open database
		_db = ActiveRecordBase.open(this, &quot;mysqlite.db&quot;, 1);

		// Create new entity
		User user = _db.newEntity(User.class);
		user.firstName = &quot;John&quot;;
		user.lastName = &quot;Smith&quot;;
			
		// Persist it to DB
		user.save();
			
		// Or find and retrieve record from DB by it's primary key
		long id = 123;
		User user2 = _db.findByID(User.class, id);
			
	} catch (ActiveRecordException e) {
		// There is also improved logger class, 
		// Allows to print log messages using 'printf'-style formats
		Logg.e(TAG, e, "(%t) %s.initDb(): Error=%s", CNAME, e.getMessage());
	} 

```