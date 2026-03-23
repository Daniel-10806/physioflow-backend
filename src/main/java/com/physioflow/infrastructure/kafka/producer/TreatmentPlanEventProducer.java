package com.physioflow.infrastructure.kafka.producer;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TreatmentPlanEventProducer {

    public void publishTreatmentPlanGenerated(UUID patientId, UUID planId) {

        // Por ahora solo log simple
        System.out.println(
                "EVENT -> TreatmentPlan generated | patient: "
                        + patientId + " | plan: " + planId);

        // Aquí luego irá KafkaTemplate
    }
}