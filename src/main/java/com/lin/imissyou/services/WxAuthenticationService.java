package com.lin.imissyou.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lin.imissyou.exception.http.ParameterException;
import com.lin.imissyou.model.User;
import com.lin.imissyou.repository.UserRepository;
import com.lin.imissyou.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class WxAuthenticationService {

    @Value("${wx.code2session}")
    private String code2SessionUrl;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.appsecret}")
    private String appsecret;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository userRepository;


    public String code2Session(String code) {
        String url = MessageFormat.format(this.code2SessionUrl,this.appid, this.appsecret,code);
        RestTemplate rest = new RestTemplate();
        Map<String, Object> session = new HashMap<>();
        String sessionText = rest.getForObject(url, String.class);
        try {
            session = mapper.readValue(sessionText, Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return this.registerUser(session);
    }

    private String registerUser(Map<String, Object> session) {
        String openId = (String) session.get("openid");
        if (openId == null) {
            throw new ParameterException(20004);
        }
        Optional<User> userOptional = this.userRepository.findByOpenid(openId);
        if (userOptional.isPresent()) {
            return JwtToken.makeToken(userOptional.get().getId());
        }
        User user = User.builder().openid(openId).build();
        userRepository.save(user);
        return JwtToken.makeToken(user.getId());
    }

}
