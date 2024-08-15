package com.philately.init;

import com.philately.model.entity.Paper;
import com.philately.model.entity.PaperEnum;
import com.philately.repository.PaperRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class InitPapers implements CommandLineRunner {

    private final Map<PaperEnum, String> paperDescriptions = Map.of(
            PaperEnum.WOVE_PAPER, "Has an even texture without any particular distinguishing features.",
            PaperEnum.LAID_PAPER, "When held up to the light, shows parallel lines of greater or less width running across the stamp.",
            PaperEnum.GRANITE_PAPER, "Has tiny specks of coloured fibre in it, which can usually be seen with the naked eye."
    );

    private final PaperRepository paperRepository;

    public InitPapers(PaperRepository paperRepository) {
        this.paperRepository = paperRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        long count = paperRepository.count();

        if (count > 0) {
            return;
        }
        for (PaperEnum paperEnum : paperDescriptions.keySet()) {
            Paper paper = new Paper()
                    .setPaperName(paperEnum)
                    .setDescription(paperDescriptions.get(paperEnum));

            paperRepository.save(paper);
        }
    }
}
