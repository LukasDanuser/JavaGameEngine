package com.fisch.rendering;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

public class GLSLProgram {
	
	private static final float[] MATRIX_BUFFER = new float[16];
	
	private int program;
	private HashMap<String, Integer> uniformVariables = new HashMap<>();
	
	public GLSLProgram(String fragmentShaderFile) {
	program = GL20.glCreateProgram();
		
		attachShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderFile);
		
		GL20.glLinkProgram(program);
	GL20.glValidateProgram(program);
	
	}
	public GLSLProgram(String vertexShaderFile,String fragmentShaderFile, String vertexAttribute, String string) {
		program = GL20.glCreateProgram();
			attachShader(GL20.GL_VERTEX_SHADER, vertexShaderFile);
			attachShader(GL20.GL_FRAGMENT_SHADER, fragmentShaderFile);
			
			GL20.glLinkProgram(program);
		GL20.glValidateProgram(program);
		
		}
	
	
	public void addUniformVariable(String variable) {
		int location = GL20.glGetUniformLocation(program, variable);
		uniformVariables.put(variable, location);
	}
	
	public void setVec3(String variable, float x, float y, float z)	{
		if (uniformVariables.containsKey(variable))	{
			enable();
			int location = uniformVariables.get(variable);
			GL20.glUniform3f(location, x, y, z);
		}
	}
	
	public void setTexture(String variable, Texture texture, int textureSlot)	{
		if(uniformVariables.containsKey(variable))	{
			enable();
			int location = uniformVariables.get(variable);
			GL20.glUniform1i(location, textureSlot);
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + textureSlot);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
		}
	}
	
	public void setMatrix(String variable, Matrix4f matrix)	{
		if(uniformVariables.containsKey(variable)) {
			enable();
			int location = uniformVariables.get(variable);
			GL20.glUniformMatrix4fv(location, false, matrix.get(MATRIX_BUFFER));
			
			
		}
	}
	
	
	public void enable() {
		GL20.glUseProgram(program);
	}
	public void disable()	{
		GL20.glUseProgram(0);
		
		
	}
	
	public void release()	{
		disable();
		GL20.glDeleteProgram(program);
	}
	
	
	private void attachShader(int shaderType, String file)	{
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(GLSLProgram.class.getResourceAsStream(file)));
			StringBuilder builder = new StringBuilder();
			while(reader.ready())	{
				builder.append(reader.readLine() + System.lineSeparator());
			
			}
			reader.close();
			int id = GL20.glCreateShader(shaderType);
			GL20.glShaderSource(id, builder);
			GL20.glCompileShader(id);
			if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)	{
				throw new RuntimeException("Failed to compile shader: " + System.lineSeparator() + GL20.glGetShaderInfoLog(id));
				
			}
			GL20.glAttachShader(program, id);
			GL20.glDeleteShader(id);
		
		}catch (IOException e)	{
			throw new RuntimeException();
		
		
		
		
}
	
	
}
}