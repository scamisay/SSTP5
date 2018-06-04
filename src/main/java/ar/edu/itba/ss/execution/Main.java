package ar.edu.itba.ss.execution;

import ar.edu.itba.ss.algorithm.GranularSystem;
import ar.edu.itba.ss.domain.Silo;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        double width = .25;

        double d1 = width/5;
        double d2 = width/4;
        double d3 = width/3;
        double d4 = width/2;

        double exitOpeningSize = d4;
        Silo silo = new Silo(width, 2, exitOpeningSize, .25,0.25);
        double dt = 1e-5;
        long dt2 = (long)2e2;
        int particleNumbers = 400;
        GranularSystem system = new GranularSystem(dt, dt2, 1.4, silo, particleNumbers);
        system.setPrintable();
        system.updateStatisticalValues(Arrays.asList( .9));
        system.simulate();
        /*double average = system.getAverageCaudal();
        double sd = system.getStandardDeviation();
        double beverlooCaudal = system.getBeverlooCaudal(1);
        System.out.println(String.format("exit=%.6f\n\naverage = %.6f\nsd=%.6f\nbeverloo=%.6f",
                exitOpeningSize, average, sd,beverlooCaudal
        ));
        String dtValues = system.getKineticEnergy().stream().map(v->v.getX()+"").collect(Collectors.joining(","));;
        String qValues = system.getCaudal().stream().map(v->v.getY()+"").collect(Collectors.joining(","));*/
        system.calculateNp();
        System.out.println(1);
    }
}
