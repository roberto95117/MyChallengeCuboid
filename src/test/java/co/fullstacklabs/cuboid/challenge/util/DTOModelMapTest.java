package co.fullstacklabs.cuboid.challenge.util;

import co.fullstacklabs.cuboid.challenge.dto.BagDTO;
import co.fullstacklabs.cuboid.challenge.dto.CuboidDTO;
import co.fullstacklabs.cuboid.challenge.model.Bag;
import co.fullstacklabs.cuboid.challenge.model.Cuboid;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class DTOModelMapTest {

    private ModelMapper mapper = new ModelMapper();
    private BagDTO bagDTO;
    private CuboidDTO cuboidDTO1;
    private CuboidDTO cuboidDTO2;

    @BeforeEach
    void setup() {
        bagDTO = BagDTO.builder().id(1L).title("title1").volume(30d).build();
        cuboidDTO1 = CuboidDTO.builder().bagId(bagDTO.getId()).id(2L).depth(1f).height(5f).width(3f).build();
        cuboidDTO2 = CuboidDTO.builder().bagId(bagDTO.getId()).id(3L).depth(1f).height(5f).width(3f).build();
        bagDTO.setCuboids(List.of(cuboidDTO1, cuboidDTO2));
    }
    @Test
    void testBagDTOMapping() {
        Bag bag = mapper.map(this.bagDTO, Bag.class);
        Assertions.assertThat(bag.getId()).isEqualTo(this.bagDTO.getId());
        Assertions.assertThat(bag.getTitle()).isEqualTo(this.bagDTO.getTitle());
        Assertions.assertThat(bag.getVolume()).isEqualTo(this.bagDTO.getVolume());
        Assertions.assertThat(bag.getCuboids()).isEmpty();

    }
    @Test
    void testCuboidDTOMapping() {
        Cuboid cuboid = mapper.map(this.cuboidDTO1, Cuboid.class);
        Assertions.assertThat(cuboid.getId()).isEqualTo(this.cuboidDTO1.getId());
        Assertions.assertThat(cuboid.getWidth()).isEqualTo(this.cuboidDTO1.getWidth());
        Assertions.assertThat(cuboid.getDepth()).isEqualTo(this.cuboidDTO1.getDepth());
        Assertions.assertThat(cuboid.getHeight()).isEqualTo(this.cuboidDTO1.getHeight());
        Assertions.assertThat(cuboid.getBag().getId()).isEqualTo(this.cuboidDTO1.getBagId());

    }


}