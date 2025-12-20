package com.zero.plantoryprojectbe.myPlant;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.myPlant.dto.MyPlantRequest;
import com.zero.plantoryprojectbe.myPlant.dto.MyPlantResponse;
import com.zero.plantoryprojectbe.myPlant.service.MyPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/myPlant")
@RequiredArgsConstructor
public class MyplantRestController {

    private final MyPlantService myPlantService;

    @GetMapping("/list")
    public List<MyPlantResponse> getMyPlantList(@AuthenticationPrincipal MemberDetail memberDetail, @RequestParam String name, @RequestParam Integer limit, @RequestParam Integer offset) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        return myPlantService.getMyPlantList(memberId, name, limit, offset);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addMyPlant(@ModelAttribute MyPlantRequest request, @RequestParam(value = "file", required = false) MultipartFile file, @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        request.setMemberId(memberId);
        if (myPlantService.registerMyPlant(request, file, memberId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "myPlant regist fail"));
        return ResponseEntity.ok().body(Map.of("message", "myPlant regist success"));
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> updateMyPlant(@ModelAttribute MyPlantRequest request, @RequestParam(name = "delFile", required = false) Long delFile, @RequestParam(name = "file", required = false) MultipartFile file, @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        request.setMemberId(memberId);
        if (myPlantService.updateMyPlant(request, delFile, file, memberId) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "myPlant regist fail"));
        return ResponseEntity.ok().body(Map.of("message", "myPlant regist success"));
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> removeMyPlant(@RequestParam Long myplantId, @RequestParam(required = false) Long delFile) throws IOException {
        if (myPlantService.removePlant(myplantId, delFile) == 0)
            return ResponseEntity.status(400).body(Map.of("message", "myPlant regist fail"));
        return ResponseEntity.ok().body(Map.of("message", "myPlant regist success"));
    }

}
