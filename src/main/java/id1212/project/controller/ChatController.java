package id1212.project.controller;

import id1212.project.entity.RequestMessage;
import id1212.project.entity.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @CrossOrigin
    @MessageMapping("/chat")
    public void messageHandling(RequestMessage requestMessage) throws Exception {
        String destination = "/topic/" + HtmlUtils.htmlEscape(requestMessage.getRoom());

        String sender = HtmlUtils.htmlEscape(requestMessage.getSender());
        String type = HtmlUtils.htmlEscape(requestMessage.getType());
        String content = HtmlUtils.htmlEscape(requestMessage.getContent());
        ResponseMessage response = new ResponseMessage(sender, type, content);

        messagingTemplate.convertAndSend(destination, response);
    }

    @CrossOrigin
    @MessageMapping("/chatAll")
    public void messageHandlingAll(RequestMessage requestMessage) throws Exception {
        String destination = "/all";
        String sender = HtmlUtils.htmlEscape(requestMessage.getSender());
        String type = HtmlUtils.htmlEscape(requestMessage.getType());
        String content = HtmlUtils.htmlEscape(requestMessage.getContent());
        ResponseMessage response = new ResponseMessage(sender, type, content);

        messagingTemplate.convertAndSend(destination, response);
    }
}
