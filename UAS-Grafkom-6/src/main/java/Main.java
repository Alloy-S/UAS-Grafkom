import Engine.*;
import Engine.Object;
import org.joml.*;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    private Window window = new Window(1080, 1080, "Hello World");
    ArrayList<Object> objectObj = new ArrayList<>();
    Camera camera = new Camera();
    private MouseInput mouseInput;
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float distance = 1f;
    float angle = 0f;
    float rotation = (float)Math.toRadians(1f);
    float move = 0.01f;
    List<Float> temp;


    public void run() throws IOException {

        init();
        loop();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() throws IOException {
        window.init();
        GL.createCapabilities();
        mouseInput = window.getMouseInput();
        camera.setPosition(-1.7f, 1f, 2.5f + distance);

        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1.0f,0.0f,0.0f,1.0f),
                "resources/model/Ring_Boxing.obj"
        ));

        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.5f,0.5f,0.0f,1.0f),
                "resources/model/character.obj"
        ));
    }

    public void input() {
        temp = objectObj.get(0).getCenterPoint();
        angle = angle % (float) Math.toRadians(360);
        float move = 0.1f;
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(move);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveBackwards(move);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveLeft(move);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveRight(move);
        }
        if(mouseInput.isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f),
                    (float)Math.toRadians(displayVec.y * 0.1f));
        }
        if(window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()- (window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveUp(move);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveDown(move);
        }
    }

    public void loop() {
        while (window.isOpen()) {
            window.update();
            glClearColor(1.0f,
                    1.0f, 1.0f,
                    1.0f);

            GL.createCapabilities();

            input();

            // code here
            for (Object object: objectObj) {
                object.draw(camera, projection);
            }


            // Restore state
            glDisableVertexAttribArray(0);
            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}
