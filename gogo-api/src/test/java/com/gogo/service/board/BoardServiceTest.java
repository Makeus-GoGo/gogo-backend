package com.gogo.service.board;

import com.gogo.domain.board.*;
import com.gogo.domain.hashtag.HashTag;
import com.gogo.domain.hashtag.HashTagRepository;
import com.gogo.service.board.dto.request.CreateBoardRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardContentRepository boardContentRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    @AfterEach
    void cleanUp() {
        boardContentRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        hashTagRepository.deleteAll();
    }

    @Test
    void 새로운_고민_게시물을_등록한다() {
        // given
        String title = "title";
        String description = "description";
        String pictureUrl = "http://pict.com";
        BoardType type = BoardType.MULTI_CHOICE;
        List<String> contents = Arrays.asList("이게 좋을까?", "저게 좋을까?");
        List<String> hashTags = Arrays.asList("#음식", "#먹방");

        CreateBoardRequest request = CreateBoardRequest.testBuilder()
            .title(title)
            .description(description)
            .pictureUrl(pictureUrl)
            .type(type)
            .contents(contents)
            .hashTags(hashTags)
            .build();

        // when
        boardService.createBoard(request, 1L);

        // then
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).hasSize(1);
        assertBoard(boardList.get(0), title, description, type, pictureUrl);

        List<BoardContent> boardContentList = boardContentRepository.findAll();
        assertThat(boardContentList).hasSize(2);
        assertThat(boardContentList.get(0).getContent()).isEqualTo(contents.get(0));
        assertThat(boardContentList.get(1).getContent()).isEqualTo(contents.get(1));

        List<HashTag> hashTagList = hashTagRepository.findAll();
        assertThat(hashTagList).hasSize(2);
        assertThat(hashTagList.get(0).getTag()).isEqualTo(hashTags.get(0));
        assertThat(hashTagList.get(1).getTag()).isEqualTo(hashTags.get(1));
    }

    private void assertBoard(Board board, String title, String description, BoardType type, String pictureUrl) {
        assertThat(board.getTitle()).isEqualTo(title);
        assertThat(board.getDescription()).isEqualTo(description);
        assertThat(board.getType()).isEqualTo(type);
        assertThat(board.getPictureUrl()).isEqualTo(pictureUrl);
    }

}