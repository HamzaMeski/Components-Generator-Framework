package com.example.backend.components.trainer.controller;

import com.example.backend.EntityComponentsProvider.controller.Controller;
import com.example.backend.components.trainer.dto.response.TrainerDetailResponseDTO;
import com.example.backend.components.trainer.dto.response.TrainerResponseDTO;
import com.example.backend.components.trainer.service.TrainerService;
import com.example.backend.entities.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController extends Controller<Trainer, Long> {
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