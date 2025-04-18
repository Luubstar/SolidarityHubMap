package org.pinguweb.backend.DTO;

import org.pinguweb.DTO.*;
import org.pinguweb.backend.model.*;
import org.pinguweb.backend.service.*;
import org.pinguweb.enums.RoutePointType;
import org.pinguweb.enums.RouteType;
import org.pinguweb.enums.TaskType;
import org.pinguweb.model.enums.EmergencyLevel;
import org.pinguweb.model.enums.Status;
import org.pinguweb.model.enums.UrgencyLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ModelDTOFactory {
    @Autowired
    CatastropheService catastropheService;
    @Autowired
    StorageService storageService;
    @Autowired
    TaskService taskService;
    @Autowired
    AffectedService affectedService;
    @Autowired
    ZoneService zoneService;
    @Autowired
    private RoutePointService routePointService;
    @Autowired
    private RouteService routeService;

    public Zone createFromDTO(ZoneDTO dto){
        Zone zona = new Zone();
        zona.setName(dto.getName());
        zona.setDescription(dto.getDescription());
        zona.setEmergencyLevel(EmergencyLevel.valueOf(dto.getEmergencyLevel()));

        Optional<Catastrophe> zoneCatastrophe = catastropheService.findByID(dto.getCatastrophe());
        if (zoneCatastrophe.isPresent()) {
            zona.setCatastrophe(zoneCatastrophe.get());
        }

        for (int ID : dto.getStorages()){
            Optional<Storage> zoneStorage = storageService.findByID(ID);
            if (zoneStorage.isPresent()) {
                zona.getStorages().add(zoneStorage.get());
            }
        }

        for (int i = 0; i < dto.getLatitudes().size(); i++){
            GPSCoordinates coord = new GPSCoordinates(dto.getLatitudes().get(i), dto.getLongitudes().get(i));
            zona.getPoints().add(coord);
        }

        return zona;
    }

    public Need createFromDTO(NeedDTO dto){
        Need need = new Need();

        need.setDescription(dto.getDescription());
        need.setUrgency(UrgencyLevel.valueOf(dto.getUrgency()));
        need.setTaskType(TaskType.valueOf(dto.getNeedType()));

        GPSCoordinates coord = new GPSCoordinates(dto.getLatitude(), dto.getLongitude());
        need.setLocation(coord);

        Optional<Catastrophe> needCatastrophe = catastropheService.findByID(dto.getCatastrophe());
        if (needCatastrophe.isPresent()) {
            need.setCatastrophe(needCatastrophe.get());
        }

        Optional<Affected> needAffected = affectedService.findByDni(dto.getAffected());
        if (needAffected.isPresent()) {
            need.setAffected(needAffected.get());
        }

        Optional<Task> needTask = taskService.findByID(dto.getCatastrophe());
        if (needTask.isPresent()) {
            need.setTask(needTask.get());
        }

        need.setStatus(Status.valueOf(dto.getStatus()));

        return need;
    }

    public Storage createFromDTO(StorageDTO dto){
        Storage storage = new Storage();

        storage.setName(dto.getName());
        storage.setFull(dto.isFull());

        Optional<Zone> storageZone = zoneService.findByID(dto.getZone());
        if (storageZone.isPresent()) {
            storage.setZone(storageZone.get());
        }

        GPSCoordinates coord = new GPSCoordinates(dto.getLatitude(), dto.getLongitude());
        storage.setGpsCoordinates(coord);


        return storage;
    }

    public Route createFromDTO(RouteDTO dto){
        Route route = new Route();
        route.setName(dto.getName());
        route.setRouteType(RouteType.valueOf(dto.getRouteType()));
        Optional<Catastrophe> routeCatastrophe = catastropheService.findByID(dto.getCatastrophe());
        if (routeCatastrophe.isPresent()) {
            route.setCatastrophe(routeCatastrophe.get());
        }

        for (Integer i : dto.getPoints()){
            Optional<RoutePoint> point = routePointService.findByID(i);
            if (point.isPresent()){
                route.getPoints().add(point.get());
            }
        }

        return route;
    }

    public RoutePoint createFromDTO(RoutePointDTO dto){
        RoutePoint point = new RoutePoint();
        GPSCoordinates coordinates = new GPSCoordinates(dto.getLatitude(), dto.getLongitude());
        point.setLocation(coordinates);
        point.setRoutePointType(RoutePointType.valueOf(dto.getRouteType()));
        point.setRoute(routeService.findByID(dto.getID()).get());
        return point;
    }
}