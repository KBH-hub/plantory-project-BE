package com.zero.plantoryprojectbe.plantingCalendar;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.plantingCalendar.dto.DiaryRequest;
import com.zero.plantoryprojectbe.plantingCalendar.dto.DiaryResponse;
import com.zero.plantoryprojectbe.plantingCalendar.dto.MyPlantDiaryResponse;
import com.zero.plantoryprojectbe.plantingCalendar.dto.PlantingCalendarResponse;
import com.zero.plantoryprojectbe.plantingCalendar.service.PlantingCalenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/plantingCalender")
@RequiredArgsConstructor
public class PlantingCalenderRestController {

    private final PlantingCalenderService plantingCalenderService;

    @GetMapping("/diary")
    public List<PlantingCalendarResponse> getPlantingDiaryCalendar(@AuthenticationPrincipal MemberDetail memberDetail,
                                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        return plantingCalenderService.getDiaryCalendar(memberId, startDate, endDate);
    }

    @GetMapping("/watering")
    public List<PlantingCalendarResponse> getPlantingWateringCalendar(@AuthenticationPrincipal MemberDetail memberDetail,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        return plantingCalenderService.getWateringCalendar(memberId, startDate, endDate);
    }

    @PutMapping("/watering")
    public ResponseEntity<Map<String, String>> updateWateringFlag(@RequestParam Long wateringId) {
        int result = plantingCalenderService.updatePlantWateringCheck(wateringId);
        if (result == 0) {
            return ResponseEntity.status(400).body(Map.of("message", "check fail"));
        }
        return ResponseEntity.status(200).body(Map.of("message", "check success"));
    }

    @DeleteMapping("/watering")
    public ResponseEntity<Map<String, String>> deleteWatering(@RequestParam Long myplantId, @AuthenticationPrincipal MemberDetail memberDetail) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        int result = plantingCalenderService.removePlantWatering(myplantId, memberId);
        if(result == 1) {
            return ResponseEntity.status(200).body(Map.of("message", "watering delete success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "watering check fail"));
    }

    @GetMapping("/diaryInfo/{diaryId}")
    public ResponseEntity<?> getDiaryUpdateModalInfo(@PathVariable Long diaryId) {

        DiaryResponse diary = plantingCalenderService.findDiaryUpdateInfo(diaryId);
        if (diary == null) {
            return ResponseEntity.status(400)
                    .body(Map.of(
                            "message", "diary not found",
                            "diaryId", diaryId
                    ));
        }

        List<ImageDTO> images = plantingCalenderService.findDiaryUpdateImageInfo(diaryId);

        return ResponseEntity.ok(
                Map.of(
                        "diary", diary,
                        "images", images
                )
        );
    }

    @DeleteMapping("/diary/{diaryId}")
    public ResponseEntity<Map<String, String>> deleteDiary(@PathVariable Long diaryId) {
        if (plantingCalenderService.removeDiary(diaryId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "diary delete fail"));
        return ResponseEntity.status(200).body(Map.of("message", "diary delete success"));
    }

    @PostMapping("/diary")
    public ResponseEntity<Map<String, String>> createDiary(@ModelAttribute DiaryRequest request,
                                                           @RequestPart(value = "files", required = false) List<MultipartFile> files,
                                                           @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        if (plantingCalenderService.registerDiary(request, files, memberId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "diary register fail"));
        return ResponseEntity.status(200).body(Map.of("message", "diary create success"));
    }

    @GetMapping("/diary/myplant")
    public List<MyPlantDiaryResponse> getMyPlant(@AuthenticationPrincipal MemberDetail memberDetail){
        Long memberId = memberDetail.memberResponse().getMemberId();
        return plantingCalenderService.getMyPlant(memberId);
    }


}
