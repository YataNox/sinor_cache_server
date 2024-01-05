package com.sinor.stomp.vote.controller;

import com.sinor.stomp.vote.common.BaseCrudController;
import com.sinor.stomp.vote.model.dto.request.VoteRequestDto;
import com.sinor.stomp.vote.model.dto.response.VoteResponseDto;
import com.sinor.stomp.vote.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/vote")
public class VoteController implements BaseCrudController<VoteResponseDto, VoteRequestDto, Long> {

    private final VoteService voteService;


    @Autowired
    public VoteController(
            VoteService voteService) {
        this.voteService = voteService;
    }


    @Override
    @PostMapping
    public ResponseEntity<VoteResponseDto> createObject(
            @RequestBody VoteRequestDto requestDto
    ) {
        return ResponseEntity.ok(voteService.createObject(requestDto));
    }

    @Override
    @GetMapping("/{vote_id}")
    public ResponseEntity<VoteResponseDto> readObject(
            @PathVariable(name = "vote_id") Long id
    ) {
        return ResponseEntity.ok(voteService.readObject(id));
    }

    @Override
    @PutMapping("/{vote_id}")
    public ResponseEntity<VoteResponseDto> updateObject(
            @PathVariable(name = "vote_id") Long id,
            @RequestBody VoteRequestDto voteRequestDto
    ) {
        return ResponseEntity.ok(voteService.updateObject(id, voteRequestDto));
    }

    @Override
    @DeleteMapping("/{vote_id}")
    public ResponseEntity<VoteResponseDto> deleteObject(
            @PathVariable(name = "vote_id") Long id
    ) {
        return ResponseEntity.ok(voteService.deleteObject(id));
    }

}
