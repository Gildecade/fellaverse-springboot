package com.fellaverse.backend.service;

import com.fellaverse.backend.bean.Schedule;
import com.fellaverse.backend.projection.ScheduleInfo;
import com.fellaverse.backend.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule setSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public boolean deleteSchedule(Long id) {
        try {
            scheduleRepository.deleteById(id);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateSchedule(Schedule schedule) {

        try {
            scheduleRepository.save(schedule);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public List<ScheduleInfo> findAllSchedule(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {

        return scheduleRepository.findById(id);
    }
}
