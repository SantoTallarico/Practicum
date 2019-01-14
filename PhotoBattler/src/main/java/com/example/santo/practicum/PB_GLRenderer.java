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

    private float m_ScreenWidth = 1080;
    private float m_ScreenHeight = 1776;

    private View mView;
    private Context mContext;
    private List<GameObject> m_gameObjects;

    private long prevFrame;

    float[] uvs = new float[] {
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    public PB_GLRenderer(View v, Context c, List<GameObject> gameObjects)
    {
        mView = v;
        mContext = c;
        prevFrame = System.currentTimeMillis() + 100;

        m_gameObjects = gameObjects;
    }

    public void processTouchEvent(MotionEvent event)
    {
        float touchX = event.getRawX() - m_ScreenWidth / 2;
        float touchY = m_ScreenHeight / 2 - event.getRawY();
        for (GameObject object : m_gameObjects) {
            if (object.touchable == true && event.getAction() == MotionEvent.ACTION_DOWN && object.Contains(touchX, touchY)) {
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
        int[] textureName = new int[1];
        ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
        bb.order(ByteOrder.nativeOrder());
        uvBuffer = bb.asFloatBuffer();
        uvBuffer.put(uvs);
        uvBuffer.position(0);

        for (GameObject object : m_gameObjects) {
            for (int i = 0; i < object.textureIDs.length; i++) {
                GLES20.glGenTextures(1, textureName, 0);
                object.textureIDs[i] = textureName[0];
                if (i == 0) {
                    object.currentTextureID = textureName[0];
                }
                Bitmap bmp;
                if (object.isSpriteGenerated) {
                    bmp = object.generatedSprites[i].copy(object.generatedSprites[i].getConfig(), object.generatedSprites[i].isMutable());
                } else {
                    int id = mContext.getResources().getIdentifier(object.spriteLocation, null, mContext.getPackageName());

                    bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
                }

                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, object.textureIDs[i]);

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
        }
    }

    public void AddTexture(final GameObject object, int textureIndex) {
        int[] textureName = new int[1];
        GLES20.glGenTextures(1, textureName, 0);
        object.textureIDs[textureIndex] = textureName[0];
        object.currentTextureID = object.textureIDs[textureIndex];

        Bitmap bmp;
        if (object.isSpriteGenerated) {
            bmp = object.generatedSprites[0].copy(object.generatedSprites[0].getConfig(), object.generatedSprites[0].isMutable());
        }
        else {
            int id = mContext.getResources().getIdentifier(object.spriteLocation, null, mContext.getPackageName());

            bmp = BitmapFactory.decodeResource(mContext.getResources(), id);
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, object.textureIDs[textureIndex]);

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

        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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

    private void Render(float[] pvMatrix) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        for (GameObject object : m_gameObjects) {
            if (object.visible) {
                vertexBuffer.put(object.vertices);
                vertexBuffer.position(0);

                if (object.animates) {
                    uvBuffer.put(0, object.uvsAnimation[0] + object.xOffset * object.currentFrame);
                    uvBuffer.put(1, object.uvsAnimation[1]);
                    uvBuffer.put(2, object.uvsAnimation[2] + object.xOffset * object.currentFrame);
                    uvBuffer.put(3, object.uvsAnimation[3]);
                    uvBuffer.put(4, object.uvsAnimation[4] + object.xOffset * object.currentFrame);
                    uvBuffer.put(5, object.uvsAnimation[5]);
                    uvBuffer.put(6, object.uvsAnimation[6] + object.xOffset * object.currentFrame);
                    uvBuffer.put(7, object.uvsAnimation[7]);
                }
                else {
                    uvBuffer.put(uvs);
                }
                uvBuffer.position(0);

                // Bind texture to texturename
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, object.currentTextureID);

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
                GLES20.glUniformMatrix4fv(mtrxhandle, 1, false, pvMatrix, 0);

                // Get handle to textures locations
                int mSamplerLoc = GLES20.glGetUniformLocation(Shaders.sp_Image, "s_texture");

                // Set the sampler texture unit to 0, where we have saved the texture.
                GLES20.glUniform1i(mSamplerLoc, 0);

                GLES20.glDrawElements(GLES20.GL_TRIANGLES, indices.length, GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

                GLES20.glDisableVertexAttribArray(mPositionHandle);
                GLES20.glDisableVertexAttribArray(mTexCoordLoc);
            }
        }
    }
}
