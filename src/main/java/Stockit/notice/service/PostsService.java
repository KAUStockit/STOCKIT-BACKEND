package Stockit.notice.service;

import Stockit.notice.domain.posts.Posts;
import Stockit.notice.domain.posts.PostsRepository;
import Stockit.notice.dto.PostsListResponseDto;
import Stockit.notice.dto.PostsResponseDto;
import Stockit.notice.dto.PostsSaveRequestDto;
import Stockit.notice.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.pool.TypePool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto){
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 공지가 없습니다. id = " + id)
                );
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 공지가 없습니다. id = " + id)
                );

        return new PostsResponseDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 공지가 없습니다., id = " + id)
                );

        postsRepository.delete(entity);
    }

    @Transactional
    public List<PostsListResponseDto> findAllDesc() {

        //repository.findAllDesc() -> List<Posts> 객체 return
        //PostsListsResponseDto 로 매핑해야 함.
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());

    }
}
