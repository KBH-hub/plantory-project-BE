package com.zero.plantoryprojectbe.message.mapper;

import com.zero.plantoryprojectbe.global.plantoryEnum.BoxType;
import com.zero.plantoryprojectbe.global.plantoryEnum.MessageTargetType;
import com.zero.plantoryprojectbe.global.plantoryEnum.TargetType;
import com.zero.plantoryprojectbe.message.MessageMapper;
import com.zero.plantoryprojectbe.message.dto.MessageListResponse;
import com.zero.plantoryprojectbe.message.dto.MessageRequest;
import com.zero.plantoryprojectbe.message.dto.MessageResponse;
import com.zero.plantoryprojectbe.message.dto.MessageSearchRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class MessageMapperTests {
    @Autowired
    MessageMapper messageMapper;

    @Test
    @DisplayName("쪽지 리스트 화면 - 받은 쪽지함")
    void selectMessagesTest() {
        new MessageSearchRequest();
        MessageSearchRequest dto = MessageSearchRequest.builder()
                .memberId(21L)
                .boxType(BoxType.RECEIVED)
                .targetType(null)
                .title(null)
                .limit(10)
                .offset(0)
                .build();

        List<MessageListResponse> result = messageMapper.selectMessages(dto);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("쪽지 리스트 화면 - 보낸 쪽지함 - 나눔 유형 필터")
    void getMessagesSharingTest() {
        new MessageSearchRequest();
        MessageSearchRequest dto = MessageSearchRequest.builder()
                .memberId(21L)
                .boxType(BoxType.SENT)
                .targetType(TargetType.SHARING)
                .title(null)
                .limit(10)
                .offset(0)
                .build();

        List<MessageListResponse> result = messageMapper.selectMessages(dto);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("보낸 쪽지함 - 제목 키워드 검색")
    void selectMessagesSearchTest() {
        new MessageSearchRequest();
        MessageSearchRequest dto = MessageSearchRequest.builder()
                .memberId(21L)
                .boxType(BoxType.SENT)
                .targetType(null)
                .title("나눔")
                .limit(10)
                .offset(0)
                .build();

        List<MessageListResponse> result = messageMapper.selectMessages(dto);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("쪽지 클릭(조회)시 읽음 처리")
    void updateReadFlagTest() {
        Long memberId = 1L;

        int result = messageMapper.updateReadFlag(memberId);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("수신자 쪽지 삭제 처리")
    void deleteMessagesTest() {
        List<Long> messageIds = List.of(1L, 2L);

        int result = messageMapper.deleteMessages(messageIds);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("발신자 쪽지 삭제 처리")
    void deleteSenderMessagesTest() {
        List<Long> messageIds = List.of(1L, 2L);

        int result = messageMapper.deleteSenderMessages(messageIds);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("쪽지 등록 - 쪽지 등록 화면 정보 조회")
    void selectMessageWriteInfoTest() {
        Long senderId = 11L;
        String targetType = "QUESTION";
        Long targetId = 3L;

        MessageResponse result = messageMapper.selectMessageWriteInfo(senderId, targetType, targetId);

        log.info(String.valueOf(result));
    }

    @Test
    @DisplayName("쪽지 등록 - 쪽지 등록 처리")
    void insertMessageTest() {
        new MessageRequest();
        MessageRequest dto = MessageRequest.builder()
                .senderId(1L)
                .receiverId(2L)
                .title("안녕하세요. 테스트 쪽지 제목입니다.")
                .content("테스트 쪽지 내용입니다.")
                .targetType(MessageTargetType.SHARING)
                .targetId(13L)
                .build();

        int result = messageMapper.insertMessage(dto);

        log.info("inserted rows = " + result);
    }

    @Test
    @DisplayName("쪽지 상세 모달 화면")
    void selectMessageDetailTest() {
        Long messageId = 3L;

        MessageResponse result = messageMapper.selectMessageDetail(messageId);

        log.info(String.valueOf(result));
    }

}
