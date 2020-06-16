# Room

Room:
-----

Room is a part of the Android Architecture components which provides an abstraction layer over SQlite which allows for a more robust database acces while still providing the full power of SQlite.

Room Components:
----------------

Entity:
-------

The Entity represents a table within the database and has to be annotated with @Enity. Each Entity consist of a minimum of one field has to define a primary key.

Primary key:
------------

Each entity must define at least 1 field as a primary key. Even when there is only 1 field, you still need to annotate the field with the @PrimaryKey annotation. Also, if you want Room to assign automatic IDs to entities, you can set the @PrimaryKey's autoGenerate property. If the entity has a composite primary key, you can use the primaryKeys property of the @Entity annotation

@Entity(primaryKeys = arrayOf("firstName", "lastName"))
data class User(
    val firstName: String?,
    val lastName: String?
)

By default, Room uses the class name as the database table name. If you want the table to have a different name, set the tableName property of the @Entity annotation, as shown in the following code snippet:

@Entity(tableName = "users")
data class User (
    // ...
)

ColumnInfo:
-----------

Room uses the field names as the column names in the database. If you want a column to have a different name, add the @ColumnInfo annotation to a field.

@Entity(tableName = "users")
data class User (
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?
)

@Ignore:
--------

By default, Room creates a column for each field that's defined in the entity. If an entity has fields that you don't want to persist, you can annotate them using @Ignore.

@Entity
data class User(
    @PrimaryKey val id: Int,
    val firstName: String?,
    val lastName: String?,
    @Ignore val picture: Bitmap?
)

@Embedded:
----------

@Embedded annotation is used to represent an object that you'd like to decompose into its subfields within a table. You can then query the embedded fields just as you would for other individual columns.

data class Address(
    val street: String?,
    val state: String?,
    val city: String?,
    @ColumnInfo(name = "post_code") val postCode: Int
)

@Entity
data class User(
    @PrimaryKey val id: Int,
    val firstName: String?,
    @Embedded val address: Address?
)

DAO (Database Access Object):
-----------------------------

The DAO is the main component of Room and includes methodes that offer access to your apps database it has to be annotated with @Dao. DAOs are used instead of query builders and let you seperate differend components of your database 

Insert:
-------

Using @Insert, Room generates an implementation that inserts all parameters into the database in a single transaction.

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: User)

    @Insert
    fun insertBothUsers(user1: User, user2: User)

    @Insert
    fun insertUsersAndFriends(user: User, friends: List<User>)
}

Update:
-------

The Update convenience method modifies a set of entities, given as parameters, in the database. It uses a query that matches against the primary key of each entity.

@Dao
interface MyDao {
    @Update
    fun updateUsers(vararg users: User)
}

Delete:
-------

The Delete convenience method removes a set of entities, given as parameters, from the database. It uses the primary keys to find the entities to delete.

@Dao
interface MyDao {
    @Delete
    fun deleteUsers(vararg users: User)
}

Query:
------

It allows you to perform read/write operations on a database. Each @Query method is verified at compile time, so if there is a problem with the query, a compilation error occurs instead of a runtime failure.

Room also verifies the return value of the query such that if the name of the field in the returned object doesn't match the corresponding column names in the query response, Room alerts you in one of the following two ways:

It gives a warning if only some field names match.
It gives an error if no field names match.

@Dao
interface MyDao {
    @Query("SELECT * FROM user")
    fun loadAllUsers(): Array<User>

	@Query("SELECT * FROM user WHERE age > :minAge")
    fun loadAllUsersOlderThan(minAge: Int): Array<User>
	
	@Query("SELECT * FROM user WHERE age BETWEEN :minAge AND :maxAge")
    fun loadAllUsersBetweenAges(minAge: Int, maxAge: Int): Array<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :search " +
           "OR last_name LIKE :search")
    fun findUserWithName(search: String): List<User>
	
	@Query("SELECT first_name, last_name FROM user WHERE region IN (:regions)")
    fun loadUsersFromRegions(regions: List<String>): List<NameTuple>
	
	@Query(
        "SELECT * FROM book " +
        "INNER JOIN loan ON loan.book_id = book.id " +
        "INNER JOIN user ON user.id = loan.user_id " +
        "WHERE user.name LIKE :userName"
    )
    fun findBooksBorrowedByNameSync(userName: String): List<Book>
	
}

Database:
---------

Serves as the database holder an is the main accespoint to your relational data. It has to be annotated with @Database and extents the RoomDatabase. It also containes and returns the Dao (Database Access Object).

Prepopulate from an app asset:
------------------------------

To prepopulate a Room database from a prepackaged database file that is located anywhere in your app's assets/ directory, call the createFromAsset() method from your RoomDatabase.Builder object before calling build():

Room.databaseBuilder(appContext, AppDatabase.class, "Sample.db")
    .createFromAsset("database/myapp.db")
    .build()
	
Prepopulate from the file system:
---------------------------------

To prepopulate a Room database from a prepackaged database file that is located anywhere in the device's file system except your app's assets/ directory, call the createFromFile() method from your RoomDatabase.Builder object before calling build():

@Database(
    entities = [LoginResponse::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BinMasterConverters::class)
abstract class SampleDatabase: RoomDatabase() {

    abstract fun getLoginDao(): LoginDao

    companion object {
        @Volatile
        private var instance: SampleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: createDatabase(
                    context
                ).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                SampleDatabase::class.java, "SampleDB.db").build()
    }
}

TypeConverters:
---------------

TypeConverters converts a custom class to and from a known type that Room can persist.

class BinMasterConverters {

    @TypeConverter
    fun binMastersToJson(binMasters: List<UserData>): String {
        val gson = Gson()
        val type = object : TypeToken<List<UserData>>() {}.type
        return gson.toJson(binMasters, type)
    }

    @TypeConverter
    fun jsonToBinMasters(json: String) : List<UserData>{
        val gson = Gson()
        val type = object : TypeToken<List<UserData>>() {}.getType()
        return gson.fromJson(json, type)
    }
}
