package com.picpaysimple.picpaysimple.services;

import com.picpaysimple.picpaysimple.domain.user.User;
import com.picpaysimple.picpaysimple.dto.NotificationDTO;
import com.picpaysimple.picpaysimple.exception.ExternalServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private final RestTemplate restTemplate;

    @Value("${https://util.devi.tools/api/v2/authorize}")
    private String url;

    public void sendNotification ( User user, String message ){
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        ResponseEntity<String> notificationResponse= restTemplate.postForEntity(url, notificationRequest, String.class);

        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)){
            throw new ExternalServiceUnavailableException("notification service is down"+notificationResponse.getStatusCode());
        }
    }


}