package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Area;
import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.util.FastMath;

import static ar.edu.itba.ss.helper.Numeric.randomBetween;

public class ParticlesCreator {

    public static final double MASS = 0.01;//kg
    public static final double MIN_RADIUS = 0.02/2;//m
    public static final double MAX_RADIUS = 0.03/2;//m

    private Area area;

    public ParticlesCreator(Area area) {
        this.area = area;
    }

    //todo: definir generador aleatorios con seed
    public Particle create() {
        double radius = createRadius();
        Vector2D position = createPosition(radius);
        return new Particle(position, MASS, radius);
    }

    private double createRadius() {
        return randomBetween(MIN_RADIUS, MAX_RADIUS);
    }

    private Vector2D createPosition(double radius) {
        return createRandomPosition(radius, area.getMinY());
    }

    public Vector2D createRandomPosition(double radius, double minHeight){
        double x = randomBetween(area.getMinX()+radius, area.getWidth()-radius);
        double y = randomBetween(minHeight+radius, area.getHeight()-radius);
        return new Vector2D(x,y);
    }

}
