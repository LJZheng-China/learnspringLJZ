package org.csu.mypetstoressm.persistence;

import org.csu.mypetstoressm.domain.Sequence;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceMapper {
    Sequence getSequence(Sequence sequence);
    void updateSequence(Sequence sequence);
}
