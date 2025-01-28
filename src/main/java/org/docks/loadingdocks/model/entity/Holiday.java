package org.docks.loadingdocks.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Holiday extends BaseEntity {

    @Column(nullable = false, unique = true)
    private LocalDate date; // Дата на почивния ден



    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}