import Engine.Object;
import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;

public class Main {
    private Window window = new Window(1080, 1080, "Hello World");
    ArrayList<Object> objectObj = new ArrayList<>();
    ArrayList<Object> character = new ArrayList<>();
    ArrayList<Object> objects = new ArrayList<>();
    Camera camera = new Camera();
    private MouseInput mouseInput;
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float distance = 1f;
    float angle = 0f;
    float rotation = (float)Math.toRadians(1f);
    float move = 0.01f;
    List<Float> temp;
    private float distanceCamera = 1.5f;
    private float angleAroundPlayer = 0;
    private float picth = 20;
    Vector3f characterOffset = new Vector3f(0.0f, 1f, 0f);
    Vector3f cameraOffset = new Vector3f();


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
        camera.setRotation((float) Math.toRadians(picth), 0f);
        camera.setPosition(-1.7f, 1f, 2.5f + distance);

        //Stage outside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0975f, 0.650f, 0.282f,1.0f),
                "resources/model/stage_outside.obj"
        ));
        //Stage inside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f,1.0f),
                "resources/model/stage_inside.obj"
        ));
        //Ring Side
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.500f, 0.442f, 0.440f,1.0f),
                "resources/model/ring_side.obj"
        ));

        //Ring Top
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f,1.0f),
                "resources/model/ring_top.obj"
        ));
        //Ring Stair 1
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f,1.0f),
                "resources/model/stair1.obj"
        ));

        //Ring Stair 2
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f,1.0f),
                "resources/model/stair2.obj"
        ));
        //Ring Pole
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0600f, 0.00780f, 0.00600f,1.0f),
                "resources/model/ring_pole.obj"
        ));
        //Ring Connector
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f,1.0f),
                "resources/model/ring_connector.obj"
        ));
        //Ring Cushion
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f,1.0f),
                "resources/model/ring_connector.obj"
        ));

        //Ring Net
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f,1.0f),
                "resources/model/ring_net.obj"
        ));

        //Barrier
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f,1.0f),
                "resources/model/barier.obj"
        ));

        //Chair
            //R
            objectObj.add(new Model(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.260f, 0.232f, 0.231f,1.0f),
                    "resources/model/chairR.obj"
            ));
            //L
            objectObj.add(new Model(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.260f, 0.232f, 0.231f,1.0f),
                    "resources/model/chairL.obj"
            ));
            //F
            objectObj.add(new Model(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.260f, 0.232f, 0.231f,1.0f),
                    "resources/model/chairF.obj"
            ));

        //Lamp 1
            //Outside
            objectObj.add(new Model(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.260f, 0.232f, 0.231f,1.0f),
                    "resources/model/lamp1_outside.obj"
            ));
            //Onside
            objectObj.add(new Model(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.970f, 0.979f, 0.980f,1.0f),
                    "resources/model/lamp1_inside.obj"
            ));

        //Lamp 2
            //Outside
            objectObj.add(new Model(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.260f, 0.232f, 0.231f,1.0f),
                    "resources/model/lamp2_outside.obj"
            ));
            //inside
            objectObj.add(new Model(
                    Arrays.asList(
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                            new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                    ),
                    new ArrayList<>(),
                    new Vector4f(0.970f, 0.979f, 0.980f,1.0f),
                    "resources/model/lamp2_inside.obj"
            ));


//            Character
        character.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.740f, 0.698f, 0.511f,1.0f),
                "resources/model/char_body.obj"
        ));

        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.258f, 0.990f, 0.129f,1.0f),
                "resources/model/char_green.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.220f, 0.534f, 0.630f,1.0f),
                "resources/model/char_jeans.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.870f, 0.400f, 0.348f,1.0f),
                "resources/model/char_mouth.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.880f, 0.809f, 0.801f,1.0f),
                "resources/model/char_eye.obj"
        ));

        List<Float> characterPos = character.get(0).getCenterPoint();
        camera.setPosition(characterPos.get(0) + characterOffset.x, characterPos.get(1) + characterOffset.y, characterPos.get(2) + characterOffset.z + distanceCamera);
    }



    public void input() {
        temp = objectObj.get(0).getCenterPoint();
        angle = angle % (float) Math.toRadians(360);
        float move = 0.1f;
        if (window.isKeyPressed(GLFW_KEY_W)) {
//            camera.moveForward(move);

            character.get(0).translateObject(0f, 0f, -move);
            camera.setPosition(character.get(0).getCenterPoint().get(0) + characterOffset.x, character.get(0).getCenterPoint().get(1) + characterOffset.y ,         character.get(0).getCenterPoint().get(2) + characterOffset.z + distanceCamera);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
//            camera.moveBackwards(move);

            character.get(0).translateObject(0f, 0f, move);
            camera.setPosition(character.get(0).getCenterPoint().get(0) + characterOffset.x, character.get(0).getCenterPoint().get(1) + characterOffset.y , character.get(0).getCenterPoint().get(2) + characterOffset.z + distanceCamera);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
//            camera.moveLeft(move);

            character.get(0).translateObject(-move, 0f, 0f);
            camera.setPosition(character.get(0).getCenterPoint().get(0) + characterOffset.x, character.get(0).getCenterPoint().get(1) + characterOffset.y , character.get(0).getCenterPoint().get(2) + characterOffset.z + distanceCamera);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
//            camera.moveRight(move);

            character.get(0).translateObject(move, 0f, 0f);
            camera.setPosition(character.get(0).getCenterPoint().get(0) + characterOffset.x, character.get(0).getCenterPoint().get(1) + characterOffset.y , character.get(0).getCenterPoint().get(2) + characterOffset.z + distanceCamera);
        }

        if (window.isKeyPressed(GLFW_KEY_Z)) {
            Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1) , character.get(0).getCenterPoint().get(2));
            character.get(0).translateObject(-characterPos.x, -characterPos.y , -characterPos.z);
            character.get(0).rotateObject((float) Math.toRadians(1), 0f, 1f, 0f);
            character.get(0).translateObject(characterPos.x, characterPos.y , characterPos.z);
        }

        if (window.isKeyPressed(GLFW_KEY_X)) {
            Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1) , character.get(0).getCenterPoint().get(2));
            character.get(0).translateObject(-characterPos.x, -characterPos.y , -characterPos.z);
            character.get(0).rotateObject((float) Math.toRadians(-1), 0f, 1f, 0f);
            character.get(0).translateObject(characterPos.x, characterPos.y , characterPos.z);
        }

        if(mouseInput.isLeftButtonPressed()){

            Vector2f displayVector = window.getMouseInput().getDisplVec();
            camera.addRotation((float) Math.toRadians(displayVector.x * 0.1f), (float) Math.toRadians(displayVector.y * 0.1f));
            float horizontalDistance = (float) (distanceCamera * Math.cos(Math.toRadians(picth)));
            float verticalDistance = (float) (distanceCamera * Math.sin(Math.toRadians(picth)));
            float theta = 0;
            float offsetX = (float) (horizontalDistance *  Math.cos(Math.toRadians(picth)));
            camera.setPosition(character.get(0).getCenterPoint().get(0) + characterOffset.x, character.get(0).getCenterPoint().get(1) + characterOffset.y  + verticalDistance, character.get(0).getCenterPoint().get(2) + characterOffset.z + distanceCamera);
        }

        if (window.getMouseInput().isRightButtonPressed()) {
            Vector2f displVec = window.getMouseInput().getDisplVec();

            camera.moveForward(distance);
            camera.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
            camera.moveBackwards(distance);

        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            camera.moveUp(move);
            camera.moveForward(1f);
            character.get(0).translateObject(-character.get(0).getCenterPoint().get(0), -character.get(0).getCenterPoint().get(1), -character.get(0).getCenterPoint().get(2));
            character.get(0).translateObject(camera.getPosition().x - characterOffset.x,camera.getPosition().y - characterOffset.y,camera.getPosition().z - characterOffset.z);
            camera.moveBackwards(1f);
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            camera.moveDown(move);
            camera.moveForward(1f);
            character.get(0).translateObject(-character.get(0).getCenterPoint().get(0), -character.get(0).getCenterPoint().get(1), -character.get(0).getCenterPoint().get(2));
            character.get(0).translateObject(camera.getPosition().x - characterOffset.x,camera.getPosition().y - characterOffset.y,camera.getPosition().z - characterOffset.z);
            camera.moveBackwards(1f);
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

        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            objectObj.get(18).translateObject(0f, 0f, -move);
//            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
//            camera.moveBackwards(distance);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            objectObj.get(18).translateObject(-move, 0f, 0f);
//            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
//            camera.moveBackwards(distance);
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            objectObj.get(18).translateObject(0f, 0f, move);
//            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
//            camera.moveBackwards(distance);

        }
        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            objectObj.get(18).translateObject(move, 0f, 0f);
//            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
//            camera.moveBackwards(distance);
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

            for (Object object: character) {
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
