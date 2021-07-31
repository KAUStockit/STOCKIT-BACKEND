package Stockit.member.service;

import Stockit.member.domain.Member;
import Stockit.member.dto.RankDto;
import Stockit.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) //읽기전용
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional //수정 가능
    public Long join(Member member) {
        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getIdx();
    }

    //중복 회원 검증
    public void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findAllByEmail(member.getEmail());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체 회원 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAllByOrderByNicknameDesc();
    }

    //단일 회원 조회
    public Member findMemberByIdx(Long idx) {
        return memberRepository.findById(idx).orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다."));
    }

    //랭킹 조회
    public List<RankDto> getRankList(){
        List<Member> m = memberRepository.findAllByOrderByEarningRateDesc();
        List<RankDto> rankDtos = new ArrayList<>();
        for(Member one_member: m){
            String email = one_member.getEmail();
            String nickname = one_member.getNickname();
            double earningRate = one_member.getEarningRate();
            rankDtos.add(new RankDto(email,nickname,earningRate));
            System.out.println(email + nickname + earningRate);
        }
        return rankDtos;
    }

    //수익률 업데이트
    public void updateEarningRate(){
        //logic1 : member에서 balance를 가져옴. 그 다음에 balance와 이전 금액을 빼고 백분률로 나눠줌
        List<Member> member_list = memberRepository.findAll();
        for(Member m : member_list){
            double curBalance = (double) m.getBalance();
            double befBalance = (double) m.getBeforeBalance();
            double curEarningRate = curBalance - befBalance / 100;

            //logic2 : 수정된 earning_rate를 해당 유저 earning_rate로 업데이트
        }
    }

    //닉네임 중복 검사
    public boolean validateDuplicatedNickname(String nickname) {
        List<Member> members = memberRepository.findAllByNickname(nickname);
        return members.size() == 0;
    }

    public boolean validateDuplicatedEmail(String email) {
        List<Member> members = memberRepository.findAllByEmail(email);
        return members.size() == 0;
    }

//    public Member loadUserByEmail(String email) throws UsernameNotFoundException {
//        return memberRepository.findByMemberId(username)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//    }
}
