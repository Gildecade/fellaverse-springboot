package com.fellaverse.backend.controller;

import com.fellaverse.backend.dto.ExerciseDTO;
import com.fellaverse.backend.dto.RecordAddDTO;
import com.fellaverse.backend.dto.RecordDTO;
import com.fellaverse.backend.jwt.annotation.JWTCheckToken;
import com.fellaverse.backend.mapper.ExerciseMapper;
import com.fellaverse.backend.mapper.RecordMapper;
import com.fellaverse.backend.service.RecordService;
import com.fellaverse.backend.validator.ValidGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for user record services, including CRD.
 */
@RestController
@RequestMapping("/api/record")  // any requests under token will be proceeded
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordMapper recordMapper;

    @Autowired
    private ExerciseMapper exerciseMapper;

    @JWTCheckToken(function = "select exercise")
    @GetMapping("/exercise")
    public List<ExerciseDTO> findAllExercise(){
        return recordService.findAllExercise().stream().map(exerciseMapper::toDto).collect(Collectors.toList());
    }

    @JWTCheckToken(function = "add record")
    @PostMapping("")
    public String addRecord(@RequestBody @Validated(value = ValidGroup.Crud.Create.class) RecordAddDTO recordAddDTO) {
        recordService.addRecord(recordAddDTO);
        return "Add record success!";
    }

    @JWTCheckToken(function = "delete record")
    @DeleteMapping("/{id}/{userId}")
    public String deleteRecord(@PathVariable("id") Long id, @PathVariable("userId")Long userId) {
        recordService.deleteRecord(id, userId);
        return "Delete record success!";
    }

    @JWTCheckToken(function = "select record")
    @GetMapping("/{userId}")
    public List<RecordDTO> findRecordByUserId(@PathVariable("userId") Long userId) {
        return recordService.findRecordByUserId(userId).stream().map(recordMapper::toDto).collect(Collectors.toList());
    }

}
