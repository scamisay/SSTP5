package ar.edu.itba.ss.execution;

import ar.edu.itba.ss.algorithm.GranularSystem;
import ar.edu.itba.ss.domain.Silo;

public class Main {

    public static void main(String[] args) {
        double width = .5;
        double exitOpeningSize = .15;
        Silo silo = new Silo(width, 2, exitOpeningSize, .25,0.25);
        double dt = 1e-5;
        long dt2 = (long)1e2;
        int particleNumbers = 400;
        GranularSystem system = new GranularSystem(dt, dt2, .5, silo, particleNumbers);
        system.setPrintable();
        system.updateStatisticalValues();
        system.simulate();
        System.out.println(1);
    }
}
