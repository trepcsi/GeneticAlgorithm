import java.util.ArrayList;
import java.util.List;

public class Curriculum {

    private List<Course> courses = new ArrayList();

    public Curriculum(List<Course> courses){
        this.courses = courses;
    }

    public void addCourse(Course c){
        courses.add(c);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void printCourses(){
        for(Course i : courses){
            i.printCourse();

        }System.out.println();
    }
}
