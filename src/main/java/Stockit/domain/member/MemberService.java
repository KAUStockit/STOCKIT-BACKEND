package Stockit.domain.member;

import Stockit.interfaces.member.dto.LoginRequest;
import Stockit.domain.member.dto.MemberInfo;
import Stockit.interfaces.member.dto.MemberJoinRequest;
import Stockit.domain.member.dto.RankingInfo;

import java.util.List;

public interface MemberService {

    //단일 회원 조회
    MemberInfo getMember(Long memberIdx);

    //전체 회원 조회
    List<MemberInfo> getAllMembers();

    //닉네임 중복 검사
    boolean isDuplicatedNickname(String nickname);

    //이메일 중복 검사
    boolean isDuplicatedEmail(String email);

    //랭킹 조회
    List<RankingInfo> getRankingList();

    //로그인시 회원 정보 불러오기
    MemberInfo login(LoginRequest loginRequest);

    //회원 가입
    Long join(MemberJoinRequest form);
}
