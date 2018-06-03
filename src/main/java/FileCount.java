import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileCount {
    public static void main(String[] args) {
        File file=new File("C:\\Users\\powerfulyang\\IdeaProjects\\spring-boot-demo");
        FilenameFilter filter=new FilenameFilter() {
            public boolean accept(File dir, String name) {
                if(name.contains("spring-boot-demo")){
                    return true;
                }else{
                    return false;
                }
            }
        };
        String[] strs=file.list(filter);
        List<String> list= Arrays.asList(strs);
        list.forEach((x)-> System.out.println("<module>"+x+"</module>"));

    }
}
