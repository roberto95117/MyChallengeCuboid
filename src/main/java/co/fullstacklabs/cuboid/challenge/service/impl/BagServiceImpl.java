package co.fullstacklabs.cuboid.challenge.service.impl;

import co.fullstacklabs.cuboid.challenge.dto.BagDTO;
import co.fullstacklabs.cuboid.challenge.dto.NewBagDTO;
import co.fullstacklabs.cuboid.challenge.exception.ResourceNotFoundException;
import co.fullstacklabs.cuboid.challenge.model.Bag;
import co.fullstacklabs.cuboid.challenge.repository.BagRepository;
import co.fullstacklabs.cuboid.challenge.service.BagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation class for BagService
 *
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
@Service
public class BagServiceImpl implements BagService {
   
    private BagRepository repository;   
    private ModelMapper mapper;


    @Autowired
    public BagServiceImpl(BagRepository repository, ModelMapper mapper){
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Create and persist a new bag
     * @param newBagDTO - DTO representing a new Bag
     * @return BagDTO
     */
    @Override
    @Transactional
    public BagDTO create(NewBagDTO newBagDTO) {
        Bag bag = new Bag(newBagDTO.getTitle(), newBagDTO.getVolume());
        bag = repository.save(bag);
        return mapper.map(bag, BagDTO.class);
    }

    /**
     * List all existing bags
     * @return List<BagDTO>
     */
    @Override
    @Transactional(readOnly = true)
    public List<BagDTO> getAll() {
        List<Bag> bags = repository.findAll();
        return bags.stream().map(bag -> mapper.map(bag, BagDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Find a bag by its ID
     * @param id - Bag ID
     * @return BagDTO
     */
    @Override
    @Transactional(readOnly = true)
    public BagDTO findById(long id) {
        Bag bag = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Bag not found"));
        return mapper.map(bag, BagDTO.class);
    }
}
