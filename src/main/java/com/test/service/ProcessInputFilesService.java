package com.test.service;

import com.test.util.PropertiesSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ProcessInputFilesService {

    private static final Logger log = LoggerFactory.getLogger(ProcessInputFilesService.class.getName());
    private static final String INPUT_DIRECTORY = "input.files.path";
    private static final String DRONES_NUMBER = "max.drones.number";
    private static final String MAX_ORDER = "max.orders";

    private static int dronesNumber;
    private static String inputDirectory;
    private static int maxOrders;

    private static int cont=0;

    ProcessDeliveryService processDeliveryService;

    public ProcessInputFilesService() throws Exception {

        Properties applicationProperties = PropertiesSingleton.getApplicationProperties();
        dronesNumber = Integer.parseInt(applicationProperties.getProperty(DRONES_NUMBER));
        inputDirectory = applicationProperties.getProperty(INPUT_DIRECTORY);
        maxOrders = Integer.parseInt(applicationProperties.getProperty(MAX_ORDER));


        log.info("application properties got successfully");

        processDeliveryService = new ProcessDeliveryService();

    }

    public void proccessInputFiles() throws Exception {
        List<String> availableFileList = getAvailableFiles();
        for (String fileName : availableFileList) {
            log.info(fileName);
        }
        if (availableFileList.size() == 0) {
            throw new Exception("available file list is empty");
        }

        processDeliveryService.processOrders(getFileInformation(availableFileList));

    }

    public List<String> getAvailableFiles() {
        File directoryPath = new File(inputDirectory);
        String[] contents = directoryPath.list();
        assert contents != null;
        return Arrays.asList(contents);
    }

    public Map<Integer, List<String>> getFileInformation(List<String> inputFileList) {
        Map<Integer, List<String>> ordersByDrone = new HashMap<Integer, List<String>>();
        long start = System.currentTimeMillis();
        inputFileList.stream().forEach(fileName -> {
            log.info(fileName);
            try {
                log.info("path: " + inputDirectory + fileName);
                String content = Files.readString(Paths.get(inputDirectory + fileName));
                int drone = Integer.parseInt(fileName.substring(2, 4));
                log.info(String.valueOf(drone));
                log.info(content);
                List<String> lines = Arrays.asList(content.split(System.getProperty("line.separator")));
                if (maxOrders <= lines.size()) {
                    lines = lines.subList(0, maxOrders);
                } else {
                    lines = lines.subList(0, lines.size());
                }
                log.info("lines, size: " + lines.size());
                if(cont<dronesNumber){
                    ordersByDrone.put(drone, lines);
                    cont++;
                }
            } catch (IOException e) {
                log.error("reading file error");
            }
        });
        long end = System.currentTimeMillis();
        long result = end - start;
        log.info("result: " + result);
        log.info(ordersByDrone.toString());
        return ordersByDrone;
    }

}
