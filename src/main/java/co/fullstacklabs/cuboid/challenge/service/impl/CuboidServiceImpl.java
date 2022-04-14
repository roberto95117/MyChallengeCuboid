package co.fullstacklabs.cuboid.challenge.service.impl;

import co.fullstacklabs.cuboid.challenge.dto.CuboidDTO;
import co.fullstacklabs.cuboid.challenge.exception.ResourceNotFoundException;
import co.fullstacklabs.cuboid.challenge.exception.UnprocessableEntityException;
import co.fullstacklabs.cuboid.challenge.model.Bag;
import co.fullstacklabs.cuboid.challenge.model.Cuboid;
import co.fullstacklabs.cuboid.challenge.repository.BagRepository;
import co.fullstacklabs.cuboid.challenge.repository.CuboidRepository;
import co.fullstacklabs.cuboid.challenge.service.CuboidService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation class for BagService
 *
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
@Service
public class CuboidServiceImpl implements CuboidService {

    private final CuboidRepository repository;
    private final BagRepository bagRepository;
    private final ModelMapper mapper;

    @Autowired
    public CuboidServiceImpl(@Autowired CuboidRepository repository,
                             BagRepository bagRepository, ModelMapper mapper) {
        this.repository = repository;
        this.bagRepository = bagRepository;
        this.mapper = mapper;
    }

    /**
     * Create a new cuboid and add it to its bag checking the bag available capacity.
     *
     * @param cuboidDTO DTO with cuboid properties to be persisted
     * @return CuboidDTO with the data created
     */
    @Override
    @Transactional
    public CuboidDTO create(CuboidDTO cuboidDTO) {
        Bag bag = getBagById(cuboidDTO.getBagId());
        Cuboid cuboid = mapper.map(cuboidDTO, Cuboid.class);
        cuboid.setBag(bag);
        if(bag.getVolume() < cuboidDTO.getVolume()) throw new UnprocessableEntityException("Bag with not enough capacity");
        cuboid = repository.save(cuboid);
        return mapper.map(cuboid, CuboidDTO.class);
    }

    /**
     * List all cuboids
     * @return List<CuboidDTO>
     */
    @Override
    @Transactional(readOnly = true)
    public List<CuboidDTO> getAll() {
        List<Cuboid> cuboids = repository.findAll();
        return cuboids.stream().map(bag -> mapper.map(bag, CuboidDTO.class))
                .collect(Collectors.toList());
    }
    private Bag getBagById(long bagId) {
        return bagRepository.findById(bagId).orElseThrow(() -> new ResourceNotFoundException("Bag not found"));
    }

	@Override
	public CuboidDTO update(CuboidDTO dto) {
		Bag bag = getBagById(dto.getBagId());
        Cuboid cuboid = mapper.map(dto, Cuboid.class);
        cuboid.setBag(bag);
        if(bag.getVolume() < dto.getVolume()) throw new UnprocessableEntityException("Bag with not enough capacity");
        if(this.getById(cuboid.getId()).getId() > 0) {
            cuboid = repository.save(cuboid);        	
        }
        return mapper.map(cuboid, CuboidDTO.class);
	}

	@Override
	public Cuboid getById(Long id) {
		Optional<Cuboid> cuboid = repository.findById(id);
		if(cuboid.isPresent()) {
			return cuboid.get();
		}else {
			throw new ResourceNotFoundException("Cuboid not found");
		}
	}

	@Override
	public Boolean delete(Long id) {
		if(this.getById(id).getId() > 0) {
			repository.deleteById(id);
		}
		
		return repository.findById(id).isPresent();
	}


  
}
