package com.zero.plantoryprojectbe.notice;

import com.zero.plantoryprojectbe.notice.dto.NoticeDTO;
import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeRestController {

    @Autowired
    NoticeService noticeService;

    @GetMapping
    public List<NoticeDTO> getNoticeByReceiver(@AuthenticationPrincipal MemberDetail memberDetail) {
        Long receiverId = memberDetail.memberResponse().getMemberId();
        return noticeService.getNoticeByReceiver(receiverId);
    }

    @PostMapping
    public int registerNotice(@RequestBody NoticeDTO noticeDTO) {
        return noticeService.registerNotice(noticeDTO);
    }

    @PutMapping
    public int updateNotice(@RequestParam("noticeId") Long noticeId) {
        return noticeService.updateNoticeReadFlag(noticeId);
    }

    @DeleteMapping
    public int removeAllNotice(@AuthenticationPrincipal MemberDetail memberDetail) {
        Long receiverId = memberDetail.memberResponse().getMemberId();
        return noticeService.removeAllNotice(receiverId);
    }

}
