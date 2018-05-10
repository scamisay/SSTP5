package ar.edu.itba.ss.helper;

import ar.edu.itba.ss.domain.Particle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class Printer {

    private double L;

    private static final String FILE_NAME_OVITO = "ovito.xyz";

    public Printer(double l) {
        try{
            File file = new File(FILE_NAME_OVITO);

            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        }catch (Exception e){
            System.out.println("problemas creando el archivo "+FILE_NAME_OVITO);
        }
        L = l;
    }

    public void printState(double time, List<Particle> particles){
        printForOvito(time, particles);
    }

    private void printForOvito(double time, List<Particle> particles) {
        printStringToFile(FILE_NAME_OVITO, printParticles(time, particles));
    }

    private String printParticles(double time, List<Particle> particles) {
        return (particles.size()+2)+"\n"+
                time + "\n" +
                "0 0 0 0 0\n"+
                L +" "+L+" 0 0 0\n"+
                particles.stream()
                        .map(Particle::toString)
                        .collect(Collectors.joining("\n")) +"\n";
    }

    private void printStringToFile(String filename, String content){
        try {
            Files.write(Paths.get(filename), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            try {
                new File(filename).createNewFile();
                Files.write(Paths.get(filename), content.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e1) {
                System.out.println("No se pudo crear el archivo "+filename);
            }
        }
    }

}
