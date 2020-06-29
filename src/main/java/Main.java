
import com.test.domain.CardinalPoint;
import com.test.domain.Drone;
import com.test.service.ProcessDeliveryService;
import com.test.service.ProcessInputFilesService;
import com.test.util.PropertiesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

public class Main {

   private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String args[]) throws IOException {


        try {
            ProcessInputFilesService processInputFilesService = new ProcessInputFilesService();
            processInputFilesService.proccessInputFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        Drone drone1 = new Drone(0,0, CardinalPoint.NORTH);
        drone1.moveA();
        drone1.moveA();
        drone1.moveA();
        drone1.moveA();
        drone1.moveLeft();
        drone1.moveA();
        drone1.moveA();
        log.info(drone1.getLocation().toString());*/

        /*
        try {
            ProcessDeliveryService processDeliveryService = new ProcessDeliveryService();
            Drone drone1 = new Drone(0, 0, CardinalPoint.NORTH);
            Boolean result = processDeliveryService.validateFinalPoint(0,0,"DDDAIAD",10);
            log.info(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/


    }
}
