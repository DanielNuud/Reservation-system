package daniel.nuud.reservationsystem.house.controller;

import daniel.nuud.reservationsystem.house.dto.HouseCreateDTO;
import daniel.nuud.reservationsystem.house.dto.HouseDTO;
import daniel.nuud.reservationsystem.house.dto.HouseUpdateDTO;
import daniel.nuud.reservationsystem.house.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HouseController {

    @Autowired
    private HouseService houseService;



    @GetMapping(path = "/houses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<HouseDTO>> getAllHouses() {
        var houses = houseService.getAllHouses();
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(houses.size()))
                .body(houses);
    }

    @GetMapping(path = "/houses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HouseDTO getHouseById(@PathVariable Long id) {
        return houseService.findHouseById(id);
    }

    @PostMapping(path = "/houses")
    @ResponseStatus(HttpStatus.CREATED)
    public HouseDTO createHouse(@RequestBody HouseCreateDTO houseCreateDTO) {
        var house = houseService.createHouse(houseCreateDTO);
        return house;
    }

    @PutMapping(path = "/houses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HouseDTO updateHouse(@PathVariable Long id, @RequestBody HouseUpdateDTO houseUpdateDTO) {
        return houseService.updateHouse(id, houseUpdateDTO);
    }

    @DeleteMapping(path = "/houses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
    }
}