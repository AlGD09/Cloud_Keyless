package com.datix.coresystem_poc.service;

import com.datix.coresystem_poc.dto.TransactionDetailsDTO;
import com.datix.coresystem_poc.entity.Booking;
import com.datix.coresystem_poc.entity.ChargingTransaction;
import com.datix.coresystem_poc.map.TransactionMap;
import com.datix.coresystem_poc.repository.BookingRepository;
import com.datix.coresystem_poc.repository.ChargingTransactionRepository;
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

    private final String STEVE_URL = "http://host.docker.internal:8180";
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ChargingTransactionRepository transactionRepository;

    private final String STEVE_ELEKEY_PATH = "/steve/elekey";

    private final String CHANGE_AVAILABILITY_PATH = "/ChangeAvailability";
    private final String REMOTE_START_PATH = "/RemoteStart";
    private final String REMOTE_STOP_PATH = "/RemoteStop";
    private final String TRANSACTION_DETAILS_PATH = "/transactions/details";

    private final String CHARGE_POINT_KEY = "chargePointSelectList";
    private final String CHARGE_POINT_VALUE_FORMAT = "V_16_JSON;%s;-";
    private final String CONNECTOR_ID_KEY = "connectorId";
    private final String CONNECTOR_ID_VALUE = "0";
    private final String ID_TAG_KEY = "idTag";
    private final String ID_TAG_VALUE = "42D8114A";
    private final String TRANSACTION_ID_KEY = "transactionId";
    private final String AVAILABILITY_TYPE_KEY = "availType";
    private final String AVAILABILITY_OPERATIVE_VALUE = "OPERATIVE";
    private final String AVAILABILITY_INOPERATIVE_VALUE = "INOPERATIVE";

    public String turnOn(String rentedWallboxId) {
        ResponseEntity<String> response = steveRestTemplate.postForEntity(
                STEVE_URL + STEVE_ELEKEY_PATH + CHANGE_AVAILABILITY_PATH,
                createChangeAvailabilityRequest(rentedWallboxId, AVAILABILITY_OPERATIVE_VALUE),
                String.class
        );

        return response.getBody();
    }

    public String turnOff(String rentedWallboxId) {
        ResponseEntity<String> response = steveRestTemplate.postForEntity(
                STEVE_URL + STEVE_ELEKEY_PATH + CHANGE_AVAILABILITY_PATH,
                createChangeAvailabilityRequest(rentedWallboxId, AVAILABILITY_INOPERATIVE_VALUE),
                String.class
        );

        return response.getBody();
    }

    public Integer triggerRemoteStart(String rentedWallboxId) {
        ResponseEntity<Integer> response = steveRestTemplate.postForEntity(
                STEVE_URL + STEVE_ELEKEY_PATH + REMOTE_START_PATH,
                createRemoteStartRequest(rentedWallboxId),
                Integer.class
        );

        return response.getBody();
    }

    public void triggerRemoteStop(String rentedWallboxId, Long bookingId) {
        ResponseEntity<TransactionDetailsDTO> response = steveRestTemplate.postForEntity(
                STEVE_URL + STEVE_ELEKEY_PATH + REMOTE_STOP_PATH,
                createRemoteStopRequest(rentedWallboxId),
                TransactionDetailsDTO.class
        );
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        ChargingTransaction transaction = TransactionMap.toEntity(response.getBody());
        booking.addTransaction(transaction);
        bookingRepository.save(booking);
    }


    public TransactionDetailsDTO getTransactionDetails(int transactionId) {
        ResponseEntity<TransactionDetailsDTO> response = steveRestTemplate.getForEntity(
                STEVE_URL + STEVE_ELEKEY_PATH + TRANSACTION_DETAILS_PATH + String.format("/%s", transactionId),
                TransactionDetailsDTO.class
        );

        return response.getBody();
    }

    private HttpEntity<MultiValueMap<String, String>> createChangeAvailabilityRequest(String wallboxPhysicalId, String availabilityType) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(CHARGE_POINT_KEY, String.format(CHARGE_POINT_VALUE_FORMAT, wallboxPhysicalId));
        body.add(CONNECTOR_ID_KEY, CONNECTOR_ID_VALUE);
        body.add(AVAILABILITY_TYPE_KEY, availabilityType);

        return new HttpEntity<>(body, createRequestHeader());
    }

    private HttpEntity<MultiValueMap<String, String>> createRemoteStartRequest(String wallboxPhysicalId) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(CHARGE_POINT_KEY, String.format(CHARGE_POINT_VALUE_FORMAT, wallboxPhysicalId));
        body.add(CONNECTOR_ID_KEY, CONNECTOR_ID_VALUE);
        body.add(ID_TAG_KEY, ID_TAG_VALUE);

        return new HttpEntity<>(body, createRequestHeader());
    }

    private HttpEntity<MultiValueMap<String, String>> createRemoteStopRequest(String wallboxPhysicalId) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(CHARGE_POINT_KEY, String.format(CHARGE_POINT_VALUE_FORMAT, wallboxPhysicalId));
        body.add(TRANSACTION_ID_KEY, "0");

        return new HttpEntity<>(body, createRequestHeader());
    }

    private HttpHeaders createRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return headers;
    }

}
