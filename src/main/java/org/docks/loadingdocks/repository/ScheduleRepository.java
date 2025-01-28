package org.docks.loadingdocks.repository;

import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.docks.loadingdocks.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Променяме типа на параметъра от String на BranchLocationEnum
    List<Schedule> findByBranchAndDate(BranchLocationEnum branch, LocalDate date);

    // Запазваме този метод, но ако имате нужда, променете и другите
    List<Schedule> findAllByReservedBy(String email);
}