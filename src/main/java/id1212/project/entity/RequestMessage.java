package id1212.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestMessage {

    private String sender;
    private String room;
    private String type;
    private String content;

}
