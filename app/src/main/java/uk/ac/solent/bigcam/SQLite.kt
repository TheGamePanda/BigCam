import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.Context

class SQLite(ctx:Context) : SQLiteOpenHelper(ctx,"TestDB", null, 1) {


    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("CREATE TABLE IF NOT EXISTS albums (ID INTEGER PRIMARY KEY, name VARCHAR(255));")
        db.execSQL("CREATE TABLE IF NOT EXISTS photos (ID INTEGER PRIMARY KEY, name VARCHAR(255), location VARCHAR(255), albumID INTEGER, FOREIGN KEY(albumID) REFERENCES albums(ID));");
    }
    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int) {
        db.execSQL ("DROP TABLE IF EXISTS photos")
        db.execSQL ("DROP TABLE IF EXISTS albums")
        onCreate(db)
    }
   fun SelectPhotos(album: Integer) : List<Photo> {
        val photos = mutableListOf<Photo>()
        val db = getReadableDatabase()
        val cursor = db.rawQuery ("SELECT * FROM People WHERE Firstname=?", arrayOf<Integer>(album ) )
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                val p = Photo(cursor.getString(cursor.getColumnIndex("Firstname")),cursor.getString(cursor.getColumnIndex("Lastname")),cursor.getLong(cursor.getColumnIndex("Age")))
                photos.add(p)
                cursor.moveToNext()
            }
        }
        cursor.close()
        return photos
    }
}