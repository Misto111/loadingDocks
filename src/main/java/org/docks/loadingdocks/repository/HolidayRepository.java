package org.docks.loadingdocks.repository;

import org.docks.loadingdocks.model.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    boolean existsByDate(LocalDate date);
}
