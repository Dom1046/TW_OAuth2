package security.com.securityjwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import security.com.securityjwt.dto.JoinRequestDTO;
import security.com.securityjwt.jwt.auth.CustomMemberDetails;
import security.com.securityjwt.service.MemberService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinRequestDTO joinRequestDTO) {
        memberService.joinProcess(joinRequestDTO);
        return "ok";
    }

    @GetMapping("/email")
    public ResponseEntity<String> getEmail(Authentication authentication) {
        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        String email = userDetails.getEmail();

        return ResponseEntity.ok(email);
    }
}
