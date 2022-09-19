package com.lexxkit.hogwarts.school.controller;

import com.lexxkit.hogwarts.school.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @Value("${server.port}")
    private int serverPort;

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPort() {
        return ResponseEntity.ok(serverPort);
    }

    @GetMapping("/getSumWithStream")
    public ResponseEntity<Integer> calculateSum() {
        return ResponseEntity.ok(infoService.calculateSumFromIterator());
    }
}
