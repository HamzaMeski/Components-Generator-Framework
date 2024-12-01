package com.example.backend.components.trainer.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.EntityComponentsProvider.controller.Controller;
import com.example.backend.components.trainer.dto.request.CreateTrainerDTO;
import com.example.backend.components.trainer.dto.request.UpdateTrainerDTO;
import com.example.backend.components.trainer.dto.response.TrainerDetailResponseDTO;
import com.example.backend.components.trainer.dto.response.TrainerResponseDTO;
import com.example.backend.components.trainer.service.TrainerService;
import com.example.backend.entities.Trainer;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController extends Controller<Trainer, Long, CreateTrainerDTO, UpdateTrainerDTO, TrainerResponseDTO> {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        super(trainerService);
        this.trainerService = trainerService;
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<TrainerDetailResponseDTO> getTrainerDetails(@PathVariable Long id) {
        return ResponseEntity.ok(trainerService.getDetailById(id));
    }

    @GetMapping("/details")
    public ResponseEntity<Page<TrainerDetailResponseDTO>> getAllTrainersWithDetails(Pageable pageable) {
        return ResponseEntity.ok(trainerService.getAllWithDetails(pageable));
    }

    @GetMapping("/experience/{years}")
    public ResponseEntity<List<TrainerResponseDTO>> getTrainersByExperience(@PathVariable int years) {
        return ResponseEntity.ok(trainerService.findByExperienceYears(years));
    }
}