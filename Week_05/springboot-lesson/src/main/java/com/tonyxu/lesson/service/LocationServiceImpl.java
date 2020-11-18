package com.tonyxu.lesson.service;

import com.tonyxu.lesson.dao.jpa.LocationRepositoryJpa;
import com.tonyxu.lesson.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created on 2020/11/18.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Service
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationRepositoryJpa locationRepository;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void initLocations() {
        locationRepository.save(new Location((long) 1, "1", 38.998064, 117.317267));
        locationRepository.save(new Location((long) 2, "2", 38.997793, 117.317069));
        locationRepository.save(new Location((long) 3, "3", 38.998006, 117.317101));
        locationRepository.save(new Location((long) 4, "4", 38.997814, 117.317332));
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deleteLocation(long id) {
        locationRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void updateLocation(Location location) {
        locationRepository.saveAndFlush(location);
    }

    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

}
