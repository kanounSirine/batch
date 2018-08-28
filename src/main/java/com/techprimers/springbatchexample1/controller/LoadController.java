package com.techprimers.springbatchexample1.controller;

import com.techprimers.springbatchexample1.model.User;
import com.techprimers.springbatchexample1.model.UserConvert;
import com.techprimers.springbatchexample1.service.UserService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.techprimers.springbatchexample1.service.UerServiceImp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@RestController
@Controller
@CrossOrigin (origins = "http://localhost:4200")
@RequestMapping("/load")
public class LoadController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @Autowired
    private UserService userService;


    @GetMapping
    public BatchStatus load() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {


        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);

        System.out.println("JobExecution: " + jobExecution.getStatus());

        System.out.println("Batch is Running...");
        while (jobExecution.isRunning()) {
            System.out.println("...");
        }

        return jobExecution.getStatus();
    }
    @RequestMapping (value = "/allusers")
    public List<User> getListAllUsers(){
    List<User> allUsers = new ArrayList<>();

        allUsers= userService.getAllUsers();
        return allUsers;

    }
    @RequestMapping (value = "/writexcel")
    public void writeExcel() throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        List<User> allUsers = new ArrayList<>();

        allUsers= userService.getAllUsers();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet( " Employee Info ");

        //Create row object
        XSSFRow row;

        //This data needs to be written (Object[])
        Map < String, UserConvert[] > listinfo = new TreeMap< String, UserConvert[] >();
        int i=1;
        List <UserConvert> list = new ArrayList<>();
        for (int j=0;j<allUsers.size();j++){

            list.get(j).setId(String.valueOf(allUsers.get(j).getId()));
            list.get(j).setDept(String.valueOf(allUsers.get(j).getDept()));
            list.get(j).setName(String.valueOf(allUsers.get(j).getName()));

            list.get(j).setSalary(String.valueOf(allUsers.get(j).getSalary()));
        }
        for(UserConvert k: list){
            listinfo.put(Integer.toString(i), new UserConvert[] { k });
            i++;
        };

        //Iterate over data and write to sheet
        Set < String > keyid = listinfo.keySet();
        int rowid = 0;

        for (String key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object [] objectArr = listinfo.get(key);
            int cellid = 0;
            for (Object obj : objectArr){
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String)obj);
            }
        }
        //Write the workbook in file system
        FileOutputStream out = new FileOutputStream(
                new File("C:/Users/GMI-PC/Writesheet.xlsx"));

        workbook.write(out);
        out.close();
        System.out.println("Writesheet.xlsx written successfully");
    }
}
