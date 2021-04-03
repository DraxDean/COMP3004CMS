package com.COMP3004CMS.cms.Service;

import com.COMP3004CMS.cms.Model.User;
import com.COMP3004CMS.cms.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;


    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findUserByUserid(String userid){
        return userRepository.findUserByUserid(userid);
    }

    public void deleteById(String id){
        userRepository.deleteById(id);
    }

    public void approveUserById(String id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            //no user `found`
        }else {
            User temp = user.get();
            if (temp.getRoles().equals("STUDENT_PENDING")) {
                temp.setRoles("STUDENT");
                ArrayList<String> a = new ArrayList<>();
                a.add("Welcome! Your request has been approved and your Carleton student account has been created.");
                temp.setAnnouncements(a);
            }
            if (temp.getRoles().equals("PROFESSOR_PENDING")) {
                temp.setRoles("PROFESSOR");
                ArrayList<String> a = new ArrayList<>();
                a.add("Welcome! Your request has been approved and your Carleton professor account has been created.");
                temp.setAnnouncements(a);
            }
            userRepository.save(temp);
        }
    }

    public User findUserById(String id){
        return userRepository.findUserById(id);
    }

    public List<User> findAllByRoles(String roles){
        return userRepository.findAllByRoles(roles);
    }

    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    public void update(User user) {
        userRepository.save(user);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            //set login username, password and authorities
            List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(user.getRoles()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

}
