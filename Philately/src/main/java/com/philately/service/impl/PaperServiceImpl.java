package com.philately.service.impl;

import com.philately.model.entity.Paper;
import com.philately.model.entity.PaperEnum;
import com.philately.repository.PaperRepository;
import com.philately.service.PaperService;
import org.springframework.stereotype.Service;

@Service
public class PaperServiceImpl implements PaperService {

    private final PaperRepository paperRepository;

    public PaperServiceImpl(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }

    @Override
    public Paper findPaperByName(String name) {
        return paperRepository.findByPaperName(PaperEnum.valueOf(name)).orElse(null);
    }
}
