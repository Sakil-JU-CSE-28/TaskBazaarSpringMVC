package service;

import dao.PostDao;
import dto.PostDto;
import exception.DbException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Post;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PostService {

    private final PostDao postDao;
    private final ModelMapper modelMapper;

    public List<PostDto> getAll() throws RuntimeException {
        return postDao.getAll().stream().map(
                post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList()
        );
    }

    public Optional<PostDto> getById(Long id) throws RuntimeException {
        Post post = postDao.getById(id);
        return Optional.of((modelMapper.map(post, PostDto.class)));
    }

    public void save(PostDto postDto) throws DbException {
        postDao.save(modelMapper.map(postDto, Post.class));
    }

    public void update(PostDto postDto) throws DbException {
        try {
            postDao.update(modelMapper.map(postDto, Post.class));
        } catch (Exception e) {
            log.error("Error in postService update : {}", e.getMessage(), e);
            throw new DbException(e.getMessage());
        }
    }

    public boolean isOwner(Long postId, String username) throws DbException {
        try {
            Post post = postDao.getById(postId);
            return post.getAuthor().equals(username);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new DbException(e.getMessage());
        }
    }

    public void deleteById(Long id) throws DbException {
        try {
            postDao.delete(id);
        } catch (Exception e) {
            log.error("Error in deleting post: {} ", e.getMessage(), e);
            throw new DbException(e.getMessage());
        }
    }
}