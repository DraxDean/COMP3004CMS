package com.COMP3004CMS.cms.Service;

import com.COMP3004CMS.cms.Model.Log;
import com.COMP3004CMS.cms.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    public void addLog(Log log) {
        System.out.println("SERVERICE: " + log);
        System.out.println("SERVERICE1: "  +logRepository);
        logRepository.save(log);
    }
    public void saveLog(Log log) {
        System.out.println("SERVERICE: " + log);
        System.out.println("SERVERICE1: "  +logRepository);
        logRepository.save(log);
    }


    public List<Log> getLogs(){
        try {
           return logRepository.findAll();
        }
        catch(Exception e) {
            return new ArrayList<Log>();
        }
    }

}

