package com.example.soundrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart;
    Button btnStop;

    TextView txtShow;

    MediaRecorder recorder = null;

    private static final int REQUEST_CODE_REC = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(MainActivity.this);

        btnStop = (Button) findViewById(R.id.btn_stop);
        btnStop.setOnClickListener(MainActivity.this);

        txtShow = (TextView) findViewById(R.id.text_show);

        //ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_REC);
        //InitRecorder();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_REC);
        }
        else
        {
            InitRecorder();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btn_start:
                StartRecording();
                break;

            case R.id.btn_stop:
                StopRecording();
                break;

            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
        {
            if(requestCode == REQUEST_CODE_REC) {
                InitRecorder();
            }
        }
    }

    private void InitRecorder()
    {
        try {

            File Root = Environment.getExternalStorageDirectory();
            //File apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + "recorder/fisier.3gp");
            //File apkStorage = new File(Root.getAbsolutePath() + "/recorder");
            File apkStorage = new File("/sdcard/recorder");
            if (!apkStorage.exists()) {
                if(apkStorage.mkdirs()) {
                    txtShow.setText("sa creat");
                }
                else
                {
                    txtShow.setText("nu sa creat");
                }
            }
            else
            {
                txtShow.setText("avem director");
            }

            /*File audioFile = new File(apkStorage, "fisier.txt");
            if(audioFile.exists())
            {
                txtShow.setText("avem fisier");
            }
            else
            {
                txtShow.setText("nu avem fisier");
            }*/

            String xmlPath = Environment.getExternalStorageDirectory().toString() + "/recorder/fisier1.3gp";

            /*String folder_nou = "recorder";
            File file = new File(xmlPath);//getExternalFilesDir(null), folder_nou);
            if (!file.exists()) {
                if(!file.mkdirs())
                {
                    int n = 6;
                    txtShow.setText("nu sa creat");
                }
            }
            else
                {
                    txtShow.setText(xmlPath);
                    File fisier = new File(file.getAbsoluteFile(), "fisier.3gpp");
                    if (!fisier.exists())
                    {
                        fisier.createNewFile();
                    }
                    else
                    {
                        txtShow.setText("avem fisier");
                    }
            }*/

            if (recorder != null) {
                recorder.release();
            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
            recorder.setOutputFile(xmlPath);
            //recorder.prepare();
            //recorder.start();
        }
        catch (Exception ex)
        {
            String res = ex.getMessage();
        }
    }

    public void StartRecording()
    {
        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            txtShow.setText(e.getMessage());
        }
    }

    public void StopRecording()
    {
        recorder.stop();
    }
}