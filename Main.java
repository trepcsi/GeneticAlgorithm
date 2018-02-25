import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {



    public static void main(String [ ] args) throws FileNotFoundException {
        Reader reader = new Reader();
        reader.read(args);

        List<Course> courses = new ArrayList<>();
        List<Room> rooms = new ArrayList<>();
        List<Curriculum> curricula = new ArrayList<>();

        //test
        /*courses = reader.getCourses();
        rooms = reader.getRooms();
        curricula = reader.getCurricula();
        for(Course i: courses) i.printCourse();
        for(Room i : rooms) i.printRoom();*/
    }

}
