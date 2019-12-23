package com.netcracker.service;

import com.netcracker.dto.EmployeeDto;
import com.netcracker.dto.EventCategoryDto;
import com.netcracker.dto.RequestDto;

import java.math.BigDecimal;

public interface EventService {
    void setName(String name, int id);
    //void createEventFromRequest(RequestDto requestDto, EmployeeDto employeeDto, EventCategoryDto eventCategoryDto);
    void setDescription(String content, int num);
    void setCost(BigDecimal cost, int num);
}
