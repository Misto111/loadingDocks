package org.docks.loadingdocks.controller;

import org.docks.loadingdocks.model.entity.Holiday;
import org.docks.loadingdocks.model.service.HolidayService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    @GetMapping
    public List<Holiday> getAllHolidays() {
        return holidayService.getAllHolidays();
    }

    @PostMapping
    public Holiday addHoliday(@RequestBody Holiday holiday) {
        return holidayService.addHoliday(holiday);
    }

    @DeleteMapping("/{id}")
    public void deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);
    }

    @GetMapping("/isHoliday")
    public boolean isHoliday(@RequestParam LocalDate date) {
        return holidayService.isHoliday(date);
    }
}
