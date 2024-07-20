package com.fisch.rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	
	private static final Vector3f UP_VECTOR = new Vector3f(0, 1, 0);
	
	protected Matrix4f viewMatrix = new Matrix4f();
	protected Matrix4f projectionMatrix = new Matrix4f();
	
	protected Vector3f position = new Vector3f();
	protected Vector3f rotation = new Vector3f(0, 90, 0);
	protected Vector3f direction = new Vector3f();
	protected Vector3f target = new Vector3f();
	
	protected float nearPlane = 0.1f;
	protected float farPlane = 1000f;
	protected float fov = 60;
	
	public void update()	{
		double pitch = Math.toRadians(rotation.x);
		double yaw = Math.toRadians(rotation.y);
		
		direction.x = (float) (Math.cos(pitch) * Math.sin(yaw));
		direction.y = (float) Math.sin(pitch);
		direction.z = (float) (Math.cos(pitch) * Math.cos(yaw));
		
		viewMatrix.setLookAt(position, position.add(direction, target), UP_VECTOR);
		projectionMatrix.setPerspective((float) Math.toRadians(fov), 800f / 600f, nearPlane, farPlane);
		
		
	}
	
	public Matrix4f getViewMatrix() {
		return viewMatrix;
	}
	
	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	public Vector3f getRotation() {
		return rotation;
	}
	
}
