package rszymani.api.users;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;


@RestController
@RequestMapping("/")
public class UsersController {

    private Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParserService parserService;

    @RequestMapping(value="/saveUsers", method=RequestMethod.POST)
    @ResponseBody
    public List<User> saveUsers(@RequestParam MultipartFile file){
        List<User> users = parserService.getUserList(file);
        for(User user:users){
            userRepository.save(user);
        }
        return users;
    }

    @RequestMapping(value="/countUsers", method = RequestMethod.GET,produces="application/json")
    @ResponseBody
    public String countUsers(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("NoOfUsers", userRepository.count());
        return jsonObject.toString();
    }

    @RequestMapping(value="/allUsers",method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String allUsers(){
        List<User> users = userRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        for(User user:users){
            JSONObject jsonObject = new JSONObject(user.toString());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @RequestMapping(value="/usersByLastName/{lastName}",method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public List<User> usersByLastName(@PathVariable String lastName){
        return userRepository.findByLastName(lastName);
    }

    @RequestMapping(value="/oldestUserWithNumber", method = RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String oldestUserWithNumber(){
        User user = userRepository.findOldestWithNumber();
        return (new JSONObject(user.toString())).toString();
    }

    @RequestMapping(value="/deleteUser/{id}",method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    public String deleteUser(@PathVariable String id){
        int deleted = userRepository.delete(id);
        JSONObject response = new JSONObject();
        String message;
        if(deleted==1){
            message = "Deleted user with id :" + id;
        }else{
            message = "Couldn't delete user with id : " + id+ " check if id exists in database";
        }
        response.put("message", message);
        logger.info(message);
        return response.toString();
    }

    @RequestMapping(value="/deleteUsers/{ids}",method = RequestMethod.DELETE, produces="application/json")
    @ResponseBody
    public String deleteUsers(@PathVariable String ids){
        String[] idsSplitted = ids.split(",");
        List<String> deletedIds = new LinkedList<String>();
        List<String> notDeletedIds = new LinkedList<String>();
        for(String id:idsSplitted){
            int deleted = userRepository.delete(id);
            if(deleted == 1){
                deletedIds.add(id);
            }else{
                notDeletedIds.add(id);
            }
        }
        String message = "Deleted " + deletedIds.toString() + " couldn't delete: " +notDeletedIds.toString();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", message);
        logger.info(message);
        return jsonObject.toString();
    }




}
