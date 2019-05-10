import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.Context
import uk.ac.solent.bigcam.Photo

class SQLite(ctx:Context) : SQLiteOpenHelper(ctx,"TestDB", null, 1) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS photos (ID INTEGER PRIMARY KEY, location VARCHAR(255), albumID INTEGER)");
    }
    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int) {
        db.execSQL ("DROP TABLE IF EXISTS photos")
        db.execSQL ("DROP TABLE IF EXISTS albums")
        onCreate(db)
    }
    fun insertPhoto(location: String) : Long{
        val db = getWritableDatabase()
        val stmt = db.compileStatement ("INSERT INTO photos(location, albumID) VALUES (?, 1)");
        stmt.bindString (1, location)
        val id = stmt.executeInsert()
        return id
    }
    fun SelectByAlbum(album: String) : List<Photo> {
        val photos = mutableListOf<Photo>()
        val db = getReadableDatabase()
        val cursor = db.rawQuery ("SELECT * FROM photos WHERE albumID=?", arrayOf<String>(album))
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                val p = Photo(
                    cursor.getString(cursor.getColumnIndex("ID")),
                    cursor.getString(cursor.getColumnIndex("location")),
                    cursor.getString(cursor.getColumnIndex("albumID"))
                )
                photos.add(p)
                cursor.moveToNext()
            }
        }
        cursor.close()
        return photos
    }
}