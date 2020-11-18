package me.dongqinglin.zuul_gateway.BController;


import me.dongqinglin.zuul_gateway.ZBean.*;
import me.dongqinglin.zuul_gateway.DFileApi.MyFileSystem;
import me.dongqinglin.zuul_gateway.DFileApi.MyFileSystemImpl;
import me.dongqinglin.zuul_gateway.ZEntity.User;
import me.dongqinglin.zuul_gateway.CService.FileService;
import me.dongqinglin.zuul_gateway.CService.UserService;
import me.dongqinglin.zuul_gateway.CServiceImpl.MyUserDetailService;
import me.dongqinglin.zuul_gateway.CServiceImplAlgorithm.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.Optional;

@RestController
public class MainController {

    @Value("${static-root}")
    private String STATIC_ROOT;

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailService userDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @GetMapping("/")
    public RedirectView home(){
        RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        redirectTarget.setUrl("/index.html");
        return redirectTarget;
    }


    @PostMapping("api/signIn")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest signInRequest) throws Exception{
        SignInResponse result = null;
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
            );
        }catch (BadCredentialsException e){
            throw new Exception("incoorect username or passWord", e);
        }
        final UserDetails userDetails = userDetailService
                .loadUserByUsername(signInRequest.getUsername());
        User user = null;
        Optional<User> userOptional = userService.findByUsername(signInRequest.getUsername());
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        if (user != null) {
            final String jwt = jwtUtil.generateToken(userDetails);
            result = new SignInResponse(jwt, user);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("api/signUp")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest ) throws Exception{
        SignUpResponse result;
        String username = signUpRequest.getUsername();
        String password = signUpRequest.getPassword();
        String email = signUpRequest.getEmail();
        Optional<User> user = userService.findByUsername(username);
        if(user.isPresent()){
            result = new SignUpResponse(false, "该用户已存在");
        }else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setEnable(true);
            newUser.setRoles("ROLE_USER");
            newUser.setLevel(1);
            userService.save(newUser);
            result = new SignUpResponse(true, "注册成功");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("api/requestCode")
    public ResponseEntity<?> requestCode(@RequestBody String email ) throws Exception{
        MessageDefined result;
        MyFileSystem system = new MyFileSystemImpl();
        String code = system.view(STATIC_ROOT + "password/code.flina");
        result = new MessageDefined(200, "success", code);
        return ResponseEntity.ok(result);
    }

    @PostMapping("api/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request ) throws Exception{
        MessageDefined result;
        System.out.println(request);
        String username = request.getUsername();
        String password = request.getPassword();
        String email = request.getEmail();

        MyFileSystem system = new MyFileSystemImpl();
        String code = system.view(STATIC_ROOT + "password/code.flina").trim();
        result = new MessageDefined(200,"error","该用户不存在");
        User user = null;
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        System.out.println(request.getCode().equals(code));
        if (user != null && request.getCode().equals(code) && request.getEmail().equals(user.getEmail()) ) {
            User newUser = new User();
            user.setLastPassword(user.getPassword());
            user.setPassword(password);
            user.setLastPasswordModifyTime(new Date());
            userService.save(newUser);
            result = new MessageDefined(200, "success", "修改密码成功");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("api/upload")
    ResponseEntity upload(MultipartFile[] files, String targetPath){
        // System.out.println("post");
        MessageDefined result = null;
        if(targetPath == null) targetPath = "";
        targetPath = fileService.getStaticRootStr() + targetPath;
        try {
            fileService.handleUpload(files, targetPath);
            result = new MessageDefined(200, "success", "传输完成");
        } catch (Exception e) {
            result = new MessageDefined(500, "error", e.toString());
        }
        System.out.println(result);
        return ResponseEntity.ok(result);
    }
}
