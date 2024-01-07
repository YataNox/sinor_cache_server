package com.sinor.stomp.vote.controller;

import com.sinor.stomp.vote.common.BaseCrudController;
import com.sinor.stomp.vote.model.dto.request.VoteLogRequestDto;
import com.sinor.stomp.vote.model.dto.response.VoteLogResponseDto;
import com.sinor.stomp.vote.service.VoteLogService;
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
@RequestMapping(path = "/vote/log")
public class VoteLogController implements BaseCrudController<VoteLogResponseDto, VoteLogRequestDto, Long> {

    private final VoteLogService voteLogService;

    @Autowired
    public VoteLogController(VoteLogService voteLogService) {
        this.voteLogService = voteLogService;
    }

    @Override
    @PostMapping
    public ResponseEntity<VoteLogResponseDto> createObject(
            @RequestBody VoteLogRequestDto requestDto
    ) {
        return ResponseEntity.ok(voteLogService.createObject(requestDto));
    }


    @Override
    @GetMapping("/{log_id}")
    public ResponseEntity<VoteLogResponseDto> readObject(
            @PathVariable(value = "log_id") Long id
    ) {
        return ResponseEntity.ok(voteLogService.readObject(id));
    }


    @Override
    @PutMapping("/{log_id}")
    public ResponseEntity<VoteLogResponseDto> updateObject(
            @PathVariable(value = "log_id") Long id,
            @RequestBody VoteLogRequestDto voteLogRequestDto
    ) {
        return ResponseEntity.ok(voteLogService.updateObject(id, voteLogRequestDto));
    }

    @Override
    @DeleteMapping("/{log_id}")
    public ResponseEntity<VoteLogResponseDto> deleteObject(
            @PathVariable(value = "log_id") Long id
    ) {
        return ResponseEntity.ok(voteLogService.deleteObject(id));
    }
}
