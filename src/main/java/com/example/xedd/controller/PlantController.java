package com.example.xedd.controller;

import com.example.xedd.dto.PlantRequestDto;
import com.example.xedd.dto.PlantResponseDto;
import com.example.xedd.exception.NotFoundException;
import com.example.xedd.exception.RecordNotFoundException;
import com.example.xedd.model.*;
import com.example.xedd.repository.PlantRepository;
import com.example.xedd.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("api/v1/plants")
@CrossOrigin("*")
public class PlantController {

    @Autowired
    PlantService plantService;

    @Autowired
    PlantRepository repository;


    @GetMapping("")
    public ResponseEntity<Object> getAllPlants() {
        List<Plant> plants = plantService.getPlants();
        return ResponseEntity.ok().body(plants);
    }

    @GetMapping("/page")
    public Page<Plant> getPlantsPaged(@RequestParam("page") int page){
        var pageRequest  = PageRequest.of(page, 10);
        pageRequest.withSort(Sort.Direction.ASC, "name");
        return plantService.findAllPlants(PageRequest.of(page, 10));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPlantInfo(@PathVariable("id") long id) {
        PlantResponseDto response = plantService.getPlantById(id);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}/download")
    public ResponseEntity downloadFile(@PathVariable long id) {
        Resource resource = plantService.downloadFile(id);
        String mediaType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
        public ResponseEntity<Object> addPlant(PlantRequestDto plantDto) {
        long newId = plantService.addPlant(plantDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).body(location);
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> partialUpdatePlant(PlantRequestDto plantRequestDto){
        plantService.partialUpdatePlant(plantRequestDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/file/{id}")
    public ResponseEntity<Object> uploadImage(
//            @RequestParam("file") MultipartFile file
            PlantRequestDto plantRequestDto
    ) {
        plantService.uploadImage(plantRequestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand().toUri();

        return ResponseEntity.created(location).body(location);
    }


    @PostMapping("/file")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = plantService.uploadFile(file);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand().toUri();

        return ResponseEntity.created(location).body(location);
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<Object> deletePlant(@PathVariable long id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }
//hier een whileloop van maken met not found exceptie
    @GetMapping(value = "/search")
    public ResponseEntity<Object> findPlants(@RequestParam(value = "query", required = false) String query) {
        List<Plant> found = plantService.findByName(query);
        if (found.isEmpty()) {
            return ResponseEntity.ok().body(plantService.findByLatin(query));
        }
        else {
            return ResponseEntity.ok().body(plantService.findByName(query));
            //throw new RecordNotFoundException();
        }
    }

//    @GetMapping(value = "/byUser/{uploadedByUserName}")
//    public ResponseEntity<Object> getAllByUploadedByUsername(@PathVariable("uploadeByUserName") String uploadedByUserName) {
//        return ResponseEntity.ok().body(plantService.getAllByUploadedByUsername(uploadedByUserName));
//    }

    @GetMapping("/byD/{difficulty}")
    public List<Plant> findAllByDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        return plantService.findAllByDifficulty(difficulty);}
    @GetMapping("/byW/{watering}")
    public List<Plant> findAllByWatering(@PathVariable("watering") Watering watering) {
        return plantService.findAllByWatering(watering);}
    @GetMapping("/byL/{light}")
    public List<Plant> findAllByLight(@PathVariable("light") Light light) {
        return plantService.findAllByLight(light);
    }
    @GetMapping("/byF/{food}")
    public List<Plant> findAllByFood(@PathVariable("food") Food food) {
        return plantService.findAllByFood(food);
    }

}
