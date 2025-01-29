package org.docks.loadingdocks.model.entity;
import jakarta.persistence.*;
import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.docks.loadingdocks.enums.VehicleType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Schedule extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private BranchLocationEnum branch;

    @ElementCollection
    @CollectionTable(name = "schedule_goods", joinColumns = @JoinColumn(name = "schedule_id"))
    @MapKeyColumn(name = "goods_type")
    @Column(name = "linear_meters")
    private Map<String, Double> goods = new HashMap<>();

    @Column(nullable = false)
    private int unloadingTime;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Column(nullable = false)
    private String reservedBy;


    public BranchLocationEnum getBranch() {
        return branch;
    }

    public Schedule setBranch(BranchLocationEnum branch) {
        this.branch = branch;
        return this;
    }

    public Map<String, Double> getGoods() {
        return goods;
    }

    public void setGoods(Map<String, Double> goods) {
        this.goods = goods;
    }

    public int getUnloadingTime() {
        return unloadingTime;
    }

    public void setUnloadingTime(int unloadingTime) {
        this.unloadingTime = unloadingTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Schedule setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
        return this;
    }

    public String getReservedBy() {
        return reservedBy;
    }

    public Schedule setReservedBy(String reservedBy) {
        this.reservedBy = reservedBy;
        return this;
    }
}
