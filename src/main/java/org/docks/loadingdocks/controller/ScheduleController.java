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

    /**
     * Получава всички графици.
     *
     * @return списък с всички графици
     */
    @GetMapping
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    /**
     * Добавя нов график.
     *
     * @param schedule - информация за новия график
     * @return добавеният график
     */
    @PostMapping
    public Schedule addSchedule(@RequestBody Schedule schedule) {
        // Проверка дали графикът съдържа стоки
        if (schedule.getGoods() == null || schedule.getGoods().isEmpty()) {
            throw new IllegalArgumentException("The goods must be selected.");
        }

        // Проверка дали графикът съдържа валидна дата и филиал
        if (schedule.getDate() == null || schedule.getBranch() == null) {
            throw new IllegalArgumentException("You need to fill in the date and branch.");
        }

        // Изчисляваме линейните метри, ако са налични стоки
        int totalUnloadingTime = schedule.getGoods().values().stream()
                .mapToInt(meters -> meters.intValue() * 5)  // Ако е необходимо, можеш да промениш логиката
                .sum();

        schedule.setUnloadingTime(totalUnloadingTime);

        return scheduleService.addSchedule(schedule);
    }

    /**
     * Получава графици за конкретен филиал и дата.
     *
     * @param branch - името на филиала
     * @param date - дата за графиците
     * @return списък с графици за дадения филиал и дата
     */
    @GetMapping("/branch")
    public List<Schedule> getSchedulesByBranchAndDate(@RequestParam String branch, @RequestParam LocalDate date) {
        // Преобразуваме низа в енум
        BranchLocationEnum branchEnum = BranchLocationEnum.valueOf(branch.toUpperCase());
        return scheduleService.getSchedulesByBranchAndDate(branchEnum, date);
    }
    /**
     * Изтрива график по неговото ID.
     *
     * @param id - ID на графика
     */
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}