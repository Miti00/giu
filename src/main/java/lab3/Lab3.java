package lab3;

import javax.swing.*;
import java.awt.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

public class Lab3 extends JFrame implements GLEventListener {

	private GLCanvas canvas;
	private FPSAnimator animator;
	double equation[] = { 1f, 1f, 1f, 1f };
	
	//circle attributes
	 float circleX = -2f;
	

	public Lab3() {
		super("Lab3");

		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(800, 600);

	
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

		animator = new FPSAnimator(canvas, 60);
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

		gl.glEnable(GL.GL_LINE_SMOOTH);

		// Activate the GL_BLEND state variable. Means activating blending.
//        gl.glEnable(GL.GL_BLEND);
	}
	
	public void start() {
        animator.start();
    }


	@Override
	public void dispose(GLAutoDrawable glAutoDrawable) {

	}


	public void drawHouseAndSun() {
		GL2 gl = canvas.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// drawing house
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f(0.f, 0.f, 1.f);
		gl.glVertex2f(1f, 1f);
		gl.glVertex2f(3f, 1f);
		gl.glVertex2f(1f, 3);
		gl.glEnd();

		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f(0.f, 1.f, 0.f);
		gl.glVertex2f(1f, 3f);
		gl.glVertex2f(3f, 3f);
		gl.glVertex2f(3f, 1f);
		gl.glEnd();

		gl.glColor3f(1.f, 0.f, 0.f);
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glVertex2f(1f, 3f);
		gl.glVertex2f(2f, 5f);
		gl.glVertex2f(3f, 3f);
		gl.glEnd();
		
		
		//drawing the sun
	
		
		 float circleSpeed = 0.02f;
		 float circleY = 10.7f;
		 float circleRadius = 1.1f;
		

		gl.glColor3f(1.0f, 1.0f, 0.0f);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
		gl.glVertex2f(circleX, circleY);
		for (int i = 0; i <= 360; i += 10) {
			float x = circleX + circleRadius * (float) Math.cos(Math.PI * i / 180.0);
			float y = circleY + circleRadius * (float) Math.sin(Math.PI * i / 180.0);
			gl.glVertex2f(x, y);
		}
		gl.glEnd();
		// Update the position of the cirle
		circleX += circleSpeed;
        if (circleX > 20f) {
            circleX = -1.0f; // reset position of the Sun
        }
        
     
		
	}

	@Override
	public void display(GLAutoDrawable glAutoDrawable) {

         drawHouseAndSun();

//		chess();
	}


	private void chess() {
		GL2 gl = canvas.getGL().getGL2();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					gl.glColor4f(0.5f, 0.3f, 0.1f, 1.0f);
				} else {
					gl.glColor3f(1.0f, 1.0f, 1.0f);

				}

				gl.glBegin(GL2.GL_POLYGON);
				gl.glVertex2f(0.2f + j + 5, 1.2f + i + 5);
				gl.glVertex2f(1.2f + j + 5, 1.2f + i + 5);
				gl.glVertex2f(1.2f + j + 5, 0.2f + i + 5);
				gl.glVertex2f(0.2f + j + 5, 0.2f + i + 5);
				gl.glEnd();
			}
		}
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