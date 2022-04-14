package co.fullstacklabs.cuboid.challenge.service;

import co.fullstacklabs.cuboid.challenge.dto.BagDTO;
import co.fullstacklabs.cuboid.challenge.dto.NewBagDTO;

import java.util.List;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
public interface BagService {
    BagDTO create(NewBagDTO newBagDTO);

    List<BagDTO> getAll();

    BagDTO findById(long id);
}
