package com.example.backend.components.trainer.service;

import com.example.backend.EntityComponentsProvider.dto.request.CreateDTO;
import com.example.backend.EntityComponentsProvider.dto.request.UpdateDTO;
import com.example.backend.EntityComponentsProvider.dto.response.ResponseDTO;
import com.example.backend.EntityComponentsProvider.service.EntityServiceImpl;
import com.example.backend.components.trainer.dto.request.CreateTrainerDTO;
import com.example.backend.components.trainer.dto.request.UpdateTrainerDTO;
import com.example.backend.components.trainer.dto.response.TrainerDetailResponseDTO;
import com.example.backend.components.trainer.dto.response.TrainerResponseDTO;
import com.example.backend.components.trainer.mapper.TrainerMapper;
import com.example.backend.components.trainer.repository.TrainerRepository;
import com.example.backend.config.exception.DuplicateResourceException;
import com.example.backend.entities.Trainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Transactional
public class TrainerService extends EntityServiceImpl<Trainer, Long> {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;

    public TrainerService(TrainerRepository trainerRepository, TrainerMapper trainerMapper) {
        super(trainerRepository, trainerMapper);
        this.trainerRepository = trainerRepository;
        this.trainerMapper = trainerMapper;
    }

    @Override
    public ResponseDTO<Trainer, Long> create(CreateDTO<Trainer> request) {
        CreateTrainerDTO createTrainerDTO = (CreateTrainerDTO) request;
        if (trainerRepository.existsByEmail(createTrainerDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }
        return super.create(request);
    }

    @Override
    public void update(Long id, UpdateDTO<Trainer> request) {
        UpdateTrainerDTO updateTrainerDTO = (UpdateTrainerDTO) request;
        if (trainerRepository.existsByEmailAndIdNot(updateTrainerDTO.getEmail(), id)) {
            throw new DuplicateResourceException("Email already exists");
        }
        super.update(id, request);
    }

    // Additional custom methods
    @Transactional(readOnly = true)
    public TrainerDetailResponseDTO getDetailById(Long id) {
        Trainer trainer = checkEntityExistence(id);
        return trainerMapper.toDetailResponseDTO(trainer);
    }

    @Transactional(readOnly = true)
    public Page<TrainerDetailResponseDTO> getAllWithDetails(Pageable pageable) {
        return trainerRepository.findAll(pageable)
                .map(trainerMapper::toDetailResponseDTO);
    }

    @Transactional(readOnly = true)
    public List<TrainerResponseDTO> findByExperienceYears(int years) {
        LocalDate registrationDate = LocalDate.now().minusYears(years);
        return trainerRepository.findByRegistrationDateBefore(registrationDate)
                .stream()
                .map(trainerMapper::toResponseDTO)
                .toList();
    }
}