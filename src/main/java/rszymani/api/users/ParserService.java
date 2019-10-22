package rszymani.api.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ParserService {
    private final String delimiter = ";";
    private Logger logger = LoggerFactory.getLogger(ParserService.class);

    public List<User> getUserList(MultipartFile file){
        List<User> users = new LinkedList<>();
        try {
            BufferedReader br = multipartToBufferedReader(file);
            users = convertCsvToUsers(br);
        }catch(Exception e){
            e.printStackTrace();
            logger.warn(e.getMessage());
        }
        return users;

    }
    public BufferedReader multipartToBufferedReader(MultipartFile file) throws IOException{
        BufferedReader br;
        InputStream is = file.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        return br;
    }
    private List<User> convertCsvToUsers(BufferedReader br) throws IOException,ParseException{
        List<User> users = new LinkedList<>();
        String line = br.readLine();
        while ((line = br.readLine()) != null){
            logger.info("Trying to parse line: " + line);
            try {
                String[] fields = line.split(delimiter);

                String firstName = getFirstName(fields);
                String secondName = getSecondName(fields);
                Date birthDate = getBirthDate(fields);
                Integer phoneNo = getPhoneNumber(fields);

                User user = new User(firstName, secondName, birthDate, phoneNo);
                users.add(user);
            }catch(IllegalArgumentException e){
                logger.warn(e.getMessage());
            }
        }
        return users;
    }
    private String getFirstName(String[] fields){
        if(fields.length>0 && !fields[0].isEmpty()) {
            String firstName = fields[0].trim();
            return firstName;
        }else{
            throw new IllegalArgumentException("FirstName cannot be null, not adding to DB");
        }
    }
    private String getSecondName(String[] fields){
        if(fields.length>1 && !fields[1].isEmpty()){
            String secondName = fields[1].trim();
            return secondName;
        }else{
            throw new IllegalArgumentException("FirstName cannot be null, not adding to DB");
        }
    }
    private Date getBirthDate(String[] fields)
            throws ParseException, IllegalArgumentException{
        if(fields.length>2 && !fields[2].isEmpty()){
            String stringDate = fields[2].trim();
            Date birthDate = convertDate(stringDate);
            return birthDate;
        }else{
            throw new IllegalArgumentException("Birth Date cannot be null, not adding to DB");
        }
    }
    private Date convertDate(String stringDate) throws ParseException{
        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date date = formatter.parse(stringDate);
        return date;
    }
    private Integer getPhoneNumber(String[] fields){
        if(fields.length>3 && !fields[3].isEmpty()) {
            String phoneNumber = fields[3].trim();
            if(phoneNumber.length() == 9){
                return Integer.parseInt(phoneNumber);
            }else{
               return null;
            }
        }else{
            return null;
        }
    }




}
