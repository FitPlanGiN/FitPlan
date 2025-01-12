package com.fitplan.validation.controller;

import com.fitplan.validation.model.Workout;
import com.fitplan.validation.service.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validation")
//@RequiredArgsConstructor
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String askAI(@RequestParam String name, @RequestParam String description) {
        return validationService.askAI(name, description);
    }
}
