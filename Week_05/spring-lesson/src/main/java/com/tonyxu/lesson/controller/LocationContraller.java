package com.tonyxu.lesson.controller;

import com.tonyxu.lesson.domain.Location;
import com.tonyxu.lesson.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created on 2020/11/17.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
@Controller
public class LocationContraller {

    @Autowired
    private LocationService locationService;

    @ResponseBody
    @RequestMapping("/init")
    public String init(){
        try {
            locationService.initLocations();
            return "init ok";
        }catch (Exception e){
           e.printStackTrace();
           return "init fail";
        }
    }

    @ResponseBody
    @RequestMapping("/save")
    public String save(){
        try {
            locationService.saveLocation(
                    new Location((long) 5, "1", 38.998065, 117.317269));
            return "save ok";
        }catch (Exception e){
            e.printStackTrace();
            return "save fail";
        }
    }

    @ResponseBody
    @RequestMapping("/update")
    public String update(){
        try {
            locationService.updateLocation(
                    new Location((long) 4, "1", 40.998065, 118.317269));
            return "update ok";
        }catch (Exception e){
            e.printStackTrace();
            return "update fail";
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public String delete(){
        try {
            locationService.deleteLocation(4);
            return "update ok";
        }catch (Exception e){
            e.printStackTrace();
            return "update fail";
        }
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public List<Location> findAll(){
        return locationService.findAll();
    }

}
