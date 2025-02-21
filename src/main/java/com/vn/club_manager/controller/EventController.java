package com.vn.club_manager.controller;

import com.vn.club_manager.model.EventDetail;
import com.vn.club_manager.model.EventDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.security.UserDetailsImpl;
import com.vn.club_manager.service.EventService;
import com.vn.club_manager.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;
    private final PageUtil pageUtil;

    @GetMapping("")
    public ResponseEntity<PageDto<EventDetail>> getEvents(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "12") int pageSize,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Pageable pageable = pageUtil.getPageable(pageIndex, pageSize, key, orderBy);
        PageDto<EventDetail> events = eventService.searchEvents(name, userDetails.getId(), pageable);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/of-member/{memId}")
    public ResponseEntity<PageDto<EventDto>> getEventsOfMember(
            @PathVariable Long memId,
            @RequestParam(value = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(value = "pageSize", defaultValue = "12") int pageSize,
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Pageable pageable = pageUtil.getPageable(pageIndex, pageSize, key, orderBy);
        PageDto<EventDto> events = eventService.getEventsOfMember(memId, pageable);
        return ResponseEntity.ok(events);
    }
}
