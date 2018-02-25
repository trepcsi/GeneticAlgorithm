import java.util.ArrayList;
import java.util.List;

public class ClassesInSamePeriod {
    List<Course> classes;
    private int numberOfClasses;

    private List<Curriculum> curricula;
    private List<Room> rooms;
    private int numberOfRooms;

    public ClassesInSamePeriod(List<Room> rooms, List<Curriculum> curricula){
        classes = new ArrayList<>();
        this.rooms = new ArrayList<>(rooms);
        numberOfRooms = rooms.size();
        this.curricula = new ArrayList<>(curricula);
    }

    public boolean IsCourseInThisPeriod(Course course){
        for(Course i : classes){
            if(i.getName().equals(course.getName())) return true;
        }
        return false;
    }

    public void addClass(Course course){
        if(classes.size()==rooms.size()){  return;} //nincs tobb terem, mar itt leellenorizve
        classes.add(course);
        numberOfClasses++;
    }

    public boolean hasAvailableRoom(){
        return numberOfClasses < numberOfRooms;
    }

    public boolean courseCanBeAdded(Course course){
        if(classes.size()==0) return true; //persze, hogy jöhet még semmi sincs benne
        if(classes.size()==rooms.size()) return false;
        //ugyananannak a tankornek nem lehet egyszerre 2 oraja
        for(Course i : classes){
            for(Curriculum j : curricula){
                if(j.isInCourses(i) && j.isInCourses(course)) return false;
            }
        }
        //egy tanaranak nem lehet egyszerre 2 oraja
        for(Course i : classes){
            if(i.getTeacher().equals(course.getTeacher())) return false;
        }
        return true;
    }

    public void printClasses(){
        for(Course i : classes){
            System.out.print(i.getName()+" | ");
        }System.out.println();
    }

    public int countViolations() {
        int sum = 0;
        for(Course i : classes){
            for(Course j : classes){
                if(!i.equals(j)){
                    for(Curriculum c : curricula){
                        if(c.isInCourses(i) && c.isInCourses(j))
                            sum++;
                    }
                }
            }
        }
        return sum;
    }
}
