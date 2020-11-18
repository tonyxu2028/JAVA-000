package com.tonyxu.lesson.service;

import com.tonyxu.lesson.domain.Location;

import java.util.List;

/**
 * Created on 2020/11/18.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public interface LocationService {

    void initLocations();

    void saveLocation(Location location);

    void deleteLocation(long id);

    void updateLocation(Location location);

    List<Location> findAll();
}
