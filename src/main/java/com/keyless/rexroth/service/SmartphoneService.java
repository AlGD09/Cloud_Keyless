package com.keyless.rexroth.service;

import com.keyless.rexroth.entity.RCU;
import com.keyless.rexroth.repository.RCURepository;
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

    @Autowired
    private RCURepository rcuRepository;

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

        tokenStore.values().removeIf(id -> id.equals(deviceId));


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

    public String getTokenForSmartphone(Long smartphoneId) {
        Smartphone device = smartphoneRepository.findById(smartphoneId).orElse(null);
        if (device == null) return null;

        // Suche in tokenStore nach dem zugehörigen Token
        for (Map.Entry<String, String> entry : tokenStore.entrySet()) {
            if (entry.getValue().equals(device.getDeviceId())) {
                return entry.getKey(); // Token gefunden
            }
        }

        return null; // Kein Token aktiv
    }

    public void deleteSmartphone(Long id) {
        if (smartphoneRepository.existsById(id)) {

            // Alle RCUs abrufen
            List<RCU> rcuList = rcuRepository.findAll();

            // Prüfen, ob eine RCU das Smartphone zugewiesen hat
            for (RCU rcu : rcuList) {
                if (rcu.getAssignedSmartphone() != null &&
                        rcu.getAssignedSmartphone().getId().equals(id)) {

                    // Smartphone-Zuweisung aufheben
                    rcu.unassignSmartphone();
                    rcuRepository.save(rcu); // Änderung speichern
                }
            }

            // Smartphone aus Repository löschen
            smartphoneRepository.deleteById(id);
        }
    }

    public void unassignSmartphone(String rcuId, Long smartphoneId){
        RCU device = rcuRepository.findByRcuId(rcuId);

        device.unassignSmartphone();
        rcuRepository.save(device);


    }
}



