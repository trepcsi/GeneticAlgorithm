import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithm {

    private final int NUMBER_OF_GENERATIONS = 10;
    private final int POPULATION_SIZE = 20;

    private List<Timetable> generation;
    private List<Timetable> parents;


    private int daysPerWeek;
    private int periodsPerDay;

    private int numberOfRooms;
    private int periodsPerWeek;

    private List<Course> courses = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Curriculum> curricula = new ArrayList<>();

    public Algorithm(int daysPerWeek, int periodsPerDay, List<Room> rooms, List<Course> courses, List<Curriculum> curricula){
        this.rooms = new ArrayList<>(rooms);
        this.courses = new ArrayList<>(courses);
        this.curricula = new ArrayList<>(curricula);
        this.daysPerWeek = daysPerWeek;
        this.periodsPerDay = periodsPerDay;

        numberOfRooms = rooms.size();
        periodsPerWeek = daysPerWeek * periodsPerDay;
        generation = new ArrayList<>();
        parents = new ArrayList<>();

    }

    public void run(){
        generateFirstGeneration();
        print_AVG_BEST(0);

        //TODO
        /*for(int i = 1; i < NUMBER_OF_GENERATIONS; i++){
            selectParents();
            crossOver();
            print_AVG_BEST(i);
            generation.clear();
            generation.addAll(parents.subList(0,parents.size()/2));
        }*/
    }


    private void print_AVG_BEST(int whichGeneration) {
        System.out.print(whichGeneration+".  ");
        int best = 10000;
        for(Timetable i : generation){
            if(i.getFitness() < best) best = i.getFitness();
            System.out.print(i.getFitness()+",  ");
        }System.out.println();
        System.out.println("BEST: "+best);System.out.println();

    }

    private void crossOver() {
        //TODO
    }

    private void selectParents() {
        //TODO
    }

    private void generateFirstGeneration() {
        for(int i = 0; i < POPULATION_SIZE; i++){
            Timetable t = new Timetable(rooms,courses,curricula,periodsPerWeek,periodsPerDay);
            t.schedule();
            generation.add(t);
        }
    }


}
