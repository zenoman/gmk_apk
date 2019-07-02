package project.com.gmklabel.Model;

import java.util.List;

import project.com.gmklabel.Konstruktor.User_info;


public class Respon_User {
    private List<User_info> data;
    private  String status,msg,error;

    public List<User_info> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getError() {
        return error;
    }
}
