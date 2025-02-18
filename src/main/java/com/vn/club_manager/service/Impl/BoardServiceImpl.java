package com.vn.club_manager.service.Impl;

import com.vn.club_manager.entity.Board;
import com.vn.club_manager.entity.Club;
import com.vn.club_manager.exception.NoPermissionException;
import com.vn.club_manager.exception.ResourceNotFoundException;
import com.vn.club_manager.model.BoardDto;
import com.vn.club_manager.model.request.BoardRequest;
import com.vn.club_manager.repository.BoardRepository;
import com.vn.club_manager.repository.ClubRepository;
import com.vn.club_manager.service.AuthUserService;
import com.vn.club_manager.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;
    private final ClubRepository clubRepository;
    private final AuthUserService authUserService;

    @Override
    public void create(Long clubId, BoardRequest request, Long userId) {
        if (!authUserService.hasClubManagementRights(userId, clubId)) {
            throw new NoPermissionException("create board");
        }
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", clubId));
        Board board = new Board();
        board.setName(request.getName());
        board.setDescription(request.getDescription());
        board.setClub(club);
        boardRepository.save(board);
    }

    @Override
    public List<BoardDto> findBoardsByClubId(Long clubId) {
        clubRepository.findById(clubId)
                .orElseThrow(() -> new ResourceNotFoundException("Club", "id", clubId));
        List<Board> boards = boardRepository.findAllByClubId(clubId);
        return boards.stream().map(BoardDto::new).collect(Collectors.toList());
    }

    @Override
    public void delete(Long clubId, Long boardId, Long userId) {
        if (!authUserService.hasClubManagementRights(userId, clubId)) {
            throw new NoPermissionException("delete this board");
        }
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));
        boardRepository.delete(board);
    }
}
