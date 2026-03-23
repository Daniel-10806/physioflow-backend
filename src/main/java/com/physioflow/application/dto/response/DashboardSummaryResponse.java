package com.physioflow.application.dto.response;

public class DashboardSummaryResponse {

        private long sessionsToday;
        private long patientsActive;
        private long sessionsCompletedToday;
        private long sessionsPendingToday;

        public DashboardSummaryResponse(
                        long sessionsToday,
                        long patientsActive,
                        long sessionsCompletedToday,
                        long sessionsPendingToday) {

                this.sessionsToday = sessionsToday;
                this.patientsActive = patientsActive;
                this.sessionsCompletedToday = sessionsCompletedToday;
                this.sessionsPendingToday = sessionsPendingToday;
        }

        public long getSessionsToday() {
                return sessionsToday;
        }

        public long getPatientsActive() {
                return patientsActive;
        }

        public long getSessionsCompletedToday() {
                return sessionsCompletedToday;
        }

        public long getSessionsPendingToday() {
                return sessionsPendingToday;
        }
}