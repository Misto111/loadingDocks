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

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Schedule addSchedule(Schedule schedule) {
        if (schedule.getGoods() == null || schedule.getGoods().isEmpty()) {
            throw new IllegalArgumentException("Goods cannot be empty.");
        }

        int totalUnloadingTime = calculateUnloadingTime(schedule.getGoods());
        schedule.setUnloadingTime(totalUnloadingTime);

        boolean isAvailable = isPortalAvailable(schedule.getBranch(), schedule.getDate(), schedule.getStartTime(), schedule.getEndTime());

        if (!isAvailable) {
            throw new IllegalArgumentException("There are no portals available for the selected interval.");
        }

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedulesByBranchAndDate(BranchLocationEnum branch, LocalDate date) {
        return scheduleRepository.findByBranchAndDate(branch, date);
    }

    public boolean isPortalAvailable(BranchLocationEnum branch, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Schedule> schedules = getSchedulesByBranchAndDate(branch, date);
        for (Schedule schedule : schedules) {
            if ((startTime.isBefore(schedule.getEndTime()) && endTime.isAfter(schedule.getStartTime())) ||
                    startTime.equals(schedule.getStartTime()) || endTime.equals(schedule.getEndTime())) {
                return false;
            }
        }
        return true;
    }


    public int calculateUnloadingTime(Map<String, Double> goods) {
        int totalUnloadingTime = 0;
        for (Map.Entry<String, Double> entry : goods.entrySet()) {
            GoodsEnum goodsType = GoodsEnum.valueOf(entry.getKey());
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
        DriverEntity driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new IllegalArgumentException("The driver does not exist."));

        BranchLocationEnum branchEnum = BranchLocationEnum.valueOf(branch.toUpperCase());

        Schedule schedule = new Schedule();
        schedule.setBranch(branchEnum);
        schedule.setDate(date);
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        schedule.setUnloadingTime(unloadingTime);
        schedule.setVehicleType(vehicleType);

        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.findById(id).ifPresentOrElse(existingSchedule -> {
            scheduleRepository.delete(existingSchedule);
        }, () -> {
            throw new IllegalArgumentException("Schedule not found with id: " + id);
        });
    }

    public boolean hasAvailablePortals(BranchLocationEnum branch, LocalDate date) {
        List<Schedule> schedules = getSchedulesByBranchAndDate(branch, date);
        return schedules.isEmpty();
    }

}
