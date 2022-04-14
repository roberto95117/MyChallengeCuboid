package testbuilders;

import co.fullstacklabs.cuboid.challenge.model.Bag;
import co.fullstacklabs.cuboid.challenge.model.Cuboid;
import lombok.Builder;

public class CuboidTestBuilder {

    @Builder
    public static Cuboid cuboid(Long id, float width, float height,
                                float depth, Bag bag) {
        Cuboid cuboid = new Cuboid();
        cuboid.setId(id);
        cuboid.setWidth(width);
        cuboid.setHeight(height);
        cuboid.setDepth(depth);
        cuboid.setBag(bag);
        return cuboid;
    }

    public static class CuboidBuilder {
        private float width = 3f;
        private float height = 2f;
        private float depth = 3f;
        private Bag bag = BagTestBuilder.builder().id(1L).build();
    }
}
