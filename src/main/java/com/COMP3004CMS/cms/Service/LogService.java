package com.COMP3004CMS.cms.Service;

import com.COMP3004CMS.cms.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    public void addLog(String log) {
        logRepository.add(log);
    }

    public ArrayList<String> getLogs(){
        return logRepository.getLogs();
    }

}

