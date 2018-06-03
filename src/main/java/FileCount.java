import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

public class FileCount {
    public static void main(String[] args) {
        File file=new File(System.getProperty("user.dir"));
        FilenameFilter filter= (dir, name) -> name.contains("spring-boot-demo");
        String[] fileList=file.list(filter);
        assert fileList != null;
        List<String> list= Arrays.asList(fileList);
        list.forEach((x)-> System.out.println("<module>"+x+"</module>"));

    }
}
