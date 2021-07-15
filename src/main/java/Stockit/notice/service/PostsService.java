package Stockit.notice.service;

import Stockit.notice.domain.posts.Posts;
import Stockit.notice.domain.posts.PostsRepository;
import Stockit.notice.dto.PostsResponseDto;
import Stockit.notice.dto.PostsSaveRequestDto;
import Stockit.notice.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 공지가 없습니다. id = " + id)
                );

        return new PostsResponseDto(entity);
    }
}
