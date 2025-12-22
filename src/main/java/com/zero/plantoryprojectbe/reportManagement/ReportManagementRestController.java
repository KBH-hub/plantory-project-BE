package com.zero.plantoryprojectbe.reportManagement;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.dto.ImageTargetType;
import com.zero.plantoryprojectbe.image.service.ImageService;
import com.zero.plantoryprojectbe.reportManagement.dto.*;
import com.zero.plantoryprojectbe.reportManagement.service.ReportManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reportManagement")
public class ReportManagementRestController {
//    softDelete
private final ReportManagementService reportManagementService;
private final ImageService imageService;

        @GetMapping("/list")
        public ResponseEntity<ReportManagementPageResponse>  getReportList(ReportManagementSearchRequest request) {
            return ResponseEntity.ok().body(reportManagementService.getReporManagmentList(request));
        }

    @PutMapping("/softDelete")
    public ResponseEntity<?> softDelete(@RequestBody IdListRequest request) {

        int updated = reportManagementService.deleteReporManagmentList(request.getIds());

        if (updated > 0) {
            return ResponseEntity.ok("삭제 성공: " + updated + "건");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("삭제 실패: 삭제 대상 없음");
        }
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<ReportManagementDetailResponse> getReportDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(reportManagementService.getReportDetail(id));
    }

    @GetMapping("/images")
    public List<ImageDTO> getImagesByTarget(
            @RequestParam("targetType") ImageTargetType targetType,
            @RequestParam("targetId") Long targetId
    ) {
        return imageService.getImagesByTarget(targetType, targetId);
    }

    @PostMapping("/{reportId}")
    public ResponseEntity<?> processReport(
            @PathVariable Long reportId,
            @RequestBody ReportProcessRequest request
    ) {

        reportManagementService.processReport(
                reportId,
                request.getTargetMemberId(),
                request.getAdminMemo(),
                request.getStopDays()
        );

        return ResponseEntity.ok("처리 완료");
    }
}
