package Stockit.interfaces.member.dto;

import Stockit.domain.member.Member;
import Stockit.interfaces.order.dto.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class MemberInfo {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedDate;
    private String role;
    private Double earningRate;
    private Integer balance;
    private Integer beforeBalance;
    private List<OrderInfo> orders;
    private List<AccountStockInfo> stocks;
    private String token;

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
                m.getAccount().getAccountStocks().stream().map(AccountStockInfo::new).collect(Collectors.toList()), "");
    }
}
