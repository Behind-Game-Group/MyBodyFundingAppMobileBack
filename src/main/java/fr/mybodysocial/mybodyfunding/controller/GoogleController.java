package fr.mybodysocial.mybodyfunding.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GoogleController {

    @GetMapping("/public")
    public String publicResource() {
        return "Public resource";
    }

    @GetMapping("/restricted")
    public Map<String, Object> restricted(Principal principal) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Restricted Resource");
        data.put("principal", principal);
        return data;
    }
}
