import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    int daysPerWeek;
    int periodsPerDay;

    int numberOfRooms;
    int periodsPerWeek;

    List<Course> courses = new ArrayList<>();
    List<Room> rooms = new ArrayList<>();
    List<Curriculum> curricula = new ArrayList<>();

    public Algorithm(int daysPerWeek, int periodsPerDay, List<Room> rooms, List<Course> courses, List<Curriculum> curricula){
        this.rooms = new ArrayList<>(rooms);
        this.courses = new ArrayList<>(courses);
        this.curricula = new ArrayList<>(curricula);
        this.daysPerWeek = daysPerWeek;
        this.periodsPerDay = periodsPerDay;

        numberOfRooms = rooms.size();
        periodsPerWeek = daysPerWeek * periodsPerDay;

    }

    

}
