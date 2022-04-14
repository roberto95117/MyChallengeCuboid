package co.fullstacklabs.cuboid.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.fullstacklabs.cuboid.challenge.model.Bag;

/**
 * @author FullStack Labs
 * @version 1.0
 * @since 2021-10-22
 */
@Repository
public interface BagRepository extends JpaRepository<Bag, Long> {
}