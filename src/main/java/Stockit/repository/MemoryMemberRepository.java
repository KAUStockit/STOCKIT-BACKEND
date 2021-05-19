package Stockit.repository;

import Stockit.domain.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryMemberRepository implements MemberRepository {

    // 동시성 문제 고려로 AtmoicLong, ConcurrentHashMap 사용
    Map<Long, Member> store = new ConcurrentHashMap<>();
    AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Member> findByNickname(String nickname) {
        return store.values().stream()
                .filter(member -> member.getNickname().equals(nickname))
                .findAny();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findAny();
    }

    @Override
    public Member save(Member member) {
        member.setId(sequence.incrementAndGet());
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    //테스트 용도
    @Override
    public void clearStore() {
        store.clear();
    }
}
