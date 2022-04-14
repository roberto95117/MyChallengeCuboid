package co.fullstacklabs.cuboid.challenge.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing Bags table
 *
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
@Entity
@Table(name = "BAGS")
@Getter
@Setter
@NoArgsConstructor
public class Bag {
    public static final int TITLE_MAX_SIZE = 100;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "VOLUME", nullable = false)
    private double volume;
    @Column(name = "TITLE", nullable = false, length = TITLE_MAX_SIZE)
    private String title;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "bag")
    @Setter(AccessLevel.PRIVATE)
    private List<Cuboid> cuboids = new ArrayList<>();

    public Bag(String title, double volume) {
        this.setVolume(volume);
        this.setTitle(title);
    }

    /**
     * Returns an unmodifiable List containing the cuboids elements.
     *
     * @return List<Cuboid>
     */
    public List<Cuboid> getCuboids() {
        return List.copyOf(cuboids);
    }

    public void addCuboid(Cuboid cuboid) {
        cuboids.add(cuboid);
    }
}
