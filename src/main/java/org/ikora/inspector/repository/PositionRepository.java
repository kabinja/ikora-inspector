package org.ikora.inspector.repository;

import org.ikora.inspector.model.PositionEntity;
import org.ikora.inspector.model.ProjectEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Lazy
@Repository
public interface PositionRepository extends CrudRepository<PositionEntity, Long> {
    Optional<PositionEntity> findByStartAndEndAndFileAndProject(String start, String end, String file, ProjectEntity project);
}
