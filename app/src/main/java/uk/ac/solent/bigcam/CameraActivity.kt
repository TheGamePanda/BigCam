package uk.ac.solent.bigcam

import android.database.sqlite.SQLiteDatabase
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraDevice
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.TextureView
import io.fotoapparat.Fotoapparat
import io.fotoapparat.configuration.CameraConfiguration
import io.fotoapparat.selector.*
import kotlinx.android.synthetic.main.camera_activity.*
import java.io.File
import java.security.Timestamp
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement

class CameraActivity : AppCompatActivity() {

    lateinit var fotoapparat  : Fotoapparat
    override fun onCreate(savedInstanceState: Bundle?, db : SQLiteDatabase) {
        db.execSQL ("CREATE TABLE IF NOT EXISTS People (Id INTEGER PRIMARY KEY, Firstname VARCHAR(255), Lastname VARCHAR(255), Age INTEGER)")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_activity)
        fotoapparat = Fotoapparat(
                context = this,
                view = cameraView
        )
        val cameraConfiguration = CameraConfiguration(
                pictureResolution = highestResolution(),
                previewResolution = highestResolution(),
                previewFpsRange = highestFps(),
                focusMode = firstAvailable(
                        continuousFocusPicture(),
                        autoFocus(),
                        fixed()
                ),
                flashMode = firstAvailable(
                        autoRedEye(),
                        autoFlash(),
                        torch(),
                        off()
                ),
                antiBandingMode = firstAvailable(
                        auto(),
                        hz50(),
                        hz60(),
                        none()
                ),
                jpegQuality = manualJpegQuality(90),
                sensorSensitivity = lowestSensorSensitivity(),
                frameProcessor = {  frame ->    }
        )

        takePhoto.setOnClickListener {
            fotoapparat
                    .takePicture()
                    .saveToFile(File("${Environment.getExternalStorageDirectory().getAbsolutePath()}/DCIM/PHOTO-${System.currentTimeMillis()}.jpg"))
        }
    }

    override fun onStart(){
        super.onStart()
        fotoapparat.start()
    }
    override fun onStop(){
        super.onStop()
        fotoapparat.stop()
    }
}
