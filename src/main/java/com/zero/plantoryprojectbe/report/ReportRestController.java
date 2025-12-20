package com.zero.plantoryprojectbe.report;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.report.dto.NameListResponse;
import com.zero.plantoryprojectbe.report.dto.ReportRequest;
import com.zero.plantoryprojectbe.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportRestController {
    private final ReportService reportService;

    @GetMapping("/users")
    public ResponseEntity<List<NameListResponse>> searchUserByNickname(@RequestParam String nickname, @AuthenticationPrincipal MemberDetail memberDetail) {
        Long viewerId = memberDetail.memberResponse().getMemberId();
        return ResponseEntity.ok().body(reportService.getUsersIdByNickname(nickname, viewerId));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String,String>> registerReport(
            @ModelAttribute ReportRequest request,                 // 폼 필드 → 객체 바인딩
            @RequestParam("files") List<MultipartFile> files,       // key 이름: files
            @AuthenticationPrincipal MemberDetail memberDetail
    ) throws IOException {
        Long repoterId = memberDetail.memberResponse().getMemberId();
        request.setReporterId(repoterId);
        int saved = reportService.registerReport(request, files);
        int expected = (files == null ? 0 : files.size()) + 1;     // report 1건 + 이미지 N건 = 총 등록 건수
        if (saved == expected) {
            return ResponseEntity.ok(Map.of("message", "registry report success"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "register report fail"));
    }
}
