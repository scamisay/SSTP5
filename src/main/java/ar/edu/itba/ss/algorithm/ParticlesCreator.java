package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Particle;

import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toSet;

public class ParticlesCreator {

    protected int particlesPerCall;

    public ParticlesCreator(int particlesPerCall) {
        this.particlesPerCall = particlesPerCall;
    }

    public Set<Particle> create() {
        return IntStream.range(0,particlesPerCall).boxed()
                .map( i -> createParticle())
                .collect(toSet());
    }

    //todo: definir generador aleatorios con seed
    private Particle createParticle() {
        return null;
    }
}
