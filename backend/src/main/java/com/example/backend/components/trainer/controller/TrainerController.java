package com.example.backend.components.trainer.controller;

import com.example.backend.EntityComponentsProvider.controller.Controller;
import com.example.backend.components.trainer.dto.request.CreateTrainerDTO;
import com.example.backend.components.trainer.dto.request.UpdateTrainerDTO;
import com.example.backend.components.trainer.dto.response.TrainerResponseDTO;
import com.example.backend.components.trainer.service.TrainerService;
import com.example.backend.entities.Trainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController extends Controller<Trainer, Long, CreateTrainerDTO, UpdateTrainerDTO, TrainerResponseDTO> {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        super(trainerService);
        this.trainerService = trainerService;
    }
}
