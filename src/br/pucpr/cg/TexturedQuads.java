package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Shader;
import br.pucpr.mage.Texture;
import br.pucpr.mage.Window;
import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.PhongMaterial;

public class TexturedQuads implements Scene {
    private static final String PATH = "/Users/vinigodoy/img/opengl/";
    
    private Keyboard keys = Keyboard.getInstance();
    private boolean normals = false;
    
    //Dados da cena
    private Camera camera = new Camera();
    private DirectionalLight light;

    //Dados da malha
    private Mesh mesh;
    private PhongMaterial material; 
    
    private float angleX = 0.0f;
    private float angleY = 0.0f;
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glPolygonMode(GL_FRONT_FACE, GL_LINE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        
        camera.getPosition().z = 0.9f;
        light = new DirectionalLight(
                new Vector3f( 1.0f, -1.0f, -1.0f),    //direction
                new Vector3f( 0.02f,  0.02f,  0.02f), //ambient
                new Vector3f( 1.0f,  1.0f,  1.0f),    //diffuse
                new Vector3f( 1.0f,  1.0f,  1.0f));   //specular

        mesh = MeshFactory.createSquare();
        material = new PhongMaterial(
                new Vector3f(1.0f, 1.0f, 1.0f), //ambient
                new Vector3f(0.7f, 0.7f, 0.7f), //diffuse
                new Vector3f(0.5f, 0.5f, 0.5f), //specular
                2.0f);                          //specular power
        material.setTexture(new Texture(PATH + "textures/stone_t.jpg"));
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
        
        material.getShader().bind()
            .set(camera)
            .set(light)
        .unbind();
    
        mesh.setUniform("uWorld", new Matrix4f().rotateX(angleX).rotateY(angleY));
        mesh.draw(material);
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new TexturedQuads(), "Textures", 1024, 768).show();
    }
}
