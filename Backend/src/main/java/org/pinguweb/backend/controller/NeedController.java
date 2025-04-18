package org.pinguweb.backend.controller;

import org.pinguweb.DTO.NeedDTO;
import org.pinguweb.backend.DTO.BackendDTOFactory;
import org.pinguweb.backend.DTO.ModelDTOFactory;
import org.pinguweb.backend.controller.common.ServerException;
import org.pinguweb.backend.model.Need;
import org.pinguweb.backend.service.NeedService;
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
public class NeedController {

    @Autowired
    NeedService service;

    @Async
    @GetMapping("/need")
    public CompletableFuture<ResponseEntity<List<NeedDTO>>> getAll(){
        if (ServerException.isServerClosed(service.getNeedRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}
        BackendDTOFactory factory = new BackendDTOFactory();

        List<NeedDTO> needs = service.findAll().stream().map(factory::createNeedDTO).collect(Collectors.toList());

        return CompletableFuture.completedFuture(ResponseEntity.ok(needs));
    }

    @Async
    @GetMapping("/need/{ID}")
    public CompletableFuture<ResponseEntity<NeedDTO>> getNeed(@PathVariable Integer ID) {
        if (ServerException.isServerClosed(service.getNeedRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        BackendDTOFactory factory = new BackendDTOFactory();
        Optional<Need> res = service.findByID(ID);
        if (res.isPresent()) {
            return CompletableFuture.completedFuture(ResponseEntity.ok(factory.createNeedDTO(res.get())));
        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @Async
    @PostMapping("/need")
    public CompletableFuture<ResponseEntity<NeedDTO>> addNeed(@RequestBody NeedDTO need) {
        if (ServerException.isServerClosed(service.getNeedRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        ModelDTOFactory factory = new ModelDTOFactory();
        BackendDTOFactory dtoFactory = new BackendDTOFactory();

        return CompletableFuture.completedFuture(ResponseEntity.ok(dtoFactory.createNeedDTO(service.saveNeed(factory.createFromDTO(need)))));
    }

    @Async
    @DeleteMapping("/need/{ID}")
    public CompletableFuture<ResponseEntity<Void>> deleteNeed(@PathVariable int ID) {
        if (ServerException.isServerClosed(service.getNeedRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        Optional<Need> res = service.findByID(ID);
        if (res.isPresent()) {
            service.deleteNeed(res.get());
            return CompletableFuture.completedFuture(ResponseEntity.ok().build());
        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }

    @Async
    @PutMapping("/need")
    public CompletableFuture<ResponseEntity<NeedDTO>> updateNeed(@RequestBody NeedDTO need) {
        if (ServerException.isServerClosed(service.getNeedRepository())){return CompletableFuture.completedFuture(ResponseEntity.internalServerError().build());}

        Optional<Need> res = service.findByID(need.getID());
        if (res.isPresent()) {
            ModelDTOFactory factory = new ModelDTOFactory();
            BackendDTOFactory dtoFactory = new BackendDTOFactory();

            return CompletableFuture.completedFuture(ResponseEntity.ok(dtoFactory.createNeedDTO(service.saveNeed(factory.createFromDTO(need)))));
        }
        else {
            return CompletableFuture.completedFuture(ResponseEntity.notFound().build());
        }
    }
}
