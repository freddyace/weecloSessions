package com.weeclo.Sessions;


import com.weeclo.Sessions.response.WeeCloSessionCreationFailureResponse;
import com.weeclo.Sessions.response.WeeCloSessionCreationSuccessResponse;
import com.weeclo.Sessions.response.WeeCloSessionNotFoundResponse;
import com.weeclo.Sessions.session.WeeCloSession;
import com.weeclo.Sessions.session.certificate.Certificate;
import com.weeclo.Sessions.session.repository.WeeCloSessionRepository;
import entities.UserEntity;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    WeeCloSessionRepository weeCloSessionRepository;

    public Controller(WeeCloSessionRepository weeCloSessionRepository){
        this.weeCloSessionRepository = weeCloSessionRepository;
    }

    @GetMapping("/all")
    public Object all() {
        return weeCloSessionRepository.findAll();
    }

    @RequestMapping(value = "/v1/post/session", method = RequestMethod.POST)
    public ResponseEntity<?> postSession(@RequestBody UserEntity userEntity ){
        WeeCloSession weeCloSession = new WeeCloSession(new Certificate(userEntity));
        log.info("**********************Entering postSession**********************");
        JSONObject jsonObject = new JSONObject(weeCloSession);
        System.out.println(jsonObject.toString());
        try {
//            WeeCloSession weeCloSession = new WeeCloSession(new Certificate());
            weeCloSessionRepository.save(weeCloSession);
            WeeCloSessionCreationSuccessResponse weeCloSessionCreationSuccessResponse = new WeeCloSessionCreationSuccessResponse();
            weeCloSessionCreationSuccessResponse.setToken(weeCloSession.getCertificate().getToken());
            ResponseEntity responseEntity = new ResponseEntity(weeCloSessionCreationSuccessResponse, HttpStatus.OK);
            log.info("**********************Exiting postSession**********************");
            return responseEntity;
        }catch(Exception e){
            WeeCloSessionCreationFailureResponse weeCloSessionCreationFailureResponse =
                    new WeeCloSessionCreationFailureResponse(e);
            return new ResponseEntity<>(weeCloSessionCreationFailureResponse, HttpStatus.OK);
        }

    }
    @RequestMapping(value = "/get/session/by/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable("id") String id){
        if(weeCloSessionRepository.findById(id)==null){
            JSONObject jsonObject = new JSONObject(new WeeCloSessionNotFoundResponse());
            return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
        }
        else {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(weeCloSessionRepository.findById(id), httpHeaders,HttpStatus.OK);
        }

    }

    @GetMapping("/get/all/sessions")
    public Map<String, WeeCloSession> findall() {
        try {
            return weeCloSessionRepository.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("An issue was encountered with Redis WeeCloSession retrieval.");
            System.out.println("*********************************************");
            System.out.println("FLUSHDB on Redis Server???");
            System.out.println("*********************************************");
            System.out.println("FLUSHALL on Redis Server???");
            System.out.println("*********************************************");
        }
        WeeCloSessionNotFoundResponse weeCloSessionNotFoundResponse = new WeeCloSessionNotFoundResponse();
        HashMap hashMap = new HashMap();
        hashMap.put(weeCloSessionNotFoundResponse.getMessage(), null);
        return hashMap;
    }


}
