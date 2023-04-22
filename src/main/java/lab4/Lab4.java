package lab4;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;

public class Lab4 extends JFrame implements GLEventListener {

    private GLCanvas canvas;
    private Animator animator;
    double equation[] = {1f, 1f, 1f, 1f};
    
    private int windowWidth = 800;
    private int windowHeight = 600;
    
    private final int NO_TEXTURES = 1;
    private int texture[] = new int[NO_TEXTURES];
    TextureReader.Texture[] tex = new TextureReader.Texture[NO_TEXTURES];
    
    // GLU object used for mipmapping.
    private GLU glu;

    public Lab4() {
        super("Lab4");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(windowWidth, windowHeight);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }



    private void initializeJogl() {
        GLProfile glProfile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        this.canvas = new GLCanvas();

        this.getContentPane().add(this.canvas);

        this.canvas.addGLEventListener(this);

        this.animator = new Animator();

        this.animator.add(this.canvas);

        this.animator.start();
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glClearColor(0, 0, 0, 0);

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();
        int height = 600, width = 800;
        gl.glOrtho(0, width, 0, height, -1, 1);

        gl.glEnable(GL2.GL_CLIP_PLANE1);

        gl.glClipPlane(GL2.GL_CLIP_PLANE1, equation, 0);


        // Bind (select) the texture.
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

    }


    private void makeRGBTexture(GL gl, GLU glu, TextureReader.Texture img, int target, boolean mipmapped) {
        if (mipmapped) {
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        } else {
            gl.glTexImage2D(target, 0, GL.GL_RGB, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, img.getPixels());
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void afisarePoza(String path) {

        GL2 gl = canvas.getGL().getGL2();
        gl.glEnable(GL.GL_BLEND);

        glu = GLU.createGLU();

        // Generate a name (id) for the texture.
        // This is called once in init no matter how many textures we want to generate in the texture vector
        gl.glGenTextures(NO_TEXTURES, texture, 0);

        // Define the filters used when the texture is scaled.
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        // Do not forget to enable texturing.
        gl.glEnable(GL.GL_TEXTURE_2D);
        try {
            tex[0] = TextureReader.readTexture(path);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Construct the texture and use mipmapping in the process.
        this.makeRGBTexture(gl, glu, tex[0], GL.GL_TEXTURE_2D, true);

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_CLAMP_TO_EDGE);
    }

    public void chess() {
        GL2 gl = canvas.getGL().getGL2();
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture[0]);

        // Draw a square and apply a texture on it.
        // Lower left corner.

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gl.glBegin(GL2.GL_QUADS);

                gl.glTexCoord2f(0.0f + j + 2, 0.0f + i + 2);
                gl.glVertex2f(1.1f + j + 2, 1.1f + i + 2);

                // Lower right corner.
                gl.glTexCoord2f(1.0f + j + 2, 0.0f + i + 2);
                gl.glVertex2f(1.9f + j + 2, 1.1f + i + 2);

                // Upper right corner.
                gl.glTexCoord2f(1.0f + j + 2, 1.0f + i + 2);
                gl.glVertex2f(1.9f + j + 2, 1.9f + i + 2);

                // Upper left corner.
                gl.glTexCoord2f(0.0f + j + 2, 1.0f + i + 2);
                gl.glVertex2f(1.1f + j + 2, 1.9f + i + 2);
                gl.glEnd();
                
                if ((i + j) % 2 == 0) {
                    afisarePoza(buildPath("textura1"));
                } else {
                    afisarePoza(buildPath("textura2"));

                }
            }
        }
    }
    
    private String buildPath(String texture) {
    	return String.format("textures/%s.jpg", texture);
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
//        chess();
//    	afisarePoza(buildPath("textura1"));
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int left, int top, int width, int height) {
        GL2 gl = canvas.getGL().getGL2();

        gl.glViewport(0, 0, width, height);

        double ratio = (double) width / (double) height;

        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);

        gl.glLoadIdentity();

        if (ratio < 1) {
            gl.glOrtho(0, 1, 0, 1 / ratio, -1, 1);
        } else {
            gl.glOrtho(0, 15, 0, 15, -1, 1);
        }

        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
    }
}