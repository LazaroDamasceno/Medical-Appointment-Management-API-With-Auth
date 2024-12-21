package com.api.v2.controllers.doctors;

import com.api.v2.dtos.DoctorHiringDto;
import com.api.v2.dtos.DoctorResponseDto;
import com.api.v2.services.DoctorHiringService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class DoctorController {

    private final DoctorHiringService hiringService;

    public DoctorController(DoctorHiringService hiringService) {
        this.hiringService = hiringService;
    }

    @PostMapping
    public Mono<DoctorResponseDto> hire(@Valid @RequestBody DoctorHiringDto hiringDto) {
        return hiringService.hire(hiringDto);
    }
}
