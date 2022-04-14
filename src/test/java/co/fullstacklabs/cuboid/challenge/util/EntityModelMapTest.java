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
import testbuilders.BagTestBuilder;
import testbuilders.CuboidTestBuilder;


@ExtendWith(SpringExtension.class)
class EntityModelMapTest {

    private final ModelMapper mapper = new ModelMapper();
    private Bag bag;
    private Cuboid cuboid1;
    private Cuboid cuboid2;

    @BeforeEach
    void setup() {
        bag = BagTestBuilder.builder().build();
        cuboid1 = CuboidTestBuilder.builder().bag(bag).id(2L).depth(1f).height(5f).width(3f).build();
        cuboid2 = CuboidTestBuilder.builder().bag(bag).id(3L).depth(2f).height(4f).width(3f).build();
        bag.addCuboid(cuboid1);
        bag.addCuboid(cuboid2);
    }
    @Test
    void testBagMapping() {
        BagDTO bagDTO = mapper.map(bag, BagDTO.class);
        Assertions.assertThat(bagDTO.getId()).isEqualTo(bag.getId());
        Assertions.assertThat(bagDTO.getTitle()).isEqualTo(bag.getTitle());
        Assertions.assertThat(bagDTO.getVolume()).isEqualTo(bag.getVolume());
        Assertions.assertThat(bagDTO.getCuboids()).map(CuboidDTO::getWidth).contains(cuboid1.getWidth(), cuboid2.getWidth());
        Assertions.assertThat(bagDTO.getPayloadVolume()).isEqualTo(39d);
        Assertions.assertThat(bagDTO.getAvailableVolume()).isEqualTo(11d);

    }
    @Test
    void testCuboidMapping() {
        CuboidDTO cuboidDTO = mapper.map(cuboid1, CuboidDTO.class);
        Assertions.assertThat(cuboidDTO.getId()).isEqualTo(cuboid1.getId());
        Assertions.assertThat(cuboidDTO.getWidth()).isEqualTo(cuboid1.getWidth());
        Assertions.assertThat(cuboidDTO.getDepth()).isEqualTo(cuboid1.getDepth());
        Assertions.assertThat(cuboidDTO.getHeight()).isEqualTo(cuboid1.getHeight());
        Assertions.assertThat(cuboidDTO.getVolume()).isEqualTo(15d);
        Assertions.assertThat(cuboidDTO.getBagId()).isEqualTo(cuboid1.getBag().getId());
    }


}