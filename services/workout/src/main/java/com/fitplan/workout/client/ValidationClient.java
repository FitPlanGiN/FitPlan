package com.fitplan.workout.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

//@FeignClient(name = "validation", url = "${validation.url}")
@HttpExchange("/api/validation")
public interface ValidationClient {

    //Feign client samodejno generira HTTP zahteve na podlagi tega vmesnika
    //@RequestMapping(method = RequestMethod.POST, value = "/api/validation")
    @PostExchange
    String askAI(@RequestParam String name, @RequestParam String description);


}
