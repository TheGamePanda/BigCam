package uk.ac.solent.bigcam

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val camera : Button = findViewById(R.id.opencamera)
        camera.setOnClickListener {
            var clickintent = Intent(this, CameraActivity::class.java)
            startActivity(clickintent)
        }
    }
}
