package Stockit.domain.member.dto;

import Stockit.domain.member.Member;
import Stockit.interfaces.member.dto.AccountStockInfo;
import Stockit.interfaces.order.dto.OrderInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class MemberInfo {
    private final Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private final LocalDateTime createdTime;
    private final LocalDateTime lastModifiedDate;
    private final String role;
    private final Double earningRate;
    private final Integer balance;
    private final Integer beforeBalance;
    private final List<OrderInfo> orders;
    private final List<AccountStockInfo> stocks;
    private final String token;

    public MemberInfo(Member m, String token) {
        this(m.getId(), m.getName(), m.getNickname(), m.getEmail(), m.getCreatedTime(), m.getLastModifiedDate(),
                m.getRole().name(), m.getAccount().getEarningRate(), m.getAccount().getBalance(),
                m.getAccount().getBeforeBalance(), m.getOrders().stream().map(OrderInfo::new).collect(Collectors.toList()),
                m.getAccount().getAccountStocks().stream().map(AccountStockInfo::new).collect(Collectors.toList()), token);
    }

    public MemberInfo(Member m) {
        this(m.getId(), m.getName(), m.getNickname(), m.getEmail(), m.getCreatedTime(), m.getLastModifiedDate(),
                m.getRole().name(), m.getAccount().getEarningRate(), m.getAccount().getBalance(),
                m.getAccount().getBeforeBalance(), m.getOrders().stream().map(OrderInfo::new).collect(Collectors.toList()),
                m.getAccount().getAccountStocks().stream().map(AccountStockInfo::new).collect(Collectors.toList()), null);
    }
}
