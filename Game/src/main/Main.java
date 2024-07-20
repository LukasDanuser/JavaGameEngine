package main;


import com.fisch.Game;
import com.fisch.rendering.Camera;
import com.fisch.rendering.Entity;
import com.fisch.rendering.GLSLProgram;
import com.fisch.rendering.Mesh;
import com.fisch.rendering.Texture;



public class Main extends Game	{
	
	
	private GLSLProgram basicShaderProgram;
	private GLSLProgram entityShaderProgramm;
	private Texture brickTexture;
	private Entity cube;
	private Camera camera;
	
	public static void main(String[] args)	{
	Main t = new Main();
	t.run();
	
	
	
		}
	public void init() {
		camera = new Camera();
		
		brickTexture = new Texture("/res/texture/bricktexture.jpg");

		Mesh mesh = new Mesh(new int[] { 0, 1, 3, 3, 1, 2, 4, 5, 7, 7, 5, 6, 8, 9, 11, 11, 9, 10, 12, 13, 15, 15, 13, 14, 16, 17, 19, 19, 17, 18, 20, 21, 23, 23, 21, 22 }, new float[] { -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
 
                0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
 
                -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
 
                -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
 
                -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f }, new float[] { 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0 });
			
		cube = new Entity(mesh);
		
		basicShaderProgram = new GLSLProgram("/res/shaders/basic.vs", "/res/shaders/basic.fs", "positions", "textureCoordinates");
		basicShaderProgram.addUniformVariable("offset");
		basicShaderProgram.addUniformVariable("diffuseTexture");
		
		entityShaderProgramm = new GLSLProgram("/res/shaders/entity.vs", "/res/shaders/entity.fs", "position", "textureCoordinates");
		entityShaderProgramm.addUniformVariable("diffuseTexture");
		entityShaderProgramm.addUniformVariable("transformationMatrix");
		entityShaderProgramm.addUniformVariable("viewMatrix");
		entityShaderProgramm.addUniformVariable("projectionMatrix");


	}
	
	public void render() {
		cube.getPosition().x += 0.1f;
		cube.getRotation().y += 0.5f;
		cube.update();
		camera.update();
		
		entityShaderProgramm.enable();
		entityShaderProgramm.setTexture("diffuseTexture", brickTexture, 0);
		entityShaderProgramm.setMatrix("transformationMatrix", cube.getTransformationMatrix());
		entityShaderProgramm.setMatrix("viewMatrix", camera.getViewMatrix());
		entityShaderProgramm.setMatrix("projectionMatrix", camera.getProjectionMatrix());
		
		cube.getMesh().render();
		
		

	}
	public void release() {
		cube.getMesh().release();
		basicShaderProgram.release();
	}
	
}
		

	
	
	
