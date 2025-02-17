package com.vn.club_manager.controller;

import com.vn.club_manager.model.ClubDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.ClubRequest;
import com.vn.club_manager.service.ClubService;
import com.vn.club_manager.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;
    private final PageUtil pageUtil;

    @GetMapping("")
    private ResponseEntity<PageDto<ClubDto>> searchClubs(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "orderBy", required = false) String orderBy
    ) {
        Pageable pageable = pageUtil.getPageable(pageIndex, pageSize, key, orderBy);
        PageDto<ClubDto> clubs = clubService.searchClubs(name, pageable);
        return ResponseEntity.ok(clubs);
    }

    @GetMapping("{id}")
    private ResponseEntity<ClubDto> getClub(@PathVariable Long id) {
        ClubDto club = clubService.findClubById(id);
        return ResponseEntity.ok(club);
    }

    @PostMapping("")
    private ResponseEntity<ClubDto> createClub(@RequestBody ClubRequest request) {
        ClubDto club = clubService.create(request);
        return ResponseEntity.ok(club);
    }

    @PutMapping("{id}")
    private ResponseEntity<ClubDto> updateClub(@PathVariable Long id, @RequestBody ClubRequest request) {
        ClubDto club = clubService.update(id, request);
        return ResponseEntity.ok(club);
    }
}
