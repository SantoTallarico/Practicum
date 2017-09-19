package com.example.santo.practicum.Scenes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.santo.practicum.GameObjects.Equipment;
import com.example.santo.practicum.GameObjects.GameButton;
import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.GameObjects.PB_Character;
import com.example.santo.practicum.PB_GLSurfaceView;
import com.example.santo.practicum.PhotoAccess.PhotoAccess;
import com.example.santo.practicum.PhotoGeneration;
import com.example.santo.practicum.R;

import java.io.IOException;

public class MainMenuScene extends GameScene {
    public static Bitmap bitmap;
    public static Bitmap palette1 = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);
    public static Bitmap palette2 = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);
    public static Bitmap palette3 = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);
    GameObject p1 = new GameObject(new Rect(-550, 250, -250, 550), palette1, 100);
    GameObject p2 = new GameObject(new Rect(-550, -150, -250, 150), palette2, 100);
    GameObject p3 = new GameObject(new Rect(-550, -550, -250, -250), palette3, 100);

    MediaPlayer musicPlayer;
    AudioAttributes attributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build();
    SoundPool soundEffects = new SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(attributes)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        musicPlayer = MediaPlayer.create(this, R.raw.costadelsanto);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        musicPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        musicPlayer.start();

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        GameButton btnFight = new GameButton(new Rect(-150, 250, 150, 350), "drawable/btnfight", 100);
        GameButton btnGeneratePhoto = new GameButton(new Rect(-150, -50, 150, 50), "drawable/btngenerate", 100);
        GameButton btnViewEdit = new GameButton(new Rect(-150, -350, 150, -250), "drawable/btnviewedit", 100);
        gameObjects.add(btnFight);
        gameObjects.add(btnGeneratePhoto);
        gameObjects.add(btnViewEdit);


        gameObjects.add(p1);
        gameObjects.add(p2);
        gameObjects.add(p3);

        btnFight.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FightSelectScene.class);
                startActivity(i);
            }
        });

        btnGeneratePhoto.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                PhotoAccess.performFileSearch(MainMenuScene.this);
            }
        });

        btnViewEdit.SetOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViewEditScene.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        musicPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        musicPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        musicPlayer.release();
        soundEffects.release();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == PhotoAccess.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                try {
                    bitmap = PhotoAccess.getBitmapFromUri(uri, this);
                    int[] colourInfo = PhotoGeneration.Generate(bitmap);

                    palette1 = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);
                    palette2 = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);
                    palette3 = Bitmap.createBitmap(32, 32, Bitmap.Config.ARGB_8888);

                    for (int i = 0; i < 32; i++) {
                        for (int j = 0; j < 32; j++) {
                            palette1.setPixel(i, j, colourInfo[3]);
                        }
                    }

                    for (int i = 0; i < 32; i++) {
                        for (int j = 0; j < 32; j++) {
                            palette2.setPixel(i, j, colourInfo[4]);
                        }
                    }

                    for (int i = 0; i < 32; i++) {
                        for (int j = 0; j < 32; j++) {
                            palette3.setPixel(i, j, colourInfo[5]);
                        }
                    }

                    p1.generatedSprite = palette1;
                    p2.generatedSprite = palette2;
                    p3.generatedSprite = palette3;

                    GameObject object;

                    if (colourInfo[0] % 2 == 0) {

                        object = new PB_Character(new Rect(-300, -300, 300, 300), "drawable/painting", 0, colourInfo[0] / (32 * 32), colourInfo[1] / (32 * 32), colourInfo[2] / (32 * 32));
                    }
                    else {

                        object = new Equipment(new Rect(-300, -300, 300, 300), "drawable/painting", 0, colourInfo[0] / (32 * 32), colourInfo[1] / (32 * 32), colourInfo[2] / (32 * 32));
                    }
                }
                catch (IOException e) {

                }
            }
        }
    }
}
