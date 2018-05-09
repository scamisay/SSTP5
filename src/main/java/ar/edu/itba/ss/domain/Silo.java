package ar.edu.itba.ss.domain;

import ar.edu.itba.ss.algorithm.ParticlesCreator;

import java.io.InputStream;
import java.util.Set;
import java.util.stream.IntStream;

public class Silo{

    private double width;
    private double height;
    private double exitOpeningSize;
    private double falldownLimit;
    private Area insideSiloArea;

    private Set<Particle> particles;

    // L > W > D
    public Silo(double width, double height, double exitOpeningSize, double siloTopPadding) {
        if(exitOpeningSize > height || height > width){
            throw new IllegalArgumentException("width > height > exitOpeningSize");
        }
        this.width = width;
        this.height = height;
        this.exitOpeningSize = exitOpeningSize;
        insideSiloArea = new Area(0,siloTopPadding,width,siloTopPadding+height);
    }

    private static final int MAX_CREATION_TRIES = 1000;
    public void fillSilo(int particlesToAdd) {
        ParticlesCreator filler = new ParticlesCreator(particlesToAdd);
        IntStream.rangeClosed(0,particlesToAdd).forEach( i -> addOne(filler));
    }

    private int addOne(ParticlesCreator filler ){
        int added = 0;
        for(int intent = 1 ; intent <= MAX_CREATION_TRIES; intent++){
            Particle particle = filler.create();
            if(isThereRoomForParticle(particle)){
                addParticle(particle);
                added = 1;
                break;
            }
        }
        return added;
    }

    public boolean isFull() {
        return false;
    }

    public void addParticles(Set<Particle> newParticles) {
        newParticles.forEach(this::addParticle);
    }

    //todo: usar cim
    private boolean canAddParticle(Particle p) {
        return false;
    }

    private void addParticle(Particle particle) {
        particles.add(particle);
    }
}
