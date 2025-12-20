package com.zero.plantoryprojectbe.weightManagement;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.weightManagement.dto.*;
import com.zero.plantoryprojectbe.weightManagement.service.WeightManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weightManagement")
@RequiredArgsConstructor
public class WeightManagementRestController {

    private final WeightManagementService weightManagementService;

    @GetMapping("/list")
    public List<WeightManagementResponse> getMemberWeightList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(required = false) String range
    ) {
        return weightManagementService.getMemberWeightList(keyword,limit,offset,range);
    }

    @PostMapping("/list")
    public void saveWeights(@AuthenticationPrincipal MemberDetail principal,
                            @RequestBody SaveWeightRequest saveWeightRequest) {

        Long memberId = principal.memberResponse().getMemberId();

        weightManagementService.saveWeights(memberId, saveWeightRequest);
    }

    @GetMapping("/latest")
    public WeightLoggingResponse getLatestWeights() {
        return weightManagementService.getLatestWeights();
    }

    @GetMapping("/careCounts")
    public ResponseEntity<?> getCareCounts() {
        return ResponseEntity.ok(weightManagementService.getPlantsNeedingAttention());
    }

    @GetMapping("/rate")
    public ResponseEntity<RateResponse> getRate() { return ResponseEntity.ok(weightManagementService.getRate());}

    @PostMapping("/rate")
    public void saveRate(@AuthenticationPrincipal MemberDetail principal,
                            @RequestBody SaveRateRequest saveRateRequest) {

        Long memberId = principal.memberResponse().getMemberId();

        weightManagementService.saveRate(memberId, saveRateRequest);
    }
}
