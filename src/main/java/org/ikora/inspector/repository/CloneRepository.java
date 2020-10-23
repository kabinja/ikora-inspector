package org.ikora.inspector.repository;

import org.ikora.inspector.model.CloneClusterEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface CloneRepository extends CrudRepository<CloneClusterEntity, Long> { }
