/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import sg.atom.core.lifecycle.AbstractManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import sg.atom.core.AtomMain;

/**
 * EffectManager.
 * 
 * Manage instance of ParticleEffect with Pools.
 * @author cuong.nguyen
 */
public class EffectManager extends AbstractManager {

    public EffectManager(AtomMain app) {
        super(app);
    }

    public void createParticleEffects() {
    }

    public ParticleEmitter createExplosion() {
        ParticleEmitter explosionEffect = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/Debris.png"));
        explosionEffect.setMaterial(debrisMat);
        explosionEffect.setImagesX(3);
        explosionEffect.setImagesY(3); // 3x3 texture animation
        explosionEffect.setRotateSpeed(4);
        explosionEffect.setSelectRandomImage(true);
        explosionEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        explosionEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
        explosionEffect.setGravity(0f, 6f, 0f);
        explosionEffect.getParticleInfluencer().setVelocityVariation(.60f);
        
        return explosionEffect;
    }
    
    public ParticleEmitter createDerbis() {
        ParticleEmitter debrisEffect = new ParticleEmitter("Debris", ParticleMesh.Type.Triangle, 10);
        Material debrisMat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debrisMat.setTexture("Texture", assetManager.loadTexture("Textures/Effects/Explosion/Debris.png"));
        debrisEffect.setMaterial(debrisMat);
        debrisEffect.setImagesX(3);
        debrisEffect.setImagesY(3); // 3x3 texture animation
        debrisEffect.setRotateSpeed(4);
        debrisEffect.setSelectRandomImage(true);
        debrisEffect.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        debrisEffect.setStartColor(new ColorRGBA(1f, 1f, 1f, 1f));
        debrisEffect.setGravity(0f, 6f, 0f);
        debrisEffect.getParticleInfluencer().setVelocityVariation(.60f);
        
        return debrisEffect;
    }
}
