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
    ArrayList<Object> barier = new ArrayList<>();
    ArrayList<Object> ring = new ArrayList<>();
    ArrayList<Object> stair = new ArrayList<>();
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
    SkyBoxCube skybox;

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
                "resources/model/Stage/stage_outside.obj"
        ));
        //Stage inside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Stage/stage_inside.obj"
        ));
        //Ring Side
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.500f, 0.442f, 0.440f, 1.0f),
                "resources/model/Ring/ring_side.obj"
        ));

        //Ring Top
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_top.obj"
        ));
        //Ring Stair 1
        stair.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/Ring/stairL_point.obj"
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
                "resources/model/Ring/stairR_point.obj"
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
                "resources/model/Ring/ring_pole.obj"
        ));
        //Ring Connector
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.430f, 0.434f, 0.434f, 1.0f),
                "resources/model/Ring/ring_connector.obj"
        ));
        //Ring Cushion
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Ring/ring_connector.obj"
        ));

        //Ring Net
        ring.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.970f, 0.979f, 0.980f, 1.0f),
                "resources/model/Ring/ring_net.obj"
        ));

        //Barrier
        //1
        barier.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.530f, 0.504f, 0.504f, 1.0f),
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
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
                "resources/model/Barier/barier.obj"
        ));
        barier.get(38).translateObject(-1.47143f, -0.36f, -21.1495f);
        //Chair
        //R
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Chair/chairR.obj"
        ));
        //L
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Chair/chairL.obj"
        ));
        //F
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.260f, 0.232f, 0.231f, 1.0f),
                "resources/model/Chair/chairF.obj"
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
                "resources/model/Truss/truss_RF.obj"
        ));
        //LF
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_LF.obj"
        ));
        //RB
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_RB.obj"
        ));
        //LB
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_LB.obj"
        ));
        //Pillar
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.480f, 0.485f, 0.485f, 1.0f),
                "resources/model/Truss/truss_pillar.obj"
        ));
        //Lighting Outside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0200f, 0.0154f, 0.0154f, 1.0f),
                "resources/model/Truss/truss_light_outside.obj"
        ));
        //Lighting Inside
        objectObj.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.990f, 0.921f, 0.921f, 1.0f),
                "resources/model/Truss/truss_light_inside.obj"
        ));

//            Character
        character.add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.740f, 0.698f, 0.511f, 1.0f),
                "resources/model/Char/char_body.obj"
        ));

        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.258f, 0.990f, 0.129f, 1.0f),
                "resources/model/Char/char_green.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.220f, 0.534f, 0.630f, 1.0f),
                "resources/model/Char/char_jeans.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.870f, 0.400f, 0.348f, 1.0f),
                "resources/model/Char/char_mouth.obj"
        ));
        character.get(0).getChildObject().add(new Model(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.880f, 0.809f, 0.801f, 1.0f),
                "resources/model/Char/char_eye.obj"
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
        maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
        maincamera.setPosition(cameraMode0.getPosition().get(0), cameraMode0.getPosition().get(1), cameraMode0.getPosition().get(2));
    }

    boolean collision;

    public void input() {
        float move = 0.1f;
//        System.out.println("camera mode: " + cameraMode);
        float dx = (float) (move * Math.sin(Math.toRadians(character.get(0).rotation)));
        float dz = (float) (move * Math.cos(Math.toRadians(character.get(0).rotation)));
        float theta = character.get(0).rotation + angleAroundPlayer;
        float offsetX = (float) (distanceCamera * Math.sin(Math.toRadians(theta)));
        float offsetZ = (float) (distanceCamera * Math.cos(Math.toRadians(theta)));
        float offsetX1 = (float) (FPPOffset.z * Math.sin(Math.toRadians(theta)));
        float offsetZ1 = (float) (FPPOffset.z * Math.cos(Math.toRadians(theta)));

        if (window.isKeyPressed(GLFW_KEY_W)) {
            if (cameraMode >= 2) {
                cameraMode2.moveForward(move);
            } else {
//            update TPP
                character.get(0).translateObject(dx, 0f, dz);
                if (!checkCollision()) {
                    cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                    cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);
//            update FPP
                    cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
                } else {
                    character.get(0).translateObject(-dx, 0f, -dz);
                }
            }

            if (!checkCollision()) {
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
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {

            if (cameraMode >= 2) {
                cameraMode2.moveBackwards(move);
            } else {

                character.get(0).translateObject(-dx, 0f, -dz);
                if (!checkCollision()) {
                    cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                    cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

//            cameraMode1.setRotation(0f, (float) Math.toRadians(180 -theta));
                    cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
                } else {
                    character.get(0).translateObject(dx, 0f, dz);
                }
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

//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
                cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);

//                System.out.println(-character.get(0).rotation + ", " + cameraMode1.getRotation().y);
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
//            System.out.println(character.get(0).getCenterPoint());
//            System.out.println(offsetX + ", " + offsetZ);
                cameraMode0.setRotation(0f, (float) Math.toRadians(180 - theta));
                cameraMode0.setPosition(character.get(0).getCenterPoint().get(0) - offsetX, character.get(0).getCenterPoint().get(1) + TPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ);


                cameraMode1.addRotation(0, (float) Math.toRadians(2));
                cameraMode1.setPosition(character.get(0).getCenterPoint().get(0) - offsetX1, character.get(0).getCenterPoint().get(1) + FPPOffset.y, character.get(0).getCenterPoint().get(2) - offsetZ1);
            }
        }

        collision = checkCollision();

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
                maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
                maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
            }

        }

        if (window.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
            if (cameraMode >= 2) {
                cameraMode2.moveDown(move);
                maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
                maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
            }

        }


        if (window.isKeyPressed(GLFW_KEY_1)) {
            cameraMode = 0;
            maincamera.setRotation(cameraMode0.getRotation().x, cameraMode0.getRotation().y);
            maincamera.setPosition(cameraMode0.getPosition().x, cameraMode0.getPosition().y, cameraMode0.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_2)) {
            cameraMode = 1;
            System.out.println("1");
            maincamera.setRotation(cameraMode1.getRotation().x, cameraMode1.getRotation().y);
            maincamera.setPosition(cameraMode1.getPosition().x, cameraMode1.getPosition().y, cameraMode1.getPosition().z);
        }

        if (window.isKeyPressed(GLFW_KEY_3)) {
            cameraMode = 2;
            maincamera.setRotation(cameraMode2.getRotation().x, cameraMode2.getRotation().y);
            maincamera.setPosition(cameraMode2.getPosition().x, cameraMode2.getPosition().y, cameraMode2.getPosition().z);
        }

        if (window.getMouseInput().getScroll().y != 0) {
            projection.setFOV(projection.getFOV() - (window.getMouseInput().getScroll().y * 0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }
    }


    public boolean checkCollision() {
        Vector3f characterPos = new Vector3f(character.get(0).getCenterPoint().get(0), character.get(0).getCenterPoint().get(1), character.get(0).getCenterPoint().get(2));

        System.out.println("-----------------------");
        System.out.println(characterPos);
        for (Object object : barier) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 0.95) {
                System.out.println("nabrak");
                System.out.println(objPos + " = " + distance);
                return true;
            }
//            System.out.println(objPos + " = " + distance);
        }
        for (Object object : ring) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 4.5f) {
                System.out.println("nabrak");
                System.out.println(objPos + " = " + distance);
                return true;
            }
//            System.out.println(objPos + " = " + distance);
        }

        for (Object object : stair) {
            Vector3f objPos = new Vector3f(object.getCenterPoint().get(0), object.getCenterPoint().get(1), object.getCenterPoint().get(2));
            float distance = (float) Math.sqrt(Math.pow(objPos.x - characterPos.x, 2) + Math.pow(objPos.z - characterPos.z, 2));

            if (distance < 0.95) {
                System.out.println("nabrak");
                System.out.println(objPos + " = " + distance);
                return true;
            }
//            System.out.println(objPos + " = " + distance);
        }
        System.out.println("-----------------------");
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

            // code here
            for (Object object : objectObj) {
                object.draw(maincamera, projection);
            }

            for (Object object : character) {
                object.draw(maincamera, projection);
            }

            for (Object object : ring) {
                object.draw(maincamera, projection);
            }

            for (Object object : stair) {
                object.draw(maincamera, projection);
            }

            for (Object object : barier) {
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
