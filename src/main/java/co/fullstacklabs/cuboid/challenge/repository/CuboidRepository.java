package co.fullstacklabs.cuboid.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.fullstacklabs.cuboid.challenge.model.Cuboid;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
@Repository
public interface CuboidRepository extends JpaRepository<Cuboid, Long> {
}
