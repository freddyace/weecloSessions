package com.weeclo.Sessions;


import com.weeclo.Sessions.response.WeeCloSessionCreationFailureResponse;
import com.weeclo.Sessions.response.WeeCloSessionCreationSuccessResponse;
import com.weeclo.Sessions.session.WeeCloSession;
import com.weeclo.Sessions.session.certificate.Certificate;
import com.weeclo.Sessions.session.repository.WeeCloSessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "v1/post/session", method = RequestMethod.POST)
    public ResponseEntity<?> postSession(@RequestBody WeeCloSession weeCloSession){
        log.info("**********************Entering postSession**********************");
        System.out.println(weeCloSession.toString());
        try {
//            WeeCloSession weeCloSession = new WeeCloSession(new Certificate());
            weeCloSessionRepository.save(weeCloSession);
            WeeCloSessionCreationSuccessResponse weeCloSessionCreationSuccessResponse = new WeeCloSessionCreationSuccessResponse();
            ResponseEntity responseEntity = new ResponseEntity(weeCloSessionCreationSuccessResponse, HttpStatus.OK);
            return responseEntity;
        }catch(Exception e){
            WeeCloSessionCreationFailureResponse weeCloSessionCreationFailureResponse =
                    new WeeCloSessionCreationFailureResponse(e);
            return new ResponseEntity<>(weeCloSessionCreationFailureResponse, HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @GetMapping("/get/session/by/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id){
        if(weeCloSessionRepository.findById(id).equals(null)||
                weeCloSessionRepository.findById(id)==null){
            return new ResponseEntity<>("Session Not Found", HttpStatus.NOT_FOUND);
        }
        else return new ResponseEntity<>(weeCloSessionRepository.findById(id), HttpStatus.OK);

    }
    @GetMapping("/find/all/sessions")
    public Map<String, WeeCloSession> findall(){
        return weeCloSessionRepository.findAll();
    }


}
