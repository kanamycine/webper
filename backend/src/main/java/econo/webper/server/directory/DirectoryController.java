package econo.webper.server.directory;

import econo.webper.server.Member.Member;
import econo.webper.server.Member.MemberDetails;
import econo.webper.server.Member.MemberRepository;
import econo.webper.server.Member.MemberService;
import econo.webper.server.utils.ExceptionMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Api(value = "Directory CRUD", tags = {"Directory CRUD - 담당자 : 배종진"})
@RestController
public class DirectoryController {

    private final DirectoryService directoryService;

    private final MemberService memberService;


    public DirectoryController(DirectoryService directoryService, MemberService memberService, MemberRepository memberRepository) {
        this.directoryService = directoryService;
        this.memberService = memberService;
    }

    @GetMapping("/Directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity getDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody DirectoryDTO directoryDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.getDirectory(savedMember, directoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_GET_DIRECTORY);
        }
        return ResponseEntity.ok(directory);
    }

    @PostMapping("/Directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity createDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody CreateDirectoryDTO createDirectoryDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.createDirectory(savedMember, createDirectoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_CREATE_DIRECTORY);
        }
        return ResponseEntity.ok(savedMember);
    }

    @PutMapping("/Directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity updateDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody DirectoryDTO directoryDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        Directory directory = directoryService.updateDirectory(savedMember, directoryDTO);
        if (directory == null) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_UPDATE_DIRECTORY);
        }
        return ResponseEntity.ok(directory);
    }

    @DeleteMapping("/Directory")
    @ApiImplicitParam(name = "Authorization", value = "Access_Token", required = true, paramType = "header")
    public ResponseEntity deleteDirectory(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody DirectoryDTO directoryDTO) {
        Member savedMember = memberService.findMemberByEmail(memberDetails.getMember().getEmail());
        boolean isDelete = directoryService.deleteDirectory(savedMember, directoryDTO);
        if (isDelete == false) {
            return ResponseEntity.badRequest().body(ExceptionMessage.NOT_DELETE_DIRECTORY);
        }
        return ResponseEntity.ok(savedMember);
    }
}

