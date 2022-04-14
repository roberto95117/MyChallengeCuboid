package co.fullstacklabs.cuboid.challenge.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity representing Cuboids table
 *
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
@Entity
@Table(name = "CUBOIDS")
@Getter
@Setter
public class Cuboid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "WIDTH", nullable = false)
    private float width;
    @Column(name = "HEIGHT", nullable = false)
    private float height;
    @Column(name = "DEPTH", nullable = false)
    private float depth;
    @ManyToOne
    @JoinColumn(name = "BAG_ID", nullable = false)
    private Bag bag;
}
