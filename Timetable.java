import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Timetable {
    private List<Course> courses = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Curriculum> curricula = new ArrayList<>();
    private ClassesInSamePeriod[] timetable;

    private int numberOfRooms;
    private int numberOfPeriodsPerWeek;
    private int numberOfPeriodsPerDay;
    private int numberOfDays;

    private int fitness;
    private int numberOfViolation;

    public Timetable( List<Room> rooms, List<Course> courses, List<Curriculum> curricula, int numberOfPeriodsPerWeek,int numberOfPeriodsPerDay){
        this.rooms = new ArrayList<>(rooms);
        this.courses = new ArrayList<>(courses);
        this.curricula = new ArrayList<>(curricula);
        numberOfRooms = rooms.size();
        this.numberOfPeriodsPerWeek = numberOfPeriodsPerWeek;
        this.numberOfPeriodsPerDay = numberOfPeriodsPerDay;
        numberOfDays=numberOfPeriodsPerWeek/numberOfPeriodsPerDay;

        timetable = new ClassesInSamePeriod[numberOfPeriodsPerWeek];
        for(int i = 0; i< numberOfPeriodsPerWeek; i++) {
            ClassesInSamePeriod classesAtOnce = new ClassesInSamePeriod(rooms, curricula);
            timetable[i] = classesAtOnce;
        }
    }

    private boolean tryToAddClass(Course course){
        for(int i = 0; i < 20; i++){
            Random rand = new Random();
            int index = rand.nextInt(numberOfPeriodsPerWeek);
            if(timetable[index].courseCanBeAdded(course)) {
                timetable[index].addClass(course);
                return true;
            }
        }
        return false;
    }

    public void schedule(){

        for(Course i : courses){
            for(int j = 0; j < i.getNumberOfClasses(); j++) {
                if(!tryToAddClass(i)) {
                    boolean success = false;
                    List<Integer> availablePeriods = new ArrayList<>();
                    for(int k = 0;  k < numberOfPeriodsPerWeek; k++){
                        availablePeriods.add(new Integer(k));
                    }
                    while(!success) {
                        Collections.shuffle(availablePeriods);
                        Integer index = availablePeriods.get(0);
                        if(timetable[index].hasAvailableRoom()) {
                            timetable[index].addClass(i);
                            success=true;
                        }else{
                            availablePeriods.remove(index);
                        }
                    }
                }

            }
        }
        scheduleRooms();
        calculateFitness();
        calculateViolations();
    }
    private void scheduleRooms(){
        for(int i = 0; i < timetable.length; i++){
            timetable[i].scheduleRooms();
        }
    }

    private void calculateViolations() {
        for(int i = 0; i < numberOfPeriodsPerWeek; i++){
            numberOfViolation += timetable[i].countViolations();
        }
    }
    public int getViolations(){
        return numberOfViolation;
    }

    private void calculateFitness(){
        calculateRoomCapacityFaults();
        //int a = fitness;
        //System.out.println("roomcapacity: "+a);
        calculateMinimumWorkingDaysFaults();
        //int b = fitness;
        //System.out.println("minworkingday: "+(b-a));

        calculateCurriculumCompactnessFaults();
        //int c = fitness;
        //System.out.println("lyukas: "+(c-b));

        calculateRoomStabilityFaults();
        //System.out.println("roomstability: "+(fitness-c));
    }

    private void calculateRoomStabilityFaults() {
        //TODO
    }

    private void calculateCurriculumCompactnessFaults() {
        for(Curriculum j : curricula) {
            for (int i = 0; i < numberOfDays; i++) {
                fitness += dailyNotAdjacentFault(i,j);
            }
        }
    }
    private int dailyNotAdjacentFault(int day, Curriculum curriculum){
        int result ;
        List<Boolean> whereClassesOfCurriculumAre = new ArrayList<>();
        for(int i = day*numberOfPeriodsPerDay; i<(day+1)*numberOfPeriodsPerDay; i++){
            if(timetable[i].IsClassOfCurriculumInThisPeriod(curriculum)){
                whereClassesOfCurriculumAre.add(true);
            }else{
                whereClassesOfCurriculumAre.add(false);
            }
        }
        result = countAdjacent(whereClassesOfCurriculumAre);
        return result;
    }
    private int countAdjacent(List<Boolean> b){
        int result = 0;
        b.add(0,false);
        b.add(false);
        for(int i = 1; i<b.size()-1; i++){
            if(!b.get(i - 1) && b.get(i) && !b.get(i+1)) {
                result++;
            }
        }
        return result;
    }

    private void calculateMinimumWorkingDaysFaults() {
        for(Course i : courses){
            int howManyDiffDays = howManyDiffDaysCourseIs(i);
            if(howManyDiffDays < i.getMinimumDays()){
                fitness += (i.getMinimumDays()-howManyDiffDays)*5;
            }
        }
    }

    private int howManyDiffDaysCourseIs(Course course) {
        int sum = 0;
        for(int i = 0; i < numberOfDays; i++){
            if(courseOnThisDay(i,course))
                sum++;
        }
        return sum;
    }
    private boolean courseOnThisDay(int dayNumber, Course course){
        boolean result = false;
        for(int i = dayNumber*numberOfPeriodsPerDay; i < (dayNumber+1)*numberOfPeriodsPerDay-1; i++){
            if(timetable[i].IsCourseInThisPeriod(course)) result = true;
        }
        return result;
    }

    private void calculateRoomCapacityFaults() {
        for (ClassesInSamePeriod i : timetable) {
            fitness += i.countRoomCapacityFaults();
        }
    }

    public int getFitness(){
        return fitness;
    }


    public void printTimetable(){
        for(int i = 0; i < numberOfPeriodsPerWeek; i++){
            if(i>9){System.out.print(i+".  | ");
            }else{
                System.out.print(i+".   | ");
            }
            timetable[i].printClasses();
            if((i+1)%6==0) System.out.println("-----------------------------------------------------------------------------------------------------");
        }
    }
}
