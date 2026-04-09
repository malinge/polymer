package com.polymer.api.sequence;

import com.polymer.api.sequence.dto.DbSeqDTO;

public interface SequenceApi {
    String nextNo(DbSeqDTO dto);
}
