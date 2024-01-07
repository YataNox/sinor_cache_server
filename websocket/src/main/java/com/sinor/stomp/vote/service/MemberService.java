package com.sinor.stomp.vote.service;

import com.sinor.stomp.vote.common.AbstractCrudService;
import com.sinor.stomp.vote.model.dto.request.MemberRequestDto;
import com.sinor.stomp.vote.model.dto.response.MemberResponseDto;
import com.sinor.stomp.vote.model.entity.Member;
import com.sinor.stomp.vote.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends
        AbstractCrudService<MemberResponseDto, MemberRequestDto, MemberRepository, Member, Long> {

    @Autowired
    protected MemberService(MemberRepository repository) {
        super(repository);
    }

    @Override
    protected Member fromRequestDtoToEntity(MemberRequestDto memberRequestDto) {
        return Member.builder().name(memberRequestDto.name()).profile(memberRequestDto.profile()).build();
    }

    @Override
    protected MemberResponseDto fromEntitytoResponseDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .profile(member.getProfile())
                .build();
    }

    @Override
    public MemberResponseDto updateObject(Long id, MemberRequestDto memberRequestDto) {
        Member entity = repository.findById(id).orElseThrow();
        entity.setProfile(memberRequestDto.profile());
        entity.setName(memberRequestDto.name());
        return fromEntitytoResponseDto(repository.save(entity));
    }

}