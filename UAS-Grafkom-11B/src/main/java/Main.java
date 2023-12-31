import Engine.Object;
import Engine.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL20.*;

public class Main {
    private Window window = new Window(1920, 1080, "Hello World");
    ArrayList<Object> objectObj = new ArrayList<>();
    ArrayList<Object> character = new ArrayList<>();
    ArrayList<Object> character2 = new ArrayList<>();
    ArrayList<Object> barier = new ArrayList<>();
    ArrayList<Object> ring = new ArrayList<>();
    ArrayList<Object> sideRing = new ArrayList<>();
    ArrayList<Object> stair = new ArrayList<>();
    ArrayList<Object> totem = new ArrayList<>();
    ArrayList<Object> container = new ArrayList<>();
    ArrayList<Object> pole = new ArrayList<>();
    ArrayList<Object> net = new ArrayList<>();
    Camera maincamera = new Camera();
    Camera cameraMode0 = new Camera();
    Camera cameraMode1 = new Camera();
    Camera cameraMode2 = new Camera();
    Camera cameraMode3 = new Camera();
    Camera cameraMode4 = new Camera();
    Camera cameraMode5 = new Camera();
    private MouseInput mouseInput;
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    private float distanceCamera = 2f;
    private float angleAroundPlayer = 0;
    private float angle360 = 0;
    private float angleFPPX = 0;
    private float angleFPPY = 0;
    Vector3f TPPOffset = new Vector3f(0.0f, 1.2f, 0f);
    Vector3f FPPOffset = new Vector3f(0.f, 1.05f, -0.3f);
    int cameraMode = 0;
    SkyBoxCube skybox;
    boolean goIn = false;
    boolean goOut = false;
    boolean insideRing = false;

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
//        skybox = new SkyBoxCube();
        skybox = new SkyBoxCube(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/skybox.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/skybox.vert", GL_VERTEX_SHADER)
                )
        );
        //Stage outside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.865f, 0.990f, 0.782f, 1.0f),
                "resources/model/Stage/stage_outside.obj",
                false
        ));
        //Stage inside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Stage/stage_inside.obj",
                false
        ));
        //SideRing
        //L
        sideRing.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.500f, 0.442f, 0.440f, 1.0f),
                "resources/model/Ring/ring_sideVertical.obj",
                false
        ));
        sideRing.get(0).rotateObject((float) Math.toRadians(180), 0f, 1f, 0f);
        sideRing.get(0).translateObject(3.25547f, 0.0f, -0.140957f);
        //R
        sideRing.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.500f, 0.442f, 0.440f, 1.0f),
                "resources/model/Ring/ring_sideVertical.obj",
                false
        ));
        sideRing.get(1).translateObject(-3.27045f, 0.0f, 0.138541f);
        //B
        sideRing.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.500f, 0.442f, 0.440f, 1.0f),
                "resources/model/Ring/ring_sideHORIZONTAL.obj",
                false
        ));
        sideRing.get(2).translateObject(-0.145017f, 0.0f, 3.40085f);
        //F
        sideRing.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.500f, 0.442f, 0.440f, 1.0f),
                "resources/model/Ring/ring_sideHORIZONTAL.obj",
                false
        ));
        sideRing.get(3).rotateObject((float) Math.toRadians(180), 0f, 1f, 0f);
        sideRing.get(3).translateObject(0.145017f, 0.0f, -3.38976f);

        //Ring Top
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_top.obj",
                false
        ));
        //Ring Stair 1
        stair.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/Ring/stairL_point.obj",
                false
        ));
        stair.get(0).translateObject(3.41534f, -0.05f, -3.56649f);

        //Ring Stair 2
        stair.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/Ring/stairR_point.obj",
                false
        ));
        stair.get(1).translateObject(-3.45392f, -0.05f, 3.54823f);
        //Ring Pole
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0600f, 0.00780f, 0.00600f, 1.0f),
                "resources/model/Ring/ring_pole.obj",
                false
        ));
        //Ring Connector
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/Ring/ring_connector.obj",
                false
        ));
        //Ring Cushion
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Ring/ring_connector.obj",
                false
        ));
        //Ring Pole
        //RF
        pole.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0600f, 0.00780f, 0.00600f, 1.0f),
                "resources/model/Ring/pole_point.obj",
                false
        ));
        pole.get(0).translateObject(-3.40388f, 1.05951f, -3.57681f);
        //LF
        pole.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0600f, 0.00780f, 0.00600f, 1.0f),
                "resources/model/Ring/pole_point.obj",
                false
        ));
        pole.get(1).translateObject(3.39651f, 1.05951f, -3.57681f);
        //RB
        pole.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0600f, 0.00780f, 0.00600f, 1.0f),
                "resources/model/Ring/pole_point.obj",
                false
        ));
        pole.get(2).translateObject(-3.40388f, 1.05951f, 3.57681f);
        //LB
        pole.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0600f, 0.00780f, 0.00600f, 1.0f),
                "resources/model/Ring/pole_point.obj",
                false
        ));
        pole.get(3).translateObject(3.39651f, 1.05951f, 3.57681f);

        //Ring Net
        //F
        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts.obj",
                false
        ));
        net.get(0).translateObject(1.37081f, 1.78004f, -2.96735f);

        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts.obj",
                false
        ));
        net.get(1).translateObject(-1.44812f, 1.78004f, -2.96735f);

        //B
        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts.obj",
                false
        ));
        net.get(2).translateObject(1.41448f, 1.78004f, 2.99452f);

        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts.obj",
                false
        ));
        net.get(3).translateObject(-1.40711f, 1.78004f, 2.99452f);
        //Barrier
        //R
        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts2.obj",
                false
        ));
        net.get(4).translateObject(-2.87052f, 1.78012f, 1.64151f);

        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts3.obj",
                false
        ));
        net.get(5).translateObject(-2.87048f, 1.78012f, -1.60665f);

        //L
        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts2.obj",
                false
        ));
        net.get(6).translateObject(2.86315f, 1.78012f, 1.64583f);

        net.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_netParts3.obj",
                false
        ));
        net.get(7).translateObject(2.86315f, 1.78012f, -1.60665f);

        //Barrier
        //1
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(0).translateObject(1.27622f, -0.36f, -21.1026f);
        //2
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(1).translateObject(1.27622f, -0.36f, -18.7194f);
        //3
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(2).translateObject(1.27622f, -0.36f, -16.3464f);
        //4
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(3).translateObject(1.27622f, -0.36f, -13.979f);
        //5
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(4).translateObject(1.27622f, -0.36f, -11.6362f);
        //6
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(5).translateObject(1.27622f, -0.36f, -9.21208f);
//        7
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(6).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(6).translateObject(2.30669f, -0.36f, -7.86746f);
        //8
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(7).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(7).translateObject(4.65895f, -0.36f, -7.86746f);
        //9
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(8).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(8).translateObject(6.93514f, -0.36f, -7.86746f);
        //10
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(9).translateObject(8.34971f, -0.36f, -6.41871f);
        //11
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(10).translateObject(8.34971f, -0.36f, -4.06022f);
        //12
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(11).translateObject(8.34971f, -0.36f, -1.72914f);
        //13
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(12).translateObject(8.34971f, -0.36f, 0.61934f);
        //14
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(13).translateObject(8.34971f, -0.36f, 2.98676f);
        //15
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(14).translateObject(8.34971f, -0.36f, 5.32952f);
        //16
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(15).translateObject(8.34971f, -0.36f, 7.66422f);
        //17
        //17
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(16).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(16).translateObject(6.88403f, -0.36f, 8.81884f);
        //18
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(17).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(17).translateObject(4.53554f, -0.36f, 8.81884f);
        //19
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(18).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(18).translateObject(2.18329f, -0.36f, 8.81884f);
        //20
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(19).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(19).translateObject(-0.165194f, -0.36f, 8.81884f);
        //21
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(20).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(20).translateObject(-2.53262f, -0.36f, 8.81884f);
        //22
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(21).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(21).translateObject(-4.87966f, -0.36f, 8.81884f);
        //23
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(22).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(22).translateObject(-7.16141f, -0.36f, 8.81884f);
        //24
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(23).translateObject(-8.45367f, -0.36f, 7.62012f);
        //25
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(24).translateObject(-8.45367f, -0.36f, 5.28546f);
        //26
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(25).translateObject(-8.45367f, -0.36f, 2.94268f);
        //27
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(26).translateObject(-8.45367f, -0.36f, 0.57526f);
        //28
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(27).translateObject(-8.45367f, -0.36f, -1.77323f);
        //29
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(28).translateObject(-8.45367f, -0.36f, -4.12548f);
        //30
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(29).translateObject(-8.45367f, -0.36f, -6.47396f);
        //31
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(30).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(30).translateObject(-7.20474f, -0.36f, -7.85814f);
        //32
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(31).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(31).translateObject(-4.87008f, -0.36f, -7.85814f);
        //33
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(32).rotateObject((float) Math.toRadians(90), 0f, 1f, 0f);
        barier.get(32).translateObject(-2.5273f, -0.36f, -7.85814f);
        //34
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(33).translateObject(-1.47143f, -0.36f, -9.25968f);
        //35
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(34).translateObject(-1.47143f, -0.36f, -11.5943f);
        //36
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(35).translateObject(-1.47143f, -0.36f, -13.9371f);
        //37
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(36).translateObject(-1.47324f, -0.36f, -16.3568f);
        //38
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(37).translateObject(-1.47143f, -0.36f, -18.7749f);
        //39
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj",
                false
        ));
        barier.get(38).translateObject(-1.47143f, -0.36f, -21.1495f);
        //        container
//        right
        container.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.730f, 0.0730f, 0.0730f, 1.0f),
                "resources/model/Container/red_container.obj",
                false
        ));
//      back
        container.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.172f, 0.730f, 0.0730f, 1.0f),
                "resources/model/Container/green_container.obj",
                false
        ));
//      left
        container.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0730f, 0.325f, 0.730f, 1.0f),
                "resources/model/Container/blue_container.obj",
                false
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
                "resources/model/Chair/chairR.obj",
                false
        ));
        //L
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Chair/chairL.obj",
                false
        ));

        //F
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Chair/chairF.obj",
                false
        ));

        //Truss
        //RF
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_RF.obj",
                false
        ));
        //LF
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_LF.obj",
                false
        ));
        //RB
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_RB.obj",
                false
        ));
        //LB
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_LB.obj",
                false
        ));
        //Pillar
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_pillar.obj",
                false
        ));
        //Lighting Outside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0200f, 0.0154f, 0.0154f, 1.0f),
                "resources/model/Truss/truss_light_outside.obj",
                false
        ));
        //Lighting Inside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.990f, 0.921f, 0.921f, 1.0f),
                "resources/model/Truss/truss_light_inside.obj",
                true
        ));

        totem.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.590f, 0.303f, 0.0354f, 1.0f),
                "resources/model/Totem/base_totem.obj",
                true
        ));

        totem.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 0.84f, 0f, 1.0f),
                "resources/model/Totem/totem.obj",
                true
        ));
        totem.get(0).rotateObject((float) Math.toRadians(-90), 0f, 1f, 0f);
        totem.get(0).translateObject(-2.86893f, -0.15f, -20.5927f);

        totem.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.590f, 0.303f, 0.0354f, 1.0f),
                "resources/model/Totem/base_totem.obj",
                true
        ));


        totem.get(1).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 0.84f, 0f, 1.0f),
                "resources/model/Totem/totem.obj",
                true
        ));

        totem.get(1).translateObject(2.8171f, -0.15f, -20.5927f);

        totem.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.590f, 0.303f, 0.0354f, 1.0f),
                "resources/model/Totem/base_totem.obj",
                true
        ));

        totem.get(2).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 0.84f, 0f, 1.0f),
                "resources/model/Totem/totem.obj",
                true
        ));
        totem.get(2).rotateObject((float) Math.toRadians(-90), 0f, 1f, 0f);
        totem.get(2).translateObject(-2.86893f, -0.15f, -12.5927f);

        totem.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.590f, 0.303f, 0.0354f, 1.0f),
                "resources/model/Totem/base_totem.obj",
                true
        ));


        totem.get(3).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(1f, 0.84f, 0f, 1.0f),
                "resources/model/Totem/totem.obj",
                true
        ));

        totem.get(3).translateObject(2.8171f, -0.15f, -12.5927f);

        //Character JOHN CENA
        character.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.868f, 0.766f, 1.0f),
                "resources/model/Char/char_body.obj",
                false
        ));

        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.258f, 0.990f, 0.129f, 1.0f),
                "resources/model/Char/char_green.obj",
                false
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.220f, 0.534f, 0.630f, 1.0f),
                "resources/model/Char/char_jeans.obj",
                false
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.870f, 0.400f, 0.348f, 1.0f),
                "resources/model/Char/char_mouth.obj",
                false
        ));



        //Character The ROCK
        character2.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.350f, 0.250f, 0.150f, 1.0f),
                "resources/model/Char 2/char2_body.obj",
                true
        ));

        character2.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.980f, 0.0490f, 0.530f, 1.0f),
                "resources/model/Char 2/char2_outfit.obj",
                false
        ));
        character2.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.790f, 0.729f, 0.711f, 1.0f),
                "resources/model/Char 2/char2_mata.obj",
                false
        ));
        character2.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.760f, 0.603f, 0.555f, 1.0f),
                "resources/model/Char 2/char2_mulut.obj",
                false
        ));

//        character.get(0).rotateObject((float) Math.toRadians(180), 0f, 1f, 0f);
//        character.get(0).rotation += 180;
        character.get(0).translateObject(-0.10231846f, 0.8f, -20.969143f);

        character2.get(0).rotateObject((float) Math.toRadians(135), 0f, 1f, 0f);
        character2.get(0).rotation += 180;
        character2.get(0).translateObject(-2.15f, 1.95f, 2.35f);


//        character.get(0).rotateObject((float) Math.toRadians(180), 0f, 1f, 0f);
//        character.get(0).rotation += 180;
//        character.get(0).translateObject(4f, 0.0f, -19.963898f);

        float theta = character.get(0).rotation + angleAroundPlayer;
        float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
        float offsetX1 = (float) (FPPOffset.z * Math.sin(Math.toRadians(theta)));
        float offsetZ1 = (float) (FPPOffset.z * Math.cos(Math.toRadians(theta)));
//            System.out.println(character.get(0).getCenterPoint());
//        System.out.println(offsetX + ", " + offsetZ);
//        TPP
        float picth = 10;
        cameraMode0.setRotation((float) Math.toRadians(picth), (float) Math.toRadians(180 - theta));
        cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

//        FPP
        cameraMode1.setRotation(0, (float) Math.toRadians(180));
        cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
//        Free Cam
        cameraMode2.setRotation(0, (float) Math.toRadians(180));
        cameraMode2.setPosition(-0.07410684f, 3.8507197f, -30.646013f);
//        set camera 3
        cameraMode3.setRotation((float) Math.toRadians(picth), 0);
        cameraMode3.setPosition(0f, 5f, 10f);
        //set camera 4
        cameraMode4.setRotation((float) Math.toRadians(35), (float) Math.toRadians(130));
        cameraMode4.setPosition(-10f, 13.96f, -8.866f);

//        camera 5
        cameraMode5.setRotation((float) Math.toRadians(0), (float) Math.toRadians(180));
        cameraMode5.setPosition(0f, 0f, -30f);
//        set main camera
        maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
        maincamera.setPosition(cameraMode0.getPosition().get(0), cameraMode0.getPosition().get(1), cameraMode0.getPosition().get(2));
    }

    public void input() {
//        System.out.println(maincamera.getPosition());
        float move = 0.05f;
//        System.out.println("camera mode: " + cameraMode);
        float dx = (float) (move * Math.sin(Math.toRadians(character.get(0).rotation)));
        float dz = (float) (move * Math.cos(Math.toRadians(character.get(0).rotation)));
        float theta = character.get(0).rotation + angleAroundPlayer;
        float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
        float offsetX1 = (float) (FPPOffset.z * Math.sin(Math.toRadians(theta)));
        float offsetZ1 = (float) (FPPOffset.z * Math.cos(Math.toRadians(theta)));

        if (window.isKeyPressed(GLFW_KEY_W)) {
            if (cameraMode == 2) {
                cameraMode2.moveForward(move);
            } else {

                character.get(0).translateObject(dx, 0f, dz);
                if (!checkCollision()) {
                    // update TPP
                    cameraMode0.setRotation(cameraMode0.getRotation().x, (float) Math.toRadians(180 - theta));
                    cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);
                    //  update FPP
                    cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
                } else {
                    character.get(0).translateObject(-dx, 0f, -dz);
                }
            }

        }
        if (window.isKeyPressed(GLFW_KEY_S)) {

            if (cameraMode == 2) {
                cameraMode2.moveBackwards(move);
            } else {

                character.get(0).translateObject(-dx, 0f, -dz);
                if (!checkCollision()) {
                    cameraMode0.setRotation(cameraMode0.getRotation().x, (float) Math.toRadians(180 - theta));
                    cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

//            cameraMode1.setRotation(0f, (float) Math.toRadians(180 -theta));
                    cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
                } else {
                    character.get(0).translateObject(dx, 0f, dz);
                }
            }
        }

        if (window.isKeyPressed(GLFW_KEY_A)) {
            if (cameraMode == 2) {
                cameraMode2.moveLeft(move);
            } else {
                Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1), character.get(0).getCenterPoint().get(2));
                character.get(0).translateObject(-characterPos.x, -characterPos.y, -characterPos.z);
                character.get(0).rotateObject((float) Math.toRadians(2), 0f, 1f, 0f);
                character.get(0).rotation += 2;
                character.get(0).translateObject(characterPos.x, characterPos.y, characterPos.z);

//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
                cameraMode0.setRotation(cameraMode0.getRotation().x, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

//                System.out.println(-character.get(0).rotation + ", " + cameraMode1.getRotation().y);
                angleFPPY -= 2;
                cameraMode1.addRotation(0, (float) Math.toRadians(-2));
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
            }
        }

        if (window.isKeyPressed(GLFW_KEY_D)) {

            if (cameraMode == 2) {
                cameraMode2.moveRight(move);
            } else {
                Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1), character.get(0).getCenterPoint().get(2));
                character.get(0).translateObject(-characterPos.x, -characterPos.y, -characterPos.z);
                character.get(0).rotateObject((float) Math.toRadians(-2), 0f, 1f, 0f);
                character.get(0).rotation -= 2;
                character.get(0).translateObject(characterPos.x, characterPos.y, characterPos.z);

//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
                cameraMode0.setRotation(cameraMode0.getRotation().x, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

                angleFPPY += 2;
                cameraMode1.addRotation(0, (float) Math.toRadians(2));
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
            }
        }

        if (mouseInput.isLeftButtonPressed()) {
            Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1), character.get(0).getCenterPoint().get(2));
            if (!insideRing && goIn) {
                insideRing = true;
                character.get(0).translateObject(-characterPos.x, 1.1f, -characterPos.z);
//                tpp update
                cameraMode0.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);
//              fpp update
//                cameraMode1.addRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
                goIn = false;
            }
            if(insideRing && goOut){
                insideRing = false;
                character.get(0).translateObject(-characterPos.x, -1.1f, -7f);
                goOut = false;
//                tpp update
                cameraMode0.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);
//              fpp update
//                cameraMode1.addRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
                goOut = false;
            }
        }

        if (window.getMouseInput().isRightButtonPressed()) {
            if (cameraMode == 0) {
                Vector2f displVec = window.getMouseInput().getDisplVec();
                float angel = (float) Math.toRadians(displVec.y * 3f);
                cameraMode0.addRotation(0, angel);
                angleAroundPlayer += angel;
//                System.out.println(cameraMode0.getRotation());
                cameraMode0.setRotation(cameraMode0.getRotation().x, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);
            } else if (cameraMode == 1) {
                Vector2f displVec = window.getMouseInput().getDisplVec();
                float degreeX = (float) Math.toRadians(displVec.x * 0.1f);
                float degreeY = (float) Math.toRadians(displVec.y * 0.1f);
                cameraMode1.addRotation(degreeX, degreeY);
                angleFPPY += degreeY;
//                angleFPPX += degreeX;
                Vector2f currDegree = new Vector2f((float) Math.toDegrees(cameraMode1.getRotation().x), (float) Math.toDegrees(cameraMode1.getRotation().y));
                System.out.println("currdegre: " + currDegree);
                float limitBawah = 82f + angleFPPY;
                float limitAtas = 271f + angleFPPY;
                if (currDegree.y < limitBawah || currDegree.y > limitAtas) {
                    cameraMode1.addRotation(0, -degreeY);
                }

//                System.out.println("bawah: " + limitBawah + ", atas: " + limitAtas);
                if (currDegree.x < -20f || currDegree.x > 89f) {
                    cameraMode1.addRotation(-degreeX, 0);
                }
            } else if (cameraMode == 2) {
                Vector2f displVec = window.getMouseInput().getDisplVec();
                cameraMode2.addRotation((float) Math.toRadians(displVec.x * 0.1f), (float) Math.toRadians(displVec.y * 0.1f));
//                System.out.println(cameraMode2.getRotation());
            }

        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            if (cameraMode == 2) {
                cameraMode2.moveUp(move);
            }

        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            if (cameraMode == 2) {
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
//            System.out.println("1");
            maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
            maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_3)) {
            cameraMode = 2;
            maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
            maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_4)) {
            cameraMode = 3;
            maincamera.setRotation(cameraMode3.getRotation().x, cameraMode3.getRotation().y);
            maincamera.setPosition(cameraMode3.getPosition().x, cameraMode3.getPosition().y, cameraMode3.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_5)) {
            cameraMode = 4;
        }
        if (window.isKeyPressed(GLFW_KEY_6)) {
            cameraMode = 5;
        }

        if (window.getMouseInput().getScroll().y != 0) {
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }

        switch (cameraMode) {
            case 0 -> {
                System.out.println(character.get(0).getCenterPoint());
                maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
                maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
            }
            case 1 -> {
                maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
                maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
            }
            case 2 -> {
                System.out.println(maincamera.getPosition().x + ", " + maincamera.getPosition().y + ", " +maincamera.getPosition().z);
                maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
                maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
            }
            case 3 -> {
                cameraMode3.addRotation(0, (float) Math.toRadians(0.6));
                angle360 += 0.6;
                theta = angle360;
                offsetX = (float) (10 * Math.sin(Math.toRadians(theta)));
                offsetZ = (float) (10 * Math.cos(Math.toRadians(theta)));
                cameraMode3.setRotation(cameraMode3.getRotation().x, (float) Math.toRadians(180 - theta));
                cameraMode3.setPosition(0 - offsetX, 5, 0 - offsetZ);
                maincamera.setRotation(cameraMode3.getRotation().x, cameraMode3.getRotation().y);
                maincamera.setPosition(cameraMode3.getPosition().x, cameraMode3.getPosition().y, cameraMode3.getPosition().z);
            }
            case 4 -> {
                maincamera.setRotation(cameraMode4.getRotation().x, cameraMode4.getRotation().y);
                maincamera.setPosition(cameraMode4.getPosition().x, cameraMode4.getPosition().y, cameraMode4.getPosition().z);
            }

            case 5 -> {
                System.out.println(cameraMode5.getPosition().x + ", " + cameraMode5.getPosition().y + ", " + cameraMode5.getPosition().z);
                if (cameraMode5.getPosition().y < 4.2f) {
                    cameraMode5.moveForward(0.025f);
                    cameraMode5.addRotation((float) Math.toRadians(-0.1), 0f);
                } else {
                    cameraMode5.setRotation((float) Math.toRadians(0), (float) Math.toRadians(180));
                    cameraMode5.setPosition(0f, 0f, -30f);
                    cameraMode = 3;
                }

                maincamera.setRotation(cameraMode5.getRotation().x, cameraMode5.getRotation().y);
                maincamera.setPosition(cameraMode5.getPosition().x, cameraMode5.getPosition().y, cameraMode5.getPosition().z);
            }
        }
    }


    public boolean checkCollision() {
        Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1), character.get(0).getCenterPoint().get(2));
        float characterDistance = (float) Math.sqrt(Math.pow(0 - characterPos.x, 2) + Math.pow(0 - characterPos.z, 2));
        System.out.println("char dis: " + characterDistance);
        if (characterDistance > 21f) {
            return true;
        }
//        System.out.println("-----------------------");
//        System.out.println(characterPos);
        for (Object object : barier) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 1.1) {
//                System.out.println("nabrak");
//                System.out.println(objPos + " = " + distance);
                return true;
            }
//            System.out.println(objPos + " = " + distance);
        }

        for (Object object : sideRing) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 1f) {
//                System.out.println("nabrak");
//                System.out.println(objPos + " = " + distance);
                if (insideRing) {
                    goOut = true;
                }
                return true;
            }
//            System.out.println(objPos + " = " + distance);
        }

        for (Object object : stair) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 1.1) {
//                System.out.println("nabrak");
//                System.out.println(objPos + " = " + distance);
                if (!insideRing) {
                    goIn = true;
                }
                return true;
            }

//            System.out.println(objPos + " = " + distance);
        }
//        System.out.println("-----------------------");

        for (Object object : net) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 0.9) {
//                System.out.println("nabrak");
//                System.out.println(objPos + " = " + distance);
                if(insideRing) {
                    goOut = true;
                }
                return true;
            }
//            System.out.println(objPos + " = " + distance);
        }

        for (Object object : pole) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 1.1f) {
//                System.out.println("nabrak");
//                System.out.println(objPos + " = " + distance);
                return true;
            }
//            System.out.println(objPos + " = " + distance);
        }
//        System.out.println("-----------------------");
        return false;
    }

    public void loop() {
        while (window.isOpen()) {
            window.update();
            glClearColor(1.0f,
                    1.0f, 1.0f,
                    1.0f);

            GL.createCapabilities();

            input();

            Random random = new Random();
            if (random.nextFloat() >= 0.98) {
                totem.get(1).getChildObject().get(0).setLightObject(!totem.get(1).isLightObject());
                totem.get(1).setLightObject(!totem.get(1).isLightObject());
            }

            // code here
            for (Object object : objectObj) {
                object.draw(maincamera, projection);
            }
//            if (cameraMode != 1) {
            for (Object object : character) {
                object.draw(maincamera, projection);
            }

            for (Object object : character2) {
                object.draw(maincamera, projection);
            }

            for (Object object : ring) {
                object.draw(maincamera, projection);
            }

            for (Object object : sideRing) {
                object.draw(maincamera, projection);
            }

            for (Object object : stair) {
                object.draw(maincamera, projection);
            }

            for (Object object : pole) {
                object.draw(maincamera, projection);
            }

            for (Object object : net) {
                object.draw(maincamera, projection);
            }

            for (Object object : barier) {
                object.draw(maincamera, projection);
            }

            for (Object object : container) {
                object.draw(maincamera, projection);
            }

            for (Object object : totem) {
                object.draw(maincamera, projection);
            }

            skybox.draw(maincamera, projection);


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
