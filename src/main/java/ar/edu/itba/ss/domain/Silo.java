package ar.edu.itba.ss.domain;

import ar.edu.itba.ss.algorithm.ParticlesCreator;
import ar.edu.itba.ss.algorithm.cim.CellIndexMethod;

import java.util.ArrayList;
import java.util.List;

public class Silo{

    private static final int MAX_CREATION_TRIES = 1000;

    private double width;
    private double height;
    private double exitOpeningSize;
    private double falldownLimit;
    private Area insideSiloArea;
    private double topPadding;
    private double bottomPadding;
    private List<Particle> particles;
    private double kN = 10e5;//N/m.
    private double gamma = 10e2;

    //Cota superior para M: L/(2 * rMax)/4 > M
    private static final int M =18;

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

    public void fillSilo(int particlesToAdd) {
        ParticlesCreator filler = new ParticlesCreator(particlesToAdd, insideSiloArea);
        for(int i = 0; i < particlesToAdd; i++){
            if(!addOne(filler)){
                break;
            }
        }
    }

    private CellIndexMethod instantiateCIM(List<Particle> particles){
        return new CellIndexMethod(M, insideSiloArea.getHeight(),
                ParticlesCreator.MAX_RADIUS*2., particles, false);
    }

    private boolean addOne(ParticlesCreator filler){
        for(int intent = 1 ; intent <= MAX_CREATION_TRIES; intent++){

            List<Particle> pAux = new ArrayList<>(this.particles);
            Particle particle = filler.create();
            pAux.add(particle);

            CellIndexMethod cim = instantiateCIM(particles);
            cim.calculate();

            if(isThereRoomForParticle(particle)){
                addParticle(particle);
                return true;
            }
        }
        return false;
    }

    private boolean isThereRoomForParticle(Particle particle) {
        return particle.getNeighbours().stream()
                .noneMatch( p ->  particle.overlap(p) > 0);
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

    public double getWidth() {
        return width;
    }

    public void setkN(double kN) {
        this.kN = kN;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public void evolve(double dt) {
        CellIndexMethod cim = instantiateCIM(particles);
        cim.calculate();
        particles.forEach( p -> p.updatePosition(dt));
        particles.forEach(Particle::updateForce);
        particles.forEach( p -> p.calculateForce(kN, gamma));
        particles.forEach( p -> p.updateVelocity(dt));
    }

}
