package org.ikora.inspector.repository;

import org.ikora.inspector.model.CloneDispersionEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface CloneDispersionRepository extends CrudRepository<CloneDispersionEntity, Long> { }
