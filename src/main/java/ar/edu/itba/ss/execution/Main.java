package ar.edu.itba.ss.execution;

import ar.edu.itba.ss.algorithm.GranularSystem;
import ar.edu.itba.ss.domain.Silo;

public class Main {

    public static void main(String[] args) {
        Silo silo = new Silo(1, 3, .5, 1,0.5);
        double dt = 0.005;
        long dt2 = 500;
        int particleNumbers = 50;
        GranularSystem system = new GranularSystem(dt, dt2, 200, silo, particleNumbers);
        system.setPrintable();
        system.simulate();
        System.out.println(1);
    }
}
