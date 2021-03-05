package com.github.mtlsclient.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client")
public class ClientController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    ResponseEntity<?> getMessage() {
	return ResponseEntity.ok("Client successfully called!");
    }

}