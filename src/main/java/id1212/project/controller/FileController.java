package id1212.project.controller;

import id1212.project.entity.UserFile;
import id1212.project.repository.UserFileRepository;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    private final UserFileRepository userFileRepository;
    private final File HOMEDIR = new File(System.getProperty("user.home")+File.separator+"ProjectFiles"+File.separator);

    public FileController(UserFileRepository userFileRepository){
        this.userFileRepository = userFileRepository;
        if (!HOMEDIR.exists()){
            try{
                if (HOMEDIR.mkdir()) {
                    System.out.println("Folder " + HOMEDIR.toString() + " created");
                } else {
                    System.out.println("Folder " + HOMEDIR.toString() + " create failed");
                }
            } catch (Exception e){
                System.out.println(e.toString());
            }
        }
    }

    @CrossOrigin
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("files") MultipartFile[] files, Long room_id, Long user_id) throws JSONException {
        JSONObject object = new JSONObject();
        try {
            for (MultipartFile uploadedFile : files) {
                String name = uploadedFile.getOriginalFilename();
                UserFile userFile = new UserFile();
                if (userFileRepository.findUserFileByNameAndRoom(name, room_id).isPresent()) {
                    userFile = userFileRepository.findUserFileByNameAndRoom(name, room_id).get();
                    if (!userFile.getOwner().equals(user_id)) {
                        object.put("status", 3);
                        object.put("message", "Room had same file, you are not owner so can not update it.");
                        return object.toString();
                    }
                } else {
                    userFile.setName(uploadedFile.getOriginalFilename());
                    userFile.setOwner(user_id);
                    userFile.setRoom(room_id);
                    userFile.setType(uploadedFile.getContentType());
                }
                userFile.setUpdateTime(new Date(System.currentTimeMillis()));
                userFile = userFileRepository.save(userFile);
                File file = new File(HOMEDIR,userFile.getId().toString());
                uploadedFile.transferTo(file);
            }
        } catch (Exception e){
            System.out.println(e.toString());
            object.put("status",2);
            object.put("message","Upload Error, Please try to resubmit");
            return object.toString();
        }
        object.put("status",1);
        object.put("message","Upload Successful");
        return object.toString();
    }
}
