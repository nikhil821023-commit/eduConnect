package com.nikhil.educonnect.services;

import com.nikhil.educonnect.dto.*;
import com.nikhil.educonnect.exceptions.ResourceNotFoundException;
import com.nikhil.educonnect.models.Attendance;
import com.nikhil.educonnect.models.Teacher;
import com.nikhil.educonnect.repositories.AttendanceRepository;
import com.nikhil.educonnect.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    // ─── MARK ATTENDANCE ──────────────────────────────────
    public List<AttendanceResponse> markAttendance(
            AttendanceMarkRequest request) {

        // Verify teacher exists
        Teacher teacher = teacherRepository
                .findById(request.getTeacherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Teacher", request.getTeacherId()
                        )
                );

        LocalDate date = LocalDate.parse(request.getDate());
        LocalTime classTime = LocalTime.parse(request.getClassTime());

        List<AttendanceResponse> responses = new ArrayList<>();

        // Save attendance for each student
        for (AttendanceMarkRequest.StudentAttendance s
                : request.getStudents()) {

            Attendance attendance = new Attendance(
                    s.getStudentId(),
                    s.getStudentName(),
                    teacher.getId(),
                    teacher.getName(),
                    request.getSubject(),
                    date,
                    classTime,
                    s.getStatus().toUpperCase(),
                    s.getNotes()
            );

            Attendance saved = attendanceRepository
                    .save(attendance);
            responses.add(toResponse(saved));
        }

        return responses;
    }

    // ─── GET STUDENT ATTENDANCE ───────────────────────────
    public List<AttendanceResponse> getStudentAttendance(
            String studentId) {
        return attendanceRepository
                .findByStudentId(studentId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─── GET MONTHLY SUMMARY ──────────────────────────────
    public AttendanceSummary getMonthlySummary(
            String studentId, int month, int year) {

        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        List<Attendance> records = attendanceRepository
                .findByStudentIdAndDateBetween(studentId, start, end);

        int total = records.size();
        int present = (int) records.stream()
                .filter(a -> a.getStatus().equals("PRESENT"))
                .count();
        int absent = (int) records.stream()
                .filter(a -> a.getStatus().equals("ABSENT"))
                .count();
        int late = (int) records.stream()
                .filter(a -> a.getStatus().equals("LATE"))
                .count();

        double percentage = total > 0
                ? Math.round((present * 100.0 / total) * 10.0) / 10.0
                : 0.0;

        String studentName = records.isEmpty()
                ? "" : records.get(0).getStudentName();

        AttendanceSummary summary = new AttendanceSummary();
        summary.setStudentId(studentId);
        summary.setStudentName(studentName);
        summary.setTotalClasses(total);
        summary.setPresentCount(present);
        summary.setAbsentCount(absent);
        summary.setLateCount(late);
        summary.setAttendancePercentage(percentage);
        summary.setMonth(month);
        summary.setYear(year);

        return summary;
    }

    // ─── CONVERTER ────────────────────────────────────────
    private AttendanceResponse toResponse(Attendance a) {
        AttendanceResponse r = new AttendanceResponse();
        r.setId(a.getId());
        r.setStudentId(a.getStudentId());
        r.setStudentName(a.getStudentName());
        r.setTeacherId(a.getTeacherId());
        r.setTeacherName(a.getTeacherName());
        r.setSubject(a.getSubject());
        if (a.getDate() != null)
            r.setDate(a.getDate().toString());
        if (a.getClassTime() != null)
            r.setClassTime(a.getClassTime().toString());
        r.setStatus(a.getStatus());
        r.setNotes(a.getNotes());
        r.setParentNotified(a.isParentNotified());
        if (a.getMarkedAt() != null)
            r.setMarkedAt(a.getMarkedAt().toString());
        return r;
    }
}