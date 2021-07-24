package Stockit.notice.controller;

import Stockit.notice.dto.PostsListResponseDto;
import Stockit.notice.dto.PostsResponseDto;
import Stockit.notice.dto.PostsSaveRequestDto;
import Stockit.notice.dto.PostsUpdateRequestDto;
import Stockit.notice.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostsController {
    private final PostsService postsService;

    //공지사항 저장
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    //공지사항 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    //공지사항 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    //공지사항 삭제
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

    //공지사항 리스트 보여주기
    @GetMapping("/api/v1/posts/list")
    public List<PostsListResponseDto> findAllDesc(){
        return postsService.findAllDesc();
    }

}
