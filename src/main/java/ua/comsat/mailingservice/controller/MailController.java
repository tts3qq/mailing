package ua.comsat.mailingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.comsat.mailingservice.model.RawUserInfo;
import ua.comsat.mailingservice.service.MailService;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/")
    public String getHome(@RequestParam(required = false, defaultValue = "ua") String lang, Model model) {
        model.addAttribute("language", lang);
        return "index";
    }

    @PostMapping("/send")
    public String sendMessage(@ModelAttribute("rawData") RawUserInfo rawUserInfo) {
        return mailService.send(rawUserInfo) ? "index" : "error";
    }
}