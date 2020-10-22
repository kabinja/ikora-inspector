package org.ikora.inspector.repository;

import org.ikora.inspector.model.ViolationEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface ViolationRepository  extends CrudRepository<ViolationEntity, Long> { }
