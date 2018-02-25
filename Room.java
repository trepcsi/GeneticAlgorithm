public class Room {

    private String name;

    //number of seats
    private int capacity;
    //number of taken seats
    private int numberOfTakenSeats;

    //how good is the room for class
    private double efficiency;

    public Room(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.numberOfTakenSeats = 0;
        this.efficiency = 0;
    }

    public String getName(){
        return name;
    }

    public int getCapacity(){
        return capacity;
    }

    public double getEfficiency(){
        return efficiency;
    }

    public void addClassToRoom(){
        //TODO
    }

    public void printRoom(){
        System.out.print(name+" "+capacity+'\n');
    }

}
