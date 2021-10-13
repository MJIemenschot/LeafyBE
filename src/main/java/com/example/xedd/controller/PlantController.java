package com.example.xedd.controller;

import com.example.xedd.dto.PlantRequestDto;
import com.example.xedd.dto.PlantResponseDto;
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



    //    @GetMapping("")
//    public ResponseEntity<Object> getFiles() {
//        Iterable<Plant> files = plantService.findAll();
//        return ResponseEntity.ok().body(files);
//    }
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


    @PostMapping("/file")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = plantService.uploadFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileName)
                .toUriString();

        //FileResponse fileResponse = new FileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        return new ResponseEntity<Object>(fileName, HttpStatus.OK);
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

    @PutMapping(value = "/update",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ResponseEntity<Object> updatePlant(PlantRequestDto plantDto) {
        plantService.updatePlant(plantDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand().toUri();

        return ResponseEntity.created(location).body(location);
    }
    @PatchMapping("{id}")
    public ResponseEntity<Object> partialUpdatePlant(PlantRequestDto plantRequestDto){
        return ResponseEntity.noContent().build();
    }

    //    //Werkt zonder nieuwe fileupload
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Object> editPlant(@PathVariable("id") long id, @RequestBody Plant plant) {
//
//        plantService.editPlant(plant);
//        return ResponseEntity.noContent().build();
//    }

    //kan file niet vinden
//    @PutMapping("/{id}/image")
//    public void uploadPicture(@PathVariable("id") long id, @RequestParam("file") MultipartFile file) throws IOException {
//        plantService.uploadImage(PlantRequestDto.file);
//    }
    //fout 415
//    @PutMapping("edito/{id}")
//    public PlantResponseDto editoPlant(@PathVariable("id") long id, @RequestBody PlantRequestDto dto) {
//        var plant = plantService.editPlant(id, dto.toPlant());
//        return PlantResponseDto.fromPlant(plant);
//    }

    //    @PutMapping
//    public PlantResponseDto editPlant(@RequestBody PlantRequestDto dto) {
//        var plant = plantService.savePlant(dto.toPlant());
//        return PlantResponseDto.fromPlant(plant);
//    }

//    @PutMapping("/edit")
//    public ResponseEntity<Plant> updatPlant(@PathVariable("id")Long id,
//                                            @RequestParam("name") String name,
//                                            @RequestParam(value = "latinName", required = false)String latinName,
//                                            @RequestParam("description")String description,
//                                            @RequestParam("difficulty") Difficulty difficulty,
//                                            @RequestParam("watering") Watering watering,
//                                            @RequestParam("light") Light light,
//                                            @RequestParam("food") Food food,
//                                            @RequestParam(value = "file", required = false) Optional<MultipartFile> file
//                                            ){
//        Plant newPlant = new Plant();
//        newPlant.setName(name);
//        newPlant.setLatinName(latinName);
//        newPlant.setDescription(description);
//        newPlant.setDifficulty(difficulty);
//        newPlant.setWatering(watering);
//        newPlant.setLight(light);
//        newPlant.setFood(food);
//        if((file.isPresent())){newPlant.setFileName(file.get().getOriginalFilename());}
//        try {
//            if((file.isPresent())){plantService.uploadFile(file.get());}
////            if((file.isPresent())){fileStorageService.updateFile(file.get(),id);}
//            plantService.updatPlant(id, newPlant);
//            return ResponseEntity.noContent().build();
//        }
//        catch (Exception ex) {
//            throw new RuntimeException();
//        }
//    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<Object> deletePlant(@PathVariable long id) {
        plantService.deletePlant(id);
        return ResponseEntity.noContent().build();
    }
//    @GetMapping(value = "/by/{name}")
//    public ResponseEntity<Object> getPlantByName(@PathVariable("name") String name) {
//        return ResponseEntity.ok().body(plantService.findAllByName(name));
//    }
//    @GetMapping(value = "/byLatin/{latinName}")
//    public ResponseEntity<Object> getPlantByLatinName(@PathVariable("latinName") String latinName) {
//        return ResponseEntity.ok().body(plantService.findAllByLatinName(latinName));
//    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> findPlants(@RequestParam(value = "query", required = false) String query) {
        List<Plant> found = plantService.findByName(query);
        if (found.isEmpty()) {
            return ResponseEntity.ok().body(plantService.findByLatin(query));
        }
        else {
            return ResponseEntity.ok().body(plantService.findByName(query));
        }


    }
//@GetMapping(value = "/by")
//public ResponseEntity<Object> findPlants(@PathVariable(value = "name", required = false ) String name,
//                                         @PathVariable(value = "latinName", required = false) String latinName) {
//    if (latinName == null) {
//        return ResponseEntity.ok().body(plantService.findAllByName(name));
//    } else{
//        return ResponseEntity.ok().body(plantService.findAllByLatinName(latinName));
//    }
//        else {
//
//        }

//}


//    @GetMapping
//    public Collection<PlantResponseDto> findPlants(@RequestParam(value = "name", required = false ) String name,
//                                             @RequestParam(value = "latinName", required = false) String latinName) {
//        var dtos = new ArrayList<PlantResponseDto>();
//
//        Collection<Plant> plants;
//        if (latinName == null) {
//            plants = plantService.findAllByName(name);
//        } else {
//            plants = plantService.findAllByLatinName(latinName);
//        }
//        for (Plant plant : plants) {
//            dtos.add(PlantResponseDto.fromPlant(plant));
//        }
//        return dtos;
//    }




//    @GetMapping(value = "/byUser/{uploadedByUserName}")
//    public ResponseEntity<Object> getAllByUploadedByUsername(@PathVariable("uploadeByUserName") String uploadedByUserName) {
//        return ResponseEntity.ok().body(plantService.getAllByUploadedByUsername(uploadedByUserName));
//    }



    @GetMapping("/byD/{difficulty}")
    public Collection<Plant> findAllByDifficulty(@PathVariable("difficulty") Difficulty difficulty) {
        return plantService.findAllByDifficulty(difficulty);
    }
    @GetMapping("/byW/{watering}")
    public Collection<Plant> findAllByWatering(@PathVariable("watering") Watering watering) {
        return plantService.findAllByWatering(watering);
    }
    @GetMapping("/byL/{light}")
    public Collection<Plant> findAllByLight(@PathVariable("light") Light light) {
        return plantService.findAllByLight(light);
    }
    @GetMapping("/byL/{food}")
    public Collection<Plant> findAllByFood(@PathVariable("food") Food food) {
        return plantService.findAllByFood(food);
    }

}
