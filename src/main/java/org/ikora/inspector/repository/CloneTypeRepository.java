package org.ikora.inspector.repository;

import org.ikora.inspector.model.CloneTypeEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface CloneTypeRepository extends CrudRepository<CloneTypeEntity, Long> { }
