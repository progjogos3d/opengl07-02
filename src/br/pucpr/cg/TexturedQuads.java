package br.pucpr.cg;

import br.pucpr.mage.*;
import br.pucpr.mage.camera.Camera;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.Material;
import org.joml.Matrix4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class TexturedQuads implements Scene {
    private static final String PATH = "/Users/vinigodoy/opengl";

    private Keyboard keys = Keyboard.getInstance();
    private boolean normals = false;

    //Dados da cena
    private Camera camera = new Camera();
    private DirectionalLight light;
    private Shader shader;

    //Dados da malha
    private Mesh mesh;
    private Material material;

    private float angleX = 0.0f;
    private float angleY = 0.0f;

    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glPolygonMode(GL_FRONT_FACE, GL_LINE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        shader = Shader.loadProgram("phong");

        camera.getPosition().z = 0.9f;
        light = new DirectionalLight()
                .setDirection(1.0f, -1.0f, -1.0f)
                .setAmbient(0.02f)
                .setColor(1.0f);

        mesh = MeshFactory.createSquare(shader);
        material = new Material()
                .setColor(0.7f)
                .setSpecular(0.5f).setPower(2.0f)
                .setTexture(PATH + "/textures/stone_t.jpg");
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), true);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            angleY += secs;
        }

        if (keys.isDown(GLFW_KEY_D)) {
            angleY -= secs;
        }

        if (keys.isDown(GLFW_KEY_W)) {
            angleX += secs;
        }

        if (keys.isDown(GLFW_KEY_S)) {
            angleX -= secs;
        }

        if (keys.isDown(GLFW_KEY_SPACE)) {
            angleY = 0;
            angleX = 0;
        }

        if (keys.isPressed(GLFW_KEY_N)) {
            normals = !normals;
        }
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shader.bind()
                .set(camera)
                .set(light)
                .unbind();

        mesh.setUniform("uWorld", new Matrix4f().rotateX(angleX).rotateY(angleY))
                .setItem("material", material)
                .draw(shader);
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new TexturedQuads(), "Textures", 1024, 768).show();
    }
}