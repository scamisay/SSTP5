package ar.edu.itba.ss.execution;

import ar.edu.itba.ss.algorithm.GranularSystem;
import ar.edu.itba.ss.domain.Silo;

public class Main {

    public static void main(String[] args) {
        Silo silo = new Silo(.5, 2, 0, 1,0.5);
        double dt = 1e-7;
        long dt2 = (long)1e5;
        int particleNumbers = 100;
        GranularSystem system = new GranularSystem(dt, dt2, 5, silo, particleNumbers);
        system.setPrintable();
        system.simulate();
        System.out.println(1);
    }
}
