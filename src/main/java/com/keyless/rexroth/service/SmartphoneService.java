package com.keyless.rexroth.service;




import com.keyless.rexroth.repository.SmartphoneRepository;
import com.keyless.rexroth.entity.Smartphone;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service




public class SmartphoneService {

    @Autowired
    private SmartphoneRepository smartphoneRepository;

    // Feste Testgeräte (deviceId → secretHash)
    private final Map<String, String> registeredDevices = new HashMap<>();

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public SmartphoneService() {
        registeredDevices.put("smph-01-ubuntu", "cc03e747a6afbbcbf8be7668acfebee5");
        registeredDevices.put("smph-02-android", "5ebe2294ecd0e0f08eab7690d2a6ee69");
    }


    public String authenticateAndGenerateToken(String deviceId, String secretHash) {
        if (deviceId == null || secretHash == null) return null;

        String storedHash = registeredDevices.get(deviceId);
        if (storedHash == null || !storedHash.equals(secretHash)) return null;


        // Token generieren
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenStore.put(token, deviceId);
        return token;
    }

    // ggf. Token-Revocation-Funktion
    public void revokeToken(String token) {
        tokenStore.remove(token);
    }

    public boolean verifyToken(String token, String deviceId) {
        String storedDeviceId = tokenStore.get(token);
        return storedDeviceId != null && storedDeviceId.equals(deviceId);
    }



}
