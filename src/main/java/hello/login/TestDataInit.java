package hello.login;

import hello.login.domain.item.Item;
import hello.login.domain.item.ItemRepository;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));

        Member memberA = new Member();
        memberA.setLoginId("testA");
        memberA.setName("테스트A");
        memberA.setPassword("testa!");

        Member memberB = new Member();
        memberB.setLoginId("testB");
        memberB.setName("테스트B");
        memberB.setPassword("testb!");

        memberRepository.save(memberA);
        memberRepository.save(memberB);


    }

}