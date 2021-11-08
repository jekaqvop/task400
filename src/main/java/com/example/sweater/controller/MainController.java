package com.example.sweater.controller;


import com.example.sweater.domain.Message;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {
    public List<Message> messages = new ArrayList<>();
    public int CountSis = 0;
    public int CountBro = 0;

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = this.messages;
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/main")
    public String add(HttpServletRequest request,
                      @AuthenticationPrincipal OAuth2User user,
                      Map<String, Object> model
    ) {
        String nameButton = request.getParameter("button");
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("HH:mm");
        Message message = new Message(nameButton, formatForDateNow.format(dateNow), getPrincipalName(user));
        this.messages.removeAll(messages);
        this.messages.add(message);
        if (nameButton.equals("Sis!")) CountSis++;
        if (nameButton.equals("Bro!")) CountBro++;
        Iterable<Message> messages = this.messages;
        model.put("messages", messages);
        return "main";
    }

    private String getPrincipalName(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) return "";
        if (principal.getAttribute("name") == null) return principal.getAttribute("login");
        return principal.getAttribute("name");
    }

    @GetMapping("/")
    public String retlogin(Map<String, Object> model) {
        model.put("message", "Count Sis: " + Integer.toString(CountSis) + " Count Bro: " + Integer.toString(CountBro));
        return "Mylogin";
    }
}
