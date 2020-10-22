package org.ikora.inspector.repository;

import org.ikora.inspector.model.KeywordTypeEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Lazy
@Repository
public interface KeywordTypeRepository extends CrudRepository<KeywordTypeEntity, Long> {
}
