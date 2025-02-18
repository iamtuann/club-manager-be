package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.Club;
import com.vn.club_manager.entity.Event;
import com.vn.club_manager.entity.Member;
import com.vn.club_manager.exception.NoPermissionException;
import com.vn.club_manager.exception.ResourceNotFoundException;
import com.vn.club_manager.model.EventDto;
import com.vn.club_manager.model.PageDto;
import com.vn.club_manager.model.request.EventRequest;
import com.vn.club_manager.repository.ClubRepository;
import com.vn.club_manager.repository.EventRepository;
import com.vn.club_manager.repository.MemberRepository;
import com.vn.club_manager.service.AuthUserService;
import com.vn.club_manager.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AuthUserService authUserService;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;

    @Override
    public PageDto<EventDto> searchEvents(String name, Long clubId, Pageable pageable) {

        return null;
    }

    @Override
    public void create(Long clubId, EventRequest request, Long userId) {
        if (!authUserService.isClubManager(userId, clubId)) {
            throw new NoPermissionException("create event");
        }
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", clubId));
        Event event = new Event();
        updateEventFromRequest(request, event, clubId);
        eventRepository.save(event);
    }

    @Override
    public void update(Long eventId, Long clubId, EventRequest request, Long userId) {
        if (!authUserService.isClubManager(userId, clubId)) {
            throw new NoPermissionException("create event");
        }
        Event event = eventRepository.findByIdAndClubId(eventId, clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));
        updateEventFromRequest(request, event, clubId);
        eventRepository.save(event);
    }

    @Override
    public void delete(Long eventId, Long clubId, Long userId) {
        if (!authUserService.isClubManager(userId, clubId)) {
            throw new NoPermissionException("create event");
        }
        Event event = eventRepository.findByIdAndClubId(eventId, clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Event", "id", eventId));
        eventRepository.delete(event);
    }

    public void updateEventFromRequest(EventRequest request, Event event, Long clubId) {
        if ( request == null ) {
            return;
        }
        if (request.getName() != null) {
            event.setName(request.getName());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getEventDate() != null) {
            event.setEventDate(request.getEventDate());
        }
        if (request.getAddress() != null) {
            event.setAddress(request.getAddress());
        }
        if (request.getUserIds() != null) {
            List<Member> members = new ArrayList<Member>();
            for (Long id : request.getUserIds()) {
                Member member = memberRepository.findByClubIdAndUserId(clubId, id);
                if (member != null) {
                    members.add(member);
                }
            }
            event.setMembers(members);
        }
    }
}
