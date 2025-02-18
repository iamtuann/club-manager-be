package com.vn.club_manager.controller;

import com.vn.club_manager.model.BoardDto;
import com.vn.club_manager.model.ClubDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.AddMemberRequest;
import com.vn.club_manager.model.request.BoardRequest;
import com.vn.club_manager.model.request.ClubRequest;
import com.vn.club_manager.model.request.EventRequest;
import com.vn.club_manager.security.UserDetailsImpl;
import com.vn.club_manager.service.BoardService;
import com.vn.club_manager.service.ClubService;
import com.vn.club_manager.service.EventService;
import com.vn.club_manager.service.MemberService;
import com.vn.club_manager.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubService clubService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final EventService eventService;
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

    @PostMapping("{id}/add-member")
    private ResponseEntity<?> addMember(@PathVariable Long id, @RequestBody AddMemberRequest request) {
        memberService.AddMember(id, request);
        return ResponseEntity.ok("Add member successfully");
    }

    @PostMapping("{id}/set-president")
    private ResponseEntity<?> setPresident(@PathVariable Long id, @RequestBody Long userId) {
        clubService.setPresident(id, userId);
        return ResponseEntity.ok("Set President successfully");
    }

    @GetMapping("{id}/boards")
    private ResponseEntity<List<BoardDto>> getBoards(@PathVariable Long id) {
        List<BoardDto> boards = boardService.findBoardsByClubId(id);
        return ResponseEntity.ok(boards);
    }

    @PostMapping("{id}/boards")
    private ResponseEntity<?> createBoard(@PathVariable Long id, @RequestBody BoardRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.create(id, request, userDetails.getId());
        return ResponseEntity.ok("Create board successfully");
    }

    @DeleteMapping("{id}/boards/{boardId}")
    private ResponseEntity<?> deleteBoard(@PathVariable Long id, @PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.delete(id, boardId, userDetails.getId());
        return ResponseEntity.ok("Delete board successfully");
    }

    @PostMapping("{id}/events")
    private ResponseEntity<?> createEvent(@PathVariable Long id, @RequestBody EventRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        eventService.create(id, request, userDetails.getId());
        return ResponseEntity.ok("Create event successfully");
    }

    @PutMapping("{id}/events/{eventId}")
    private ResponseEntity<?> createEvent(@PathVariable Long id, @PathVariable Long eventId, @RequestBody EventRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        eventService.update(eventId, id, request, userDetails.getId());
        return ResponseEntity.ok("Update event successfully");
    }

    @DeleteMapping("{id}/boards/{eventId}")
    private ResponseEntity<?> deleteEvent(@PathVariable Long id, @PathVariable Long eventId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        eventService.delete(eventId, id, userDetails.getId());
        return ResponseEntity.ok("Delete event successfully");
    }
}
