package ar.edu.itba.ss.domain;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;

public class Particle {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D force;
    private double mass;
    private double radius;
    private List<Particle> neighbours;
}
