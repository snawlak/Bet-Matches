package com.bet.matches.api.core.stadium;

import com.bet.matches.api.core.StadiumDto;
import com.bet.matches.api.core.TeamDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final ModelMapper modelMapper;

    public List<StadiumDto> getAllStadiums() {
        return stadiumRepository.getAllStadiums().stream()
                .map(stadium -> modelMapper.map(stadium, StadiumDto.class))
                .collect(Collectors.toList());
    }

    public StadiumDto getStadium(final int id) {
        return stadiumRepository.getStadium(id)
                .map(stadium -> modelMapper.map(stadium, StadiumDto.class))
                .orElseThrow(IllegalStateException::new);
    }

    public StadiumDto addStadium(final StadiumDto stadiumDto) {
        Stadium stadium = modelMapper.map(stadiumDto, Stadium.class);
        stadium = stadiumRepository.addOrUpdateStadium(stadium);
        return modelMapper.map(stadium, StadiumDto.class);
    }

    @Transactional
    public StadiumDto updateStadium(final int id, final StadiumDto newStadium) {
        final Stadium stadium = stadiumRepository.getStadium(id)
                .orElseThrow(IllegalStateException::new);

        stadium.setName(newStadium.getName());
        stadium.setCapacity(newStadium.getCapacity());
        stadium.setCity(newStadium.getCity());
        stadium.setCountry(newStadium.getCountry());

        return modelMapper.map(stadium, StadiumDto.class);
    }

    public boolean deleteStadium(final int id) {
        if (stadiumRepository.getStadium(id).isPresent()) {
            stadiumRepository.deleteStadium(id);
            return true;
        }
        return false;
    }
}
