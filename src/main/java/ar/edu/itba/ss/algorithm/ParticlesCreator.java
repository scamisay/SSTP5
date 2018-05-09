package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class ParticlesCreator {

    private Integer maxParticlesToCreate;

    public ParticlesCreator(Integer maxParticlesToCreate, ) {
        if(maxParticlesToCreate != null && maxParticlesToCreate > 0){
            this.maxParticlesToCreate = maxParticlesToCreate;
        }
    }

    public Particle create() {
        return IntStream.range(0,particlesPerCall).boxed()
                .map( i -> createParticle())
                .collect(toSet());
    }

    //todo: definir generador aleatorios con seed
    private Particle createParticle() {
        return null;
    }

}
