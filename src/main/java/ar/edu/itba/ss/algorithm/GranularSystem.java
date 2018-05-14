package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Silo;
import ar.edu.itba.ss.helper.Printer;

public class GranularSystem {

    private double dt;
    private long dt2;
    private double simulationTime;
    private int particleNumbers;

    private Silo silo;
    private Printer printer;

    public GranularSystem(double dt, long dt2, double simulationTime, Silo silo, int particleNumbers) {
        this.dt = dt;
        this.dt2 = dt2;
        this.simulationTime = simulationTime;
        this.silo = silo;
        this.particleNumbers = particleNumbers;
    }

    public void setPrintable(){
        printer = new Printer(silo);
    }

    public void simulate(){
        silo.fillSilo(particleNumbers);

        double t = 0;
        long i = 0;
        long previous = System.currentTimeMillis();
        for (; t < simulationTime ; t+=dt, i++ ){
            if (printer != null && (i % dt2 == 0)) {
                long current = System.currentTimeMillis();
                printer.printState(t, silo.getParticles());
                System.out.println(t + " - " + (current-previous));
                previous = current;
            }
            silo.evolve(dt);
        }
    }




}
