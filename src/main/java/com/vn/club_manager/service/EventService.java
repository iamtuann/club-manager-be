package com.vn.club_manager.service;

import com.vn.club_manager.model.EventDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.EventRequest;
import org.springframework.data.domain.Pageable;

public interface EventService {
    PageDto<EventDto> searchEvents(String name, Long clubId, Pageable pageable);

    void create(Long clubId, EventRequest request, Long userId);

    void update(Long eventId, Long clubId, EventRequest request, Long userId);

    void delete(Long eventId, Long clubId, Long userId);
}
