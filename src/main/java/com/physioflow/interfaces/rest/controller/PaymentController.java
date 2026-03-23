package com.physioflow.interfaces.rest.controller;

import com.physioflow.infrastructure.persistence.entity.PatientJpaEntity;
import com.physioflow.infrastructure.persistence.entity.PaymentJpaEntity;
import com.physioflow.infrastructure.persistence.repository.PaymentJpaRepository;
import com.physioflow.infrastructure.persistence.repository.PatientJpaRepository;
import com.physioflow.application.dto.response.PaymentResponse;
import com.physioflow.application.service.PaymentService;
import com.physioflow.application.service.RoleService;
import com.physioflow.domain.model.enumtype.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import com.physioflow.infrastructure.persistence.repository.TherapistJpaRepository;
import com.physioflow.infrastructure.persistence.entity.TherapistJpaEntity;
import org.springframework.web.bind.annotation.*;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.physioflow.infrastructure.persistence.repository.SessionJpaRepository;
import com.physioflow.infrastructure.persistence.repository.SessionRecordJpaRepository;
import com.physioflow.infrastructure.persistence.entity.SessionJpaEntity;
import com.physioflow.infrastructure.persistence.entity.SessionRecordJpaEntity;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Div;
import java.time.format.DateTimeFormatter;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.geom.Rectangle;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

        private final PaymentJpaRepository paymentRepository;
        private final PatientJpaRepository patientRepository;
        private final TherapistJpaRepository therapistRepository;
        private final SessionJpaRepository sessionRepository;
        private final SessionRecordJpaRepository recordRepository;
        private final PaymentService paymentService;
        private final RoleService roleService;

        public PaymentController(
                        PaymentJpaRepository paymentRepository,
                        PatientJpaRepository patientRepository,
                        TherapistJpaRepository therapistRepository,
                        SessionJpaRepository sessionRepository,
                        SessionRecordJpaRepository recordRepository,
                        PaymentService paymentService,
                        RoleService roleService) {

                this.paymentRepository = paymentRepository;
                this.patientRepository = patientRepository;
                this.therapistRepository = therapistRepository;
                this.sessionRepository = sessionRepository;
                this.recordRepository = recordRepository;
                this.paymentService = paymentService;
                this.roleService = roleService;
        }

        private Cell cellHeader(String text) {

                return new Cell()
                                .add(new Paragraph(text).setBold())
                                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                .setPadding(5);

        }

        private Cell cellValue(String text) {

                return new Cell()
                                .add(new Paragraph(text))
                                .setPadding(5);

        }

        @GetMapping
        public List<PaymentResponse> getPayments() {

                if (!roleService.hasRole(Role.ROLE_THERAPIST)
                                && !roleService.hasRole(Role.ROLE_ADMIN)) {

                        throw new RuntimeException("No autorizado");
                }

                UUID therapistId = (UUID) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

                var payments = paymentRepository.findByTherapistId(therapistId);

                return payments.stream().map(p -> {

                        var patient = patientRepository
                                        .findById(p.getPatientId())
                                        .orElse(null);

                        String name = patient != null
                                        ? patient.getFullName()
                                        : "Paciente";

                        return new PaymentResponse(

                                        p.getId(),

                                        p.getPatientId(),

                                        name,

                                        p.getAmount(),

                                        p.getMethod(),

                                        p.getStatus(),

                                        p.getCreatedAt()

                        );

                }).toList();

        }

        @PostMapping
        public void createPayment(@RequestBody PaymentJpaEntity payment) {

                if (!roleService.hasRole(Role.ROLE_THERAPIST)
                                && !roleService.hasRole(Role.ROLE_ADMIN)) {

                        throw new RuntimeException("No autorizado");
                }

                payment.setId(UUID.randomUUID());
                payment.setCreatedAt(LocalDateTime.now());

                UUID therapistId = (UUID) SecurityContextHolder
                                .getContext()
                                .getAuthentication()
                                .getPrincipal();

                paymentService.createPayment(
                                payment,
                                therapistId);

                payment.setTherapistId(therapistId);

                // ===== tipo =====

                if (payment.getType() == null)
                        payment.setType("BOLETA");

                String serie;

                if (payment.getType().equals("FACTURA")) {
                        serie = "F001";
                } else {
                        serie = "B001";
                }

                payment.setSerie(serie);

                Long last = paymentRepository.getLastNumberBySerie(serie);

                payment.setNumber(last + 1);

                // ===== IGV =====

                double subtotal = payment.getAmount() / 1.18;
                double igv = payment.getAmount() - subtotal;

                payment.setSubtotal(subtotal);
                payment.setIgv(igv);

                paymentRepository.save(payment);
        }

        @GetMapping("/patients")
        public List<PatientJpaEntity> getPatients() {
                return patientRepository.findAll();
        }

        @GetMapping("/{id}/pdf")
        public ResponseEntity<byte[]> getPdf(@PathVariable UUID id) {

                if (!roleService.hasRole(Role.ROLE_THERAPIST)
                                && !roleService.hasRole(Role.ROLE_ADMIN)) {

                        throw new RuntimeException("No autorizado");
                }

                var payment = paymentRepository.findById(id).orElseThrow();

                var patient = patientRepository
                                .findById(payment.getPatientId())
                                .orElse(null);

                String name = patient != null
                                ? patient.getFullName()
                                : "Paciente";

                UUID therapistId = payment.getTherapistId();

                TherapistJpaEntity therapistEntity = therapistRepository.findById(therapistId)
                                .orElse(null);

                String therapistName = therapistEntity != null
                                ? therapistEntity.getFullName()
                                : "Fisioterapeuta";

                String therapistGender = therapistEntity != null
                                ? therapistEntity.getGender()
                                : "M";

                // ================= SESSION =================

                SessionJpaEntity session = null;

                if (payment.getSessionId() != null) {
                        session = sessionRepository
                                        .findById(payment.getSessionId())
                                        .orElse(null);
                }

                // ================= RECORD =================

                SessionRecordJpaEntity record = null;

                if (session != null) {

                        record = recordRepository
                                        .findBySessionId(session.getId())
                                        .stream()
                                        .findFirst()
                                        .orElse(null);
                }

                // ================= VARIABLES REALES =================

                String observaciones = record != null && record.getProgressNotes() != null
                                ? record.getProgressNotes()
                                : "-";

                String diagnostico = record != null && record.getAssessment() != null
                                ? record.getAssessment()
                                : "-";

                String tratamiento = record != null && record.getTreatment() != null
                                ? record.getTreatment()
                                : "-";

                String duracion = session != null
                                ? session.getDurationMinutes() + " minutos"
                                : "-";

                String area = session != null
                                ? session.getType()
                                : "-";

                String proximaCita = session != null
                                ? session.getSessionDate() + " " + session.getSessionTime()
                                : "-";

                try {

                        double subtotal = payment.getSubtotal() != null
                                        ? payment.getSubtotal()
                                        : payment.getAmount() / 1.18;

                        double igv = payment.getIgv() != null
                                        ? payment.getIgv()
                                        : payment.getAmount() - subtotal;

                        double total = payment.getAmount() != null
                                        ? payment.getAmount()
                                        : 0;

                        ByteArrayOutputStream out = new ByteArrayOutputStream();

                        PdfWriter writer = new PdfWriter(out);
                        PdfDocument pdf = new PdfDocument(writer);
                        Document document = new Document(
                                        pdf,
                                        com.itextpdf.kernel.geom.PageSize.A4,
                                        false);

                        document.setMargins(15, 15, 15, 15);

                        Paragraph watermark = new Paragraph("PHYSIOFLOW")
                                        .setFontSize(35)
                                        .setFontColor(new com.itextpdf.kernel.colors.DeviceRgb(230, 230, 230))
                                        .setTextAlignment(TextAlignment.CENTER);

                        document.add(watermark);
                        watermark.setMarginBottom(4);

                        document.add(new Paragraph(""));

                        var blue = new com.itextpdf.kernel.colors.DeviceRgb(0, 110, 150);
                        var green = new com.itextpdf.kernel.colors.DeviceRgb(0, 160, 120);
                        var gray = new com.itextpdf.kernel.colors.DeviceRgb(230, 230, 230);

                        /// ================= BANDA =================

                        document.add(
                                        new Div()
                                                        .setHeight(15)
                                                        .setBackgroundColor(blue));

                        /// ================= HEADER =================

                        Table header = new Table(
                                        UnitValue.createPercentArray(new float[] { 6, 4 })).useAllAvailableWidth();

                        Cell clinic = new Cell().setBorder(null);

                        clinic.add(
                                        new Paragraph("PHYSIOFLOW CLINIC")
                                                        .setBold()
                                                        .setFontSize(18));

                        clinic.add(
                                        new Paragraph(
                                                        "Av. Principal 123 - Lima\n" +
                                                                        "Email: contacto@physioflow.com\n" +
                                                                        "Web: www.physioflow.com")
                                                        .setFontSize(9));

                        clinic.add(
                                        new Paragraph(
                                                        "Centro de fisioterapia y rehabilitación\n" +
                                                                        "RUC 00000000000\n" +
                                                                        "Lima Perú\n" +
                                                                        "Tel 999999999")
                                                        .setFontSize(9));

                        header.addCell(clinic);
                        clinic.setMarginBottom(4);

                        String serie = payment.getSerie() != null ? payment.getSerie() : "B001";
                        Long number = payment.getNumber() != null ? payment.getNumber() : 0L;

                        String serieNumber = serie + "-" + String.format("%08d", number);

                        Cell serieBox = new Cell()
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setBorder(new SolidBorder(blue, 2));

                        serieBox.add(
                                        new Paragraph(
                                                        payment.getType() != null ? payment.getType() : "BOLETA")
                                                        .setBold());

                        serieBox.add(
                                        new Paragraph(serieNumber));

                        header.addCell(serieBox);

                        document.add(header);
                        header.setMarginBottom(5);

                        /// ================= LINE =================

                        document.add(
                                        new Div()
                                                        .setHeight(2)
                                                        .setBackgroundColor(blue));

                        /// ================= FECHA =================

                        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                        String date = payment.getCreatedAt().format(fmt);

                        /// ================= INFO =================

                        Table info = new Table(
                                        UnitValue.createPercentArray(
                                                        new float[] { 3, 7 }))
                                        .useAllAvailableWidth();

                        info.setBorder(new SolidBorder(blue, 1));

                        info.setMarginBottom(10);

                        info.addCell(
                                        new Cell()
                                                        .add(new Paragraph("Paciente").setBold())
                                                        .setBackgroundColor(gray));

                        info.addCell(name);

                        info.addCell(
                                        new Cell()
                                                        .add(new Paragraph("Fecha").setBold())
                                                        .setBackgroundColor(gray));

                        info.addCell(date);

                        info.addCell(
                                        new Cell()
                                                        .add(new Paragraph("Metodo").setBold())
                                                        .setBackgroundColor(gray));

                        info.addCell(payment.getMethod());

                        document.add(info);
                        info.setMarginBottom(4);

                        /// ================= DETALLE =================

                        Table detail = new Table(
                                        UnitValue.createPercentArray(
                                                        new float[] { 7, 1, 2 }))
                                        .useAllAvailableWidth();

                        detail.addHeaderCell(
                                        new Cell()
                                                        .add(new Paragraph("Descripción").setBold())
                                                        .setBackgroundColor(blue)
                                                        .setFontColor(ColorConstants.WHITE));

                        detail.addHeaderCell(
                                        new Cell()
                                                        .add(new Paragraph("Cant").setBold())
                                                        .setBackgroundColor(blue)
                                                        .setFontColor(ColorConstants.WHITE));

                        detail.addHeaderCell(
                                        new Cell()
                                                        .add(new Paragraph("Importe").setBold())
                                                        .setBackgroundColor(blue)
                                                        .setFontColor(ColorConstants.WHITE));

                        detail.addCell("Sesión de fisioterapia");
                        detail.addCell("1");
                        detail.addCell(
                                        String.format("S/ %.2f", subtotal));

                        document.add(detail);
                        detail.setMarginBottom(4);

                        /// ================= INFO TERAPEUTA =================

                        Table therapist = new Table(
                                        UnitValue.createPercentArray(new float[] { 3, 7 }))
                                        .useAllAvailableWidth();

                        therapist.addCell(
                                        new Cell()
                                                        .add(new Paragraph("Fisioterapeuta").setBold())
                                                        .setBackgroundColor(gray));

                        String prefix = "F".equalsIgnoreCase(therapistGender)
                                        ? "Lic. "
                                        : "Lic. ";

                        therapist.addCell(prefix + therapistName);

                        therapist.addCell(
                                        new Cell()
                                                        .add(new Paragraph("ID Profesional").setBold())
                                                        .setBackgroundColor(gray));

                        therapist.addCell(therapistId.toString().substring(0, 8));

                        document.add(therapist);
                        therapist.setMarginBottom(4);

                        /// ================= OBSERVACIONES =================

                        Table obs = new Table(1)
                                        .useAllAvailableWidth();

                        obs.addCell(
                                        new Cell()
                                                        .add(new Paragraph("Observaciones").setBold())
                                                        .setBackgroundColor(gray));

                        obs.addCell(
                                        new Cell()
                                                        .add(new Paragraph(observaciones))
                                                        .setHeight(70));

                        document.add(obs);
                        obs.setMarginBottom(4);

                        /// ================= DATOS CLINICOS =================

                        Table medical = new Table(
                                        UnitValue.createPercentArray(new float[] { 3, 7 }))
                                        .useAllAvailableWidth();

                        medical.addCell(diagnostico);
                        medical.addCell(tratamiento);
                        medical.addCell(duracion);

                        document.add(medical);
                        medical.setMarginBottom(4);

                        /// ================= PROXIMA CITA =================

                        Table next = new Table(
                                        UnitValue.createPercentArray(new float[] { 3, 7 }))
                                        .useAllAvailableWidth();

                        next.addCell(proximaCita);
                        next.addCell(area);

                        document.add(next);
                        next.setMarginBottom(4);

                        /// ================= TOTALES =================

                        Table totals = new Table(
                                        UnitValue.createPercentArray(
                                                        new float[] { 6, 4 }))
                                        .useAllAvailableWidth();

                        totals.addCell(new Cell().setBorder(null));

                        Table box = new Table(
                                        UnitValue.createPercentArray(
                                                        new float[] { 5, 5 }))
                                        .useAllAvailableWidth();

                        box.setBorder(
                                        new SolidBorder(blue, 1));

                        box.addCell(
                                        new Cell().add(
                                                        new Paragraph("Subtotal")));
                        box.addCell(
                                        String.format("S/ %.2f", subtotal));

                        box.addCell("IGV");
                        box.addCell(
                                        String.format("S/ %.2f", igv));

                        box.addCell(
                                        new Cell()
                                                        .add(new Paragraph("TOTAL").setBold())
                                                        .setBackgroundColor(green)
                                                        .setFontColor(ColorConstants.WHITE));

                        box.addCell(
                                        new Cell()
                                                        .add(
                                                                        new Paragraph(
                                                                                        String.format("S/ %.2f", total))
                                                                                        .setBold())
                                                        .setBackgroundColor(green)
                                                        .setFontColor(ColorConstants.WHITE));

                        totals.addCell(
                                        new Cell().add(box));

                        document.add(totals);
                        totals.setMarginBottom(4);

                        /// ================= QR / FIRMA / SELLO =================

                        Table bottom = new Table(
                                        UnitValue.createPercentArray(
                                                        new float[] { 3, 4, 3 }))
                                        .useAllAvailableWidth();

                        String qrText = serieNumber + "|" +
                                        name + "|" +
                                        total;

                        BarcodeQRCode qrCode = new BarcodeQRCode(qrText);

                        Image qr = new Image(
                                        qrCode.createFormXObject(pdf));

                        qr.scaleToFit(65, 65);

                        bottom.addCell(
                                        new Cell()
                                                        .add(qr)
                                                        .setBorder(null));

                        Cell sign = new Cell()
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setBorder(null);

                        sign.add(
                                        new Paragraph("______________________"));
                        sign.add(
                                        new Paragraph("Firma autorizada")
                                                        .setFontSize(9));

                        bottom.addCell(sign);

                        Cell stamp = new Cell()
                                        .setTextAlignment(TextAlignment.CENTER)
                                        .setBorder(null);

                        stamp.add(
                                        new Paragraph("CLÍNICA PHYSIOFLOW")
                                                        .setBold());

                        stamp.add(
                                        new Paragraph("Documento válido")
                                                        .setFontSize(9));

                        bottom.addCell(stamp);

                        bottom.addCell(
                                        new Cell()
                                                        .add(new Paragraph("Código: " + serieNumber))
                                                        .setBorder(null));

                        document.add(bottom);
                        bottom.setMarginBottom(4);

                        /// ================= NOTA LEGAL =================

                        Paragraph legal = new Paragraph(
                                        "Documento emitido por PHYSIOFLOW CLINIC. " +
                                                        "Este comprobante acredita el pago por servicios médicos. " +
                                                        "No sustituye historia clínica. " +
                                                        "Para validación comuníquese al 999999999. " +
                                                        "RUC 00000000000 - Lima Perú.")
                                        .setFontSize(8)
                                        .setTextAlignment(TextAlignment.CENTER);

                        document.add(legal);

                        /// ================= FOOTER =================

                        document.add(
                                        new Paragraph(
                                                        "PhysioFlow Clinical System • Professional Medical Software • v1.0")
                                                        .setTextAlignment(TextAlignment.CENTER)
                                                        .setFontSize(8));

                        document.close();

                        return ResponseEntity.ok()
                                        .header(
                                                        HttpHeaders.CONTENT_DISPOSITION,
                                                        "attachment; filename=boleta.pdf")
                                        .contentType(MediaType.APPLICATION_PDF)
                                        .body(out.toByteArray());

                } catch (Exception e) {

                        throw new RuntimeException(e);

                }
        }
}