package com.example.santo.practicum;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import java.util.List;

/**
 * Created by Santo on 5/1/2017.
 */

public class PB_GLSurfaceView extends GLSurfaceView {
    private final PB_GLRenderer renderer;

    public PB_GLSurfaceView(Context context, List<GameObject> gameObjects) {
        super(context);

        setEGLContextClientVersion(2);

        renderer = new PB_GLRenderer(context, gameObjects);
        setRenderer(renderer);

        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        renderer.processTouchEvent(event);
        return true;
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        renderer.onPause();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        renderer.onResume();
    }
}
