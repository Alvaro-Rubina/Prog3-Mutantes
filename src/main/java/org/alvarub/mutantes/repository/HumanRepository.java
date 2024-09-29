package org.alvarub.mutantes.repository;

import org.alvarub.mutantes.model.entity.Human;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HumanRepository extends JpaRepository<Long, Human> {
}
