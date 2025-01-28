package org.docks.loadingdocks.model.service;

import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.docks.loadingdocks.enums.GoodsEnum;
import org.docks.loadingdocks.enums.VehicleType;
import org.docks.loadingdocks.model.entity.DriverEntity;
import org.docks.loadingdocks.model.entity.Schedule;
import org.docks.loadingdocks.repository.DriverRepository;
import org.docks.loadingdocks.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final DriverRepository driverRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, DriverRepository driverRepository) {
        this.scheduleRepository = scheduleRepository;
        this.driverRepository = driverRepository;
    }

    // Връщане на всички графици
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Добавяне на нов график
    public Schedule addSchedule(Schedule schedule) {
        if (schedule.getGoods() == null || schedule.getGoods().isEmpty()) {
            throw new IllegalArgumentException("Goods cannot be empty.");
        }

        // Изчисляваме общото време за разтоварване
        int totalUnloadingTime = calculateUnloadingTime(schedule.getGoods());
        schedule.setUnloadingTime(totalUnloadingTime);

        // Проверка за наличност на портали
        boolean isAvailable = isPortalAvailable(schedule.getBranch(), schedule.getDate(), schedule.getStartTime(), schedule.getEndTime());

        if (!isAvailable) {
            throw new IllegalArgumentException("There are no portals available for the selected interval.");
        }

        return scheduleRepository.save(schedule);
    }

    // Връщане на графици за определен филиал и дата
    public List<Schedule> getSchedulesByBranchAndDate(BranchLocationEnum branch, LocalDate date) {
        return scheduleRepository.findByBranchAndDate(branch, date);
    }

    // Проверка за наличност на портали в определен времеви интервал
    public boolean isPortalAvailable(BranchLocationEnum branch, LocalDate date, LocalTime startTime, LocalTime endTime) {
        // Извикваме метода с енум
        List<Schedule> schedules = getSchedulesByBranchAndDate(branch, date);
        for (Schedule schedule : schedules) {
            // Проверка за припокриване на времеви интервали
            if ((startTime.isBefore(schedule.getEndTime()) && endTime.isAfter(schedule.getStartTime())) ||
                    startTime.equals(schedule.getStartTime()) || endTime.equals(schedule.getEndTime())) {
                return false; // Порталът е зает
            }
        }
        return true; // Порталът е свободен
    }

    // Изчислява общото време за разтоварване на базата на стоките и техните линейни метри
    public int calculateUnloadingTime(Map<String, Double> goods) {
        int totalUnloadingTime = 0;
        for (Map.Entry<String, Double> entry : goods.entrySet()) {
            GoodsEnum goodsType = GoodsEnum.valueOf(entry.getKey()); // Преобразуваме низ обратно към енум
            switch (goodsType) {
                case CABINET_FURNITURE:
                    totalUnloadingTime += entry.getValue() * 5;
                    break;
                case UPHOLSTERED_FURNITURE:
                    totalUnloadingTime += entry.getValue() * 4;
                    break;
                case GARDEN_FURNITURE:
                    totalUnloadingTime += entry.getValue() * 2;
                    break;
                case MATTRESSES:
                    totalUnloadingTime += entry.getValue() * 3;
                    break;
            }
        }
        return totalUnloadingTime;
    }

    public Schedule createSchedule(Long driverId, String branch, LocalDate date, LocalTime startTime, LocalTime endTime, int unloadingTime, VehicleType vehicleType) {
        // Проверяваме дали шофьорът съществува в базата данни
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("The driver does not exist."));

        // Преобразуваме низът в съответния енум
        BranchLocationEnum branchEnum = BranchLocationEnum.valueOf(branch.toUpperCase());  // Уверяваме се, че е в правилен формат

        // Създаваме новия график и задаваме стойности
        Schedule schedule = new Schedule();
        schedule.setBranch(branchEnum);  // Задаваме енум стойност за клон
        schedule.setDate(date);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setUnloadingTime(unloadingTime);
        schedule.setVehicleType(vehicleType);

        // Записваме графика в базата
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.findById(id).ifPresentOrElse(existingSchedule -> {
            // Прекратяваме всякаква специфична логика за клони
            // Прекратяваме изтриването
            scheduleRepository.delete(existingSchedule);
        }, () -> {
            // Ако графикът не е намерен, хвърляме изключение
            throw new IllegalArgumentException("Schedule not found with id: " + id);
        });
    }

    // Проверка дали има налични портали за даден клон и дата
    public boolean hasAvailablePortals(BranchLocationEnum branch, LocalDate date) {
        List<Schedule> schedules = getSchedulesByBranchAndDate(branch, date);
        return schedules.isEmpty();
    }

}
