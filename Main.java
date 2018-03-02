import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {



    public static void main(String [ ] args) throws FileNotFoundException {
        String pathString = new String("inputs/dataset_1.txt");
        Reader reader = new Reader();
        reader.read(args,pathString);

        List<Course> courses = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<Curriculum> curricula = new ArrayList<>();
        courses = reader.getCourses();
        rooms = reader.getRooms();
        curricula = reader.getCurricula();
        int numberOfDays = reader.getNumberOfDays();
        int numberOfPeriodsPerDay = reader.getNumberOfPeriodsPerDay();

        //orarendkiezet demo
        /*Timetable t = new Timetable(rooms,courses,curricula,numberOfPeriodsPerDay*numberOfDays,numberOfPeriodsPerDay);
        t.schedule();
        t.printTimetable();*/

        //program
        Algorithm GA = new Algorithm(numberOfDays,numberOfPeriodsPerDay,rooms,courses,curricula);
        GA.run();


    }

}
