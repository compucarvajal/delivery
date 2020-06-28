
import com.test.service.ProcessInputFilesService;
import com.test.util.PropertiesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Main {

   private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) throws IOException {
        System.out.println("Test application");

        Properties prop = PropertiesFactory.getProperties("application.properties");
        System.out.println(prop.getProperty("input.files.path"));

        try {
            ProcessInputFilesService processInputFilesService = new ProcessInputFilesService();
            processInputFilesService.proccessInputFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
