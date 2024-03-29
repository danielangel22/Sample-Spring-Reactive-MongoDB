package com.example.spring.mogo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring.mogo.documents.Message;
import com.example.spring.mogo.repository.MessageRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public Mono<Message> save(Message message) {
		return this.messageRepository.save(message);
	}

	@Override
	public Mono<Message> delete(String id) {
		return this.messageRepository.findById(id)
				.flatMap(p -> this.messageRepository.deleteById(p.getId()).thenReturn(p));

	}

	@Override
	public Mono<Message> update(String id, Message message) {
		return this.messageRepository.findById(id).flatMap(message1 -> {
			message.setId(id);
			return save(message);
		}).switchIfEmpty(Mono.empty());
	}

	@Override
	public Flux<Message> findByThreadId(String threadId) {
		return this.messageRepository.findByThreadId(threadId);
	}

	@Override
	public Flux<Message> findAll() {
		return this.messageRepository.findAll();
	}

	@Override
	public Mono<Message> findById(String id) {
		return this.messageRepository.findById(id);
	}
}
