package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Area;
import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;

public class ParticlesCreator {

    private static final double MASS = 0.01;//kg
    private static final double MIN_RADIUS = 0.02;//m
    public static final double MAX_RADIUS = 0.03;//m

    private int maxParticlesToCreate;
    private Area area;

    public ParticlesCreator(int maxParticlesToCreate, Area area) {
        this.maxParticlesToCreate = maxParticlesToCreate;
        this.area = area;
    }

    //todo: definir generador aleatorios con seed
    public Particle create() {
        double radius = createRadius();
        Vector2D position = createPosition(radius);
        return new Particle(position, MASS, radius);
    }

    private double randomBetween(double a, double b){
        if(b < a){
            throw new IllegalArgumentException("a<b");
        }
        return FastMath.random()*(b-a) + a;

    }

    private double createRadius() {
        return randomBetween(MIN_RADIUS, MAX_RADIUS);
    }

    private Vector2D createPosition(double radius) {
        double x = randomBetween(area.getMinX()+radius, area.getWidth()-radius);
        double y = randomBetween(area.getMinY()+radius, area.getHeight()-radius);
        return new Vector2D(x,y);
    }

}
