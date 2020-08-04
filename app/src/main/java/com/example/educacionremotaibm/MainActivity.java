package com.example.educacionremotaibm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnGrabar;
    private MediaRecorder grabacion;
    private String salida = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGrabar = (ImageButton) findViewById(R.id.btnGrabar1);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }


    }

    public void Recorder(View view){

        if(grabacion == null){
            salida =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(salida);

            try {
                grabacion.prepare();
                grabacion.start();

            }catch (IOException e){

            }

            btnGrabar.setBackgroundResource(R.mipmap.btngrabar2);
            Toast.makeText(getApplicationContext(), "Grabando...", Toast.LENGTH_SHORT).show();

        }else if(grabacion != null){
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            btnGrabar.setBackgroundResource(R.mipmap.btngrabar1);
            Toast.makeText(getApplicationContext(), "Grabacion finalizada", Toast.LENGTH_SHORT).show();
        }
    }

    public void Reprodicir(View view){
        MediaPlayer mediaplayer = new MediaPlayer();

        try {
            mediaplayer.setDataSource(salida);
            mediaplayer.prepare();
            mediaplayer.start();
        } catch (IOException e){

        }

        Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_SHORT).show();
    }
}