package com.datix.coresystem_poc.service;

import com.datix.coresystem_poc.entity.RentedWallbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SteveService {

    @Autowired
    private RestTemplate steveRestTemplate;

    private final String STEVE_URL = "http://localhost:8180";

    private final String REMOTE_START_PATH = "/steve/elekey/RemoteStart";

    private final String CHARGE_POINT_KEY = "chargePointSelectList";
    private final String CHARGE_POINT_VALUE_FORMAT = "V_16_JSON;%s;-";
    private final String CONNECTOR_ID_KEY = "connectorId";
    private final String CONNECTOR_ID_VALUE = "0";
    private final String ID_TAG_KEY = "idTag";
    private final String ID_TAG_VALUE = "42D8114A";

    public String triggerRemoteStart(RentedWallbox rentedWallbox) {

        ResponseEntity<String> response = steveRestTemplate.postForEntity(
                STEVE_URL + REMOTE_START_PATH,
                createRemoteStartRequest(rentedWallbox.getWallbox().getPhysicalId()),
                String.class
        );

        return response.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> createRemoteStartRequest(String wallboxPhysicalId) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(CHARGE_POINT_KEY, String.format(CHARGE_POINT_VALUE_FORMAT, wallboxPhysicalId));
        body.add(CONNECTOR_ID_KEY, CONNECTOR_ID_VALUE);
        body.add(ID_TAG_KEY, ID_TAG_VALUE);

        return new HttpEntity<>(body, createRequestHeader());
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return  headers;
    }

}
