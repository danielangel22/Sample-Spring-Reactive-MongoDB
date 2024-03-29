package com.example.spring.mogo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.mogo.documents.Message;
import com.example.spring.mogo.service.MessageService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MessageResource {

	@Autowired
	private MessageService messageService;

	@PostMapping("/messages")
	@ResponseStatus(HttpStatus.ACCEPTED)
	private Mono<Message> save(@RequestBody Message message) {
		return this.messageService.save(message);
	}

	@DeleteMapping("/messages/{id}")
	private Mono<ResponseEntity<Message>> delete(@PathVariable("id") String id) {
		return this.messageService.delete(id).flatMap(message -> Mono.just(ResponseEntity.ok(message)))
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

	}

	@PutMapping("/messages/{id}")
	private Mono<ResponseEntity<Message>> update(@PathVariable("id") String id, @RequestBody Message message) {
		return this.messageService.update(id, message).flatMap(message1 -> Mono.just(ResponseEntity.ok(message1)))
				.switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

	}

	@GetMapping("/messages/thread/{threadId}")
	private Flux<Message> findAllByThreadId(@PathVariable("threadId") String threadId) {
		return this.messageService.findByThreadId(threadId);
	}

	@GetMapping(value = "/messages")
	private Flux<Message> findAll() {
		return this.messageService.findAll();
	}

}
