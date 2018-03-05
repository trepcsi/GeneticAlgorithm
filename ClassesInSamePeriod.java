import java.util.ArrayList;
import java.util.HashMap;
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
    public boolean IsClassOfCurriculumInThisPeriod(Curriculum curriculum){
        for(Course i : curriculum.getCourses()){
            if(IsCourseInThisPeriod(i)) return true;
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
        int j = 0;
        for(Course i : classes){
            if(rooms.get(j).getCapacity()-i.getNumberOfStudents()<-9 ) {
                System.out.print(i.getName() + ", "+rooms.get(j).getName() +"(" + (rooms.get(j++).getCapacity() - i.getNumberOfStudents()) +")"+ " | ");
            }else if(rooms.get(j).getCapacity()-i.getNumberOfStudents()<0 ) {
                System.out.print(i.getName() + ", "+rooms.get(j).getName() +"(" + (rooms.get(j++).getCapacity() - i.getNumberOfStudents()) +")"+ "  | ");
            }else if(rooms.get(j).getCapacity()-i.getNumberOfStudents()<10 ) {
                System.out.print(i.getName() + ", "+rooms.get(j).getName() +"(" + (rooms.get(j++).getCapacity() - i.getNumberOfStudents()) +")"+ "   | ");
            }else if(rooms.get(j).getCapacity()-i.getNumberOfStudents()<100)          {
                System.out.print(i.getName() + ", "+rooms.get(j).getName() +"(" + (rooms.get(j++).getCapacity() - i.getNumberOfStudents()) +")"+ "  | ");
            }else{
                System.out.print(i.getName() + ", "+rooms.get(j).getName() +"(" + (rooms.get(j++).getCapacity() - i.getNumberOfStudents()) +")"+ " | ");
            }
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

    public void scheduleRooms() {
        RoomFinder rf = new RoomFinder(rooms,classes);
        HashMap<Course, List<Room>> hm = rf.getRoomOrders();
        List<Room> availableRooms = new ArrayList<>(rooms);
        List<Room> orderedRooms = new ArrayList<>();
        for(Course i : classes){
            List<Room> bestOrder = hm.get(i);
            for(Room j : bestOrder){
                if(availableRooms.contains(j)){
                    orderedRooms.add(j);
                    availableRooms.remove(j);
                }
            }
        }
        rooms = orderedRooms;
    }

    public int countRoomCapacityFaults() {
        int result = 0;
        for(int i = 0; i<classes.size(); i++){
            if(rooms.get(i).getCapacity()-classes.get(i).getNumberOfStudents()<0) {
                result += (classes.get(i).getNumberOfStudents() - rooms.get(i).getCapacity());
            }

        }
        return result;

    }

    public String getRoomNameForClass(Course course){
        for(int i = 0; i<classes.size(); i++){
            if(classes.get(i).getName().equals(course.getName())){
                return rooms.get(i).getName();
            }
        }
        return null;
    }

}
