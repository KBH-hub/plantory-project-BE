package com.zero.plantoryprojectbe.plantingCalendar.service;


import com.zero.plantoryprojectbe.plantingCalendar.dto.SMSRequestDTO;

import java.util.Map;

public interface SMSService {
    Map<String, Object> sendSMS(SMSRequestDTO smsRequestDTO) throws Exception;
}
