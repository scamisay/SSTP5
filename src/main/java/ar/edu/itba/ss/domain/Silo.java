package ar.edu.itba.ss.domain;

import ar.edu.itba.ss.algorithm.ParticlesCreator;
import ar.edu.itba.ss.algorithm.cim.CellIndexMethod;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.algorithm.ParticlesCreator.MASS;
import static ar.edu.itba.ss.algorithm.ParticlesCreator.MAX_RADIUS;
import static ar.edu.itba.ss.domain.Particle.G;

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
        //fillSiloForTest();
        ParticlesCreator filler = new ParticlesCreator(insideSiloArea);
        for(int i = 0; i < particlesToAdd; i++){
            if(!addOne(filler)){
                break;
            }
        }
    }

    private void fillSiloForTest(){
        double x1 = 0;
        double x2 = insideSiloArea.getWidth();
        double y = insideSiloArea.getHeight();

        Particle p1 = new Particle(new Vector2D(x1,y), MASS, MAX_RADIUS);
        p1.setForce(new Vector2D(MASS*G,0));
        particles.add(p1);

        Particle p2 = new Particle(new Vector2D(x2,y), MASS, MAX_RADIUS);
        p2.setForce(new Vector2D(-1*MASS*G,0));

        particles.add(p2);

        CellIndexMethod cim = instantiateCIM(particles);
        cim.calculate();
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

            CellIndexMethod cim = instantiateCIM(pAux);
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
