package com.example.santo.practicum;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.santo.practicum.PhotoAccess.PhotoAccess;

import java.io.IOException;

public class MainMenuActivity extends GameScene {
    public static Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glView = new PB_GLSurfaceView(this, gameObjects);
        setContentView(glView);

        GameButton generatePhoto = new GameButton(new Rect(400, 400, 700, 500), "drawable/btngenerate");
        GameButton painting = new GameButton(new Rect(0, 100, 100, 200), "drawable/painting");
        gameObjects.add(painting);
        gameObjects.add(generatePhoto);
        touchables.add(generatePhoto);
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
                }
                catch (IOException e) {

                }
            }
        }
    }
}
