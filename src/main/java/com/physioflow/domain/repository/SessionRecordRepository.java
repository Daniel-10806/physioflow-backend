package com.physioflow.domain.repository;

import com.physioflow.domain.model.entity.SessionRecord;
import java.util.List;
import java.util.UUID;

public interface SessionRecordRepository {

    void save(SessionRecord record);

    List<SessionRecord> findByPatientId(UUID patientId);

}