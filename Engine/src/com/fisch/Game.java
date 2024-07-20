package com.fisch;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Game {
	private long window;
	
	public void run()	{
		GLFWErrorCallback.createPrint(System.err).set();
		GLFW.glfwInit();
		window = GLFW.glfwCreateWindow(800, 600, "Game", 0, 0);
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window, vidmode.width() / 2 - 800 / 2, vidmode.height() / 2 - 600 / 2);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSwapInterval(1);
		
		GL.createCapabilities();
		GL11.glClearColor(0, 0, 1, 1);
		
		init();
		
		
		while (!GLFW.glfwWindowShouldClose(window))	{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			// Rendering
			
			render();
			
			
			
			GLFW.glfwSwapBuffers(window);
			
			
			GLFW.glfwPollEvents();
			
			
}
		release();
		
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
		
}
	public void init()	{}
	
	public void render() {}
	
	public void release() {}
	
}
