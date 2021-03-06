package com.gogo.service.board;

import com.gogo.domain.board.Board;
import com.gogo.domain.board.BoardRepository;
import com.gogo.service.board.dto.request.CreateBoardRequest;
import com.gogo.service.board.dto.response.BoardDetailInfoResponse;
import com.gogo.service.board.dto.response.BoardInfoResponse;
import com.gogo.service.hashtag.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final HashTagService hashTagService;

    @Transactional
    public BoardDetailInfoResponse createBoard(CreateBoardRequest request, Long memberId) {
        Board board = boardRepository.save(request.toEntity(memberId));
        List<String> hashTags = hashTagService.addHashTags(request.getHashTags(), board.getId(), memberId);
        return BoardDetailInfoResponse.of(board, hashTags);
    }

    @Transactional(readOnly = true)
    public List<BoardInfoResponse> getBoardsLessThanBoardId(Long lastBoardId, int size) {
        return lastBoardId == 0 ? getLatestBoards(size) : getLatestBoardLessThanId(lastBoardId, size);
    }

    private List<BoardInfoResponse> getLatestBoards(int size) {
        return boardRepository.findBoardsOrderByIdDesc(size).stream()
            .map(BoardInfoResponse::of)
            .collect(Collectors.toList());
    }

    private List<BoardInfoResponse> getLatestBoardLessThanId(Long lastBoardId, int size) {
        return boardRepository.findBoardsLessThanOrderByIdDescLimit(lastBoardId, size).stream()
            .map(BoardInfoResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardDetailInfoResponse getBoardInfo(Long boardId) {
        Board board = BoardServiceUtils.findBoardById(boardRepository, boardId);
        List<String> hashTags = hashTagService.retrieveHashTagsInBoard(boardId);
        return BoardDetailInfoResponse.of(board, hashTags);
    }

    @Transactional(readOnly = true)
    public List<BoardInfoResponse> searchBoardsByKeyword(String keyword) {
        return boardRepository.findBoardsByLikeTitle(keyword).stream()
            .map(BoardInfoResponse::of)
            .collect(Collectors.toList());
    }

}
