package com.example.backend.components.trainer.service;

import com.example.backend.EntityComponentsProvider.service.EntityServiceImpl;
import com.example.backend.components.trainer.dto.request.CreateTrainerDTO;
import com.example.backend.components.trainer.dto.request.UpdateTrainerDTO;
import com.example.backend.components.trainer.dto.response.TrainerResponseDTO;
import com.example.backend.components.trainer.mapper.TrainerMapper;
import com.example.backend.components.trainer.repository.TrainerRepository;
import com.example.backend.entities.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class TrainerService extends EntityServiceImpl<Trainer, Long, CreateTrainerDTO, UpdateTrainerDTO, TrainerResponseDTO> {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public TrainerService(
            TrainerRepository trainerRepository,
            TrainerMapper trainerMapper,
            ApplicationContext applicationContext) {
        super(trainerRepository, trainerMapper, applicationContext);
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }
}
