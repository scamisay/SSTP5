package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Silo;
import ar.edu.itba.ss.helper.Printer;
import org.apache.commons.math3.util.FastMath;

public class GranularSystem {

    private double dt;
    private long dt2;
    private double simulationTime;

    private Silo silo;
    private ParticlesCreator particlesCreator;
    private Printer printer;

    public GranularSystem(double dt, long dt2, double simulationTime, Silo silo) {
        this.dt = dt;
        this.dt2 = dt2;
        this.simulationTime = simulationTime;
        this.silo = silo;

        int particlesPerCall = (int) FastMath.ceil(5*dt);
        particlesCreator = new ParticlesCreator(particlesPerCall);
    }

    public void setPrintable(){
        printer = new Printer();
    }

    public void simulate(){
        double t =0;
        long i = 0;
        for (; t < simulationTime ; t+=dt, i++ ){
            if (printer != null && (i % dt2 == 0)) {
                printer.printState();
            }


        }
    }
}
