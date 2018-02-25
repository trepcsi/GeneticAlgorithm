import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private List<Course> courses = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();
    private List<Curriculum> curricula = new ArrayList<>();
    List<Integer> generalInformation = new ArrayList<>();

    public void read(String [ ] args, String pathString) throws FileNotFoundException {
        File file;
        file = new File(pathString);

        BufferedReader br = null;

        String line;


        try{
            br = new BufferedReader(new FileReader(file));

            int i=0;
            while((line = br.readLine()) != null){
                if(i == 0){System.out.println(line);}
                else if(i<=6){
                    generalInformation.add(Integer.parseInt(line.split(" ")[1]));}  /* 0: courses
                                                                                       1: rooms
                                                                                       2: days
                                                                                       3. periods/day
                                                                                       4. curricula
                                                                                       5. constrains   */

                else if (i > 6+2 && i <= 6+2+generalInformation.get(0)) {
                    String[] parameters = line.split(" ");
                    Course c = new Course(parameters[0], parameters[1], Integer.parseInt(parameters[2]),
                            Integer.parseInt(parameters[3]), Integer.parseInt(parameters[4]));
                    courses.add(c);
                }
                else if(i > 6+2+2+generalInformation.get(0) && i <= 6+2+2+generalInformation.get(0)+generalInformation.get(1)){

                    String[] parameters = line.split("\t");
                    rooms.add(new Room(parameters[0], Integer.parseInt(parameters[1])));
                }
                else if(i> 6+2+2+2+generalInformation.get(0)+generalInformation.get(1) && i<=6+2+2+2+generalInformation.get(0)+generalInformation.get(1)+generalInformation.get(4)) {
                    String[] c = line.split(" ");
                    List<Course> temp = new ArrayList<>();
                    for(int j = 2; j<c.length; j++){
                        for(int k=0; k<courses.size();k++){
                            if(c[j].equals(courses.get(k).getName())){
                                temp.add(courses.get(k));

                            }
                        }
                    }
                    curricula.add(new Curriculum(temp));
                }
                i++;
            }

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(br !=null) br.close();
            }catch (IOException e){
            }
        }
    }

    public List<Course> getCourses(){
        return courses;
    }
    public List<Curriculum> getCurricula(){
        return curricula;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public int getNumberOfDays() {
        return generalInformation.get(2);
    }

    public int getNumberOfPeriodsPerDay() {
        return generalInformation.get(3);
    }
}
