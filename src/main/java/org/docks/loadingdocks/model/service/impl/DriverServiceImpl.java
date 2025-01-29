package org.docks.loadingdocks.model.service.impl;

import com.vaadin.flow.server.VaadinSession;
import jakarta.transaction.Transactional;
import org.docks.loadingdocks.model.entity.DriverEntity;
import org.docks.loadingdocks.model.entity.Schedule;
import org.docks.loadingdocks.model.service.DriverService;
import org.docks.loadingdocks.repository.DriverRepository;
import org.docks.loadingdocks.repository.ScheduleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;


    public DriverServiceImpl(DriverRepository driverRepository, PasswordEncoder passwordEncoder, ScheduleRepository scheduleRepository) {
        this.driverRepository = driverRepository;
        this.passwordEncoder = passwordEncoder;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<DriverEntity> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public DriverEntity saveDriver(DriverEntity driverEntity) {
        if (driverEntity.getId() == null) {
            // Това е нов драйвър, задаваме му криптирана парола
            driverEntity.setPassword(passwordEncoder.encode(driverEntity.getPassword()));
            return driverRepository.save(driverEntity);
        }

        // Ако драйвърът вече съществува, обновяваме данните му
        DriverEntity existingDriver = driverRepository.findById(driverEntity.getId())
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with ID: " + driverEntity.getId()));

        existingDriver.setFirstName(driverEntity.getFirstName());
        existingDriver.setLastName(driverEntity.getLastName());
        existingDriver.setPhoneNumber(driverEntity.getPhoneNumber());

        // Ако е променена паролата, я криптираме
        if (!driverEntity.getPassword().equals(existingDriver.getPassword())) {
            existingDriver.setPassword(passwordEncoder.encode(driverEntity.getPassword()));
        }

        return driverRepository.save(existingDriver);
    }

    @Override
    public boolean existByEmail(String email) {
        return driverRepository.existsByEmail(email);
    }

    @Override
    public boolean existByPhoneNumber(String phoneNumber) {
        return driverRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public DriverEntity getDriverByUsername(String email) {
        return driverRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Driver not found for email: " + email));
    }

    @Override
    public boolean authenticateDriver(String email, String password) {
        DriverEntity driver = driverRepository.findByEmail(email).orElse(null);

        if (driver != null && passwordEncoder.matches(password, driver.getPassword())) {
            // Записваме информация в сесията
            VaadinSession.getCurrent().setAttribute("email", driver.getEmail());
            VaadinSession.getCurrent().setAttribute("role", driver.getRole().toString());  // Записваме ролята
            return true;
        }

        return false;
    }


    @Override
    @Transactional
    public DriverEntity getDriverByEmail(String email) {
        return driverRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("A driver with this email does not exist."));
    }

    @Override
    @Transactional
    public void deleteDeliverySlot(Schedule slot) {
        if (scheduleRepository.existsById(slot.getId())) {
            scheduleRepository.delete(slot);
        } else {
            throw new IllegalArgumentException("Delivery slot not found with ID: " + slot.getId());
        }
    }

    @Override
    // Изтриване на график по ID
    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }

    @Override
    public List<Schedule> getDriverSchedule(String email) {
        return scheduleRepository.findAllByReservedBy(email);
    }

}
