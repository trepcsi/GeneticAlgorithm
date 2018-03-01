import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class RoomFinder {
    private List<Room> rooms;
    private List<Course> courses;

    private HashMap<Course, List<Room>> roomOrders;

    public RoomFinder(List<Room> rooms, List<Course> courses){
        this.rooms = new ArrayList<>(rooms);
        this.courses = new ArrayList<>(courses);
        roomOrders = new HashMap<>();
        orderRooms();

    }

    public HashMap<Course, List<Room>> getRoomOrders() {
        for(Course i : courses){
            bestRoomsForCourse(i);
        }
        return roomOrders;
    }

    private void orderRooms(){  //gnome sort
        int i = 0;
        while(i < rooms.size()-1){
            if(rooms.get(i).getCapacity() <= rooms.get(i+1).getCapacity()){
                i++;
            }
            else{
                Collections.swap(rooms, i, i+1);
                if(--i < 0) i = 0;
            }
        }
    }

    private void bestRoomsForCourse(Course course){
        List<Room> roomOrder = new ArrayList<>();
        int j=0;
        for(Room i : rooms){
            if(i.getCapacity() < course.getNumberOfStudents()){
                roomOrder.add(0,i);
            }
            else{
                roomOrder.add(j++,i);
            }
        }
        roomOrders.put(course,roomOrder);
    }

}
