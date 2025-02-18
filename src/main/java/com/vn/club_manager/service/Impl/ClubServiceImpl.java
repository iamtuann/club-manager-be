package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.Club;
import com.vn.club_manager.entity.User;
import com.vn.club_manager.enums.EStatus;
import com.vn.club_manager.exception.APIException;
import com.vn.club_manager.exception.ResourceNotFoundException;
import com.vn.club_manager.model.ClubDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.ClubRequest;
import com.vn.club_manager.repository.AuthUserRepository;
import com.vn.club_manager.repository.ClubRepository;
import com.vn.club_manager.repository.MemberRepository;
import com.vn.club_manager.service.ClubService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;
    private final AuthUserRepository authUserRepository;
    private final MemberRepository memberRepository;

    @Override
    public ClubDto findClubById(Long id) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", id));
        return new ClubDto(club);
    }

    @Override
    public PageDto<ClubDto> searchClubs(String name, Pageable pageable) {
        Page<Club> pages = clubRepository.searchClubs(name, pageable);
        List<ClubDto> clubs = pages.stream().map(ClubDto::new).toList();
        return new PageDto<>(clubs, pages);
    }

    @Override
    public ClubDto create(ClubRequest request) {
        Club club = new Club();
        club.setStatus(EStatus.ACTIVE.getValue());
        UpdateClubFromRequest(request, club);
        club = clubRepository.save(club);
        return new ClubDto(club);
    }

    @Override
    public ClubDto update(long id, ClubRequest request) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", id));
        UpdateClubFromRequest(request, club);
        club = clubRepository.save(club);
        return new ClubDto(club);
    }

    @Override
    public ClubDto setPresident(long id, Long userId) {
        Club club = clubRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", id));
        User user = authUserRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        if (memberRepository.existsByClubIdAndMemberId(id, userId)) {
            club.setManager(user);
        } else {
            throw new APIException(HttpStatus.BAD_REQUEST, "User is not a member of this Club");
        }
        return null;
    }

    @Override
    public void delete(long id, long userId) {

    }

    public void UpdateClubFromRequest(ClubRequest request, Club club) {
        if ( request == null ) {
            return;
        }
        if ( request.getName() != null ) {
            club.setName( request.getName() );
        }
        if ( request.getDescription() != null ) {
            club.setDescription( request.getDescription() );
        }
        if ( request.getStatus() != null ) {
            club.setStatus( request.getStatus() );
        }
    }
}
