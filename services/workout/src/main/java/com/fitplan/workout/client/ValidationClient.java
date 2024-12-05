package com.fitplan.workout.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "validation", url = "${validation.url}")
public interface ValidationClient {

    //Feign client samodejno generira HTTP zahteve na podlagi tega vmesnika
    @RequestMapping(method = RequestMethod.POST, value = "/api/validation")
    String askAI(@RequestParam String name, @RequestParam String description);


}
