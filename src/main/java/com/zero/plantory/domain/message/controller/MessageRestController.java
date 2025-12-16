package com.zero.plantory.domain.message.controller;

import com.zero.plantory.domain.message.dto.MessageListResponse;
import com.zero.plantory.domain.message.dto.MessageRequest;
import com.zero.plantory.domain.message.dto.MessageResponse;
import com.zero.plantory.domain.message.dto.MessageSearchRequest;
import com.zero.plantory.domain.message.service.MessageService;
import com.zero.plantory.domain.message.service.MessageServiceImpl;
import com.zero.plantory.global.dto.BoxType;
import com.zero.plantory.global.dto.TargetType;
import com.zero.plantory.global.security.MemberDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/message")
public class MessageRestController {

    private final MessageService messageService;

    @GetMapping("/{boxType}")
    public ResponseEntity<List<MessageListResponse>> getMessageList(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @PathVariable BoxType boxType,
            @RequestParam(required = false) TargetType targetType,
            @RequestParam(required = false) String title,
            @RequestParam int offset,
            @RequestParam int limit
    ) {

        MessageSearchRequest req = new MessageSearchRequest(
                memberDetail.getMemberResponse().getMemberId(), boxType, targetType, title, offset, limit
        );
        return ResponseEntity.ok().body(messageService.getMessageList(req));
    }


    @DeleteMapping("/deleteMessages")
    public ResponseEntity<Map<String, String>> deleteMessages(@RequestBody List<Long> messageIds) {
        int result = messageService.removeMessages(messageIds);
        if (result > 0) {
            return ResponseEntity.ok().body(Map.of("message", "delete message success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "delete message fail"));
    }

    @DeleteMapping("/deleteSenderMessages")
    public ResponseEntity<Map<String, String>> deleteSenderMessages(@RequestBody List<Long> messageIds) {
        int result = messageService.removeSenderMessages(messageIds);
        if (result > 0) {
            return ResponseEntity.ok().body(Map.of("message", "delete message success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "delete message fail"));
    }

    @GetMapping("/detail/{messageId}")
    public ResponseEntity<MessageResponse> getMessage(@PathVariable Long messageId, @AuthenticationPrincipal MemberDetail memberDetail) {
        MessageResponse result = messageService.findMessageDetail(messageId, memberDetail.getMemberResponse().getMemberId());
        if(result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/messageRegist")
    public ResponseEntity<Map<String, String>> registerMessage(@RequestBody MessageRequest messageRequest) {
        int result = messageService.registerMessage(messageRequest);
        if (result > 0) {
            return ResponseEntity.ok().body(Map.of("message", "regist message success"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "regist message fail"));
    }
}
