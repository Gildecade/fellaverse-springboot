package com.fellaverse.backend.service;

import com.fellaverse.backend.bean.Exercise;
import com.fellaverse.backend.bean.Program;
import com.fellaverse.backend.projection.ProgramInfo;
import com.fellaverse.backend.repository.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public List<ProgramInfo> findAllPrograms(Long scheduleId) {
        return programRepository.findByScheduleId(scheduleId);
    }

    @Override
    @Transactional
    public Program addProgram (Program program) {
        Program result = programRepository.save(program);
        for (Exercise exercise : program.getExercises()) {
            programRepository.insertToProgramExercise(exercise.getId(), result.getId());
        }
        return result;
    }

    @Override
    public boolean deleteProgram(Long id) {
        try {
            programRepository.deleteById(id);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateProgram(Program program) {
        System.out.println(program.getId());
        try {
            programRepository.save(program);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public Optional<Program> findProgramById(Long id) {

        return programRepository.findById(id);
    }
}
