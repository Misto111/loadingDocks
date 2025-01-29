package org.docks.loadingdocks.controller;

import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.docks.loadingdocks.model.entity.Schedule;
import org.docks.loadingdocks.model.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @PostMapping
    public Schedule addSchedule(@RequestBody Schedule schedule) {
        if (schedule.getGoods() == null || schedule.getGoods().isEmpty()) {
            throw new IllegalArgumentException("The goods must be selected.");
        }

        if (schedule.getDate() == null || schedule.getBranch() == null) {
            throw new IllegalArgumentException("You need to fill in the date and branch.");
        }

        int totalUnloadingTime = schedule.getGoods().values().stream()
                .mapToInt(meters -> meters.intValue() * 5)
                .sum();

        schedule.setUnloadingTime(totalUnloadingTime);

        return scheduleService.addSchedule(schedule);
    }

    @GetMapping("/branch")
    public List<Schedule> getSchedulesByBranchAndDate(@RequestParam String branch, @RequestParam LocalDate date) {
        BranchLocationEnum branchEnum = BranchLocationEnum.valueOf(branch.toUpperCase());
        return scheduleService.getSchedulesByBranchAndDate(branchEnum, date);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}