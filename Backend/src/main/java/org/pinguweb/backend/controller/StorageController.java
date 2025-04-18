package org.pinguweb.backend.controller;

import org.pinguweb.DTO.StorageDTO;
import org.pinguweb.backend.DTO.BackendDTOFactory;
import org.pinguweb.backend.DTO.ModelDTOFactory;
import org.pinguweb.backend.controller.common.ServerException;
import org.pinguweb.backend.model.Storage;
import org.pinguweb.backend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class StorageController {
    @Autowired
    StorageService service;

    @Async
    @GetMapping("/storage")
    public CompletableFuture<ResponseEntity<List<StorageDTO>>> getAll(){
        if (ServerException.isServerClosed(service.getStorageRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        BackendDTOFactory factory = new BackendDTOFactory();

        List<StorageDTO> zones = service.findAll().stream().map(factory::createStorageDTO).collect(Collectors.toList());
        return CompletableFuture.completedFuture(ResponseEntity.ok(zones));
    }

    @Async
    @GetMapping("/storage/{ID}")
    public CompletableFuture<ResponseEntity<StorageDTO>> getStorage(@PathVariable int ID) {
        if (ServerException.isServerClosed(service.getStorageRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        BackendDTOFactory factory = new BackendDTOFactory();
        Optional<Storage> res = service.findByID(ID);
        if (res.isPresent()) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(factory.createStorageDTO(res.get())));
        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @Async
    @PostMapping("/storage")
    public CompletableFuture<ResponseEntity<StorageDTO>> addZone(@RequestBody StorageDTO storage) {
        if (ServerException.isServerClosed(service.getStorageRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        ModelDTOFactory factory = new ModelDTOFactory();
        BackendDTOFactory dtoFactory = new BackendDTOFactory();

        return CompletableFuture.completedFuture(ResponseEntity.ok(dtoFactory.createStorageDTO(service.saveStorage(factory.createFromDTO(storage)))));
    }

    @Async
    @DeleteMapping("/storage/{ID}")
    public CompletableFuture<ResponseEntity<Void>> deleteZone(@PathVariable int ID) {
        if (ServerException.isServerClosed(service.getStorageRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        Optional<Storage> res = service.findByID(ID);
        if (res.isPresent()) {
            service.delete(res.get());
            return CompletableFuture.completedFuture(ResponseEntity.ok().build());
        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @Async
    @PutMapping("/storage")
    public CompletableFuture<ResponseEntity<StorageDTO>> updateZone(@RequestBody StorageDTO storage) {
        if (ServerException.isServerClosed(service.getStorageRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        Optional<Storage> res = service.findByID(storage.getID());
        if (res.isPresent()) {
            ModelDTOFactory factory = new ModelDTOFactory();
            BackendDTOFactory dtoFactory = new BackendDTOFactory();

            return CompletableFuture.completedFuture(ResponseEntity.ok(dtoFactory.createStorageDTO(service.saveStorage(factory.createFromDTO(storage)))));
        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

}
