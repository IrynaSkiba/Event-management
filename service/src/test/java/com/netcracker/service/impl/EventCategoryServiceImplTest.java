package com.netcracker.service.impl;

import com.netcracker.converter.impl.EventCategoryConverterImpl;
import com.netcracker.dao.repository.EventCategoryRepository;
import com.netcracker.dto.EventCategoryDto;
import com.netcracker.entity.EventCategory;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EventCategoryServiceImplTest {
    @Mock
    private EventCategoryRepository categoryRepository;
    @Mock
    private EventCategoryConverterImpl converter;
    @InjectMocks
    private EventCategoryServiceImpl categoryService = new EventCategoryServiceImpl(categoryRepository, converter);

    private EventCategory eventCategory;

    @Before
    public void setUp() {
        eventCategory = new EventCategory();
        eventCategory.setId(1);
    }

    @After
    public void tearDown() {
        eventCategory = null;
    }

    @Test
    public void getAll() {
        Mockito.when(categoryRepository.findAll()).thenReturn(Collections.singletonList(eventCategory));
        Assert.assertEquals(Collections.singletonList(converter.toDto(eventCategory)), categoryService.getAll());
    }

    @Test
    public void get() {
        Optional<EventCategory> optional = Optional.of(eventCategory);
        Mockito.when(categoryRepository.findById(anyInt())).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), categoryService.get(anyInt()));
    }

    @Test
    public void create() {
        EventCategoryDto eventCategoryDto = new EventCategoryDto();
        eventCategoryDto.setId(eventCategory.getId());
        Mockito.when(converter.toEntity(eventCategoryDto)).thenReturn(eventCategory);

        categoryService.create(eventCategoryDto);
        Mockito.verify(categoryRepository).save(eventCategory);
    }

    @Test
    public void delete() {
        Optional<EventCategory> optional = Optional.of(eventCategory);
        Mockito.when(categoryRepository.findById(anyInt())).thenReturn(optional);
        categoryService.delete(eventCategory.getId());
        Mockito.verify(categoryRepository).delete(eventCategory);
    }

    @Test
    public void update() {
        EventCategoryDto eventCategoryDto = new EventCategoryDto();
        eventCategoryDto.setId(eventCategory.getId());
        Optional<EventCategory> optional = Optional.of(eventCategory);
        Mockito.when(categoryRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        categoryService.update(eventCategoryDto);
        Mockito.verify(categoryRepository, Mockito.times(1)).delete(eventCategory);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(converter.toEntity(eventCategoryDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(categoryRepository.findAll()).thenReturn(Collections.singletonList(eventCategory));
        Assert.assertTrue(categoryService.idAlreadyExist(1));
    }
}
