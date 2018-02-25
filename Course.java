public class Course {

    private String name;
    private String teacher;

    private int numberOfStudents;
    //how many classes on a week
    private int numberOfClasses;
    //how many different day
    private int minimumDays;

    public Course(String name, String teacher, int numberOfClasses, int minimumDays, int manpower){
        this.name = name;
        this.teacher = teacher;
        this.numberOfStudents = manpower;
        this.numberOfClasses = numberOfClasses;
        this.minimumDays = minimumDays;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public int getMinimumDays() {
        return minimumDays;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public int getMinimumDays(int minimumDays) {
        return minimumDays;
    }

    public void printCourse(){
        System.out.print(name+" "+teacher+" "+numberOfClasses+" "+minimumDays+" "+numberOfStudents);
        System.out.println();
    }
}
