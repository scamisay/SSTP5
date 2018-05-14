package ar.edu.itba.ss.domain;

import ar.edu.itba.ss.algorithm.ParticlesCreator;
import ar.edu.itba.ss.algorithm.cim.CellIndexMethod;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static ar.edu.itba.ss.algorithm.ParticlesCreator.MASS;
import static ar.edu.itba.ss.algorithm.ParticlesCreator.MAX_RADIUS;

public class Silo{

    private static final int MAX_CREATION_TRIES = 1000;

    private final double width;
    private final double height;
    private final double exitOpeningSize;
    private double falldownLimit;
    private Area insideSiloArea;
    private final double topPadding;
    private final double bottomPadding;
    private List<Particle> particles;
    private double kN = 1e3;//N/m.
    private double gamma = 1e2;

    //Cota superior para M: L/(2 * rMax)/4 > M
    private static final int M =4;

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
        //fillSiloForTest2Particles();
        //fillSiloForTest1Particle();
        ParticlesCreator filler = new ParticlesCreator(insideSiloArea);
        for(int i = 0; i < particlesToAdd; i++){
            if(!addOne(filler)){
                break;
            }
        }
    }

    public double getExitStart(){
        return (width / 2) - (exitOpeningSize / 2);
    }

    public double getExitEnd(){
        return getExitStart() + exitOpeningSize;
    }

    public boolean isInExitArea(double x){
        double exitStart = getExitStart();
        double exitEnd = getExitEnd();
        return (exitStart <= x) && (x <= exitEnd);
    }

    private void fillSiloForTest2Particles(){
        double x1 = MAX_RADIUS*2;
        double x2 = insideSiloArea.getWidth()-MAX_RADIUS*2;
        double y = insideSiloArea.getHeight()*.8;

        Particle p1 = new Particle(new Vector2D(x1,y), MASS, MAX_RADIUS);
        //p1.setForce(new Vector2D(0,0));
        p1.setVelocity(new Vector2D(2,0));

        Particle p2 = new Particle(new Vector2D(x2,y), MASS, MAX_RADIUS);
        //p2.setForce(new Vector2D(0,0));
        p2.setVelocity(new Vector2D(-2,0));

        List<Particle> pAux = new ArrayList<>();
        pAux.add(p1);
        pAux.add(p2);

        CellIndexMethod cim = instantiateCIM(pAux);
        cim.calculate();

        particles.addAll(pAux);
    }

    private void fillSiloForTest1Particle(){
        double x1 = 0.5;
        double y = insideSiloArea.getHeight()*.8;

        Particle p1 = new Particle(new Vector2D(x1,y), MASS, MAX_RADIUS);
        p1.setVelocity(new Vector2D(0,0));


        List<Particle> pAux = new ArrayList<>();
        pAux.add(p1);

        CellIndexMethod cim = instantiateCIM(pAux);
        cim.calculate();

        particles.addAll(pAux);
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

    public double getBottomPadding() {
        return bottomPadding;
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
        particles.forEach( p -> p.updatePosition(dt, this));
        particles.forEach(Particle::updateForce);
        particles.forEach( p -> p.calculateForce(kN, gamma, this, dt));
        particles.forEach( p -> p.updateVelocity(dt));
    }

    public boolean containsParticle(Particle particle) {
        return insideSiloArea.containsParticle(particle);
    }

    public double getLeftWall() {
        return insideSiloArea.getMinX();
    }

    public double getRightWall() {
        return insideSiloArea.getWidth();
    }

    public boolean wentOutside(Particle particle) {
        return particle.getPosition().getY() <= 0;
    }

    //todo: ponerlo dentro del silo sin superposiciones
    public Vector2D chooseAvailablePositionInSilo(double radius) {
        ParticlesCreator creator = new ParticlesCreator(insideSiloArea);
        Vector2D relocatedPosition = creator.createRandomPosition(radius,getBottomPadding()+  getHeight()*.8);
        return relocatedPosition;
    }
}
