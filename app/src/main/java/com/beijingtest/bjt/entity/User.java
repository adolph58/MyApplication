
package com.beijingtest.bjt.entity;

import com.beijingtest.bjt.util.FilePathUtils;
import com.beijingtest.bjt.util.GlobalConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class User implements Serializable  {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private byte[] avatar;
    private String avatarUrl;
    private String nickname;
    private String gender;
    private String phoneNumber;
    private String signature;
    private String registerDate;
    private String token;
    private String tokenDate;

    public User() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenDate() {
        return tokenDate;
    }

    public void setTokenDate(String tokenDate) {
        this.tokenDate = tokenDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", nickname='" + nickname + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", signature='" + signature + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", token='" + token + '\'' +
                ", tokenDate='" + tokenDate + '\'' +
                '}';
    }

    /**
     * 从序列化的文件中读取用户信息
     *
     * @return
     */
    public static User getCurrentUser() {
        try {
            File file = new File(FilePathUtils.getDiskFilesDir(), GlobalConsts.USER_INFO_NAME);
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            User user = (User)ois.readObject();
            if (user == null) {
                return new User();
            }
            return user;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new User();
    }

    /**
     * 持久化到文件中 下次打开应用用户信息依然存在
     */
    public static void saveUser(User user) {
        try {
            File file = new File(FilePathUtils.getDiskFilesDir(), GlobalConsts.USER_INFO_NAME);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(user);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}