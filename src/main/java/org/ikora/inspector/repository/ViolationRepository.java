package org.ikora.inspector.repository;

import org.ikora.inspector.entity.Violation;
import org.springframework.data.repository.CrudRepository;

public interface ViolationRepository extends CrudRepository<Violation, Long> { }
