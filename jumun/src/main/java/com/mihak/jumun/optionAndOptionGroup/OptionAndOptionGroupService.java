package com.mihak.jumun.optionAndOptionGroup;

import com.mihak.jumun.entity.Option;
import com.mihak.jumun.entity.OptionAndOptionGroup;
import com.mihak.jumun.entity.OptionGroup;
import com.mihak.jumun.option.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptionAndOptionGroupService {
    private final OptionAndOptionGroupRepository optionAndOptionGroupRepository;
    private final OptionService optionService;

    // 옵션 그룹에 옵션 추가, 중간테이블의 정보가 담긴 리스트를 리턴.
    public void addOption(OptionGroup optionGroup, Option option) {

        OptionAndOptionGroup optionAndOptionGroup = OptionAndOptionGroup.builder()
                .option(option)
                .optionGroup(optionGroup)
                .build();
        optionAndOptionGroupRepository.save(optionAndOptionGroup);
    }

    public List<Option> findAllByOptionGroup(OptionGroup optionGroup) {
        List<OptionAndOptionGroup> allByOptionGroup = optionAndOptionGroupRepository.findAllByOptionGroup(optionGroup);
        List<Option> options = new ArrayList<>();
        for(OptionAndOptionGroup optionAndOptionGroup : allByOptionGroup) {
            options.add(optionAndOptionGroup.getOption());
        }
        return options;
    }

    public void remove(Long optionId) {
        Option option = optionService.findById(optionId);
        optionAndOptionGroupRepository.deleteAllByOption(option);
    }

    @Transactional
    public void deleteAllByOptionGroup(OptionGroup optionGroup) {
        optionAndOptionGroupRepository.deleteAllByOptionGroup(optionGroup);
    }

    public void deleteAllByOption(Option option) {
        optionAndOptionGroupRepository.deleteAllByOption(option);
    }
}