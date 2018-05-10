package ar.edu.itba.ss.execution;

import ar.edu.itba.ss.algorithm.GranularSystem;
import ar.edu.itba.ss.domain.Silo;

public class Main {

    public static void main(String[] args) {
        Silo silo = new Silo(1, 3, .5, 1,0.5);
        double dt = 0.01;
        long dt2 = 10;
        int particleNumbers = 700;
        GranularSystem system = new GranularSystem(dt, dt2, 1, silo, particleNumbers);
        system.setPrintable();
        system.simulate();
        System.out.println(1);
    }
}
