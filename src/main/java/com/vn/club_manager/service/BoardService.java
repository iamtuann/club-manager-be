package com.vn.club_manager.service;

import com.vn.club_manager.model.BoardDto;
import com.vn.club_manager.model.request.BoardRequest;

import java.util.List;

public interface BoardService {
    void create(Long clubId, BoardRequest request, Long userId);

    void update(Long clubId, Long boardId, BoardRequest request, Long userId);

    List<BoardDto> findBoardsByClubId(Long clubId);

    void delete(Long clubId, Long boardId, Long userId);
}
