package br.pucpr.mage.phong;

import br.pucpr.mage.ShaderItem;
import org.joml.Vector3f;

import br.pucpr.mage.Shader;

public class DirectionalLight implements ShaderItem {
    private Vector3f direction;
    private Vector3f ambient;
    private Vector3f diffuse;
    private Vector3f specular;
    
    public DirectionalLight(Vector3f direction, Vector3f ambient, Vector3f diffuse, Vector3f specular) {
        super();
        this.direction = direction;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    @Override
    public void apply(Shader shader) {
        shader.setUniform("uLightDir", direction.normalize(new Vector3f()))
            .setUniform("uAmbientLight", ambient)
            .setUniform("uDiffuseLight", diffuse)
            .setUniform("uSpecularLight", specular);
    }
}
