package security.com.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import security.com.securityjwt.dto.JoinRequestDTO;
import security.com.securityjwt.service.MemberService;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinRequestDTO joinRequestDTO) {
        memberService.joinProcess(joinRequestDTO);
        return "ok";
    }
}
