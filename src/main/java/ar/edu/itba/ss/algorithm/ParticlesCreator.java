package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Area;
import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;

public class ParticlesCreator {

    private static final double MASS = 0.01;//kg
    private static final double MIN_RADIUS = 0.02;//m
    public static final double MAX_RADIUS = 0.03;//m

    private Integer maxParticlesToCreate;
    private Area area;

    public ParticlesCreator(Integer maxParticlesToCreate, Area area) {
        if(maxParticlesToCreate != null && maxParticlesToCreate > 0){
            this.maxParticlesToCreate = maxParticlesToCreate;
        }
        this.area = area;
    }

    //todo: definir generador aleatorios con seed
    public Particle create() {
        Vector2D position = createPosition();
        double radius = createRadius();
        return new Particle(position, MASS, radius);
    }

    private double randomBetween(double a, double b){
        if(b < a){
            throw new RuntimeException("a<b");
        }
        return FastMath.random()*(b-a) + a;
    }

    private double createRadius() {
        return randomBetween(MIN_RADIUS, MAX_RADIUS);
    }

    private Vector2D createPosition() {
        double x = randomBetween(area.getMinX(), area.getWidth());
        double y = randomBetween(area.getMinY(), area.getHeight());
        return new Vector2D(x,y);
    }

}
