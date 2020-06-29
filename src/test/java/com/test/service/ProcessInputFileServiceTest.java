package com.test.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessInputFileServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProcessInputFileServiceTest.class);


    private List<String> availableFileList = new ArrayList<>();

    @Test
    public void proccessInputFilesErrorEmptyListTest(){
        try {
            ProcessInputFilesService processInputFilesService = new ProcessInputFilesService();
            processInputFilesService.proccessInputFiles();
        }catch(Exception e){
            assertEquals(e.getMessage(),"available file list is empty");
            log.info(e.getMessage());
        }
    }
}
