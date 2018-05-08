package ar.edu.itba.ss.domain;

import ar.edu.itba.ss.algorithm.cim.Cell;
import ar.edu.itba.ss.algorithm.cim.Range;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Particle {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D force;
    private double mass;
    private double radius;
    private Cell cell;
    private List<Particle> neighbours = new ArrayList<>();

    public Particle(Vector2D position, Vector2D velocity, Vector2D force, double mass, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.force = force;
        this.mass = mass;
        this.radius = radius;
    }

    /***
     * getters and setters start
     */
    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public double getRadius() {
        return radius;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getForce() {
        return force;
    }

    public double getMass() {
        return mass;
    }

    /***
     * getters and setters end
     */



    public boolean isCloseEnough(Particle particle, double maxDistance) {
        return distanceBorderToBorder(particle) <= maxDistance;
    }

    public Double distanceBorderToBorder(Particle particle){
        return distanceCenterToCenter(particle) - (getRadius() + particle.getRadius());
    }

    private double distanceCenterToCenter(Particle particle) {
        double difx = getPosition().getX() - particle.getPosition().getX();
        double dify = getPosition().getY() - particle.getPosition().getY();
        return Math.sqrt(Math.pow(difx, 2) + Math.pow(dify, 2));
    }

    public boolean isNeighbourCloseEnough(Particle particle, double maxDistance, boolean periodicContourCondition) {
        if (periodicContourCondition) {
            List<Cell> calculated = getCell().calculateNeighbourCells();
            if (!calculated.contains(particle.getCell())) {
                //debo dar la vuelta

                //defino las direcciones en cada una de las componentes
                double newX = getNewX(calculated,particle);
                double newY = getNewY(calculated,particle);



                Particle newParticle = new Particle(
                        new Vector2D(newX, newY), particle.getVelocity(),
                        particle.getForce(), particle.getMass(), particle.getRadius());
                return distanceCenterToCenter(newParticle) <= maxDistance;
            }
        }
        return isCloseEnough(particle, maxDistance);
    }

    private double getNewX(List<Cell> calculated, Particle particle) {
        if (!hasRangeX(calculated, particle.getCell().getRangeX())) {
            if (getPosition().getX() - particle.getPosition().getX() > 0) {
                return particle.getPosition().getX() + getCell().getRangeX().getHighest();
            } else {
                return particle.getPosition().getX() - getCell().getRangeX().getHighest();
            }
        }
        return particle.getPosition().getX();
    }
    private double getNewY(List<Cell> calculated, Particle particle) {
        if (!hasRangeY(calculated, particle.getCell().getRangeY())) {
            if (getPosition().getY() - particle.getPosition().getY() > 0) {
                return particle.getPosition().getY() + getCell().getRangeY().getHighest();
            } else {
                return particle.getPosition().getY() - getCell().getRangeY().getHighest();
            }
        }
        return particle.getPosition().getY();

    }

    private boolean hasRangeX(List<Cell> calculated, Range rangex) {
        for (Cell c : calculated) {
            if (c != null && c.getRangeX().equals(rangex)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRangeY(List<Cell> calculated, Range rangey) {
        for (Cell c : calculated) {
            try {
                if (c != null && c.getRangeY().equals(rangey)) {
                    return true;
                }
            } catch (Exception e) {
                System.out.print(1);
            }

        }
        return false;
    }

    public List<Particle> getOtherParticlesInCell() {
        return getCell().getParticles().stream().filter(p -> !p.equals(this)).collect(Collectors.toList());
    }

    public void addNeighbour(Particle particle) {
        if (particle == null) {
            throw new RuntimeException("La particula no puede ser nula");
        }
        neighbours.add(particle);
    }
}
