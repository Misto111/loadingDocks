package org.docks.loadingdocks.model.service;

import org.docks.loadingdocks.model.entity.DriverEntity;
import org.docks.loadingdocks.model.entity.Schedule;

import java.util.List;


public interface DriverService {

    List<DriverEntity> getAllDrivers();

    DriverEntity saveDriver(DriverEntity driverEntity);

    boolean existByEmail(String email);

    boolean existByPhoneNumber(String phoneNumber);

    DriverEntity getDriverByUsername(String username);

    boolean authenticateDriver(String email, String password);


    DriverEntity getDriverByEmail(String email);

    void deleteDeliverySlot(Schedule slot);

    public void deleteSchedule(Long schedule);

    List<Schedule> getDriverSchedule(String email);






}
