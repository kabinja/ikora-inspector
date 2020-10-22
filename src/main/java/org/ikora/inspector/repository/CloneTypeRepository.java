package org.ikora.inspector.repository;

import org.ikora.inspector.entity.CloneType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloneTypeRepository extends CrudRepository<CloneType, Long> { }
