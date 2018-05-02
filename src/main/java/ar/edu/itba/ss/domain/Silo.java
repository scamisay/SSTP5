package ar.edu.itba.ss.domain;

import java.util.Set;

public class Silo {

    private double width;
    private double height;
    private double exitOpeningSize;
    private double falldownLimit;

    private Set<Particle> particles;

    // L > W > D
    public Silo(double width, double height, double exitOpeningSize) {
        if(exitOpeningSize > height || height > width){
            throw new RuntimeException("width > height > exitOpeningSize");
        }
        this.width = width;
        this.height = height;
        this.exitOpeningSize = exitOpeningSize;
    }

    public boolean isFull() {
        return false;
    }

    public void addParticles(Set<Particle> newParticles) {
        newParticles.stream().forEach(p -> addParticle(p));
    }

    private void addParticle(Particle p) {
        if(canAddParticle(p)){
            particles.add(p);
        }
    }

    //todo: usar cim
    private boolean canAddParticle(Particle p) {
        return false;
    }
}
