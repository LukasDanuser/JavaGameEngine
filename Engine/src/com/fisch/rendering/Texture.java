package com.fisch.rendering;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;


public class Texture {
	
	private int id;
	
	public Texture(String filename)	{
		InputStream is = Texture.class.getResourceAsStream(filename);
		if(is == null)	{
			throw new RuntimeException("Resource not found: " + filename);
			
		}
		ByteBuffer rawBytes;
		try {
			 rawBytes = ioResourceTobyteBuffer(is, 16384);
		} catch (IOException e) {

		throw new RuntimeException(e);
		}
		
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		IntBuffer c = BufferUtils.createIntBuffer(1);
		ByteBuffer decodedImage = STBImage.stbi_load_from_memory(rawBytes, w, h, c, 0);
		if(decodedImage == null)	{
			throw new RuntimeException("Image file '" + filename + "' could not be decoded : " + STBImage.stbi_failure_reason());
			
		}

		int width = w.get();
		int height = h.get();
		
		
		id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, decodedImage);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		
		STBImage.stbi_image_free(decodedImage);
	}
	
	public void release()	{
		GL11.glDeleteTextures(id);
	}
	
	public int getID() {
		return id;
		
	}
	private static ByteBuffer ioResourceTobyteBuffer(InputStream source, int bufferSize) throws IOException	{
		ByteBuffer buffer = null;
		
		try (ReadableByteChannel rbc = Channels.newChannel(source))	{
			buffer = BufferUtils.createByteBuffer(bufferSize);
			
			while (true)	{
				int bytes = rbc.read(buffer);
				if(bytes == -1)
					break;
				if(buffer.remaining() == 0)
					buffer = resizeBuffer(buffer, buffer.capacity() * 2);
			}
			
		}catch (Throwable t)	{
			throw new NullPointerException("Input stream not valid");
		}
	buffer.flip();
	return buffer;
	
	}
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity)	{
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	
}