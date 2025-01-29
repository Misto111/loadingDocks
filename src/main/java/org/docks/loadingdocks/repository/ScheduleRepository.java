package org.docks.loadingdocks.repository;

import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.docks.loadingdocks.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByBranchAndDate(BranchLocationEnum branch, LocalDate date);
    List<Schedule> findAllByReservedBy(String email);
}