package lab2;

import javax.swing.*;
import java.awt.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.util.Animator;

import static java.lang.Math.PI;

public class Lab2 extends JFrame implements GLEventListener {

	private GLCanvas canvas;
	private Animator animator;
	double equation[] = { 1f, 1f, 1f, 1f };
	private int windowWidth = 800;
	private int windowHeight = 600;

	public Lab2() {
		super("Java OpenGL");

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

		gl.glOrtho(0, windowWidth, 0, windowHeight, -1, 1);

		gl.glEnable(GL2.GL_CLIP_PLANE1);

		gl.glClipPlane(GL2.GL_CLIP_PLANE1, equation, 0);

//        gl.glEnable(GL.GL_LINE_SMOOTH);

		// Activate the GL_BLEND state variable. Means activating blending.
		gl.glEnable(GL.GL_BLEND);
	}

	@Override
	public void dispose(GLAutoDrawable glAutoDrawable) {

	}

	public void drawSquareGlLines() {
		GL2 gl = canvas.getGL().getGL2();
		gl.glLineWidth(1f);

		gl.glBegin(GL.GL_LINES);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex2f(0.1f, 0.1f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex2f(0.9f, 0.1f);

		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex2f(0.9f, 0.1f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex2f(0.9f, 0.9f);

		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex2f(0.9f, 0.9f);
		gl.glColor3f(1.0f, 0.0f, 1.0f); // Magenta
		gl.glVertex2f(0.1f, 0.9f);

		gl.glColor3f(1.0f, 0.0f, 1.0f); // Magenta
		gl.glVertex2f(0.1f, 0.9f);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex2f(0.1f, 0.1f);
		gl.glEnd();
	}

	public void drawSquareGlLineStrip() {
		GL2 gl = canvas.getGL().getGL2();
		gl.glLineWidth(1f);

		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex2f(0.1f, 0.1f);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Green
		gl.glVertex2f(0.9f, 0.1f);
		gl.glColor3f(0.0f, 0.0f, 1.0f); // Blue
		gl.glVertex2f(0.9f, 0.9f);
		gl.glColor3f(1.0f, 0.0f, 1.0f); // Magenta
		gl.glVertex2f(0.1f, 0.9f);
		gl.glColor3f(1.0f, 0.0f, 0.0f); // Red
		gl.glVertex2f(0.1f, 0.1f);
		gl.glEnd();
	}

	public void drawSquareGlLineLoop() {
		GL2 gl = canvas.getGL().getGL2();
		gl.glLineWidth(1f);

		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor3f(1, 0, 0); // Red
		gl.glVertex2f(0.1f, 0.1f);
		gl.glColor3f(0, 1, 0); // Green
		gl.glVertex2f(0.9f, 0.1f);
		gl.glColor3f(0, 0, 1); // Blue
		gl.glVertex2f(0.9f, 0.9f);
		gl.glColor3f(1, 1, 0); // Yellow
		gl.glVertex2f(0.1f, 0.9f);
		gl.glEnd();
	}

	private void drawCircleGlLineLoop() {
		GL2 gl = canvas.getGL().getGL2();
		gl.glLineWidth(2f);
		double radius = 0.5;
		int numSlices = 100;

		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Set color to red
		gl.glColor3d(1.0f, 0f, 0f);

		// Draw the circle
		double angle, x, y;;

		gl.glBegin(GL2.GL_LINE_LOOP);
		for (int i = 0; i < numSlices; i++) {
			angle = 2.0 * Math.PI * i / numSlices;
			x = 3 + radius * Math.cos(angle);
			y = 3 + radius * Math.sin(angle);
			gl.glVertex2d(x, y);
		}
		gl.glEnd();

		gl.glFlush();
	}
	

	@Override
	public void display(GLAutoDrawable glAutoDrawable) {

//		drawSquareGlLines();
//    	drawSquareGlLineStrip();
//		drawSquareGlLineLoop();
//		drawCircleGlLineLoop();
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
			gl.glOrtho(0, 5, 0, 5, -1, 1);
		}

		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
	}
}