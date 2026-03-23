package com.physioflow.application.service;

import com.physioflow.domain.model.enumtype.NotificationType;

import org.springframework.stereotype.Service;

@Service
public class NotificationMessageBuilder {

    public String buildSubject(
            NotificationType type) {

        return switch (type) {

            case SESSION_CREATED ->
                "Sesión programada";

            case SESSION_REMINDER ->
                "Recordatorio de sesión";

            case SESSION_COMPLETED ->
                "Sesión completada";

            case PAYMENT_CREATED ->
                "Pago registrado";

            case TREATMENT_PLAN_CREATED ->
                "Plan de tratamiento creado";

        };
    }

    public String buildBody(
            NotificationType type,
            String patient,
            String date,
            String time,
            String extra) {

        return switch (type) {

            case SESSION_CREATED ->
                "Hola " + patient +
                        "\n\nTu sesión ha sido programada\n" +
                        "Fecha: " + date +
                        "\nHora: " + time +
                        "\nTipo: " + extra +
                        "\n\nPhysioFlow";

            case SESSION_REMINDER ->
                "Recordatorio de sesión\n" +
                        date + " " + time;

            case SESSION_COMPLETED ->
                "Tu sesión fue completada";

            case PAYMENT_CREATED ->
                "Se registró tu pago";

            case TREATMENT_PLAN_CREATED ->
                "Tu plan de tratamiento está listo";

        };
    }
}