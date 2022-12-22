package main;
import main.java.com.models.grafico;

public class Main {
    public static void main(String[] args) {

        grafico g = new grafico("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

        System.out.println("Output #1: " + g.displayDistance("ABC"));
        System.out.println("Output #2: " + g.displayDistance("AD"));
        System.out.println("Output #3: " + g.displayDistance("ADC"));
        System.out.println("Output #4: " + g.displayDistance("AEBCD"));
        System.out.println("Output #5: " + g.displayDistance("AED"));
        System.out.println("Output #6: " + g.calculateTripsCount("C", "C", t -> t <= 3, 3));
        System.out.println("Output #7: " + g.calculateTripsCount("A", "C", t -> t == 4, 4));
        System.out.println("Output #8: " + g.calculateShortestPath("A", "C"));
        System.out.println("Output #9: " + g.calculateShortestPath("B", "B"));
        System.out.println("Output #10: " + g.calculateRoutesCount("C", "C", 30));
    }

}