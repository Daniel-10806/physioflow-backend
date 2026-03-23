package com.physioflow.application.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class SessionTodayResponse {

        private UUID id;
        private UUID patientId;
        private String patientName;

        private LocalDate sessionDate;
        private LocalTime sessionTime;

        private String type;
        private String status;

        private int durationMinutes;

        private String therapistName;

        private String area;

        private boolean paid;

        public SessionTodayResponse(
                        UUID id,
                        UUID patientId,
                        String patientName,
                        LocalDate sessionDate,
                        LocalTime sessionTime,
                        String type,
                        String status,
                        int durationMinutes,
                        String therapistName,
                        String area,
                        boolean paid) {

                this.id = id;
                this.patientId = patientId;
                this.patientName = patientName;
                this.sessionDate = sessionDate;
                this.sessionTime = sessionTime;
                this.type = type;
                this.status = status;
                this.durationMinutes = durationMinutes;
                this.therapistName = therapistName;
                this.area = area;
                this.paid = paid;
        }

        public UUID getId() {
                return id;
        }

        public boolean isPaid() {
                return paid;
        }

        public UUID getPatientId() {
                return patientId;
        }

        public String getPatientName() {
                return patientName;
        }

        public LocalDate getSessionDate() {
                return sessionDate;
        }

        public LocalTime getSessionTime() {
                return sessionTime;
        }

        public String getType() {
                return type;
        }

        public String getStatus() {
                return status;
        }

        public int getDurationMinutes() {
                return durationMinutes;
        }

        public String getTherapistName() {
                return therapistName;
        }

        public String getArea() {
                return area;
        }
}