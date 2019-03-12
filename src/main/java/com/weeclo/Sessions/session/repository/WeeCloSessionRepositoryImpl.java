package com.weeclo.Sessions.session.repository;

import com.weeclo.Sessions.session.WeeCloSession;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WeeCloSessionRepositoryImpl implements WeeCloSessionRepository {

    private RedisTemplate<String, WeeCloSession> redisSessionTemplate;
    private HashOperations hashOperations;

    public WeeCloSessionRepositoryImpl(RedisTemplate<String, WeeCloSession> redisSessionTemplate){
        this.redisSessionTemplate = redisSessionTemplate;
        hashOperations = redisSessionTemplate.opsForHash();
    }
    @Override
    public void save(WeeCloSession weeCloSession) {
        //Looking for the session by the ownerID
//        hashOperations.put("SESSION", weeCloSession.getCertificate().getOwnerID(), weeCloSession);
        //Can probably use this if you want to use the token instead..since the token # is saved as the weeCloSessionID
        HashMap<String, WeeCloSession> hashMap = new HashMap();
        System.out.println("OWNER ID: "+weeCloSession.getCertificate().getOwnerID());
        System.out.println("TOKEN ID: "+weeCloSession.getCertificate().getToken().getID());
        hashMap.put(weeCloSession.getCertificate().getOwnerID(), weeCloSession);
//        hashMap.put(weeCloSession.getCertificate().getToken().getID(), weeCloSession);
//        hashOperations.put("SESSION", weeCloSession.getCertificate().getOwnerID(), weeCloSession);
          hashOperations.putAll("SESSION", hashMap);
    }

    public WeeCloSession findByTokenID(String token){
        return (WeeCloSession) hashOperations.get("SESSION", token);
    }

    @Override
    public Map<String, WeeCloSession> findAll() {
        return hashOperations.entries("SESSION");
    }

    @Override
    public WeeCloSession findById(String id) {
        return (WeeCloSession) hashOperations.get("SESSION", id);
    }

    @Override
    public void update(WeeCloSession weeCloSession) {

    }

    @Override
    public void delete(String id) {

    }
}
