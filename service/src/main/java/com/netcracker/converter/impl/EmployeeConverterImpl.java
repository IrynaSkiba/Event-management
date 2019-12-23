package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.EmployeeDto;
import com.netcracker.dto.RoleDto;
import com.netcracker.entity.BaseEntity;
import com.netcracker.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class EmployeeConverterImpl implements Converter<Employee, EmployeeDto> {

    @Override
    public EmployeeDto toDto(Employee entity) {
        List<Integer> list = entity.getEvents().stream().map(BaseEntity::getId).collect(Collectors.toList());

        EmployeeDto employeeDto = new EmployeeDto(entity.getId(), entity.getLogin(), entity.getPassword(),
                entity.getName(), entity.getSurname(), RoleDto.valueOf(entity.getRole().name()), entity.getPhone(), entity.getEmail(), list);
        return employeeDto;
    }

    @Override
    public Employee toEntity(EmployeeDto dto) {
        Employee employee = new Employee(dto.getId(), dto.getLogin(), dto.getPassword(),
                dto.getName(), dto.getSurname(), dto.getPhone(), dto.getEmail());
        return employee;
    }
}
