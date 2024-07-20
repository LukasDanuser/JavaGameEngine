package com.fisch.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {
	
	private int vao;
	private int[] vbos;
	private int vertexCount;
	private boolean isUsingIndexBuffer;
	private int indexBufferVBO;
	
	public Mesh(float[] positions)	{
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		int positionVBO = addStaticAttribute(0, positions, 3);
		
		vbos = new int[] {positionVBO};
		vertexCount = positions.length / 3;

}
	public Mesh(int[] indices, float[] positions)	{
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		indexBufferVBO = attachIndexBuffer(indices);
		
		int positionVBO = addStaticAttribute(0, positions, 3);
		
		vbos = new int[] {positionVBO};
		vertexCount = indices.length;
		isUsingIndexBuffer = true;

}
	
	public Mesh(int[] indices, float[] positions, float[] textureCoordinates)	{
		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
		
		indexBufferVBO = attachIndexBuffer(indices);
		int positionVBO = addStaticAttribute(0, positions, 3);
		int textureCoordsVBO = addStaticAttribute(1, textureCoordinates, 2);
		
		vbos = new int[] {positionVBO, textureCoordsVBO};
		vertexCount = indices.length;
		isUsingIndexBuffer = true;

}
	
	
	public void render()	{
		GL30.glBindVertexArray(vao);
		for(int i = 0; i < vbos.length; i++)	{
			GL20.glEnableVertexAttribArray(i);
			
			
		
		
	
	}
		if(isUsingIndexBuffer)	{
			GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
			
		}else {
			GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);

		}
		
		for(int i = 0; i < vbos.length; i++)	{
		GL20.glDisableVertexAttribArray(i);
	
	
	}
	}
	
	public void release()	{
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glDeleteVertexArrays(vao);
		for(int id : vbos)	{
			GL15.glDeleteBuffers(id);
			
			
	
	}
		if(isUsingIndexBuffer)	{
			GL15.glDeleteBuffers(indexBufferVBO);
		
		
		
		}
	}
	private int addStaticAttribute(int index, float[] data, int dataSize) {
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, dataSize, GL11.GL_FLOAT, false, 0, 0);
		return vbo;
}
	private int attachIndexBuffer(int[] indices)	{
		int vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
		return vbo;
		
		
	
}
}
