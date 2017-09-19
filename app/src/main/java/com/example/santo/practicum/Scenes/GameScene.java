package com.example.santo.practicum.Scenes;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;

import com.example.santo.practicum.GameObjects.GameObject;
import com.example.santo.practicum.PB_GLSurfaceView;

import java.util.ArrayList;

/**
 * Created by Santo on 8/8/2017.
 */

public class GameScene extends AppCompatActivity {
    public ArrayList<GameObject> gameObjects;
    protected PB_GLSurfaceView glView;

    GameScene() {
        super();
        gameObjects = new ArrayList<GameObject>();
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
