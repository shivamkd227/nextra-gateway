package com.example.nextragateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/gateway")
public class GatewayController {

    private static final Logger log = LoggerFactory.getLogger(GatewayController.class);

    private final RestTemplate restTemplate;

    @Value("${services.flight-url}")
    private String flightServiceUrl;

    @Value("${services.hotel-url}")
    private String hotelServiceUrl;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/flight")
    public String routeToFlight() {
        log.info("[NEXTRA GATEWAY] Incoming request for FLIGHT. Forwarding to: {}", flightServiceUrl);
        String response = restTemplate.getForObject(flightServiceUrl + "/api/v1/flights/search", String.class);
        log.info("[NEXTRA GATEWAY] Response received from Flight Service.");
        return "Gateway -> " + response;
    }

    @GetMapping("/hotel")
    public String routeToHotel() {
        log.info("[NEXTRA GATEWAY] Incoming request for HOTEL. Forwarding to: {}", hotelServiceUrl);
        String response = restTemplate.getForObject(hotelServiceUrl + "/api/v1/hotels/search", String.class);
        log.info("[NEXTRA GATEWAY] Response received from Hotel Service.");
        return "Gateway -> " + response;
    }
}