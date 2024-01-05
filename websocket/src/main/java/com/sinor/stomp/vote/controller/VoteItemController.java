package com.sinor.stomp.vote.controller;

import com.sinor.stomp.vote.common.BaseCrudController;
import com.sinor.stomp.vote.model.dto.request.VoteItemRequestDto;
import com.sinor.stomp.vote.model.dto.response.VoteItemResponseDto;
import com.sinor.stomp.vote.service.VoteItemService;
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
@RequestMapping(path = "/vote/item")
public class VoteItemController implements
        BaseCrudController<VoteItemResponseDto, VoteItemRequestDto, Long> {

    private final VoteItemService voteItemService;

    @Autowired
    public VoteItemController(VoteItemService voteItemService) {
        this.voteItemService = voteItemService;
    }

    @Override
    @PostMapping
    public ResponseEntity<VoteItemResponseDto> createObject(
            @RequestBody VoteItemRequestDto requestDto
    ) {
        return ResponseEntity.ok(voteItemService.createObject(requestDto));
    }

    @Override
    @GetMapping("/{candidate_id}")
    public ResponseEntity<VoteItemResponseDto> readObject(
            @PathVariable(value = "candidate_id") Long id
    ) {
        return ResponseEntity.ok(voteItemService.readObject(id));
    }


    @Override
    @PutMapping("/{candidate_id}")
    public ResponseEntity<VoteItemResponseDto> updateObject(
            @PathVariable(value = "candidate_id") Long id,
            @RequestBody VoteItemRequestDto voteItemRequestDto
    ) {
        return ResponseEntity.ok(voteItemService.updateObject(id, voteItemRequestDto));
    }

    @Override
    @DeleteMapping("/{candidate_id}")
    public ResponseEntity<VoteItemResponseDto> deleteObject(
            @PathVariable(value = "candidate_id") Long id
    ) {
        return ResponseEntity.ok(voteItemService.deleteObject(id));
    }

}
