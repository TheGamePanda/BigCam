package uk.ac.solent.bigcam

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
import android.database.sqlite.SQLiteStatement
import android.widget.Toast

class CameraActivity : AppCompatActivity() {

    lateinit var fotoapparat  : Fotoapparat
    override fun onCreate(savedInstanceState: Bundle?) {
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
            val filepath = System.currentTimeMillis()
            try{
                fotoapparat
                    .takePicture()
                    .saveToFile(File("${Environment.getExternalStorageDirectory().getAbsolutePath()}/DCIM/PHOTO_${filepath}.jpg"))
                    Toast.makeText(this,"Saved to ${Environment.getExternalStorageDirectory().getAbsolutePath()}/DCIM/PHOTO_${filepath}.jpg", Toast.LENGTH_SHORT).show()
            }
            catch(e: Exception){
                Toast.makeText(this,"NOT SAVED", Toast.LENGTH_SHORT).show()
            }
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
