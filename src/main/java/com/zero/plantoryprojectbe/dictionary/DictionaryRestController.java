package com.zero.plantoryprojectbe.dictionary;

import com.fasterxml.jackson.databind.JsonNode;
import com.zero.plantoryprojectbe.dictionary.service.DryGardenApiService;
import com.zero.plantoryprojectbe.dictionary.service.GardenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dictionary")
@RequiredArgsConstructor
public class DictionaryRestController {

    private final GardenApiService gardenApiService;
    private final DryGardenApiService dryGardenApiService;

    @GetMapping("/garden")
    public JsonNode getGarden( @RequestParam(defaultValue = "1") String pageNo,
                                   @RequestParam(defaultValue = "10") String numOfRows,
                                   @RequestParam(required = false) String lightCode) {
        return gardenApiService.getGardenList(pageNo, numOfRows, lightCode);
    }

    @GetMapping("/garden/{cntntsNo}")
    public JsonNode getGardenDetail(@PathVariable String cntntsNo) {
        return gardenApiService.getGardenDetail(cntntsNo);
    }

    @GetMapping("/dry")
    public JsonNode getDryGarden(@RequestParam(defaultValue = "1") String pageNo,
                                 @RequestParam(defaultValue = "10") String numOfRows,
                                 @RequestParam(defaultValue = "") String sClCode) {
        return dryGardenApiService.getDryGardenList(pageNo, numOfRows, sClCode);
    }

    @GetMapping("/dry/{cntntsNo}")
    public JsonNode getDryGardenDetail(@PathVariable String cntntsNo) {
        return dryGardenApiService.getDryGardenDetail(cntntsNo);
    }

}
