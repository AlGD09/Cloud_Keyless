package com.keyless.rexroth.service;

import com.keyless.rexroth.repository.SmartphoneRepository;
import com.keyless.rexroth.entity.Smartphone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service

public class SmartphoneService {

    @Autowired
    private SmartphoneRepository smartphoneRepository;

    // Feste Testgeräte (deviceId → secretHash)
    //private final Map<String, String> registeredDevices = new HashMap<>();

    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    //public SmartphoneService() {
        //registeredDevices.put("smph-01-ubuntu", "cc03e747a6afbbcbf8be7668acfebee5");
        //registeredDevices.put("smph-02-android", "5ebe2294ecd0e0f08eab7690d2a6ee69");
    //}


    public String authenticateAndGenerateToken(String deviceId, String secretHash) {
        if (deviceId == null || secretHash == null) return null;

        //String storedHash = registeredDevices.get(deviceId)
        //if (storedHash == null || !storedHash.equals(secretHash)) return null;

        Smartphone device = smartphoneRepository.findByDeviceId(deviceId);

        if (device == null) {
            System.out.println("❌ Gerät nicht gefunden: " + deviceId);
            return null;
        }

        if (!secretHash.equals(device.getSecretHash())) {
            System.out.println("❌ Ungültiger SecretHash für Gerät: " + deviceId);
            return null;
        }


        // Token generieren
        String token = UUID.randomUUID().toString().replace("-", "");
        tokenStore.put(token, deviceId);

        // Zeitstempel aktualisieren
        device.setLastSeen(java.time.LocalDateTime.now());
        smartphoneRepository.save(device);


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


    public Smartphone registerSmartphone(String deviceId, String userName, String secretHash) {
        if (deviceId == null || secretHash == null) return null;

        // Prüfen, ob Gerät bereits existiert
        Smartphone existing = smartphoneRepository.findByDeviceId(deviceId);
        if (existing != null) {
            existing.setUserName(userName);
            existing.setSecretHash(secretHash);
            existing.setStatus("updated");
            return smartphoneRepository.save(existing);
        }

        Smartphone s = new Smartphone();
        s.setDeviceId(deviceId);
        s.setUserName(userName);
        s.setSecretHash(secretHash);
        s.setStatus("active");
        s.setLastSeen(java.time.LocalDateTime.now());
        return smartphoneRepository.save(s);
    }

    public List<Smartphone> getAllSmartphones() {
        return smartphoneRepository.findAll();
    }



}
