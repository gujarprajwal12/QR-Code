package gujarprawjal12gmail.com.example.qrcode

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*


class MainActivity : AppCompatActivity() {


    private lateinit var codeScanner: CodeScanner
    val MYCameraPermission_request = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scannerView = findViewById<CodeScannerView>(R.id.scannerview)


        codeScanner =CodeScanner ( this, scannerView)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled =true
        codeScanner.isTouchFocusEnabled = true



        codeScanner.decodeCallback = DecodeCallback {
               runOnUiThread {
                Toast.makeText(this, "  Your Scan result is: ${it.text}", Toast.LENGTH_LONG).show()
               }
               }

        codeScanner.errorCallback = ErrorCallback {
                runOnUiThread {
                Toast.makeText(this, "Camera initialization error  ###: ${it.message}",Toast.LENGTH_LONG).show()
            }
            }

        checkPermission()
        }

    // for use to get the run time permission for the cammera
        fun checkPermission(){
             if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){

                 ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),MYCameraPermission_request)
             }else {
                 codeScanner.startPreview()
             }
         }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


      // use for the if due to another activity it get interpeted
                      override fun onResume() {
                      super.onResume()
                  codeScanner.startPreview()
                 }


    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
                }
                }
