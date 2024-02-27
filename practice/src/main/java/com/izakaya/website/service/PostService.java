package com.izakaya.website.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.izakaya.website.entity.PostEntity;
import com.izakaya.website.entity.UserEntity;
import com.izakaya.website.model.Post;
import com.izakaya.website.model.User;
import com.izakaya.website.repository.PostRepository;
import com.izakaya.website.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public void create(Post post) {
			Optional<UserEntity> byId = userRepository.findById(post.getUser().getId());
			
			if(byId.isPresent()) {
				UserEntity userEntity = byId.get();
				
				PostEntity postEntity = PostEntity.builder()
						.id(post.getId())
						.title(post.getTitle())
						.content(post.getContent())
						.createTime(post.getCreateTime())
						.user(userEntity)
						.build();
				
		    	postRepository.save(postEntity);
			}
		
		    
	}

	public List<Post> findAll() {
		List<PostEntity> postEntityList = postRepository.findAll();
		List<Post> postList = new ArrayList<>();
		for(PostEntity postEntity : postEntityList) {
			postList.add(postEntity.toPost());
		}
		return postList;
	}

}
