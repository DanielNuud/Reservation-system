package daniel.nuud.reservationsystem.house.service;

import daniel.nuud.reservationsystem.house.dto.HouseCreateDTO;
import daniel.nuud.reservationsystem.house.dto.HouseDTO;
import daniel.nuud.reservationsystem.house.dto.HouseUpdateDTO;
import daniel.nuud.reservationsystem.house.mapper.HouseMapper;
import daniel.nuud.reservationsystem.house.repository.HouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMapper houseMapper;

    public List<HouseDTO> getAllHouses() {
        var houses = houseRepository.findAll();
        var result = houses.stream().map(house -> houseMapper.toDTO(house))
                .toList();
        return result;
    }

    public HouseDTO findHouseById(Long id) {
        var house = houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No house found with id: " + id));
        return houseMapper.toDTO(house);
    }

    public HouseDTO createHouse(HouseCreateDTO houseCreateDTO) {
        var house = houseMapper.toEntity(houseCreateDTO);
        houseRepository.save(house);
        return houseMapper.toDTO(house);
    }

    public HouseDTO updateHouse(Long id, HouseUpdateDTO houseUpdateDTO) {
        var house = houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No house found with id: " + id));
        houseMapper.updateHouse(houseUpdateDTO, house);
        houseRepository.save(house);
        return houseMapper.toDTO(house);
    }

    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }
}
