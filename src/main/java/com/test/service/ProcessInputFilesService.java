package com.test.service;

import com.test.util.PropertiesFactory;
import org.apache.commons.io.FileUtils;
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
    private static final String DRONES_NUMBER = "drones.number";
    private static final String MAX_ORDER = "max.orders";

    private static int dronsNumber;
    private static String inputDirectory;
    private static int maxOrders;
    private Properties applicationProperties;

    public ProcessInputFilesService() throws Exception {
        try {
            applicationProperties = PropertiesFactory.getProperties("application.properties");
            dronsNumber = Integer.valueOf(applicationProperties.getProperty(DRONES_NUMBER));
            inputDirectory = applicationProperties.getProperty(INPUT_DIRECTORY);
            maxOrders = Integer.valueOf(applicationProperties.getProperty(MAX_ORDER));

            log.info("application properties got successfully");
        } catch (IOException e) {
            log.error("Error loading the system properties file");
            throw new Exception(e.getMessage());
        }
    }

    public void proccessInputFiles() throws Exception {
        List<String> availableFileList = getAvailableFiles();
        for (String fileName : availableFileList) {
            log.info(fileName);
        }
        if (availableFileList.size() == 0) {
            throw new Exception("Empty available file list");
        }
        //getFileInformation(availableFileList);
        getFileInformationParallel(availableFileList);
    }

    public List<String> getAvailableFiles() {
        File directoryPath = new File(inputDirectory);
        String contents[] = directoryPath.list();
        return Arrays.asList(contents);
    }

    public void getFileInformation(List<String> inputFileList) {
        Map<Integer, List<String>> ordersByDrone = new HashMap<Integer, List<String>>();
        long start = System.currentTimeMillis();
        inputFileList.stream().forEach(fileName -> {
            log.info(fileName);
            try {
                log.info("path: " + inputDirectory + fileName);
                String content = Files.readString(Paths.get(inputDirectory + fileName));
                int drone = Integer.valueOf(fileName.substring(2, 4));
                log.info(String.valueOf(drone));
                log.info(content);
                List<String> lines = Arrays.asList(content.split(System.getProperty("line.separator")));
                if(maxOrders<=lines.size()) {
                    lines = lines.subList(0, maxOrders);
                }else{
                    lines = lines.subList(0, lines.size());
                }
                log.info("lines, size: " + lines.size());
                ordersByDrone.put(drone, lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        long result = end - start;
        log.info("result: " + result);
        log.info(ordersByDrone.toString());
    }

    public void getFileInformationParallel(List<String> inputFileList) {
        Map<Integer, List<String>> ordersByDrone = new HashMap<Integer, List<String>>();
        long start = System.currentTimeMillis();
        inputFileList.stream().parallel().forEach(fileName -> {
            log.info(fileName);
            try {
                log.info("path: " + inputDirectory + fileName);
                String content = Files.readString(Paths.get(inputDirectory + fileName));
                int drone = Integer.valueOf(fileName.substring(2, 4));
                log.info(String.valueOf(drone));
                log.info(content);
                List<String> lines = Arrays.asList(content.split(System.getProperty("line.separator")));
                log.info("lines, size: " + lines.size());
                if(maxOrders<=lines.size()) {
                    lines = lines.subList(0, maxOrders);
                }else{
                    lines = lines.subList(0, lines.size());
                }
                ordersByDrone.put(drone, lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        long result = end - start;
        log.info("result: " + result);
        log.info(ordersByDrone.toString());
    }

}
