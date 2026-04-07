package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.models.Earning;
import com.nikhil.educonnect.repositories.EarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;

    // ─── ADD EARNING RECORD ───────────────────────────────
    public EarningResponse addEarning(EarningRequest request) {

        Earning earning = new Earning(
                request.getTeacherId(),
                request.getTeacherName(),
                request.getStudentId(),
                request.getStudentName(),
                request.getAmount(),
                request.getMonth(),
                request.getYear()
        );

        Earning saved = earningRepository.save(earning);
        return toResponse(saved);
    }

    // ─── GET ALL EARNINGS FOR TEACHER ─────────────────────
    public List<EarningResponse> getTeacherEarnings(
            String teacherId) {
        return earningRepository
                .findByTeacherId(teacherId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── GET MONTHLY SUMMARY ──────────────────────────────
    public EarningsSummary getMonthlySummary(
            String teacherId, int month, int year) {

        List<Earning> records = earningRepository
                .findByTeacherIdAndMonthAndYear(teacherId, month, year);

        int totalEarned = records.stream()
                .filter(e -> e.getStatus().equals("PAID"))
                .mapToInt(Earning::getAmount)
                .sum();

        int pending = records.stream()
                .filter(e -> e.getStatus().equals("PENDING"))
                .mapToInt(Earning::getAmount)
                .sum();

        int overdue = records.stream()
                .filter(e -> e.getStatus().equals("OVERDUE"))
                .mapToInt(Earning::getAmount)
                .sum();

        EarningsSummary summary = new EarningsSummary();
        summary.setTeacherId(teacherId);
        summary.setMonth(month);
        summary.setYear(year);
        summary.setTotalEarned(totalEarned);
        summary.setPendingAmount(pending);
        summary.setOverdueAmount(overdue);
        summary.setTotalStudents(records.size());

        return summary;
    }

    // ─── MARK AS PAID ─────────────────────────────────────
    public EarningResponse markAsPaid(
            String earningId, PaymentRequest request) {

        Earning earning = earningRepository
                .findById(earningId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Earning", earningId
                        )
                );

        earning.setStatus("PAID");
        earning.setPaidAt(LocalDateTime.now());
        earning.setPaymentMethod(request.getPaymentMethod());

        Earning updated = earningRepository.save(earning);
        return toResponse(updated);
    }

    // ─── CONVERTER ────────────────────────────────────────
    private EarningResponse toResponse(Earning e) {
        EarningResponse r = new EarningResponse();
        r.setId(e.getId());
        r.setTeacherId(e.getTeacherId());
        r.setTeacherName(e.getTeacherName());
        r.setStudentId(e.getStudentId());
        r.setStudentName(e.getStudentName());
        r.setAmount(e.getAmount());
        r.setMonth(e.getMonth());
        r.setYear(e.getYear());
        r.setStatus(e.getStatus());
        r.setPaymentMethod(e.getPaymentMethod());
        if (e.getPaidAt() != null)
            r.setPaidAt(e.getPaidAt().toString());
        if (e.getCreatedAt() != null)
            r.setCreatedAt(e.getCreatedAt().toString());
        return r;
    }
}