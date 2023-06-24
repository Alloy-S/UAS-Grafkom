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
    Camera maincamera = new Camera();
    Camera cameraMode0 = new Camera();
    Camera cameraMode1 = new Camera();
    Camera cameraMode2 = new Camera();
    private MouseInput mouseInput;
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    float distance = 1f;
    float angle = 0f;
    float rotation = (float) Math.toRadians(1f);
    float move = 0.01f;
    List<Float> temp;
    private float distanceCamera = 2f;
    private float angleAroundPlayer = 0;
    private float picth = 10;
    Vector3f TPPOffset = new Vector3f(0.0f, 1.2f, 0f);
    Vector3f FPPOffset = new Vector3f(0.f, 1.4f, -0.3f);
    int cameraMode = 0;
    private float lastFrameTime;

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


        //Stage outside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0975f, 0.650f, 0.282f, 1.0f),
                "resources/model/stage_outside.obj"
        ));
        //Stage inside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/stage_inside.obj"
        ));
        //Ring Side
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.500f, 0.442f, 0.440f, 1.0f),
                "resources/model/ring_side.obj"
        ));

        //Ring Top
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/ring_top.obj"
        ));
        //Ring Stair 1
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/stair1.obj"
        ));

        //Ring Stair 2
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/stair2.obj"
        ));
        //Ring Pole
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0600f, 0.00780f, 0.00600f, 1.0f),
                "resources/model/ring_pole.obj"
        ));
        //Ring Connector
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/ring_connector.obj"
        ));
        //Ring Cushion
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/ring_connector.obj"
        ));

        //Ring Net
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/ring_net.obj"
        ));

        //Barrier
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
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
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/chairR.obj"
        ));
        //L
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/chairL.obj"
        ));
        //F
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
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
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/lamp1_outside.obj"
        ));
        //Onside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
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
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/lamp2_outside.obj"
        ));
        //inside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/lamp2_inside.obj"
        ));


//            Character
        character.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.740f, 0.698f, 0.511f, 1.0f),
                "resources/model/char_body.obj"
        ));

        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.258f, 0.990f, 0.129f, 1.0f),
                "resources/model/char_green.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.220f, 0.534f, 0.630f, 1.0f),
                "resources/model/char_jeans.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.870f, 0.400f, 0.348f, 1.0f),
                "resources/model/char_mouth.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.880f, 0.809f, 0.801f, 1.0f),
                "resources/model/char_eye.obj"
        ));

        character.get(0).rotateObject((float) Math.toRadians(180), 0f, 1f, 0f);
        character.get(0).rotation += 180;
        character.get(0).translateObject(5f, 0f, 0f);

        List<Float> characterPos = character.get(0).getCenterPoint();
        float theta = character.get(0).rotation + angleAroundPlayer;
        float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
//            System.out.println(character.get(0).getCenterPoint());
        System.out.println(offsetX + ", " + offsetZ);
//        TPP
        cameraMode0.setRotation((float) Math.toRadians(picth), (float) Math.toRadians(180 - theta));
        cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

//        FPP
        cameraMode1.setRotation(0, 0);
        cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) + FPPOffset.x, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) + FPPOffset.z);
//        Free Cam
        cameraMode2.setRotation(0, 0);
        cameraMode2.setPosition(0f, 5f, 0f);
        maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
        maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
    }


    public void input() {
        float move = 0.1f;
        System.out.println("camera mode: " + cameraMode);

        if (window.isKeyPressed(GLFW_KEY_W)) {

            if (cameraMode >= 2) {
                cameraMode2.moveForward(move);
            } else {
                float dx = (float) (move * Math.sin(Math.toRadians(character.get(0).rotation)));
                float dz = (float) (move * Math.cos(Math.toRadians(character.get(0).rotation)));
//            System.out.println("dx: " + dx + " dz: " + dz + "player rotation: " + character.get(0).rotation);
                character.get(0).translateObject(dx, 0f, dz);
                float theta = character.get(0).rotation + angleAroundPlayer;
                float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
                float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
//            update TPP
                cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);
//            update FPP
                float offsetX1 = (float) (FPPOffset.z * Math.sin(Math.toRadians(theta)));
                float offsetZ1 = (float) (FPPOffset.z * Math.cos(Math.toRadians(theta)));
//            cameraMode1.setRotation(0f, (float) Math.toRadians(180 -theta));
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);

            }

            switch (cameraMode) {
                case 0 -> {
                    maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
                    maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
                }
                case 1 -> {
                    maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
                    maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
                }
                case 2 -> {
                    maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
                    maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
                }
            }
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {

            if (cameraMode >= 2) {
                cameraMode2.moveBackwards(move);
            } else {
                float dx = (float) (move * Math.sin(Math.toRadians(character.get(0).rotation)));
                float dz = (float) (move * Math.cos(Math.toRadians(character.get(0).rotation)));
//            System.out.println("dx: " + dx + " dz: " + dz + "player rotation: " + character.get(0).rotation);
                character.get(0).translateObject(-dx, 0f, -dz);
                float theta = character.get(0).rotation + angleAroundPlayer;
                float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
                float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
                cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

                float offsetX1 = (float) (FPPOffset.z * Math.sin(Math.toRadians(theta)));
                float offsetZ1 = (float) (FPPOffset.z * Math.cos(Math.toRadians(theta)));
//            cameraMode1.setRotation(0f, (float) Math.toRadians(180 -theta));
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
            }

            switch (cameraMode) {
                case 0 -> {
                    maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
                    maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
                }
                case 1 -> {
                    maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
                    maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
                }
                case 2 -> {
                    maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
                    maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
                }
            }
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            if (cameraMode >= 2) {
                cameraMode2.moveLeft(move);
            } else {
                Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1), character.get(0).getCenterPoint().get(2));
                character.get(0).translateObject(-characterPos.x, -characterPos.y, -characterPos.z);
                character.get(0).rotateObject((float) Math.toRadians(2), 0f, 1f, 0f);
                character.get(0).rotation += 2;
                character.get(0).translateObject(characterPos.x, characterPos.y, characterPos.z);

                float theta = character.get(0).rotation + angleAroundPlayer;
                float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
                float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
                cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);
                float offsetX1 = (float) (FPPOffset.z * Math.sin(Math.toRadians(theta)));
                float offsetZ1 = (float) (FPPOffset.z * Math.cos(Math.toRadians(theta)));
                System.out.println(-character.get(0).rotation + ", " + cameraMode1.getRotation().y);
                cameraMode1.addRotation(0, (float) Math.toRadians(-2));
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
            }
            switch (cameraMode) {
                case 0 -> {
                    maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
                    maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
                }
                case 1 -> {
                    maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
                    maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
                }
                case 2 -> {
                    maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
                    maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
                }
            }
        }

        if (window.isKeyPressed(GLFW_KEY_D)) {

            if (cameraMode >= 2) {
                cameraMode2.moveRight(move);
            } else {
                Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1), character.get(0).getCenterPoint().get(2));
                character.get(0).translateObject(-characterPos.x, -characterPos.y, -characterPos.z);
                character.get(0).rotateObject((float) Math.toRadians(-2), 0f, 1f, 0f);
                character.get(0).rotation -= 2;
                character.get(0).translateObject(characterPos.x, characterPos.y, characterPos.z);

                float theta = character.get(0).rotation + angleAroundPlayer;
                float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
                float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
                cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

                float offsetX1 = (float) (FPPOffset.z * Math.sin(Math.toRadians(theta)));
                float offsetZ1 = (float) (FPPOffset.z * Math.cos(Math.toRadians(theta)));

                cameraMode1.addRotation(0, (float) Math.toRadians(2));
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
            }
            switch (cameraMode) {
                case 0 -> {
                    maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
                    maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
                }
                case 1 -> {
                    maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
                    maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
                }
                case 2 -> {
                    maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
                    maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
                }
            }
        }

        if (mouseInput.isLeftButtonPressed()) {
            if (cameraMode >= 2) {

            } else {

            }

        }
        if (window.getMouseInput().isRightButtonPressed()) {
            if (cameraMode >= 2) {
                Vector2f displVec = window.getMouseInput().getDisplVec();
                cameraMode2.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
//                System.out.println(cameraMode1.getRotation());
                maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
            } else {
                Vector2f displVec = window.getMouseInput().getDisplVec();
                cameraMode1.addRotation(0, (float) Math.toRadians(displVec.y * 0.1f));
                System.out.println(cameraMode1.getRotation());
                maincamera.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));

            }
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            if (cameraMode >= 2) {
                cameraMode2.moveUp(move);
            }

        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            if (cameraMode >= 2) {
                cameraMode2.moveDown(move);
            }

        }


        if (window.isKeyPressed(GLFW_KEY_1)) {
            cameraMode = 0;
            maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
            maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_2)) {
            cameraMode = 1;
            maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
            maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_3)) {
            cameraMode = 2;
            maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
            maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
        }

//        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
//            maincamera.moveUp(move);
//            maincamera.moveForward(1f);
//            character.get(0).translateObject(-character.get(0).getCenterPoint().get(0), -character.get(0).getCenterPoint().get(1), -character.get(0).getCenterPoint().get(2));
//            character.get(0).translateObject(maincamera.getPosition().x - TPPOffset.x, maincamera.getPosition().y - TPPOffset.y, maincamera.getPosition().z - TPPOffset.z);
//            maincamera.moveBackwards(1f);
//        }
//
//        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
//            maincamera.moveDown(move);
//            maincamera.moveForward(1f);
//            character.get(0).translateObject(-character.get(0).getCenterPoint().get(0), -character.get(0).getCenterPoint().get(1), -character.get(0).getCenterPoint().get(2));
//            character.get(0).translateObject(maincamera.getPosition().x - TPPOffset.x, maincamera.getPosition().y - TPPOffset.y, maincamera.getPosition().z - TPPOffset.z);
//            maincamera.moveBackwards(1f);
//        }

        if (window.getMouseInput().getScroll().y != 0) {
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }



//        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
//            objectObj.get(18).translateObject(0f, 0f, -move);
////            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
////            camera.moveBackwards(distance);
//        }
//        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
//            objectObj.get(18).translateObject(-move, 0f, 0f);
////            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
////            camera.moveBackwards(distance);
//        }
//        if (window.isKeyPressed(GLFW_KEY_UP)) {
//            objectObj.get(18).translateObject(0f, 0f, move);
////            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
////            camera.moveBackwards(distance);
//
//        }
//        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
//            objectObj.get(18).translateObject(move, 0f, 0f);
////            camera.setPosition(temp.get(0), temp.get(1), temp.get(2));
////            camera.moveBackwards(distance);
//        }
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
            for (Object object : objectObj) {
                object.draw(maincamera, projection);
            }

            for (Object object : character) {
                object.draw(maincamera, projection);
            }


            // Restore state
            glDisableVertexAttribArray(0);
            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    private float getTime() {
//        System.out.println((glfwGetTime() * 1000) / glfwGetTimerFrequency());
        return (float) ((glfwGetTime() * 1000) / glfwGetTimerFrequency());
    }

    public static void main(String[] args) throws IOException {
        new Main().run();
    }
}
