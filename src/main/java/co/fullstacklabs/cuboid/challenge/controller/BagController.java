package co.fullstacklabs.cuboid.challenge.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.fullstacklabs.cuboid.challenge.dto.BagDTO;
import co.fullstacklabs.cuboid.challenge.dto.NewBagDTO;
import co.fullstacklabs.cuboid.challenge.service.BagService;
/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
@RestController
@RequestMapping("/bags")
public class BagController {


    private BagService service;

    @Autowired
    public BagController(BagService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BagDTO> create(@RequestBody @Valid NewBagDTO resource) {
        BagDTO bag = service.create(resource);
        return new ResponseEntity<>(bag, HttpStatus.CREATED);
    }

    @GetMapping
    public List<BagDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<BagDTO> getById(@PathVariable("id") long bagId) {
        BagDTO bagDTO = service.findById(bagId);
        return new ResponseEntity<>(bagDTO, HttpStatus.OK);
    }
}
