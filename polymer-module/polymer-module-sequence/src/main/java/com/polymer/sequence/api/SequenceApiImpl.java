package com.polymer.sequence.api;

import com.polymer.api.sequence.SequenceApi;
import com.polymer.api.sequence.dto.DbSeqDTO;
import com.polymer.framework.common.utils.DateUtils;
import com.polymer.sequence.service.SequenceService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class SequenceApiImpl implements SequenceApi {
    @Resource
    private SequenceService sequenceService;
    @Override
    public String nextNo(DbSeqDTO dto) {
        return String.format("%s%" + dto.getRepair(), DateUtils.format(LocalDateTime.now(), dto.getPureDatetimeFormat()),
                sequenceService.nextValue(dto.getBizName(), dto.getStep(), dto.getStepStart()));    }
}
