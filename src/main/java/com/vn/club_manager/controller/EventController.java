package com.vn.club_manager.controller;

import com.vn.club_manager.model.EventDetail;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.security.UserDetailsImpl;
import com.vn.club_manager.service.EventService;
import com.vn.club_manager.utils.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
