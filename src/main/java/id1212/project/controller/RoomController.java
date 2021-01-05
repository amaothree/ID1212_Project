package id1212.project.controller;

import id1212.project.entity.Room;
import id1212.project.repository.RoomRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @PostMapping("/add")
    public Room add(String topic,
                    String password,
                    String type){
        Room room = new Room();
        room.setPassword(password);
        room.setType(type);
        room.setTopic(topic);

        return roomRepository.save(room);
    }

    @GetMapping("/get")
    @ResponseBody
    public Room get(Long id){
        if(roomRepository.findById(id).isPresent())
            return roomRepository.findById(id).get();
        System.out.println("Error");
        return null;
    }

    @GetMapping("/get_all")
    @ResponseBody
    public List<Room> getAll(){
        return roomRepository.findAll();
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public String delete(Long id){
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null)
            return "Room not exist";
        else {
            roomRepository.delete(room);
            return "Delete room Success";
        }
    }

}
