package com.zero.plantoryprojectbe.question;

import com.zero.plantoryprojectbe.question.dto.QuestionListPageResponse;
import com.zero.plantoryprojectbe.question.dto.SelectAnswerListResponse;
import com.zero.plantoryprojectbe.question.dto.SelectQuestionDetailResponse;
import com.zero.plantoryprojectbe.question.service.QuestionReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionReadRestController {
    private final QuestionReadService questionReadService;

    @GetMapping
    public QuestionListPageResponse getQuestionList(@RequestParam(defaultValue = "1") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(required = false) String keyword
    ) {
        return questionReadService.getQuestionList(keyword, page, size);
    }

    @GetMapping("/{questionId}")
    public SelectQuestionDetailResponse getQuestionDetail(@PathVariable Long questionId) {
        return questionReadService.getQuestionDetail(questionId);
    }

    @GetMapping("/{questionId}/answers")
    public List<SelectAnswerListResponse> getAnswerList(@PathVariable Long questionId) {
        return questionReadService.getAnswerList(questionId);
    }


}
