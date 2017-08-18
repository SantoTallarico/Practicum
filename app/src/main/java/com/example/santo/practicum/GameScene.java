package com.example.santo.practicum;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;

import com.example.santo.practicum.GameObjects.GameObject;

import java.util.ArrayList;

/**
 * Created by Santo on 8/8/2017.
 */

public class GameScene extends AppCompatActivity {
    public ArrayList<GameObject> gameObjects;
    public ArrayList<GameObject> touchables;
    protected GLSurfaceView glView;

    GameScene() {
        super();
        gameObjects = new ArrayList<GameObject>();
        touchables = new ArrayList<GameObject>();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        glView.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        glView.onResume();
    }
}
