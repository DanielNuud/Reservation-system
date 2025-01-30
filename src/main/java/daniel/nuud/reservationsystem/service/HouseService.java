package daniel.nuud.reservationsystem.service;

import daniel.nuud.reservationsystem.dto.HouseCreateDTO;
import daniel.nuud.reservationsystem.dto.HouseDTO;
import daniel.nuud.reservationsystem.dto.HouseUpdateDTO;
import daniel.nuud.reservationsystem.dto.UserDTO;
import daniel.nuud.reservationsystem.exception.ConflictException;
import daniel.nuud.reservationsystem.exception.InvalidRequestException;
import daniel.nuud.reservationsystem.exception.NotFoundException;
import daniel.nuud.reservationsystem.exception.ResourceNotFoundException;
import daniel.nuud.reservationsystem.mapper.HouseMapper;
import daniel.nuud.reservationsystem.mapper.UserMapper;
import daniel.nuud.reservationsystem.model.HouseEntity;
import daniel.nuud.reservationsystem.model.OrderEntity;
import daniel.nuud.reservationsystem.model.UserEntity;
import daniel.nuud.reservationsystem.repository.HouseRepository;
import daniel.nuud.reservationsystem.repository.OrderRepository;
import daniel.nuud.reservationsystem.repository.UserRepository;
import daniel.nuud.reservationsystem.util.ReferencedWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HouseService {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<HouseDTO> getAllHouses() {
        var houses = houseRepository.findAll();
        var result = houses.stream().map(house -> houseMapper.toDTO(house))
                .toList();
        return result;
    }

    public HouseDTO findHouseById(Long id) {
        var house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No house found with id: " + id));
        return houseMapper.toDTO(house);
    }

    public List<HouseDTO> getHousesByUser(UserEntity user) {
        var houses = houseRepository.findByUserId(user.getId());
        return houses.stream().map(house -> houseMapper.toDTO(house))
                .collect(Collectors.toList());
    }

    public HouseDTO createHouse(HouseCreateDTO houseCreateDTO, UserDetails userDetails) {
        if (houseRepository.existsByAddress(houseCreateDTO.getAddress())) {
            throw new ConflictException("House already exists with address: " + houseCreateDTO.getAddress());
        }

        if (houseCreateDTO.getRooms() <= 0 || houseCreateDTO.getArea() <= 0) {
            throw new InvalidRequestException("Area and rooms must be greater than 0");
        }

        var house = houseMapper.toEntity(houseCreateDTO);
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with email: " + userDetails.getUsername() + " not found"));

        house.setUser(user);
        house.setImagePaths(houseCreateDTO.getImagePaths());
        System.out.println("Saved image path: " + houseCreateDTO.getImagePaths().get(0));
        houseRepository.save(house);

        return houseMapper.toDTO(house);
    }

    public HouseDTO updateHouse(Long id, HouseUpdateDTO houseUpdateDTO) {
        var house = houseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No house found with id: " + id));

        if (houseRepository.existsByAddress(house.getAddress())) {
            throw new ConflictException("House already exists with address: " + house.getAddress());
        }

        houseMapper.updateHouse(houseUpdateDTO, house);
        houseRepository.save(house);
        return houseMapper.toDTO(house);
    }

    public void deleteHouse(Long id) {
        if (!houseRepository.existsById(id)) {
            throw new ResourceNotFoundException("No house found with id: " + id);
        }

        houseRepository.deleteById(id);
    }

    public List<HouseDTO> findAvailableHouses(String city, Instant startReservation, Instant endReservation) {
        List<HouseEntity> availableHouses = houseRepository.findAvailableHouses(city, startReservation, endReservation);
        return availableHouses.stream()
                .map(houseMapper::toDTO)
                .collect(Collectors.toList());
    }



    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final HouseEntity house = houseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final OrderEntity houseOrder = orderRepository.findFirstByHouse(house);
        if (houseOrder != null) {
            referencedWarning.setKey("house.order.house.referenced");
            referencedWarning.addParam(houseOrder.getId());
            return referencedWarning;
        }
        return null;
    }
}
