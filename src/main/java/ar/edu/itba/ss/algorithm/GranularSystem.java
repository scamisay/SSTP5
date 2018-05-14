package ar.edu.itba.ss.algorithm;

import ar.edu.itba.ss.domain.Silo;
import ar.edu.itba.ss.helper.Printer;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GranularSystem {

    private double dt;
    private long dt2;
    private double simulationTime;
    private int particleNumbers;

    private Silo silo;
    private Printer printer;

    private boolean updateStatisticalValues;
    private List<Vector2D> kineticEnergy = new ArrayList<>();
    private List<Vector2D> caudal = new ArrayList<>();

    public GranularSystem(double dt, long dt2, double simulationTime, Silo silo, int particleNumbers) {
        this.dt = dt;
        this.dt2 = dt2;
        this.simulationTime = simulationTime;
        this.silo = silo;
        this.particleNumbers = particleNumbers;
    }

    public void updateStatisticalValues(){
        updateStatisticalValues=true;
    }

    public void setPrintable(){
        printer = new Printer(silo);
    }

    public void simulate(){
        silo.fillSilo(particleNumbers);

        double t = 0;
        long i = 0;
        for (; t < simulationTime ; t+=dt, i++ ){
            if (i % dt2 == 0 ) {
                if(printer != null){
                    printer.printState(t, silo.getParticles());
                    System.out.println(t);
                }
                if(updateStatisticalValues){
                    updateKineticEnergy(t);
                    updateCaudal(t);
                    //caudalBeverloo(t)
                }
            }
            silo.evolve(dt);

        }
    }

    private void updateCaudal(double t) {
        long count = silo.numberOfparticlesHaveEscaped();
        if(count>0){
            caudal.add(new Vector2D(t, count));
        }
    }

    private void updateKineticEnergy(double t) {
        kineticEnergy.add(new Vector2D(t, silo.getKineticEnergy()));
    }


    public List<Vector2D> getKineticEnergy() {
        return kineticEnergy;
    }

    public List<Vector2D> getCaudal() {
        return caudal;
    }
}
