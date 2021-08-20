package com.example.xedd.controller;

import com.example.xedd.model.Item;
//import com.example.xedd.service.FileStorageService;
//import com.example.xedd.service.FileStorageServiceImpl;
import com.example.xedd.repository.ItemRepository;
import com.example.xedd.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping(value ="api/v1/items")
public class ItemController {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

//    @Autowired
//    FileStorageService fileStorageService;

    private List<Item> items = new ArrayList<>();


//    @PostMapping("addUserAndGetItemById/{username}/{id}")
//    public Item addItemToUser(@PathVariable("username") String username, @PathVariable("id") Long id){
//        return itemService.addItemToUser(id, username);
//    }
//    //Zaaim en projects
//    @PostMapping("addItem")
//    public Item saveItem(
//                           @RequestParam ("name") String name,
//                           @RequestParam ("description") String description,
//                           @RequestParam ("uploadedByUsername") String uploadedByUsername)
//    {
//                               Item item = new Item();
//                               item.setName(name);
//                               item.setDescription(description);
//                               item.setUser(uploadedByUsername);
//                               itemRepository.save(item);
//
//       //return new ResponseEntity<>("Product Saved With String - " + toPicture, HttpStatus.OK);
//        return  item;
//    }



    //codejava.net
//       @PostMapping("save")
//       public RedirectView saveItem(Item item,
//                       @RequestParam("toPicture")MultipartFile multipartFile) throws IOException{
//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        item.setToPicture(fileName);
//        Item savedItem = itemRepository.save(item);
//        String uploadDir = "item-photos/" + savedItem.getId();
//        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//        return new RedirectView("/items", true);
//       }

//GreenLearn
//    @PostMapping("upload")
//    ResponseEntity<ItemResponseDto> uploadFile(@RequestParam("toPicture") MultipartFile toPicture){
//
//        String fileName = itemService.uploadFile(toPicture);
//
//        ///http://localhost:8889/uploads/abc.jpg
//        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/uploads")
//                .path(fileName)
//                .toUriString();
//        String contentType = toPicture.getContentType();
//    };
//@PostMapping("")
//    public @ResponseBody ResponseEntity<?> saveItem(@RequestParam ("name") String name,
//                                         @RequestParam String ("description") description,
//                                         @RequestParam boolean isSeed, HttpServletRequest request,
//                                         final @RequestParam ("toPicture") MultipartFile toPicture) {
//        try {
//            itemService.uploadFile(toPicture);
//
//            Item item = new Item();
//            item.setName(name);
//            item.setDescription(description);
//            item.setSeed(isSeed);
//            //item.setUploadedByUsername(uploadedByUsername);
//            item.setToPicture(toPicture.getOriginalFilename());
//
//            itemService.saveItem(item);
//
//            return ResponseEntity.noContent().build();
//        } catch (Exception exception) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
    //build create item rest api
//    @PostMapping("/save")
//    public ResponseEntity<Item>saveItem(@RequestBody Item item){
//        return new ResponseEntity<Item>(itemService.saveItem(item), HttpStatus.CREATED);
//    }
//    @PostMapping("")
//    public ResponseEntity<Object>saveItem(@RequestParam String name,
//                                         @RequestParam String description,
//                                         //@RequestParam boolean isSeed,
//                                         @RequestParam MultipartFile toPicture) {
//        try {
//            itemService.uploadFile(toPicture);
//
//            Item item = new Item();
//            item.setName(name);
//            item.setDescription(description);
//           // item.setSeed(isSeed);
//            //item.setUploadedByUsername(uploadedByUsername);
//            item.setToPicture(toPicture.getOriginalFilename());
//
//            itemService.saveItem(item);
//
//            return ResponseEntity.noContent().build();
//        } catch (Exception exception) {
//            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
//        }
//    }
    @PostMapping("")
    public ResponseEntity<Object> createItem(@RequestBody Item item){
        itemService.saveItem(item);
        return ResponseEntity.ok("Added");
    }

    //uit voorbeeld met DTO
//    @PostMapping(value = "/files",
//           consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
//            produces = {MediaType.APPLICATION_JSON_VALUE} )
//    public ResponseEntity<Object> uploadFile(Method1RequestDto method1Dto) {
//        long newId = methode1Service.uploadFile(method1Dto);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(newId).toUri();
//
//        return ResponseEntity.created(location).body(location);
//    }
//
    @GetMapping
    public List<Item> getAll(){
        return itemService.getAllItems();
    }

    @GetMapping("{id}/toPicture")
    public ResponseEntity downloadFile(@PathVariable("id") Long id) {
        Resource resource = itemService.downloadFile(id);
        String fileName = itemService.getItem(id).getToPicture();
        String mediaType = "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable("id") long id) {
        return ResponseEntity.ok().body(itemService.getItemById(id));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateItem(@PathVariable("id") long id, @RequestBody Item item) {
        itemService.updateItem(id, item);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateItemPartial(@PathVariable("id") long id, @RequestBody Map<String, String> fields) {
        itemService.partialUpdateItem(id, fields);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/items/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

}

