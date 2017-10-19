package com.example.santo.practicum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.view.MotionEvent;
import android.view.View;

import com.example.santo.practicum.GameObjects.GameObject;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glGetError;
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

    private int[] textureNames;

    private float m_ScreenWidth = 1080;
    private float m_ScreenHeight = 1776;

    private View mView;
    private Context mContext;
    private List<GameObject> m_gameObjects;

    private long prevFrame;

    public PB_GLRenderer(View v, Context c, List<GameObject> gameObjects)
    {
        mView = v;
        mContext = c;
        prevFrame = System.currentTimeMillis() + 100;

        m_gameObjects = gameObjects;
    }

    public void processTouchEvent(MotionEvent event)
    {
        int touchX = (int)(event.getRawX() - m_ScreenWidth / 2);
        int touchY = (int)(m_ScreenHeight / 2 - event.getRawY());
        for (GameObject object : m_gameObjects) {
            if (object.touchable == true && event.getAction() == MotionEvent.ACTION_DOWN && object.dimensions.contains(touchX, touchY)) {
                object.clickListener.onClick(mView);
            }
        }
    }

    public void SetupBuffers()
    {
        //Vertex order when rendering
        indices = new short[] {0, 2, 1,
                                0, 3, 2};

        ByteBuffer bb = ByteBuffer.allocateDirect(3 * 4 * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();

        ByteBuffer dlb = ByteBuffer.allocateDirect(indices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(indices);
        drawListBuffer.position(0);
    }

    public void SetupTextures()
    {
        float[] uvs = new float[] {
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f
        };

        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        textureNames = new int[m_gameObjects.size()];
        GLES20.glGenTextures(m_gameObjects.size(), textureNames, 0);

        int i = 0;
        for (GameObject object : m_gameObjects) {
            object.textureID = textureNames[i];
            Bitmap bmp;
            if (object.isSpriteGenerated) {
                bmp = object.generatedSprite.copy(object.generatedSprite.getConfig(), object.generatedSprite.isMutable());
            }
            else {
                int id = mContext.getResources().getIdentifier(object.spriteLocation, null, mContext.getPackageName());

                bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
            }

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, object.textureID);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                    GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                    GLES20.GL_LINEAR);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                    GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                    GL_CLAMP_TO_EDGE);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

            bmp.recycle();

            i++;
        }
    }

    public void AddTexture(final GameObject object) {
        int[] textureName = new int[1];
        GLES20.glGenTextures(1, textureName, 0);
        object.textureID = textureName[0];

        Bitmap bmp;
        if (object.isSpriteGenerated) {
            bmp = object.generatedSprite.copy(object.generatedSprite.getConfig(), object.generatedSprite.isMutable());
        }
        else {
            int id = mContext.getResources().getIdentifier(object.spriteLocation, null, mContext.getPackageName());

            bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, object.textureID);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S,
                GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T,
                GL_CLAMP_TO_EDGE);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);

        bmp.recycle();
    }

    public void SetupShaders() {
        int vertexShader = Shaders.loadShader(GLES20.GL_VERTEX_SHADER,
                Shaders.vs_Image);
        int fragmentShader = Shaders.loadShader(GLES20.GL_FRAGMENT_SHADER,
                Shaders.fs_Image);

        Shaders.sp_Image = GLES20.glCreateProgram();
        GLES20.glAttachShader(Shaders.sp_Image, vertexShader);
        GLES20.glAttachShader(Shaders.sp_Image, fragmentShader);
        GLES20.glLinkProgram(Shaders.sp_Image);

        GLES20.glUseProgram(Shaders.sp_Image);
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        SetupBuffers();
        SetupTextures();
        SetupShaders();

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        m_ScreenWidth = width;
        m_ScreenHeight = height;

        GLES20.glViewport(0, 0, (int) m_ScreenWidth, (int) m_ScreenHeight);

        for(int i = 0; i < 16; i++)
        {
            mtrxProjection[i] = 0.0f;
            mtrxView[i] = 0.0f;
            mtrxProjectionAndView[i] = 0.0f;
        }

        Matrix.orthoM(mtrxProjection, 0, -m_ScreenWidth / 2, m_ScreenWidth / 2, -m_ScreenHeight / 2, m_ScreenHeight / 2, 0, 100);

        Matrix.setLookAtM(mtrxView, 0, 0f, 0f, 100f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mtrxProjectionAndView, 0, mtrxProjection, 0, mtrxView, 0);
    }

    public void onPause()
    {

    }

    public void onResume()
    {
        prevFrame = System.currentTimeMillis();
    }

    public void onDrawFrame(GL10 unused) {
        long now = System.currentTimeMillis();

        if (prevFrame > now) return;

        long elapsed = now - prevFrame;

        for (GameObject object : m_gameObjects) {
            object.Update(elapsed);
        }

        Render(mtrxProjectionAndView);

        prevFrame = now;
    }

    private void Render(float[] m) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        int i = 0;
        for (GameObject object : m_gameObjects) {
            if (object.visible) {
                vertexBuffer.put(object.vertices);
                vertexBuffer.position(0);

                // Bind texture to texturename
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, object.textureID);

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

                GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

                GLES20.glDisableVertexAttribArray(mPositionHandle);
                GLES20.glDisableVertexAttribArray(mTexCoordLoc);
            }

            i++;
        }
    }
}
