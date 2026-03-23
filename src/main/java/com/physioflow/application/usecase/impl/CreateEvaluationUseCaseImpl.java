package com.physioflow.application.usecase.impl;

import com.physioflow.application.dto.request.CreateEvaluationRequest;
import com.physioflow.application.usecase.CreateEvaluationUseCase;
import com.physioflow.domain.model.entity.Evaluation;
import com.physioflow.domain.model.valueobject.PainScale;
import com.physioflow.domain.model.valueobject.RangeOfMotion;
import com.physioflow.domain.repository.EvaluationRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateEvaluationUseCaseImpl
        implements CreateEvaluationUseCase {

    private final EvaluationRepository evaluationRepository;

    public CreateEvaluationUseCaseImpl(
            EvaluationRepository evaluationRepository) {

        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public UUID execute(
            UUID patientId,
            CreateEvaluationRequest request) {

        Evaluation evaluation = new Evaluation(

                UUID.randomUUID(),
                patientId,

                request.getInjuryType(),
                request.getPhase(),

                new PainScale(request.getPainScale()),
                new RangeOfMotion(request.getRangeOfMotion()),

                LocalDate.now());

        evaluationRepository.save(evaluation);

        return evaluation.getId();
    }
}