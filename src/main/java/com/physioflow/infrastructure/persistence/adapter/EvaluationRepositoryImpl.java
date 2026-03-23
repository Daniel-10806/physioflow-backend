package com.physioflow.infrastructure.persistence.adapter;

import com.physioflow.domain.model.entity.Evaluation;
import com.physioflow.domain.model.enumtype.InjuryPhase;
import com.physioflow.domain.model.enumtype.InjuryType;
import com.physioflow.domain.model.valueobject.PainScale;
import com.physioflow.domain.model.valueobject.RangeOfMotion;
import com.physioflow.domain.repository.EvaluationRepository;
import com.physioflow.infrastructure.persistence.entity.EvaluationJpaEntity;
import com.physioflow.infrastructure.persistence.entity.PatientJpaEntity;
import com.physioflow.infrastructure.persistence.repository.EvaluationJpaRepository;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public class EvaluationRepositoryImpl implements EvaluationRepository {

        private final EvaluationJpaRepository jpaRepository;
        private final PatientJpaRepository patientJpaRepository;

        public EvaluationRepositoryImpl(
                        EvaluationJpaRepository jpaRepository,
                        PatientJpaRepository patientJpaRepository) {

                this.jpaRepository = jpaRepository;
                this.patientJpaRepository = patientJpaRepository;
        }

        @Override
        public void save(Evaluation evaluation) {

                PatientJpaEntity patientRef = patientJpaRepository
                                .getReferenceById(evaluation.getPatientId());

                EvaluationJpaEntity entity = new EvaluationJpaEntity(
                                evaluation.getId(),
                                patientRef,
                                evaluation.getInjuryType().name(),
                                evaluation.getPhase().name(),
                                evaluation.getPainScale().getValue(),
                                evaluation.getRangeOfMotion().getValue(),
                                evaluation.getEvaluationDate());

                jpaRepository.save(entity);
        }

        @Override
        public Optional<Evaluation> findById(UUID id) {

                return jpaRepository.findById(id)
                                .map(entity -> new Evaluation(
                                                entity.getId(),
                                                entity.getPatient().getId(),
                                                InjuryType.valueOf(entity.getInjuryType()),
                                                InjuryPhase.valueOf(entity.getInjuryPhase()),
                                                new PainScale(entity.getPainScale()),
                                                new RangeOfMotion(entity.getRangeOfMotion()),
                                                entity.getEvaluationDate()));
        }

        @Override
        public Optional<Evaluation> findLatestByPatientId(UUID patientId) {

                return jpaRepository
                                .findTopByPatient_IdOrderByEvaluationDateDesc(patientId)
                                .map(entity -> new Evaluation(
                                                entity.getId(),
                                                entity.getPatient().getId(),
                                                InjuryType.valueOf(entity.getInjuryType()),
                                                InjuryPhase.valueOf(entity.getInjuryPhase()),
                                                new PainScale(entity.getPainScale()),
                                                new RangeOfMotion(entity.getRangeOfMotion()),
                                                entity.getEvaluationDate()));
        }

        @Override
        public List<Evaluation> findByPatientId(UUID patientId) {

                return jpaRepository.findByPatientId(patientId)
                                .stream()
                                .map(entity -> new Evaluation(
                                                entity.getId(),
                                                entity.getPatient().getId(),
                                                InjuryType.valueOf(entity.getInjuryType()),
                                                InjuryPhase.valueOf(entity.getInjuryPhase()),
                                                new PainScale(entity.getPainScale()),
                                                new RangeOfMotion(entity.getRangeOfMotion()),
                                                entity.getEvaluationDate()))
                                .toList();
        }
}