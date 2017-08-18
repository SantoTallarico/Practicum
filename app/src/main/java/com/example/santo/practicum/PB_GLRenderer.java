package com.example.santo.practicum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.view.MotionEvent;

import com.example.santo.practicum.GameObjects.GameObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static javax.microedition.khronos.opengles.GL10.GL_CLAMP_TO_EDGE;

/**
 * Created by Santo on 5/1/2017.
 */

public class PB_GLRenderer implements GLSurfaceView.Renderer {
    private final float[] mtrxProjection = new float[16];
    private final float[] mtrxView = new float[16];
    private final float[] mtrxProjectionAndView = new float[16];

    private static short indices[];
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private FloatBuffer uvBuffer;

    private int[] texturenames = new int[2];

    private float m_ScreenWidth = 1280;
    private float m_ScreenHeight = 768;

    private Context mContext;
    private long prevFrame;

    private List<GameObject> m_gameObjects;

    public PB_GLRenderer(Context c, List<GameObject> gameObjects)
    {
        mContext = c;
        prevFrame = System.currentTimeMillis() + 100;

        m_gameObjects = gameObjects;
    }

    public void processTouchEvent(MotionEvent event)
    {
        for (GameObject object : m_gameObjects) {
            if (object.touchable == true && object.dimensions.contains((int)event.getX(), (int)event.getY())) {
                object.onTouch();
            }
        }
    }

    public void SetupBuffers()
    {
        // The order of vertex rendering.
        indices = new short[] {0, 2, 1,
                                0, 3, 2};

        // The vertex buffer.
        ByteBuffer bb = ByteBuffer.allocateDirect(3 * 4 * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);

    }

    public void SetupTextures()
    {
        // Create our UV coordinates.
        float[] uvs = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        // The texture buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        // Generate Textures, if more needed, alter these numbers.
        GLES20.glGenTextures(2, texturenames, 0);

        int i = 0;
        for (GameObject object : m_gameObjects) {
            // Retrieve our image from resources.
            int id = mContext.getResources().getIdentifier(object.spriteLocation, null, mContext.getPackageName());

            // Temporary create a bitmap
            Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), id);

            // Bind texture to texturename
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[i]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                    GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                    GLES20.GL_LINEAR);

            // Set wrapping mode
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                    GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                    GL_CLAMP_TO_EDGE);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

            // We are done using the bitmap so we should recycle it.
            bmp.recycle();

            i++;
        }
    }

    public void SetupShaders() {
        int vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER,
                Shaders.vs_SolidColor);
        int fragmentShader = Shaders.loadShader(GLES20.GL_FRAGMENT_SHADER,
                Shaders.fs_SolidColor);

        Shaders.sp_SolidColor = GLES20.glCreateProgram();
        GLES20.glAttachShader(Shaders.sp_SolidColor, vertexShader);
        GLES20.glAttachShader(Shaders.sp_SolidColor, fragmentShader);
        GLES20.glLinkProgram(Shaders.sp_SolidColor);

        // Create the shaders, images
        vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER,
                Shaders.vs_Image);
        fragmentShader = Shaders.loadShader(GLES20.GL_FRAGMENT_SHADER,
                Shaders.fs_Image);

        Shaders.sp_Image = GLES20.glCreateProgram();
        GLES20.glAttachShader(Shaders.sp_Image, vertexShader);
        GLES20.glAttachShader(Shaders.sp_Image, fragmentShader);
        GLES20.glLinkProgram(Shaders.sp_Image);

        // Set our shader programm
        GLES20.glUseProgram(Shaders.sp_Image);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        SetupBuffers();
        SetupTextures();
        SetupShaders();

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // We need to know the current width and height.
        m_ScreenWidth = width;
        m_ScreenHeight = height;

        // Redo the Viewport, making it fullscreen.
        GLES20.glViewport(0, 0, (int) m_ScreenWidth, (int) m_ScreenHeight);

        // Clear our matrices
        for(int i = 0; i < 16; i++)
        {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        // Setup our screen width and height for normal sprite translation.
        Matrix.orthoM(mtrxProjection, 0, 0f, m_ScreenWidth, 0.0f, m_ScreenHeight, 0, 50);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 1f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
    }

    public void onPause()
    {
        /* Do stuff to pause the renderer */
    }

    public void onResume()
    {
        /* Do stuff to resume the renderer */
        prevFrame = System.currentTimeMillis();
    }

    public void onDrawFrame(GL10 unused) {
        // Get the current time
        long now = System.currentTimeMillis();

        // We should make sure we are valid and sane
        if (prevFrame > now) return;

        // Get the amount of time the last frame took.
        long elapsed = now - prevFrame;

        // Update our example

        // Render our example
        Render(mtrxProjectionAndView);

        // Save the current time to see how long it took
        prevFrame = now;
    }

    private void Render(float[] m) {
        // clear Screen and Depth Buffer,
        // we have set the clear color as black.
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        int i = 0;
        for (GameObject object : m_gameObjects) {
            vertexBuffer.put(object.vertices);
            vertexBuffer.position(0);

            // Bind texture to texturename
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texturenames[i]);

            // get handle to vertex shader's vPosition member
            int mPositionHandle = GLES20.glGetAttribLocation(Shaders.sp_Image, "vPosition");

            // Enable generic vertex attribute array
            GLES20.glEnableVertexAttribArray(mPositionHandle);

            // Prepare the triangle coordinate data
            GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);

            // Get handle to texture coordinates location
            int mTexCoordLoc = GLES20.glGetAttribLocation(Shaders.sp_Image, "a_texCoord");

            // Enable generic vertex attribute array
            GLES20.glEnableVertexAttribArray(mTexCoordLoc);

            // Prepare the texture coordinates
            GLES20.glVertexAttribPointer(mTexCoordLoc, 2, GLES20.GL_FLOAT, false, 0, uvBuffer);

            // Get handle to shape's transformation matrix
            int mtrxhandle = GLES20.glGetUniformLocation(Shaders.sp_Image, "uMVPMatrix");

            // Apply the projection and view transformation
            GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, m, 0);

            // Get handle to textures locations
            int mSamplerLoc = GLES20.glGetUniformLocation(Shaders.sp_Image, "s_texture");

            // Set the sampler texture unit to 0, where we have saved the texture.
            GLES20.glUniform1i(mSamplerLoc, 0);

            // Draw the triangle
            GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

            // Disable vertex array
            GLES20.glDisableVertexAttribArray(mPositionHandle);
            GLES20.glDisableVertexAttribArray(mTexCoordLoc);

            i++;
        }
    }
}
