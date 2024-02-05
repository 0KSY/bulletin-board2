package com.solo.bulletinboard2.member.service;

import com.solo.bulletinboard2.exception.BusinessLogicException;
import com.solo.bulletinboard2.exception.ExceptionCode;
import com.solo.bulletinboard2.member.entity.Member;
import com.solo.bulletinboard2.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void verifyExistsEmail(String email){
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        if(optionalMember.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    public Member findVerifiedMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        Member foundMember = optionalMember
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return foundMember;
    }

    public Member createMember(Member member){
        verifyExistsEmail(member.getEmail());
        return memberRepository.save(member);
    }

    public Member updateMember(Member member){
        Member foundMember = findVerifiedMember(member.getMemberId());

        Optional.ofNullable(member.getNickname())
                .ifPresent(nickname -> foundMember.setNickname(nickname));

        return memberRepository.save(foundMember);
    }

    public Member findMember(long memberId){
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size){
        return memberRepository.findAll(
                PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId){
        Member foundMember = findVerifiedMember(memberId);
        memberRepository.delete(foundMember);
    }
}
