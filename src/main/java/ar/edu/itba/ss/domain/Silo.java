package ar.edu.itba.ss.domain;

import ar.edu.itba.ss.algorithm.ParticlesCreator;
import ar.edu.itba.ss.algorithm.cim.CellIndexMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Silo{

    private double width;
    private double height;
    private double exitOpeningSize;
    private double falldownLimit;
    private Area insideSiloArea;
    private double topPadding;
    private double bottomPadding;
    private List<Particle> particles;

    // L > W > D
    public Silo(double width, double height, double exitOpeningSize, double topPadding, double bottomPadding) {
        if(exitOpeningSize > width || width > height){
            throw new IllegalArgumentException("height > width > exitOpeningSize");
        }
        this.width = width;
        this.height = height;
        this.exitOpeningSize = exitOpeningSize;
        this.topPadding = topPadding;
        this.bottomPadding = bottomPadding;
        particles = new ArrayList<>();
        insideSiloArea = new Area(0,bottomPadding+height,width,bottomPadding);

    }

    private static final int MAX_CREATION_TRIES = 1000;
    public void fillSilo(int particlesToAdd) {
        ParticlesCreator filler = new ParticlesCreator(particlesToAdd, insideSiloArea);
        IntStream.range(0,particlesToAdd).forEach( i -> addOne(filler));
    }

    private int addOne(ParticlesCreator filler){
        int added = 0;
        for(int intent = 1 ; intent <= MAX_CREATION_TRIES; intent++){


            List<Particle> particles = new ArrayList<>();
            particles.addAll(this.particles);
            Particle particle = filler.create();
            particles.add(particle);

            //todo: ver si M esta bien
            CellIndexMethod cim = new CellIndexMethod(1, insideSiloArea.getHeight(),
                    ParticlesCreator.MAX_RADIUS*2., particles, false);
            cim.calculate();

            if(isThereRoomForParticle(particle)){
                addParticle(particle);
                added = 1;
                break;
            }
        }
        return added;
    }

    private boolean isThereRoomForParticle(Particle particle) {
        return particle.getNeighbours().stream()
                .filter( p ->  particle.overlap(p) > 0)
                .count() == 0;
    }

    private void addParticle(Particle particle) {
        particles.add(particle);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public double getHeight() {
        return height;
    }

    public double getScenarioHeight(){
        return height + bottomPadding + topPadding;
    }
}
