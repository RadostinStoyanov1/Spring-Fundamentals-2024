package com.philately.service;

import com.philately.model.entity.Paper;

public interface PaperService {

    public Paper findPaperByName(String name);
}
