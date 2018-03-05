import com.sun.xml.internal.bind.v2.TODO;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Algorithm {

    private Timetable bestTimetable;

    private final int NUMBER_OF_GENERATIONS = 10;
    private final int POPULATION_SIZE = 800;

    private final int TOURNAMENT_SIZE = POPULATION_SIZE/4;
    private final int TOURNAMENT_WIN_SIZE = POPULATION_SIZE/10;

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

        bestTimetable = new Timetable(rooms,courses,curricula,periodsPerWeek,periodsPerDay);
        bestTimetable.schedule();

    }

    public void run(){
        generateFirstGeneration();
        int i = 0;
        print_AVG_BEST(i++);
        for(;i<NUMBER_OF_GENERATIONS;i++ ){
            selectParents();
            crossOver();
            print_AVG_BEST(i);
        }
    }


    private void print_AVG_BEST(int whichGeneration) {
        int best = 10000;
        int sum = 0;
        for(Timetable i : generation){
            sum += i.getFitness();
            if(i.getFitness() < best){
                best = i.getFitness();
                if(bestTimetable.getFitness()>best){
                    bestTimetable = i;
                }
            }
        }
        System.out.println();
        System.out.println(whichGeneration+". :"+"AVG: "+sum/POPULATION_SIZE+"| BEST: "+best);System.out.println();

    }

    private void crossOver() {
        for(int i = 0; i<parents.size()/2; i++){
            parents.get(i).cross(parents.get(parents.size()/2+i));
        }
        generation = parents.subList(0,POPULATION_SIZE);
    }

    private void selectParents() {
        List<Timetable> newParents= new ArrayList<>();
        //parents.clear();  ????????
        while(newParents.size()!=POPULATION_SIZE*2){
            newParents.addAll(tournamentSelection());
        }
        parents = newParents;
    }
    private List<Timetable> tournamentSelection(){
        List<Timetable> competitors = new ArrayList<>();
        Collections.shuffle(generation);
        competitors.addAll(generation.subList(0,TOURNAMENT_SIZE));
        competitors.sort(Comparator.comparingInt(Timetable::getFitness));
        return competitors.subList(0,TOURNAMENT_WIN_SIZE);
    }

    private void generateFirstGeneration() {
        for(int i = 0; i < POPULATION_SIZE; i++){
            Timetable t = new Timetable(rooms,courses,curricula,periodsPerWeek,periodsPerDay);
            t.schedule();
            generation.add(t);
        }
    }


    public void printBest() {
        bestTimetable.printTimetable();
    }
}
